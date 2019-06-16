package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import com.example.myapplication.Controllers.GetWeather;
import com.example.myapplication.Location.AppLocationService;
import com.example.myapplication.Model.Weather;

import java.util.Date;

public class CurrentWeather extends AppCompatActivity implements OnTaskCompleted{

    // @TODO In this Activity it should display the current weather of the current location
    // When the user will try to see another city,
    // they can type the city name and add it,
    // the application will then send the user to the 16 day
    // forecast

    public TextView cityNameText, timeText, dateText, phraseText, currentTempText, minTempText, maxTempText, descriptionText, pressureText, humidityText, windText, cloudinessText, countryText, sunriseText, sunsetText;
    private AppLocationService appLocationService;
    private ConstraintLayout base;


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
        maxTempText = findViewById(R.id.maxTempText);
        pressureText = findViewById(R.id.pressureText);
        humidityText = findViewById(R.id.humidityText);
        windText = findViewById(R.id.windText);
        cloudinessText = findViewById(R.id.cloudinessText);
        countryText = findViewById(R.id.country);
        sunriseText = findViewById(R.id.sunriseText);
        sunsetText = findViewById(R.id.sunsetText);
        base = findViewById(R.id.base);

        appLocationService = new AppLocationService(
                CurrentWeather.this, CurrentWeather.this);

    }

    @Override
    public void onTaskCompleted(Weather weather) {

//        var sec = 1425909686;
//        var date = new Date(sec * 1000);
//        var timestr = date.toLocaleTimeString();


        double temp1 = Double.parseDouble(weather.getSunrise());
        Date sunDate = new Date((long) (temp1 * 1000));
        String sunrise = sunDate.getHours() + ":" + sunDate.getMinutes();

        double temp2 = Double.parseDouble(weather.getSunset());
        Date sunsetDate = new Date((long) (temp2 * 1000));
        String sunset = sunsetDate.getHours() + ":" + sunsetDate.getMinutes();

        double tempVal = Double.parseDouble(weather.getCurrentTemp());
        int value = (int) tempVal; // 6 int score = (int) 6.99; // 6

        phraseText.setText(weather.getPhrase());
        descriptionText.setText(weather.getDescription());
        currentTempText.setText(value + "°C");
        minTempText.setText("Lowest temp.: " + weather.getMinTemp() + "°C");
        maxTempText.setText("Highest temp.: " + weather.getMaxTemp() + "°C");
        pressureText.setText("Pressure: " + weather.getPressure() + " hPa");
        humidityText.setText("Humidity: " + weather.getHumidity() + "%");
        windText.setText("Wind speed: " + weather.getWindSpeed() + "m/s");
        cloudinessText.setText("Cloud coverage: " + weather.getCloudiness() + "%");
        countryText.setText(weather.getCountry());
        cityNameText.setText(weather.getCity());
        sunriseText.setText("Sunrise: " + sunrise);
        sunsetText.setText("Sunset: " + sunset);

        setWalls(weather.getPhrase());


    }

    private void setWalls(String type) {
        if(type.contains("rain")){
            base.setBackgroundResource(R.drawable.rainy);
        } else if(type.contains("snow")){
            base.setBackgroundResource(R.drawable.snowy);
        } else if(type.contains("clouds")) {
            base.setBackgroundResource(R.drawable.default_weather);
        } else {
            base.setBackgroundResource(R.drawable.cloudy);
        }
    }
    // Credits - rainy.jpg (Photo by Max Bender on Unsplash)
    // Credits - cloudy.jpg (Photo by John Westrock on Unsplash)
    // Credits - cloudy2.jpg (Photo by John Westrock on Unsplash)
    // Credits - sunny.jpg (Photo by Jorge Vasconez on Unsplash)
    // Credits - snowy.jpg (Photo by Benjamin Raffetseder on Unsplash)
    // Credits - default.jpg (Photo by Nathan Dumlao on Unsplash)

}



