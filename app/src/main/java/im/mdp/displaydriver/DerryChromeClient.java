package im.mdp.displaydriver;

import android.content.Context;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by mdp on 4/20/14.
 */
public class DerryChromeClient extends WebChromeClient {

    private Context mContext;

    DerryChromeClient (Context context) {
        mContext = context;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Toast.makeText(mContext, "Alert: " + message, Toast.LENGTH_LONG).show();
        // return super.onJsAlert(view, url, message, result);
        return true;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Toast.makeText(mContext, consoleMessage.toString(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
