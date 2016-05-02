package com.ptrprograms.chromecast;

import android.app.Application;

import com.google.android.libraries.cast.companionlibrary.cast.CastConfiguration;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;

import java.util.Locale;

public class ChromecastApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

       String chromecastAppId = "C54BAA8D";

        CastConfiguration options = new CastConfiguration.Builder(chromecastAppId)
              .enableAutoReconnect()
              .enableLockScreen()
              .enableNotification()
              .enableWifiReconnection()
              .setLaunchOptions(false, Locale.getDefault())
              .setCastControllerImmersive(true)
              .setNextPrevVisibilityPolicy(CastConfiguration.NEXT_PREV_VISIBILITY_POLICY_DISABLED)
              .addNotificationAction(CastConfiguration.NOTIFICATION_ACTION_REWIND, false)
              .addNotificationAction(CastConfiguration.NOTIFICATION_ACTION_PLAY_PAUSE, true)
              .addNotificationAction(CastConfiguration.NOTIFICATION_ACTION_DISCONNECT, true)
              .setForwardStep(10)
              .build();

          VideoCastManager.initialize(this, options);

    }
}
