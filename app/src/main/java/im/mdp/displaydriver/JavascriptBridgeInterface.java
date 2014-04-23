package im.mdp.displaydriver;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by mdp on 4/18/14.
 */
public class JavascriptBridgeInterface {
    private static final String TAG = "Derry";
    WebActivity mHandler;
    JavascriptBridgeInterface(WebActivity h) {
        mHandler = h;
    }

    @JavascriptInterface
    public void watchdog(int delayMillis) {
        Toast.makeText(mHandler, "watchdog", Toast.LENGTH_SHORT).show();
        delayedReload(delayMillis);
    }

    @JavascriptInterface
    public String getOrientation() {
        int o = mHandler.getResources().getConfiguration().orientation;
        if (o == Configuration.ORIENTATION_LANDSCAPE) {
            return "L";
        } else {
            return "P";
        }
    }

    @JavascriptInterface
    public void setOrientation(String orientation) {
        Log.d(TAG, orientation);
        if (orientation.toUpperCase().equals("L")) {
            Log.d(TAG, "SetOrientation Landscape");
            mHandler.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            Log.d(TAG, "SetOrientation Portrait");
            mHandler.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    Handler mWatchdog = new Handler();
    Runnable mWatchdogRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(mHandler, "watchdog reload", Toast.LENGTH_SHORT).show();
            mHandler.reload();
        }
    };

    /**
     * Schedules a Webview reload
     * Automatically reset when you call it again
     * Call indefinitely to create a watchdog timer
     * Call with 0 to stop
     */
    private void delayedReload(int delayMillis) {
        if (delayMillis > 0) {
            mWatchdog.removeCallbacks(mWatchdogRunnable);
            mWatchdog.postDelayed(mWatchdogRunnable, delayMillis);
        } else {
            mWatchdog.removeCallbacks(mWatchdogRunnable);
        }
    }
}
