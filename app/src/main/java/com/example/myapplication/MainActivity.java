package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private EditText city;
    private Button submit;

    private ListView lv;
    private LoadList adapter;
    private ArrayList<String> currentArrayList, minTempArrayList,maxTempArrayList, mainArrayList, descriptionArrayList, imageArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making it full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // set an exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Slide());
        }

        // Setting flags from previous iinstance
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set current layout
        setContentView(R.layout.activity_main);

        //  Fixed Portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // Views for details
        city = findViewById(R.id.cityText);
        submit = findViewById(R.id.submit);
        lv =  findViewById(R.id.list);


        // Too many arrayLists - other option was HashSet<String, String, String, String, String> hashSet
        currentArrayList = new ArrayList<>();
        minTempArrayList = new ArrayList<>();
        maxTempArrayList = new ArrayList<>();
        descriptionArrayList = new ArrayList<>();
        mainArrayList = new ArrayList<>();
        imageArrayList = new ArrayList<>();

        // Executing the thread
        new GetResponse().execute();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // @TODO --- The on click will show execute the method to initiate the 16 weather forecast for the selected city
            }
        });
    }


    /**
     *  Method that uses AsyncTask to assign a thread to download the data
     *  and parse it using JSONObject and JSONArray
     */
    private class GetResponse extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
            // @TODO Need to add a progress bar / spinner to show that the download is progressing
        }


        @Override
        protected Void doInBackground(Void... arg0) {

            // Handler class allows to execute the URL and to get data
            HttpHandler sh = new HttpHandler();

            // !!!!!  THIS IS FOR 16 DAYS IN DUBLIN THE URL IS NOT DYNAMIC YET  !!!!!
            // Making a request to url and getting response
            String url = "https://jsonp.afeld.me/?url=https%3A%2F%2Fapi.openweathermap.org%2Fdata%2F2.5%2Fforecast%2Fdaily%3Fq%3DDublin%26mode%3Djson%26units%3Dmetric%26cnt%3D16%26appid%3Dae3723984918e29156906ffa2182bf02";

            // Putting the URL into the handler class to execute
            String jsonStr = sh.makeServiceCall(url);

            // If the response is not null
            if (jsonStr != null) {

                // This is JSON parsing, the try block is necessary to catch any possible exceptions
                try {
                    // Creating an initial JSON object from the retrieved results
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("list");

                    // Looping through all contacts
                    for (int i = 0; i < contacts.length(); i++) {

                        // Creating a JSONObject to extract data
                        JSONObject c = contacts.getJSONObject(i);
                        JSONObject temp = c.getJSONObject("temp");

                        // Retrieving strings from the JSONObject
                        String current = temp.getString("day");
                        String minTemp = temp.getString("min");
                        String maxTemp = temp.getString("max");

                        // A JSONArray is present that has series of JSONObjects that need to be extracted
                        JSONArray weather1 = c.getJSONArray("weather");
                        JSONObject weather = weather1.getJSONObject(0);

                        // Retrieving strings from the JSONObject
                        String main = weather.getString("main");
                        String description = weather.getString("description");
                        String images = weather.getString("icon");

                        // adding each child node to HashMap key => value
                        currentArrayList.add(current);
                        minTempArrayList.add(minTemp);
                        maxTempArrayList.add(maxTemp);
                        mainArrayList.add(main);
                        descriptionArrayList.add(description);
                        imageArrayList.add(images);
                    }
                } catch (final JSONException e) {
                    // Catching exceptions and notifying the user that an exception has occurred
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());

                    // This is not to occupy the current thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Sorry, error occurred! \n Try again", Toast.LENGTH_LONG).show();
                        }
                    });
                }


            } else {
                // Catching exceptions and notifying the user that an exception has occurred
                Log.e(TAG, "Couldn't get JSON from server.");

                // This is not to occupy the current thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Sorry, error occurred! \n Try again", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }


        // When the application finished downloading
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Custom list adapter for weather details
            // This takes in multiple arrayLists and then parses the details into the views accordingly
            adapter = new LoadList(MainActivity.this, currentArrayList, minTempArrayList, maxTempArrayList, mainArrayList, descriptionArrayList, imageArrayList, R.layout.list );

            // Setting the custom adapter for the listView
            lv.setAdapter(adapter);
        }
    }
}
