package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.transition.Slide;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class CurrentWeather extends AppCompatActivity implements OnTaskCompleted{

    // @TODO In this Activity it should display the current weather of the current location
    // When the user will try to see another city,
    // they can type the city name and add it,
    // the application will then send the user to the 16 day
    // forecast


    public TextView cityNameText, timeText, dateText, phraseText, currentTempText, minTempText, maxTempText, desctiptionText;
    AppLocationService appLocationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making it full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inside your activity (if you did not enable transitions in your theme)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Slide());
        }

        // Setting flags from previous instance
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set current layout
        setContentView(R.layout.activity_current_weather);

        //  Fixed Portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initialiseVars();

        appLocationService = new AppLocationService(
                CurrentWeather.this, CurrentWeather.this);

        Location location = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Log.i("Lat", latitude + " ");
            Log.i("Lon", longitude + " ");
            new GetWeather(CurrentWeather.this, getApplicationContext(), latitude, longitude, "current", CurrentWeather.this).execute();
        }
    }


    private void initialiseVars() {
        currentTempText = findViewById(R.id.currentTemp);
        cityNameText = findViewById(R.id.city);
        timeText = findViewById(R.id.time);
        dateText = findViewById(R.id.dateText);
        phraseText = findViewById(R.id.phraseText);
        desctiptionText = findViewById(R.id.desc);
    }


//
//                    all.add(main);
//                    all.add(description);
//                    all.add(images);
//                    all.add(current);
//                    all.add(minTemp);
//                    all.add(maxTemp);
//                    all.add(speed);
//                    all.add(deg);
//                    all.add(cloudsMuch);
//                    all.add(country);
//                    all.add(sunrise);
//                    all.add(sunset);
//                    all.add(city);

    public void getArrayList(ArrayList<String> arrayList) {
        if(arrayList != null) {
            phraseText.setText(arrayList.get(0));
            desctiptionText.setText(arrayList.get(1));
            currentTempText.setText(arrayList.get(3));
            cityNameText.setText(arrayList.get(12));


        } else {
            Toast.makeText(this, "There was a problem with arrayList", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onTaskCompleted(ArrayList<String> response) {
        Toast.makeText(getApplicationContext(), response.get(0), Toast.LENGTH_LONG).show();
    }
}
