package im.mdp.displaydriver.storage;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class LocationCollection {
    private static final String TAG = "LocationCollection";
    private static final String FILENAME = "applets.json";

    private ArrayList<Location> mLocations;
    private LocationSerializer mSerializer;

    private static LocationCollection sLocationCollection;
    private Context mAppContext;

    private LocationCollection(Context appContext) {
        mAppContext = appContext;
        mSerializer = new LocationSerializer(mAppContext, FILENAME);

        try {
            mLocations = mSerializer.loadLocations();
        } catch (Exception e) {
            mLocations = new ArrayList<Location>();
            Log.e(TAG, "Error loading applets: ", e);
        }
    }

    public static LocationCollection get(Context c) {
        if (sLocationCollection == null) {
            sLocationCollection = new LocationCollection(c.getApplicationContext());
        }
        return sLocationCollection;
    }

    public Location getLocation(UUID id) {
        for (Location l : mLocations) {
            if (l.getId().equals(id))
                return l;
        }
        return null;
    }

    public void addLocation(Location a) {
        mLocations.add(a);
        saveLocations();
    }

    public ArrayList<Location> getLocations() {
        return mLocations;
    }

    public boolean saveLocations() {
        try {
            mSerializer.saveLocations(mLocations);
            Log.d(TAG, "applets saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving applets: " + e);
            return false;
        }
    }
}

