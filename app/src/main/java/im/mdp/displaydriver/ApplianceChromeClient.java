package im.mdp.displaydriver;

import android.content.Context;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by mdp on 4/20/14.
 */
public class ApplianceChromeClient extends WebChromeClient {

    private static final String TAG = "Derry";
    private Context mContext;

    ApplianceChromeClient(Context context) {
        mContext = context;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.d(TAG, "Alert: " + message);
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage cm) {
        String msg = (cm.message() + " -- From line "
                + cm.lineNumber() + " of "
                + cm.sourceId());
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Console: " + msg);
        return true;
    }
}
