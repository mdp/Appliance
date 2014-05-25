package im.mdp.displaydriver;

import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

import im.mdp.displaydriver.storage.Location;
import im.mdp.displaydriver.storage.LocationCollection;
import im.mdp.displaydriver.storage.LocationDummy;

public class LocationListFragment extends ListFragment {


    private static final String TAG = Derry.TAG + ":LocationListFragment";
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

        // mLocations = LocationDummy.create(getActivity()).getLocations();
        mLocations = LocationCollection.get(getActivity()).getLocations();
        mAdapter = new LocationItemView(mLocations);
        setHasOptionsMenu(true);
        setListAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "Resume!");
        ((ArrayAdapter)getListAdapter()).notifyDataSetChanged();
        super.onResume();
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "ActivityResult");
        ((LocationItemView)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), LocationActivity.class);
        startActivityForResult(intent, 0);
    }

    public class LocationItemView extends ArrayAdapter<Location> {

        private static final int LOCATION_ID = 0;

        public LocationItemView(ArrayList<Location> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.location_list_item, null);
            }
            // Configure the view for this Crime
            Location l = getItem(position);
            TextView titleTextView =
                    (TextView) convertView.findViewById(R.id.locationListItemText);
            titleTextView.setText(l.toString());
            ImageButton playBtn = (ImageButton) convertView.findViewById(R.id.locationListItemPlayBtn);
            playBtn.setTag(R.id.locationListItemPlayBtn, l);
            playBtn.setOnClickListener(playBtnListener);
            convertView.setTag(R.id.locationListItemPlayBtn, l);
            convertView.setOnClickListener(listItemListener);
            return convertView;
        }

        private View.OnClickListener listItemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location l = (Location) v.getTag(R.id.locationListItemPlayBtn);
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                Log.d(TAG, l.getUrl());
                intent.putExtra(LocationActivity.LocationFragment.LOCATION_ID, l.getId());
                startActivityForResult(intent, 0);
            }
        };

        private View.OnClickListener playBtnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location l = (Location) v.getTag(R.id.locationListItemPlayBtn);
                Log.d(TAG, "playbutton clicked " + l.getUrl());
                Intent i = new Intent(getActivity(), WebActivity.class);
                i.putExtra(WebActivity.URL_FIELD, l.getUrl());
                startActivity(i);
            }
        };
    }
}
