package com.ptrprograms.androidtvplayground.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ptrprograms.androidtvplayground.guidedstep.GuidedStepActivity;
import com.ptrprograms.androidtvplayground.nowplaying.NowPlayingActivity;
import com.ptrprograms.androidtvplayground.pictureinpicture.PictureInPictureActivity;
import com.ptrprograms.androidtvplayground.R;
import com.ptrprograms.androidtvplayground.preferences.SettingsActivity;
import com.ptrprograms.androidtvplayground.Utils;
import com.ptrprograms.androidtvplayground.model.Video;
import com.ptrprograms.androidtvplayground.videodetails.VideoDetailsActivity;
import com.ptrprograms.androidtvplayground.videodetails.VideoDetailsFragment;
import com.ptrprograms.androidtvplayground.custom.SlothActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BrowseFragment implements OnItemViewClickedListener {

    private List<Video> mVideos = new ArrayList<Video>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();

        setTitle("Android TV");
        setHeadersState(HEADERS_HIDDEN);
        setHeadersTransitionOnBackEnabled(true);

        loadRows();

        setOnItemViewClickedListener( this );
    }

    private void loadData() {
        String json = Utils.loadJSONFromResource(getActivity(), R.raw.videos);
        Type collection = new TypeToken<ArrayList<Video>>(){}.getType();

        Gson gson = new Gson();
        mVideos = gson.fromJson( json, collection );
    }

    private void loadRows() {
        ArrayObjectAdapter adapter = new ArrayObjectAdapter( new ListRowPresenter() );
        CardPresenter presenter = new CardPresenter();

        List<String> categories = getCategories();

        if( categories == null || categories.isEmpty() )
            return;

        for( String category : categories ) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter( presenter );
            for( Video movie : mVideos ) {
                if( category.equalsIgnoreCase( movie.getCategory() ) )
                    listRowAdapter.add( movie );
            }
            if( listRowAdapter.size() > 0 ) {
                HeaderItem header = new HeaderItem( adapter.size() - 1, category );
                adapter.add( new ListRow( header, listRowAdapter ) );
            }
        }

        setupPreferences(adapter);

        setAdapter(adapter);
    }

    private void setupPreferences( ArrayObjectAdapter adapter ) {

        HeaderItem gridHeader = new HeaderItem( "Preferences" );
        PreferenceCardPresenter mGridPresenter = new PreferenceCardPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter( mGridPresenter );
        gridRowAdapter.add(getResources().getString(R.string.sloth));
        gridRowAdapter.add(getString(R.string.now_playing));
        gridRowAdapter.add(getString(R.string.picture_in_picture));
        gridRowAdapter.add(getString(R.string.settings));
        gridRowAdapter.add(getString(R.string.guided_step));
        adapter.add(new ListRow(gridHeader, gridRowAdapter));

    }

    private List<String> getCategories() {
        if( mVideos == null )
            return null;

        List<String> categories = new ArrayList<String>();
        for( Video movie : mVideos ) {
            if( !categories.contains( movie.getCategory() ) ) {
                categories.add( movie.getCategory() );
            }
        }

        return categories;
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (item instanceof Video) {
            Video video = (Video) item;
            Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
            intent.putExtra(VideoDetailsFragment.EXTRA_VIDEO, video);
            startActivity(intent);
        } else if( item instanceof String ) {
            if( ((String) item).equalsIgnoreCase( getString( R.string.sloth ) ) ) {
                Intent intent = new Intent( getActivity(), SlothActivity.class );
                startActivity( intent );
            } else if( ((String) item).equalsIgnoreCase(getString(R.string.now_playing))) {
                Intent intent= new Intent( getActivity(), NowPlayingActivity.class);
                startActivity(intent);
            } else if( ((String) item).equalsIgnoreCase(getString(R.string.picture_in_picture))) {
                Intent intent = new Intent(getActivity(), PictureInPictureActivity.class);
                intent.putExtra(VideoDetailsFragment.EXTRA_VIDEO, mVideos.get(1));
                startActivity(intent);
            } else if( ((String) item).equalsIgnoreCase(getString(R.string.guided_step))) {
                Intent intent = new Intent(getActivity(), GuidedStepActivity.class);
                startActivity(intent);
            } else if( ((String) item).equalsIgnoreCase(getString(R.string.settings))) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        }
    }
}
