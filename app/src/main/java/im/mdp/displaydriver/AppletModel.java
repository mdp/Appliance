package im.mdp.displaydriver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mdp on 4/24/14.
 */
public class AppletModel {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_URL = "url";
    private static final String JSON_DATE = "date";

    private UUID mId;
    private String mTitle;
    private String mUrl;
    private Date mDate = new Date();

    public AppletModel(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_URL));
        if (json.has(JSON_TITLE)) {
            mTitle = json.getString(JSON_TITLE);
        }
        mUrl = json.getString(JSON_URL);
        mDate = new Date(json.getLong(JSON_DATE));

    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_URL, mUrl);
        json.put(JSON_DATE, mDate.getTime());
        return json;
    }

    @Override
    public String toString() {
        return mUrl;
    }
}
