package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Location.AppLocationService;
import com.example.myapplication.Model.Weather;

import java.util.ArrayList;

public class CurrentWeather extends AppCompatActivity implements OnTaskCompleted{

    // @TODO In this Activity it should display the current weather of the current location
    // When the user will try to see another city,
    // they can type the city name and add it,
    // the application will then send the user to the 16 day
    // forecast

    public TextView cityNameText, timeText, dateText, phraseText, currentTempText, minTempText, maxTempText, descriptionText;
    private AppLocationService appLocationService;


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
        phraseText = findViewById(R.id.phrase);
        descriptionText = findViewById(R.id.desc);
        minTempText = findViewById(R.id.minTempText);
        maxTempText = findViewById(R.id.maxTemptext);

        appLocationService = new AppLocationService(
                CurrentWeather.this, CurrentWeather.this);

    }

    @Override
    public void onTaskCompleted(Weather weather) {

        Log.i("Weather", weather.getCity() + " ");

//        private String phrase;
//        private String description;
//        private String currentTemp;
//        private String minTemp;
//        private String maxTemp;
//        private String pressure;
//        private String humidity;
//        private String windSpeed;
//        private String cloudiness;
//        private String country;
//        private String city;
//        private String sunrise;
//        private String sunset;
//        private String icon;


        phraseText.setText(weather.getPhrase());
        descriptionText.setText(weather.getDescription());
        currentTempText.setText(weather.getCurrentTemp());
        cityNameText.setText(weather.getCity());
        minTempText.setText(weather.getMinTemp());
        maxTempText.setText(weather.getMaxTemp());
    }
}
