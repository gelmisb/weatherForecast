package com.example.myapplication.Controllers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LoadData {

    private ArrayList<String> cityList;

    /**
     * This method is responsible for parsing the string
     * that has been parsed and analysed in order
     * to extract and manipulate the data
     *
     */
    public String[] loadArrayData(Context context) {
        try {

            // Accessing a JSON array or objects
            JSONArray obj = new JSONArray(loadJSONFromAsset(context));

            // Initialising an arrayList that will be used for temporarily
            cityList = new ArrayList<String>();

            // Will process all the data (obj.length)
            for (int i = 0; i < obj.length(); i++) {

                // Get the object
                JSONObject info = obj.getJSONObject(i);

                // Take a string in that object called cities
                String cities = info.getString("city");
                String country = info.getString("country");

                // Add that string to the arrayList
                cityList.add(cities + ", " + country);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // return an array
        return cityList.toArray(new String[cityList.size()]);
    }

    /**
     * The method accesses the locally stored JSON file
     * and uses a InputStream to read the data
     * That then returns a whole JSON within a string
     * for further processing
     *
     */
    private String loadJSONFromAsset(Context context) {

        // Nullify the string
        String json = null;

        // try - catch block for any possible exceptions or errors
        try {

            // Input stream to access the local file
            InputStream is = context.getAssets().open("cities.json");

            // Get the size of the document that has been accessed
            int size = is.available();

            // Initialise a byte buffer with that size
            byte[] buffer = new byte[size];

            // Get the information into the buffer using the stream
            is.read(buffer);

            // Close the stream
            is.close();

            // put the data as a JSON in a string
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
