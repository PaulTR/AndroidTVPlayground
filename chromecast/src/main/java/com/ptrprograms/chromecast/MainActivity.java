package com.ptrprograms.chromecast;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumerImpl;
import com.google.android.libraries.cast.companionlibrary.widgets.IntroductoryOverlay;

public class MainActivity extends AppCompatActivity {

    private IntroductoryOverlay mOverlay;

    private MenuItem routerMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoCastManager.getInstance().checkGooglePlayServices(this);
        VideoCastManager.getInstance().reconnectSessionIfPossible();

        VideoCastConsumerImpl impl = new VideoCastConsumerImpl() {
            @Override
            public void onCastAvailabilityChanged(boolean castPresent) {
                super.onCastAvailabilityChanged(castPresent);
                if( castPresent && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
                    showOverlay();
                }
            }
        };

        VideoCastManager.getInstance().addVideoCastConsumer(impl);
    }

    private void showOverlay() {
        if( mOverlay != null ) {
            mOverlay.remove();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if( routerMenuItem == null )
                    return;

                mOverlay = new IntroductoryOverlay.Builder(MainActivity.this)
                        .setMenuItem(routerMenuItem)
                        .setTitleText(R.string.overlay_text)
                        .setSingleTime()
                        .setOnDismissed(new IntroductoryOverlay.OnOverlayDismissedListener() {
                            @Override
                            public void onOverlayDismissed() {
                                mOverlay = null;
                            }
                        })
                        .build();

                mOverlay.show();
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_cast, menu);

        VideoCastManager.getInstance().addMediaRouterButton(menu, R.id.media_route_menu_item);

        routerMenuItem = menu.findItem(R.id.media_route_menu_item);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoCastManager.getInstance().incrementUiCounter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        VideoCastManager.getInstance().decrementUiCounter();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
