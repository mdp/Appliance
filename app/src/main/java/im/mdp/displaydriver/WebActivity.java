package im.mdp.displaydriver;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import im.mdp.displaydriver.util.SystemUiHider;


public class WebActivity extends ActionBarActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    public static final String URL_FIELD = "URL";
    public static final String JS_GLOBAL = "ApplianceBridge";
    private static final String TAG = Appliance.TAG + ":WebActivity";
    public final String DEFAULT_URL = "file:///android_asset/index.html";
    public WebView mWebView;
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // No title in this view
        enableFullScreenMode();
        setContentView(R.layout.activity_main);
        preventSleep(true);
        String urlToLoad = (String) getIntent()
                .getSerializableExtra(URL_FIELD);

        if (urlToLoad.isEmpty()) {
            urlToLoad = DEFAULT_URL;
        }
        mWebView = (WebView) findViewById(R.id.fullscreen_webview);
        mWebView.setWebViewClient(new ApplianceWebClient(this));
        mWebView.setWebChromeClient(new ApplianceChromeClient(this));
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        String databasePath = this.getApplicationContext().getDir("databases", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(databasePath);
        mWebView.addJavascriptInterface(new JavascriptBridgeInterface(this), JS_GLOBAL);
        mWebView.loadUrl(urlToLoad);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Stopped");
        mWebView.stopLoading();
        mWebView.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableFullScreenMode();
        preventSleep(true);
    }

    public void preventSleep(boolean enabled) {
        if (enabled) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    public void enableFullScreenMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            immersiveMode();
        } else {
            final View contentView = findViewById(R.id.fullscreen_webview);
            mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
            mSystemUiHider.setup();
            mSystemUiHider
                    .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {

                        @Override
                        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                        public void onVisibilityChange(boolean visible) {
                            if (visible && AUTO_HIDE) {
                                // Schedule a hide().
                                delayedHide(AUTO_HIDE_DELAY_MILLIS);
                            }
                        }
                    });
            delayedHide(100);
        }
    }

    public void immersiveMode() {
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    public void reload() {
        mWebView.loadUrl(DEFAULT_URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "Hide me!");
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

}
