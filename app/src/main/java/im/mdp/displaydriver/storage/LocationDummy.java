package im.mdp.displaydriver.storage;

import android.content.Context;

/**
 * Created by mdp on 4/27/14.
 */
public class LocationDummy {
    public static LocationCollection create(Context context) {
        LocationCollection lc = LocationCollection.get(context);
        lc.deleteAllLocations();
        lc.addLocation(new Location("http://www.nytimes.com"));
        lc.addLocation(new Location("http://www.techmeme.com"));
        return lc;
    }
}
