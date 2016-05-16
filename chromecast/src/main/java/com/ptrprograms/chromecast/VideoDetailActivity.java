package com.ptrprograms.chromecast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.common.images.WebImage;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.squareup.picasso.Picasso;

public class VideoDetailActivity extends AppCompatActivity {

    public static final String EXTRA_VIDEO = "extra_video";

    private Video mVideo;

    private TextView mTitle;
    private TextView mDescription;
    private ImageView mPoster;
    private Button mPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        mVideo = (Video) getIntent().getExtras().get(EXTRA_VIDEO);

        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_cast, menu);

        VideoCastManager.getInstance().addMediaRouterButton(menu, R.id.media_route_menu_item);
        return true;
    }

    private void initViews() {
        mTitle = (TextView) findViewById(R.id.title);
        mDescription = (TextView) findViewById(R.id.description);
        mPoster = (ImageView) findViewById(R.id.poster);
        mPlayButton = (Button) findViewById(R.id.play_button);

        Picasso.with(this).load(mVideo.getPoster()).into(mPoster);
        mTitle.setText(mVideo.getTitle());
        mDescription.setText(mVideo.getDescription());

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !VideoCastManager.getInstance().isConnected() ) {
                    Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
                    intent.putExtra(EXTRA_VIDEO, mVideo);
                    startActivity(intent);
                } else {
                    MediaMetadata mediaMetadata = new MediaMetadata( MediaMetadata.MEDIA_TYPE_MOVIE );
                    mediaMetadata.putString(MediaMetadata.KEY_TITLE, mVideo.getTitle());

                    Uri uri = Uri.parse(mVideo.getPoster());
                    mediaMetadata.addImage(new WebImage(uri, 480, 270));
                    uri = Uri.parse(mVideo.getPoster());
                    mediaMetadata.addImage(new WebImage(uri, 1200, 780));

                    MediaInfo mediaInfo = new MediaInfo.Builder( mVideo.getVideoUrl() )
                            .setContentType("video/mp4")
                            .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                            .setMetadata(mediaMetadata)
                            .build();

                    VideoCastManager.getInstance().startVideoCastControllerActivity(VideoDetailActivity.this, mediaInfo, 0,
                            true);
                }
            }
        });
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
}
