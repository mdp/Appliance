package im.mdp.displaydriver;

import android.webkit.WebView;

/**
 * Created by mdp on 4/18/14.
 */
public class WebViewHandler {
    private WebView mWebView;
    private String mUrl;

    WebViewHandler(WebView w, String url) {
        mWebView = w;
        mUrl = url;
        mWebView.getSettings().setJavaScriptEnabled(true);
        // mWebView.addJavascriptInterface(new JavascriptBridgeInterface(this), "AndroidHost");
        mWebView.loadUrl(url);
    }

    public void reload() {
        mWebView.loadUrl(mUrl);
    }
}
