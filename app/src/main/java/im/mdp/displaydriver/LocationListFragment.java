package im.mdp.displaydriver;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.util.ArrayList;

import im.mdp.displaydriver.storage.Location;
import im.mdp.displaydriver.storage.LocationDummy;

public class LocationListFragment extends ListFragment {

    private ArrayList<Location> mLocations;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocationListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocations = LocationDummy.create(getActivity()).getLocations();
        mAdapter = new ArrayAdapter<Location>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, mLocations);
        setHasOptionsMenu(true);
        setListAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO: Notify data set changed
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_location_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_location:
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivityForResult(intent, 0);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(getActivity(), WebActivity.class);
        i.putExtra(WebActivity.URL_FIELD, mLocations.get(position).getUrl());
        startActivity(i);
    }
}
