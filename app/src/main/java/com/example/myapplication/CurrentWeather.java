package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myapplication.Controllers.GetWeather;
import com.example.myapplication.Location.AppLocationService;
import com.example.myapplication.Model.Weather;

import java.util.ArrayList;
import java.util.Date;

public class CurrentWeather extends AppCompatActivity implements OnTaskCompleted{

    // @TODO In this Activity it should display the current weather of the current location
    // When the user will try to see another city,
    // they can type the city name and add it,
    // the application will then send the user to the 16 day
    // forecast

    public TextView cityNameText, timeText, dateText, phraseText, currentTempText, minTempText, maxTempText, descriptionText, pressureText, humidityText, windText, cloudinessText, countryText, sunriseText, sunsetText;
    private AppLocationService appLocationService;
    private FrameLayout base;

    private ArrayList<TextView> allViews;


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

        // Initialising all the variables
        initialiseVars();

        // Setting the show for the textViews
        setTextShadow();

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
        base = findViewById(R.id.frameLayoutBase);
        allViews = new ArrayList<>();
        appLocationService = new AppLocationService(CurrentWeather.this, CurrentWeather.this);

        allViews.add(cityNameText);
        allViews.add(dateText);
        allViews.add(descriptionText);
        allViews.add(minTempText);
        allViews.add(maxTempText);
        allViews.add(pressureText);
        allViews.add(humidityText);
        allViews.add(windText);
        allViews.add(cloudinessText);
        allViews.add(countryText);
        allViews.add(sunriseText);
        allViews.add(sunsetText);
        allViews.add(phraseText);
    }

    @Override
    public void onTaskCompleted(Weather weather) {

        // Setting the background depending on weather conditions
        setWalls(weather.getPhrase());

        // Reformatting from mills to normal time
        double temp1 = Double.parseDouble(weather.getSunrise());
        Date sunDate = new Date((long) (temp1 * 1000));
        String sunrise = sunDate.getHours() + ":" + sunDate.getMinutes();

        // Reformatting from mills to normal time
        double temp2 = Double.parseDouble(weather.getSunset());
        Date sunsetDate = new Date((long) (temp2 * 1000));
        String sunset = sunsetDate.getHours() + ":" + sunsetDate.getMinutes();

        // Reformatting from double to int
        double tempVal = Double.parseDouble(weather.getCurrentTemp());
        int value = (int) tempVal;

        // Setting the values for the user
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
    }


    /**
     *
     * Setting the background of ConstraintLayout
     * depending on the weather conditions
     *
     *
     * Credits - rainy.jpg (Photo by Max Bender on Unsplash)
     * Credits - cloudy.jpg (Photo by John Westrock on Unsplash)
     * Credits - cloudy2.jpg (Photo by John Westrock on Unsplash)
     * Credits - sunny.jpg (Photo by Jorge Vasconez on Unsplash)
     * Credits - snowy.jpg (Photo by Benjamin Raffetseder on Unsplash)
     * Credits - default.jpg (Photo by Nathan Dumlao on Unsplash)
     * Credits - rainy2.jpg (Photo by Matthew Henry on Unsplash)
     *
     * @param type
     */
    private void setWalls(String type) {

        type = "Snow";

        if(type.contains("Rain")){
            base.setBackgroundResource(R.drawable.rainy);
        } else if(type.contains("Snow")){
            base.setBackgroundResource(R.drawable.snowy);
        } else if(type.contains("Clouds")) {
            base.setBackgroundResource(R.drawable.cloudy);
        } else {
            base.setBackgroundResource(R.drawable.default_weather);
        }
    }

    private void setTextShadow() {
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Raleway-Regular.ttf");
        Typeface main = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.ttf");
        currentTempText.setTypeface(face);
        currentTempText.setShadowLayer(5, 8, 8, Color.BLACK);

        for(int i = 0; i < allViews.size(); i++) {
            allViews.get(i).setShadowLayer(3, 5,5, Color.BLACK);
            allViews.get(i).setTypeface(face);
        }
    }
}



