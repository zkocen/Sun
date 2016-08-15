package com.kocen.zan.sun;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment()   )
                    .commit();
        }
    }

    public static class DetailFragment extends Fragment{
        public DetailFragment(){
        }
        //here we add side menu to details layout
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.detailmenu, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            int id = item.getItemId();
            if (id == R.id.settings_menu){
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
            if (id == R.id.pref_loca_menu){
                getLocationShowOnMaps();
            }
            if (id == R.id.menu_item_share){
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                Intent intent = getActivity().getIntent();
                if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
                    String forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                    shareIntent.setType("text/html");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, forecastStr);
                    startActivity(Intent.createChooser(shareIntent, "Share using the following: "));
                }
            }
            return super.onOptionsItemSelected(item);
        }

        private void getLocationShowOnMaps(){
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            String location = preferences.getString(getString(R.string.pref_location_key),
                    getString(R.string.pref_location_default));
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?q=" + location));
            startActivity(intent);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_detail, container, false);
            // The detail Activity called via intent.  Inspect the intent for forecast data.
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
                String forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView)rootView.findViewById(R.id.detailed_forecast_text))
                        .setText(forecastStr);

            }
            return rootView;
        }

    }
}