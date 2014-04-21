package im.mdp.displaydriver;

import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by mdp on 4/18/14.
 */
public class JavascriptBridgeInterface {
    WebActivity mHandler;
    JavascriptBridgeInterface(WebActivity h) {
        mHandler = h;
    }

    @JavascriptInterface
    public void watchdog(int delayMillis) {
        Toast.makeText(mHandler, "watchdog", Toast.LENGTH_SHORT).show();
        delayedReload(delayMillis);
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
