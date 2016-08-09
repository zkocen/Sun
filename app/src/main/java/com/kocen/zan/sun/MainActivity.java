package com.kocen.zan.sun;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    //PlaceholderFragment contaning a simple view
    public static class PlaceholderFragment extends Fragment{
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        public PlaceholderFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            String forecastStr = "Today, Sunny, 23C" +
                    "Tomorrow, Sunny, some clouds 25C" +
                    "Thursday Sunny, very clear 25C";

            ArrayList<String> weekForecast = new ArrayList<String>(
                    Arrays.asList(forecastStr)
            );

            ArrayAdapter<String> forcastAdapter = new ArrayAdapter<String>(
                    getActivity(), //current context
                    R.layout.list_item_forecast, //id of list item layout
                    R.id.list_item_forecast_textView,//id of textview to populate
                    weekForecast);///data

            ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
            listView.setAdapter(forcastAdapter);

            try{
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?id=3333129&mode=" +
                        "json&units=metric&cnt=7&APPID=dd424101f7064dbf93b35f5cb9abf42a");
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;//there is nothing to do
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null){
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0){
                    return null;//strem was empty no point in parsing
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e){
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try {
                        reader.close();
                    } catch (final IOException e){
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }

                }
            }
            
            return rootView;
        }

    }
}
