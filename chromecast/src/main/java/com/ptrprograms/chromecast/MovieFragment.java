package com.ptrprograms.chromecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends ListFragment {

    private List<Video> mVideos;
    private MovieAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        mAdapter = new MovieAdapter(getActivity());
        mAdapter.addAll(mVideos);
        setListAdapter(mAdapter);
    }

    private void loadData() {
        String json = Utils.loadJSONFromResource(getActivity(), R.raw.videos);
        Type collection = new TypeToken<ArrayList<Video>>(){}.getType();

        Gson gson = new Gson();
        mVideos = gson.fromJson( json, collection );
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class );
        intent.putExtra(VideoDetailActivity.EXTRA_VIDEO, mAdapter.getItem(position) );
        startActivity(intent);
    }
}
