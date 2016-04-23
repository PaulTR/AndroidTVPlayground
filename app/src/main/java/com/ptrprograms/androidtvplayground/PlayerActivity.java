package com.ptrprograms.androidtvplayground;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class PlayerActivity extends Activity implements PlayerControlsFragment.PlayerControlsListener {
    private VideoView mVideoView;
    private Video mVideo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initViews();
        initVideoPlayer();
    }

    private void initViews() {
        mVideoView = (VideoView) findViewById( R.id.video_view );
    }

    private void initVideoPlayer() {
        mVideo = (Video) getIntent().getSerializableExtra( VideoDetailsFragment.EXTRA_VIDEO );

        try {
            String link= mVideo.getVideoUrl();
            Uri video = Uri.parse(link);
            mVideoView.setVideoURI(video);
            mVideoView.start();
        } catch (Exception e) {
            Toast.makeText(this, "Error connecting", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void play() {
        mVideoView.start();
    }

    @Override
    public void pause() {
        mVideoView.pause();
    }
}
