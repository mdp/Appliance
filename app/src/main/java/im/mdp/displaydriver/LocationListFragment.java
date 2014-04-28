package im.mdp.displaydriver;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


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
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(getActivity(), WebActivity.class);
        i.putExtra(WebActivity.URL_FIELD, mLocations.get(position).getUrl());
        startActivity(i);
    }
}
