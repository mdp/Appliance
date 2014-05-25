package im.mdp.displaydriver.storage;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

import im.mdp.displaydriver.Derry;

/**
 * Created by mdp on 4/24/14.
 */
public class Location {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_URL = "url";
    private static final String JSON_DATE = "date";
    private static final String TAG = Derry.TAG + ":LocationModel";

    private UUID mId;
    private String mTitle;
    private String mUrl;
    private Date mDate = new Date();

    public Location(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_TITLE)) {
            mTitle = json.getString(JSON_TITLE);
        }
        mUrl = json.getString(JSON_URL);
        mDate = new Date(json.getLong(JSON_DATE));
    }

    public Location(String url) {
        mId = UUID.randomUUID();
        mUrl = url;
        mDate = new Date();
    }

    public Location() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }


    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_URL, mUrl);
        json.put(JSON_DATE, mDate.getTime());
        return json;
    }

    public UUID getId() {
        return mId;
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    public String toString() {
        if (mTitle == null || mTitle.isEmpty()) {
            return mUrl;
        }
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getTitle() {
        return this.mTitle;
    }
}
