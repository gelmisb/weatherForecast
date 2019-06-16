package com.example.myapplication.Controllers;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


class HttpHandler {private static final String TAG = HttpHandler.class.getSimpleName();

    // Necessary empty constructor
    protected HttpHandler() {}

    /**
     * The method takes care of making connection and getting the details
     * then reading those details and converting them
     *
     * There is a lot of exceptions that are being caught because of:
     *  - traffic
     *  - networking API
     *  - server responses
     *  - device compatibility and network availability
     *
     *
     * @param reqUrl
     * @return
     */
    protected String makeServiceCall(String reqUrl) {

        // Nullifying the string
        String response = null;

        // Try blocks necessary to catch all the exceptions below
        try {

            // using a URL class to form a usable link
            URL url = new URL(reqUrl);

            // Calling the connection class to open the connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Getting the information from the server
            conn.setRequestMethod("GET");

            // Reading the response
            InputStream in = new BufferedInputStream(conn.getInputStream());

            // Converting to string :D
            response = convertStreamToString(in);


        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());

        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());

        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());

        }


        return response;
    }

    /**
     * Method allows to convert the received results into a query
     * This means converting the resulting query into a usable
     * form of strings

     * @param is
     * @return
     */
    private String convertStreamToString(InputStream is) {

        // Buffered reader to take in the the request
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        // String builder to format
        StringBuilder sb = new StringBuilder();

        // Temp value
        String line;

        // Catching exceptions
        try {
            // While there's information
            while ((line = reader.readLine()) != null) {

                // Use stringBuilder to format the response
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            // And not forgetting to close the connection
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Returning a converted / parsed request
        return sb.toString();
    }
}
