package im.mdp.displaydriver;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by mdp on 4/18/14.
 */
public class JavascriptBridgeInterface {
    private static final String TAG = Appliance.TAG + "JSBridge";
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
    public int getApiLevel() {
        return Build.VERSION.SDK_INT;
    }

    @JavascriptInterface
    public int setVolume(int volume) {
        AudioManager audioManager = (AudioManager) mHandler.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        return volume;
    }

    @JavascriptInterface
    public int getVolume() {
        AudioManager audioManager = (AudioManager) mHandler.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @JavascriptInterface
    public int getMaxVolume() {
        AudioManager audioManager = (AudioManager) mHandler.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    @JavascriptInterface
    public String getOrientation() {
        Log.d(TAG, "Get Orientation");
        int o = mHandler.getResources().getConfiguration().orientation;
        if (o == Configuration.ORIENTATION_LANDSCAPE) {
            return "L";
        }
        return "P";
    }

    @JavascriptInterface
    public void setOrientation(String orientation) {
        Log.d(TAG, "Set orientation: " + orientation);
        if (orientation.toUpperCase().equals("L")) {
            Log.d(Appliance.TAG, "SetOrientation Landscape");
            mHandler.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            Log.d(Appliance.TAG, "SetOrientation Portrait");
            mHandler.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    Handler mWatchdog = new Handler();
    Runnable mWatchdogRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(mHandler, "Watchdog enforced reload", Toast.LENGTH_SHORT).show();
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
