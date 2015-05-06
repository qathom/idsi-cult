package ch.unige.idsi.cultapp;

import ch.unige.idsi.cultapp.util.ContextLocation;
import ch.unige.idsi.cultapp.util.OnLocationListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ActionBar;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceActivity extends Activity implements
		OnLocationListener {

	private GoogleMap googleMap;
	private Location userLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);

		// Set up the action bar to show a home button.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		
		actionBar.setTitle("Musee de l'histoire du OMG lol");

		try {
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		int itemId = item.getItemId();
		switch (itemId) {
		case android.R.id.home:
			
			this.finish();
			
			break;

		}

		return true;
	}

	private void initilizeMap() {

		if (googleMap == null) {

			MapFragment fragment = (MapFragment) this.getFragmentManager()
					.findFragmentById(R.id.map);
			googleMap = fragment.getMap();

			double latitude = 46.3831924;
			double longitude = 6.234856400000012;

			LatLng latlng = new LatLng(latitude, longitude);

			MarkerOptions marker = new MarkerOptions().position(latlng).title(
					"Hello Maps ");

			googleMap.addMarker(marker);

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(latlng).zoom(16).build();

			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

			ContextLocation cl = new ContextLocation();
			cl.registerListener(this);

			userLocation = cl.getLocation(this);

			System.out.println("Latitude: " + userLocation.getLatitude());
			System.out.println("Longitude: " + userLocation.getLongitude());

			Location placeLocation = new Location("");
			placeLocation.setLatitude(latitude);
			placeLocation.setLongitude(longitude);

			this.calculateDistance(userLocation, placeLocation);
		}
	}

	private void calculateDistance(Location userLocation, Location placeLocation) {

		final float distanceInMeters = userLocation.distanceTo(placeLocation);
		final ProgressBar progress = (ProgressBar) findViewById(R.id.progressDistance);
		final TextView textDistance = (TextView) findViewById(R.id.textDistance);
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (progress.isShown() || progress.isActivated()) {
					progress.setVisibility(View.GONE);
				}
				textDistance.setText("Distance: " + distanceInMeters
						+ " m away");
			}
		});
	}

	@Override
	public void onLocationChanged(Location location) {
		this.userLocation = location;
		this.calculateDistance(userLocation, location);
	}
}
