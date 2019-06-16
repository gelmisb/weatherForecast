package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Controllers.GetWeather;

public class MainActivity extends AppCompatActivity {

    private EditText city;
    private Button submit;
    private ListView listView;

    private String cityText;

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
        city = findViewById(R.id.cityText);
        submit = findViewById(R.id.submit);
        listView =  findViewById(R.id.list);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If there is text within the field
                if(city.getText() != null) {

                    // Executing the thread
                    cityText = city.getText().toString();
                    new GetWeather(MainActivity.this, getApplicationContext(), cityText, listView, "16 days").execute();
                }
            }
        });
    }
}
