package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private EditText city;
    private Button submit;

    private ArrayList<HashMap<String, String>> contactList;
    private ListView lv;
    private LoadList adapter;
    private ArrayList<String> currentArrayList, minTempArrayList,maxTempArrayList, mainArrayList, descriptionArrayList, imageArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = findViewById(R.id.cityText);
        submit = findViewById(R.id.submit);

        currentArrayList = new ArrayList<>();
        minTempArrayList = new ArrayList<>();
        maxTempArrayList = new ArrayList<>();
        descriptionArrayList = new ArrayList<>();
        mainArrayList = new ArrayList<>();
        imageArrayList = new ArrayList<>();


        lv =  findViewById(R.id.list);

        new GetResponse().execute();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                        JSONObject c = contacts.getJSONObject(i);
                        JSONObject temp = c.getJSONObject("temp");

                        Log.i("Day1", temp.get("day") + " ");
                        Log.i("Day2", temp.get("min") + " ");
                        Log.i("Day3", temp.get("max") + " ");

                        String current = temp.getString("day");
                        String minTemp = temp.getString("min");
                        String maxTemp = temp.getString("max");

                        JSONArray weather1 = c.getJSONArray("weather");
                        JSONObject weather = weather1.getJSONObject(0);

                        String main = weather.getString("main");
                        String description = weather.getString("description");
                        String images = weather.getString("icon");

                        // tmp hash map for single contact



                        // adding each child node to HashMap key => value
                        currentArrayList.add(current);
                        minTempArrayList.add(minTemp);
                        maxTempArrayList.add(maxTemp);
                        mainArrayList.add(main);
                        descriptionArrayList.add(description);
                        imageArrayList.add(images);

//                         adding contact to contact list
//                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            adapter = new LoadList(MainActivity.this, currentArrayList, minTempArrayList, maxTempArrayList, mainArrayList, descriptionArrayList, imageArrayList, R.layout.list );

//            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
//                    R.layout.item_layout, new String[]{ "Current: " + "current" + "C", "main", "Low: " +"minTemp", "high: " +"maxTemp", "description"},
//                    new int[]{R.id.current, R.id.weather, R.id.minTemp, R.id.maxTemp, R.id.description});

            lv.setAdapter(adapter);
        }
    }
}
