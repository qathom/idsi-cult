package ch.unige.idsi.cultapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.unige.idsi.cultapp.database.Database;
import ch.unige.idsi.cultapp.model.Museum;
import ch.unige.idsi.cultapp.model.Place;
import ch.unige.idsi.cultapp.model.Place.Infrastructure;
import ch.unige.idsi.cultapp.util.Constants;
import ch.unige.idsi.cultapp.util.GridAdapter;
import ch.unige.idsi.cultapp.util.OnApiResult;
import ch.unige.idsi.cultapp.thread.ApiThread;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_section_museums),
								getString(R.string.title_section_cinemas)}), this);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_favorites) {
			Intent i = new Intent(this, FavoritesActivity.class);
			this.startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
		return true;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			OnItemClickListener, OnApiResult {
		
		ArrayList<Place> gridData = new ArrayList<Place>();
		
		private Infrastructure currentType;
		private GridAdapter gridAdapter;
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
		}

		public PlaceholderFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
			gridAdapter = new GridAdapter(this.getActivity(), R.layout.row_grid, gridData);
			
			gridview.setAdapter(gridAdapter);
			gridview.setOnItemClickListener(this);
			
			// By default, show museums
			this.setData(Infrastructure.MUSEUM);
			
			Intent i = new Intent(this.getActivity(), PlaceActivity.class);
			this.getActivity().startActivity(i);
			
			return rootView;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Toast.makeText(this.getActivity(), "" + position,
					Toast.LENGTH_SHORT).show();

		}
		
		private void setData(Infrastructure type) {

			this.currentType = type;
			
			Database db = new Database(this.getActivity());
			
			this.gridData = db.getAll(type);
			
			if(gridData.isEmpty()) {
				System.out.println("DB empty => make request");
				
				String url = "";
				if(type.equals(Infrastructure.MUSEUM)) {
					url = Constants.MUSEUM_API;
				} else if(type.equals(Infrastructure.CINEMA)) {
					url = Constants.CINEMA_API;
				}
				
				ApiThread at = new ApiThread(url, this);
				at.execute();
			} else {
				System.out.println("DB not empty => read db");

				gridAdapter.clear();
				
				for(Place p : this.gridData) {
					if(p.getInfrastructure().equals(type)) {
						gridAdapter.add(p);
					}
				}
				gridAdapter.notifyDataSetChanged();
			}
		}
		
		@Override
		public void processFinish(JSONObject response) throws JSONException {

			System.out.println("RESPONSE");
			System.out.println(response);
			
			if(response != null) {
				
				JSONArray array = (JSONArray) response.get("features");
				
				int i = 0;
				int length = array.length();
				
				for (; i < length; i++) {
		
					JSONObject obj = (JSONObject) array.get(i);
					JSONObject attributes = (JSONObject) obj.get("attributes");
		
					long id = (((Long) attributes.get("ID_INFRASTRUCTURE")).intValue());
					String name = (String) attributes.get("NOM");
					String contact = (String) attributes.get("CONTACT");
					String town = (String) attributes.get("COMMUNE");
					String address = (String) attributes.get("ADRESSE");
					String url = (String) attributes.get("LIEN_WWW");
					
					Database db = new Database(this.getActivity());
					Place place = new Place(id, name, contact, town, address, url, this.currentType, 0, 0);
					
					db.insertPlace(place);
					gridAdapter.add(place);
				}
				gridAdapter.notifyDataSetChanged();
			}
		}
	}
}
