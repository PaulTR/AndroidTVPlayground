package com.ptrprograms.androidtvplayground.localsearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ptrprograms.androidtvplayground.R;

public class MediaSearchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_search );
    }

    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, MediaSearchActivity.class));
        return true;
    }
}
