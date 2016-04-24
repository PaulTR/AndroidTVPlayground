package com.ptrprograms.androidtvplayground.nowplaying;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;

import com.ptrprograms.androidtvplayground.R;

public class NowPlayingActivity extends Activity {

    MediaSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        mSession = new MediaSession(this, "MusicService");
        mSession.setCallback(new MediaSessionCallback());
        mSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);

        updateMetadata();
        updatePlaybackState();

        handlePlayRequest();

    }

    private void updatePlaybackState() {
        long position = PlaybackState.PLAYBACK_POSITION_UNKNOWN;
        /*
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            position = mMediaPlayer.getCurrentPosition();
        }
        */
        PlaybackState.Builder stateBuilder = new PlaybackState.Builder()
                .setActions(getAvailableActions());
        stateBuilder.setState(PlaybackState.STATE_PLAYING, position, 1.0f);
        mSession.setPlaybackState(stateBuilder.build());
    }

    private long getAvailableActions() {
        long actions = PlaybackState.ACTION_PLAY_PAUSE |
                PlaybackState.ACTION_PLAY_FROM_MEDIA_ID |
                PlaybackState.ACTION_PLAY_FROM_SEARCH;
        //if (mPlayingQueue == null || mPlayingQueue.isEmpty()) {
          //  return actions;
        //}
        //if (mState == PlaybackState.STATE_PLAYING) {
            actions |= PlaybackState.ACTION_PAUSE;
        //} else {
           // actions |= PlaybackState.ACTION_PLAY;
        //}
        return actions;
    }

    private void updateMetadata() {
        MediaMetadata.Builder metadataBuilder = new MediaMetadata.Builder();
        // To provide most control over how an item is displayed set the
        // display fields in the metadata
        metadataBuilder.putString(MediaMetadata.METADATA_KEY_DISPLAY_TITLE,
                "title");
        metadataBuilder.putString(MediaMetadata.METADATA_KEY_DISPLAY_SUBTITLE,
                "subtitle");
        metadataBuilder.putString(MediaMetadata.METADATA_KEY_DISPLAY_ICON_URI,
                "art uri");
        // And at minimum the title and artist for legacy support
        metadataBuilder.putString(MediaMetadata.METADATA_KEY_TITLE,
                "title");
        metadataBuilder.putString(MediaMetadata.METADATA_KEY_ARTIST,
                "artist");
        // A small bitmap for the artwork is also recommended
        metadataBuilder.putBitmap(MediaMetadata.METADATA_KEY_ART,
                ((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher, null)).getBitmap());
        // Add any other fields you have for your data as well
        mSession.setMetadata(metadataBuilder.build());
    }

    private void handlePlayRequest() {

//        tryToGetAudioFocus();

        if (!mSession.isActive()) {
            mSession.setActive(true);
        }
    }

    private void tryToGetAudioFocus() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( mSession.isActive() ) {
            mSession.setActive(false);
        }
    }

    public class MediaSessionCallback extends MediaSession.Callback {

    }
}
