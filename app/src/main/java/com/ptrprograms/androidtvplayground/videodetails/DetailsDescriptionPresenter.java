package com.ptrprograms.androidtvplayground.videodetails;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import com.ptrprograms.androidtvplayground.model.Video;

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {
    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        Video video = (Video) item;

        if (video != null) {
            viewHolder.getTitle().setText(video.getTitle());
            viewHolder.getSubtitle().setText(video.getCategory());
            viewHolder.getBody().setText(video.getDescription());
        }
    }
}
