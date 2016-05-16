package com.ptrprograms.chromecast;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {

    private VideoView mVideoView;
    private Video mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoView = (VideoView) findViewById(R.id.player);

        initVideoPlayer();
    }

    private void initVideoPlayer() {
        mVideo = (Video) getIntent().getSerializableExtra(VideoDetailActivity.EXTRA_VIDEO);

        try {
            String link= mVideo.getVideoUrl();
            Uri video = Uri.parse(link);
            mVideoView.setVideoURI(video);
            mVideoView.start();
        } catch (Exception e) {
            Toast.makeText(this, "Error connecting", Toast.LENGTH_SHORT).show();
        }
    }
}
