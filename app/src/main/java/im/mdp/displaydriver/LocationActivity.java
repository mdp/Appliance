package im.mdp.displaydriver;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import im.mdp.displaydriver.storage.Location;


public class LocationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("New Location");
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new LocationFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    public static class LocationFragment extends Fragment {

        private static final String TAG = Derry.TAG + ":LocationFragment";
        private static final String LOCATION_ID = "location_fragment.location_id";
        private Location mLocation;

        public static LocationFragment newInstance(UUID locationId) {
            Log.d(TAG, "newInstance!");
            Bundle args = new Bundle();
            args.putSerializable(LOCATION_ID, locationId);

            LocationFragment fragment = new LocationFragment();
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.location_fragment, container, false);
            Log.d(TAG, "WTF");
            Log.d(TAG, Integer.toString(R.layout.location_fragment, 16));
            Log.d(TAG, container.toString());
            if (container ==  null) {
                Log.d(TAG, "Container is null");
            }
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            return v;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.menu_fragment_location, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item_new_location:
                    return super.onOptionsItemSelected(item);
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    }

}
