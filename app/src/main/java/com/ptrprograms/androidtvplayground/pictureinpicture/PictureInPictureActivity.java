package com.ptrprograms.androidtvplayground.pictureinpicture;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import com.ptrprograms.androidtvplayground.R;
import com.ptrprograms.androidtvplayground.model.Video;
import com.ptrprograms.androidtvplayground.videodetails.VideoDetailsFragment;

public class PictureInPictureActivity extends Activity implements PictureInPicturePlayerControlsFragment.PlayerControlsListener {
    private VideoView mVideoView;
    private Video mVideo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_in_picture_player);

        initViews();
        initVideoPlayer();
    }

    private void initViews() {
        mVideoView = (VideoView) findViewById(R.id.video_view );
    }

    private void initVideoPlayer() {
        Video tmpVideo = (Video) getIntent().getSerializableExtra( VideoDetailsFragment.EXTRA_VIDEO );
        if( mVideo != null && tmpVideo.getTitle().equalsIgnoreCase(mVideo.getTitle()) ) {
            return;
        }

        mVideo = tmpVideo;
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


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initVideoPlayer();
    }
}
