package ch.unige.idsi.cultapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.unige.idsi.cultapp.database.Database;
import ch.unige.idsi.cultapp.listener.OnApiResult;
import ch.unige.idsi.cultapp.model.Place;
import ch.unige.idsi.cultapp.model.Place.Infrastructure;
import ch.unige.idsi.cultapp.util.Constants;
import ch.unige.idsi.cultapp.util.GridAdapter;
import ch.unige.idsi.cultapp.thread.ApiThread;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
        ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    private enum SORT {
        SORT_AZ, SORT_FAVORITES
    };

    private SORT currentSort = SORT.SORT_AZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar to show a dropdown list.
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<>(actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, new String[]{
                        getString(R.string.title_section_museums),
                        getString(R.string.title_section_cinemas)}),
                this);
    }

    /*
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
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        PlaceholderFragment currentFragment = (PlaceholderFragment) this
                .getFragmentManager().findFragmentById(R.id.container);

        int id = item.getItemId();
        if (id == R.id.action_sort) {
            if (currentSort.equals(SORT.SORT_AZ)) {
                currentSort = SORT.SORT_FAVORITES;
                item.setIcon(R.drawable.ic_menu_sort_alphabetically);
                item.setTitle(getString(R.string.action_az));
            } else {
                currentSort = SORT.SORT_AZ;
                item.setIcon(R.drawable.ic_menu_star);
                item.setTitle(getString(R.string.action_favorites));
            }
            currentFragment.sortData(currentSort);

        } else if (id == R.id.action_refresh) {

            currentFragment.setData(currentFragment.getDataType(), true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.

        Infrastructure infrastructure = Infrastructure.MUSEUM;
        if (position == 1) {
            infrastructure = Infrastructure.CINEMA;
        }
        PlaceholderFragment currentFragment = (PlaceholderFragment) this
                .getFragmentManager().findFragmentById(R.id.container);

        if (currentFragment != null) {
            currentFragment.setData(infrastructure, false);
            currentFragment.sortData(currentSort);
        } else {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(0))
                    .commit();
        }

        return true;
    }

    public SORT getCurrentSort() {
        return this.currentSort;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements
            OnItemClickListener, OnApiResult, OnClickListener {

        ArrayList<Place> gridData = new ArrayList<Place>();

        private Infrastructure currentType;
        private GridAdapter gridAdapter;

        private ProgressBar progressRequest;

        private ApiThread apiThread;

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);

            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);

            GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
            progressRequest = (ProgressBar) rootView.findViewById(R.id.progressRequest);

            gridAdapter = new GridAdapter(this.getActivity(),
                    R.layout.row_grid, gridData);

            gridview.setAdapter(gridAdapter);
            gridview.setOnItemClickListener(this);

            gridAdapter.registerCheckListener(this);


            // By default, show museums
            this.setData(Infrastructure.MUSEUM, false);

            return rootView;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            if (this.apiThread != null) {
                this.apiThread.cancel(true);
            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            Place p = this.gridAdapter.getItem(position);

            Intent i = new Intent(this.getActivity(), PlaceActivity.class);
            i.putExtra(Constants.INTENT_OBJECT_ID, p.getId());
            i.putExtra(Constants.INTENT_OBJECT_NAME, p.getName());
            i.putExtra(Constants.INTENT_OBJECT_TYPE, p.getInfrastructure().toString());

            this.getActivity().startActivity(i);
        }

        public Infrastructure getDataType() {
            return this.currentType;
        }

        public void setData(Infrastructure type, boolean forceRefresh) {

            this.currentType = type;

            Database db = new Database(this.getActivity());

            this.gridData = db.getAll(type);

            // If the database is empty or the user forces the refresh, we retrieve data from the API
            if (this.gridData.isEmpty() || forceRefresh) {

                String url = "";
                if (type.equals(Infrastructure.MUSEUM)) {
                    url = Constants.MUSEUM_API;
                } else if (type.equals(Infrastructure.CINEMA)) {
                    url = Constants.CINEMA_API;
                }

                this.progressRequest.setVisibility(View.VISIBLE);
                this.apiThread = new ApiThread(url, this);
                this.apiThread.execute();

            } else {
                this.gridAdapter.clear();

                for (Place p : this.gridData) {
                    if (p.getInfrastructure().equals(type)) {
                        this.gridAdapter.add(p);
                    }
                }
                this.gridAdapter.notifyDataSetChanged();
            }
        }

        private String optString(JSONObject json, String key)
                throws JSONException {

            Object value = json.get(key);

            if (json.isNull(key)) {
                return "";
            } else {
                return (String) value;
            }
        }

        private void sortData(SORT sortType) {

            if (sortType.equals(SORT.SORT_AZ)) {
                this.gridAdapter.sortAscendants();
            } else {
                this.gridAdapter.sortFavorites();
            }
        }

        @Override
        public void processFinish(JSONObject response) throws JSONException {

            this.progressRequest.setVisibility(View.GONE);

            this.apiThread = null;

            if (response != null) {

                JSONArray array = (JSONArray) response.get("features");

                int i = 0;
                int length = array.length();

                if (length > 0) {
                    this.gridAdapter.clear();
                    this.gridAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this.getActivity(), getString(R.string.no_internet_access), Toast.LENGTH_LONG).show();
                }

                for (; i < length; i++) {

                    JSONObject obj = (JSONObject) array.get(i);
                    JSONObject attributes = (JSONObject) obj.get("attributes");

                    int id = (int) attributes.get("ID_INFRASTRUCTURE");

                    String name = this.optString(attributes, "NOM");
                    String contact = this.optString(attributes, "CONTACT");
                    String town = this.optString(attributes, "COMMUNE");
                    String address = this.optString(attributes, "ADRESSE");
                    String url = this.optString(attributes, "LIEN_WWW");

                    Database db = new Database(this.getActivity());
                    Place place = new Place(id, name, contact, town, address,
                            url, this.currentType, 0, 0);

                    db.insertPlace(place);
                    this.gridAdapter.add(place);
                }
                this.gridAdapter.notifyDataSetChanged();

            } else {
                Toast.makeText(this.getActivity(), getString(R.string.no_internet_access), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onClick(View v) {

            CheckBox buttonView = (CheckBox) v;
            boolean isChecked = buttonView.isChecked();

            int uid = buttonView.getId();

            Database db = new Database(this.getActivity());
            db.setFavorite(uid, isChecked);

            List<Place> places = this.gridAdapter.getItems();

            int i = 0;
            int length = places.size();

            SORT currentSort = ((MainActivity) this.getActivity())
                    .getCurrentSort();

            for (; i < length; i++) {

                Place p = places.get(i);
                if (p.getId() == uid) {
                    p.setChecked(isChecked);
                    this.gridAdapter.notifyDataSetChanged();

                    if (currentSort.equals(SORT.SORT_FAVORITES)) {
                        this.sortData(currentSort);
                    }
                    break;
                }
            }
        }
    }
}