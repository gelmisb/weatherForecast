package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CurrentWeather extends AppCompatActivity {

    // @TODO In this Activity it should display the current weather of the current location
    // When the user will try to see another city,
    // they can type the city name and add it,
    // the application will then send the user to the 16 day
    // forecast


    private TextView cityNameText, timeText, dateText, phraseText, currentTempText, minTempText, maxTempText, desctiptionText;


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
        setContentView(R.layout.activity_current_weather);

        //  Fixed Portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initialiseVars();

        // 1. Create a request to get the weather updates
        // 2. Add the data and parse the JSON
        // 3. Put the data into the fields
        // 4. Customization will follow
        // 5. Submit


    }


    private void initialiseVars() {
        currentTempText = findViewById(R.id.currentTemp);
        cityNameText = findViewById(R.id.city);
        timeText = findViewById(R.id.time);
        dateText = findViewById(R.id.dateText);
        phraseText = findViewById(R.id.phraseText);
        desctiptionText = findViewById(R.id.desc);
    }
}
