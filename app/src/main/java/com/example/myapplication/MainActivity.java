package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.Controllers.GetWeather;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FrameLayout frameLayout;
    private TextView cityText, headingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making it full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            // inside your activity (if you did not enable transitions in your theme)
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            // set an exit transition
            getWindow().setExitTransition(new Slide());
        }

        // Setting flags from previous instance
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set current layout
        setContentView(R.layout.activity_main);

        //  Fixed Portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Views for details
        listView =  findViewById(R.id.list);
        frameLayout =  findViewById(R.id.frameLayoutBase);
        headingText =  findViewById(R.id.heading);
        cityText =  findViewById(R.id.cityName);

        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Raleway-Regular.ttf");
        cityText.setTypeface(face);
        headingText.setTypeface(face);
        headingText.setShadowLayer(2, 5, 5, Color.BLACK);
        cityText.setShadowLayer(6, 10, 10, Color.BLACK);



        String city = getIntent().getStringExtra("city");
        String phrase = getIntent().getStringExtra("phrase");

        cityText.setText(city);
        setWalls(phrase);

        new GetWeather(MainActivity.this, getApplicationContext(), city, listView, "16 days").execute();

    }

    public void setWalls(String type) {
        if(type.contains("Rain")){
            frameLayout.setBackgroundResource(R.drawable.rainy3);
        } else if(type.contains("Snow")){
            frameLayout.setBackgroundResource(R.drawable.snowy);
        } else if(type.contains("Clouds")) {
            frameLayout.setBackgroundResource(R.drawable.cloudy2);
        } else if(type.contains("Sun")) {
            frameLayout.setBackgroundResource(R.drawable.sunny);
        } else {
            frameLayout.setBackgroundResource(R.drawable.default_weather);
        }
    }
}
