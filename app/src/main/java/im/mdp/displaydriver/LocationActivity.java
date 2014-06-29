package im.mdp.displaydriver;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

import im.mdp.displaydriver.storage.Location;
import im.mdp.displaydriver.storage.LocationCollection;
import im.mdp.displaydriver.util.OkCancelDialog;


public class LocationActivity extends ActionBarActivity{

    private static final String TAG = Appliance.TAG + ":LocationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        UUID uuid = (UUID)getIntent()
                .getSerializableExtra(LocationFragment.LOCATION_ID);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = LocationFragment.newInstance(uuid);
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    public static class LocationFragment extends Fragment implements OkCancelDialog.OkCancelDialogListener{

        private static final String TAG = Appliance.TAG + ":LocationFragment";
        public static final String LOCATION_ID = "location_fragment.location_id";
        private Location mLocation;
        private EditText mTitleField;
        private EditText mUrlField;

        public static LocationFragment newInstance(UUID locationId) {
            LocationFragment fragment = new LocationFragment();
            Bundle args = new Bundle();
            args.putSerializable(LOCATION_ID, locationId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            UUID locationId = (UUID)getArguments().getSerializable(LOCATION_ID);
            if (locationId == null) {
                mLocation = new Location();
            } else {
                mLocation = LocationCollection.get(getActivity()).getLocation(locationId);
            }
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.location_fragment, container, false);
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            mTitleField =  (EditText) v.findViewById(R.id.location_title);
            mUrlField =  (EditText) v.findViewById(R.id.location_url);
            mUrlField.setText(mLocation.getUrl());
            mTitleField.setText(mLocation.getTitle());
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
                case R.id.menu_item_save_location:
                    Log.d(TAG, "Save the location");
                    saveRecord();
                    return super.onOptionsItemSelected(item);
                case R.id.menu_item_delete_location:
                    Log.d(TAG, "Delete the location");
                    deleteRecord(false);
                    return super.onOptionsItemSelected(item);
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(getActivity());
                    return super.onOptionsItemSelected(item);
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        private void saveRecord() {
            mLocation.setTitle(mTitleField.getText().toString());
            mLocation.setUrl(mUrlField.getText().toString());
            LocationCollection.get(getActivity()).saveLocation(mLocation);
            getActivity().finish();
        }

        private void deleteRecord(boolean sure) {
            if (sure) {
                LocationCollection.get(getActivity()).deleteLocaiton(mLocation);
                getActivity().finish();
            } else {
                OkCancelDialog dialog = new OkCancelDialog();
                Bundle args = new Bundle();
                args.putString("message", "Are you sure?");
                dialog.setArguments(args);
                dialog.setTargetFragment(this, 0);
                dialog.show(getFragmentManager(), "okcancel");
            }
        }

        @Override
        public void onDialogOkClick (DialogFragment dialog) {
            Log.d(TAG, "OK");
            deleteRecord(true);
        }

        @Override
        public void onDialogCancelClick (DialogFragment dialog) {
            Log.d(TAG, "Cancel");
        }

    }

}
