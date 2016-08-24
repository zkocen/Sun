package com.kocen.zan.sun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        Log.v(LOG_TAG, "onPause() ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.v(LOG_TAG, "onStop() ");
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.v(LOG_TAG, "onResume() ");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.v(LOG_TAG, "onStart() ");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        Log.v(LOG_TAG, "onDestroy() ");
        super.onDestroy();
    }
}