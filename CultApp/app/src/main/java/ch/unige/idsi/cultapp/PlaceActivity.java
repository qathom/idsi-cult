package ch.unige.idsi.cultapp;

import ch.unige.idsi.cultapp.database.Database;
import ch.unige.idsi.cultapp.model.Place;
import ch.unige.idsi.cultapp.util.Helper;
import ch.unige.idsi.cultapp.listener.OnApiResult;
import ch.unige.idsi.cultapp.listener.OnLocationListener;
import ch.unige.idsi.cultapp.model.Recommendation;
import ch.unige.idsi.cultapp.thread.ApiThread;
import ch.unige.idsi.cultapp.util.Constants;
import ch.unige.idsi.cultapp.util.ContextLocation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlaceActivity extends ActionBarActivity implements
		OnLocationListener, OnApiResult, AdapterView.OnItemClickListener {
	
	private int id;
	private String name;
	private Place.Infrastructure placeType;

	private GoogleMap googleMap;
	private Location userLocation;
	
	private ProgressBar progress;
	private ApiThread apiThread;

	private GridView gridView;
	private ArrayAdapter<String> gridAdapter;
	private ArrayList<Recommendation> gridData = new ArrayList<Recommendation>();
	private ArrayList<String> recommendationList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);
		
		Intent intent = getIntent();
		
		this.id = intent.getIntExtra(Constants.INTENT_OBJECT_ID, 0);
		this.name = intent.getStringExtra(Constants.INTENT_OBJECT_NAME);
		this.placeType = Place.Infrastructure.valueOf(intent.getStringExtra(Constants.INTENT_OBJECT_TYPE));

		this.progress = (ProgressBar) findViewById(R.id.progressDistance);
		
		// Set up the action bar to show a home button.
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(this.name);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		gridView = (GridView) findViewById(R.id.gridRecommendations);

		gridAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		gridView.setAdapter(gridAdapter);
		gridView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_place, menu);
		return true;
	}

    @Override
    public void onStart() {
        super.onStart();

		// Start the request directly after the window's creation
		this.fetchData();
    }

	/**
	 * Cancels the current thread if it exists
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();

		if (this.apiThread != null) {
			this.apiThread.cancel(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {

		int itemId = menuItem.getItemId();
		switch (itemId) {
			case android.R.id.home:

				this.finish();

				break;

			case R.id.action_refresh:

				this.fetchData();

				break;

		}
		return super.onOptionsItemSelected(menuItem);
	}

	/**
	 * Constructs the Info URL for the current Museum or Cinema ID to make the request to the API
	 * @return: the URL correctly formatted
	 */
	private String constructUrl() {

		// By default, we set the Museum-Info API
		String url = Constants.INFO_MUSEM_API;
		if(this.placeType != Place.Infrastructure.MUSEUM) {
			url = Constants.INFO_CINEMA_API;
		}

		if(!url.endsWith("/")) {
			url = url + "/";
		}

		return url + this.id + "/";
	}

	/**
	 * Executes a request if necessary to the API and updates the state of the ProgressBar (active)
	 */
	private void fetchData() {

		if(this.apiThread == null) {

			Database db = new Database(this);
			Place place = db.getPlace(this.id);

			this.setTextViews(place.getContact(), place.getTown(), place.getAddress());

			// If the location is known, set the GoogleMap
			if(place.getLatitude() != 0 && place.getLongitude() != 0) {
				this.initializeMap(place.getLatitude(), place.getLongitude());
			}

			// The execute the request to the API (update the place informations
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					progress.setVisibility(View.VISIBLE);
				}
			});

			this.apiThread = new ApiThread(constructUrl(), this);
			this.apiThread.execute();
		}
	}

	/**
	 * Creates the GoogleMap canvas on the top of the Activity
	 * This map is used to set the localisation of the cinema or the museum
	 *
	 * @param latitude
	 * @param longitude
	 */
	private void initializeMap(double latitude, double longitude) {

		if (googleMap == null) {

			MapFragment fragment = (MapFragment) this.getFragmentManager()
					.findFragmentById(R.id.map);
			googleMap = fragment.getMap();

			LatLng latlng = new LatLng(latitude, longitude);

			MarkerOptions marker = new MarkerOptions().position(latlng).title(
					this.name);

			googleMap.addMarker(marker);

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(latlng).zoom(16).build();

			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

			ContextLocation cl = new ContextLocation();
			cl.registerListener(this);

			userLocation = cl.getLocation(this);

			Location placeLocation = new Location("");
			placeLocation.setLatitude(latitude);
			placeLocation.setLongitude(longitude);

			this.calculateDistance(userLocation, placeLocation);
		}
	}

	/**
	 * Calculates the distance between the user's position and the cinmea|museum's localisation
	 * @param userLocation
	 * @param placeLocation
	 */
	private void calculateDistance(Location userLocation, Location placeLocation) {

		TextView textDistance = (TextView) findViewById(R.id.textDistance);

		if(userLocation != null && placeLocation != null) {

			float distanceInMeters = userLocation.distanceTo(placeLocation);
			textDistance.setText("Distance: " + distanceInMeters
					+ " m away");
		} else {
			textDistance.setText(getString(R.string.distance_title) + ": ?");
		}
	}

	/**
	 * Sets text to TextViews
	 * @param contact
	 * @param town
	 * @param address
	 */
	private void setTextViews(String contact, String town, String address) {

		TextView textContact = (TextView) findViewById(R.id.textContact);
		textContact.setText(contact);

		TextView textCity = (TextView) findViewById(R.id.textCity);
		textCity.setText(town);

		TextView textAddress = (TextView) findViewById(R.id.textAddress);
		textAddress.setText(address);
	}

	/**
	 * Called when we retrieve a more precised geolocalisation of the user (see: OnLocationListener)
	 * @param location
	 */
	@Override
	public void onLocationChanged(Location location) {
		this.userLocation = location;
		
		this.calculateDistance(userLocation, location);
	}

	/**
	 * Called when the ApiThread object finished a HTTP request to the API
	 * @param response
	 * @throws JSONException
	 */
	@Override
	public void processFinish(JSONObject response) {

		this.apiThread = null;

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progress.setVisibility(View.GONE);
			}
		});

		if (response != null) {

			try {
				String status = (String) response.get("status");

				if (status.equals("success")) {

					String placeUrl = (String) response.get("url");
					String contact = (String) response.get("contact");
					String town = (String) response.get("town");
					String address = (String) response.get("address");
					double latitude = (Double) response.get("latitude");
					double longitude = (Double) response.get("longitude");

					// Set text to TextViews
					this.setTextViews(contact, town, address);

					JSONArray recommendations = (JSONArray) response.get("recommendations");

					int i = 0;
					int length = recommendations.length();

					// Clear data in case of refresh
					this.gridData.clear();
					this.gridAdapter.clear();

					for (; i < length; i++) {
						JSONObject recommendation = (JSONObject) recommendations.get(i);
						String name = (String) recommendation.get("name");
						String url = (String) recommendation.get("url");

						this.gridData.add(new Recommendation(this.id, name, url));
						this.gridAdapter.add(name);
					}

					Database db = new Database(this);
					db.updatePlace(new Place(this.id, name, contact, town, address, placeUrl, this.placeType, latitude, longitude));

					if (this.gridData.size() > 0) {
						TextView textRecommendations = (TextView) findViewById(R.id.textRecommendations);
						textRecommendations.setText(getString(R.string.recommendations));
						this.gridAdapter.notifyDataSetChanged();
						Helper.correctGridSize(this.gridView, 1);
					} else {
					}

					try {
						initializeMap(latitude, longitude);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(this, getString(R.string.no_internet_access), Toast.LENGTH_LONG).show();
				}
			} catch(JSONException e) {
				e.printStackTrace();
				Toast.makeText(this, getString(R.string.no_internet_access), Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, getString(R.string.no_internet_access), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		Recommendation r = this.gridData.get(position);

		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(r.getUrl()));
		startActivity(i);
	}
}
