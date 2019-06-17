package com.example.myapplication.Controllers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Interfaces.OnTaskCompleted;
import com.example.myapplication.Model.Weather;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetWeather extends AsyncTask<Void, Void, Void>  {

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private ListView listView;
    private String city, type;
    private double lat, lon;
    private ProgressDialog pDialog;
    private OnTaskCompleted taskCompleted;
    private Weather weather;


    // Overriding constructors - for 16  days forecast
    public GetWeather(Activity activity, Context context, String city, ListView listView, String type) {
        this.context = context;
        this.city = city;
        this.activity = activity;
        this.listView = listView;
        this.type = type;
    }


    // Overriding constructors - for current forecast
    public GetWeather(Activity activity, Context context, double lat, double lon, String type, OnTaskCompleted activityContext) {
        this.context = context;
        this.activity = activity;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.taskCompleted = activityContext;
    }


    // Too many arrayLists - other option was HashSet<String, String, String, String, String> hashSet
    private ArrayList<String> currentArrayList = new ArrayList<>();
    private ArrayList<String> minTempArrayList = new ArrayList<>();
    private ArrayList<String> maxTempArrayList = new ArrayList<>();
    private ArrayList<String> descriptionArrayList = new ArrayList<>();
    private ArrayList<String> mainArrayList = new ArrayList<>();
    private ArrayList<String> imageArrayList = new ArrayList<>();


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Loading latest weather");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    /**
     *  Method that uses AsyncTask to assign a thread to download the data
     *  and parse it using JSONObject and JSONArray
     */
    @Override
    protected Void doInBackground(Void... arg0) {

        // Handler class allows to execute the URL and to get data
        HttpHandler sh = new HttpHandler();

        // Initialising string for type
        String url ;

        // If the query is for either current or 16 day forecast
        if(type.equals("current")){

            // Making a request to url and getting response
            url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric&appid=ae3723984918e29156906ffa2182bf02";

            // Putting the URL into the handler class to execute
            String jsonStr = sh.makeServiceCall(url);
            Log.i("getWeather", " " + jsonStr);

            // If the response is not null
            if (jsonStr != null) {

                // This is JSON parsing, the try block is necessary to catch any possible exceptions
                try {
                    // Creating an initial JSON object from the retrieved results
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    weather = new Weather();


                    // --- WEATHER PARAMS ---
                    // Creating a JSONObject to extract data
                    JSONArray weather2 = jsonObj.getJSONArray("weather");
                    JSONObject weather1 = weather2.getJSONObject(0);
                    // Retrieving strings from the JSONObject
                    weather.setPhrase(weather1.getString("main"));
                    weather.setDescription(weather1.getString("description"));
                    weather.setIcon(weather1.getString("icon"));


                    // --- TEMPERATURE PARAMS ---
                    JSONObject temp = jsonObj.getJSONObject("main");
                    // Retrieving strings from the JSONObject
                    weather.setCurrentTemp(temp.getString("temp"));
                    weather.setPressure(temp.getString("pressure"));
                    weather.setHumidity(temp.getString("humidity"));
                    weather.setMinTemp(temp.getString("temp_min"));
                    weather.setMaxTemp(temp.getString("temp_max"));


                    // --- WIND PARAMS ---
                    JSONObject wind = jsonObj.getJSONObject("wind");
                    // Retrieving strings from the JSONObject
                    weather.setWindSpeed(wind.getString("speed"));


                    // --- CLOUD PARAMS ---
                    JSONObject clouds = jsonObj.getJSONObject("clouds");
                    // Retrieving strings from the JSONObject
                    weather.setCloudiness(clouds.getString("all"));


                    // --- SYS PARAMS ---
                    JSONObject sys = jsonObj.getJSONObject("sys");
                    // Retrieving strings from the JSONObject
                    weather.setCountry(sys.getString("country"));
                    weather.setSunrise(sys.getString("sunrise"));
                    weather.setSunset(sys.getString("sunset"));

                    // --- CITY NAME PARAMS ---
                    weather.setCity(jsonObj.getString("name"));



                } catch (final JSONException e) {
                    // Catching exceptions and notifying the user that an exception has occurred
                    Log.e("JSON Error", "JSON parsing error: " + e.getMessage());

                    // This is not to occupy the current thread
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Sorry, error occurred! \n Try again", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                // Catching exceptions and notifying the user that an exception has occurred
                Log.e( "JSON Error", "Couldn't get JSON from server.");

                // This is not to occupy the current thread
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Sorry, error occurred! \n Try again", Toast.LENGTH_LONG).show();
                    }
                });
            }

        } else {

            // Making a request to url and getting response
            url = "https://api.openweathermap.org/data/2.5/forecast/daily?q=" + city + "&units=metric&cnt=16&appid=ae3723984918e29156906ffa2182bf02";

            // Putting the URL into the handler class to execute
            String jsonStr = sh.makeServiceCall(url);

            // If the response is not null
            if (jsonStr != null) {

                // This is JSON parsing, the try block is necessary to catch any possible exceptions
                try {
                    // Creating an initial JSON object from the retrieved results
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    weather = new Weather();

                    // Getting JSON Array node
                    JSONArray list = jsonObj.getJSONArray("list");

                    // Looping through all contacts
                    for (int i = 0; i < list.length(); i++) {

                        // Creating a JSONObject to extract data
                        JSONObject c = list.getJSONObject(i);
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
                    Log.e("JSON Error", "JSON parsing error: " + e.getMessage());

                    // This is not to occupy the current thread
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Sorry, error occurred! \n Try again", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                // Catching exceptions and notifying the user that an exception has occurred
                Log.e( "JSON Error", "Couldn't get JSON from server.");

                // This is not to occupy the current thread
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Sorry, error occurred! \n Try again", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        return null;
    }

    // When the application finished downloading
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        pDialog.dismiss();
        if(type.equals("current")) {
            taskCompleted.onTaskCompleted(weather);
        } else {
            // Custom list adapter for weather details
            // This takes in multiple arrayLists and then parses the details into the views accordingly
            LoadList adapter = new LoadList(activity, currentArrayList, minTempArrayList, maxTempArrayList, mainArrayList, descriptionArrayList, imageArrayList, R.layout.list);

            // Setting the custom adapter for the listView
            listView.setAdapter(adapter);
        }
    }
}
