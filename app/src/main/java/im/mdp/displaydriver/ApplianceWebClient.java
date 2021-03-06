package im.mdp.displaydriver;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by mdp on 4/21/14.
 */
public class ApplianceWebClient extends WebViewClient {

    private Context mContext;

    ApplianceWebClient(Context context) {
        mContext = context;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        Toast.makeText(mContext, "Error: "+ description, Toast.LENGTH_SHORT).show();
    }
}
