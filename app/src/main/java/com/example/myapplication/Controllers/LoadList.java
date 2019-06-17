package com.example.myapplication.Controllers;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.crypto.spec.DESedeKeySpec;

import static android.content.ContentValues.TAG;


public class LoadList extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> degrees, mPhrase, desc, minTemp, maxTemp, imageArray;
    private final int layout;

    /**
     * Constructor that takes in parameters
     * that are initialized with the local
     * variables to be executed
     *
     *
     * @param context
     * @param degrees
     * @param minTemp
     * @param maxTemp
     * @param mPhrase
     * @param desc
     * @param imageArray
     * @param layout
     */
    public LoadList(Activity context, ArrayList<String>  degrees, ArrayList<String>  minTemp, ArrayList<String>  maxTemp, ArrayList<String>  mPhrase, ArrayList<String>  desc, ArrayList<String>  imageArray, int layout) {
        super(context, layout, degrees);
        this.context=context;
        this.degrees=degrees;
        this.mPhrase=mPhrase;
        this.desc=desc;
        this.minTemp=minTemp;
        this.maxTemp=maxTemp;
        this.imageArray=imageArray;
        this.layout=layout;
    }


    // getView will allow to push
    // the information into the views
    public View getView(int position,View view,ViewGroup parent) {

        // Inflating the layouts
        LayoutInflater inflater = context.getLayoutInflater();

        // Initialising the views for the layout
        View rowView = inflater.inflate(layout, null,true);

        // Getting a reference of each of the view
        TextView degreesText =  rowView.findViewById(R.id.degreesText);
        TextView phraseText =  rowView.findViewById(R.id.phraseText);
        TextView minTempText =  rowView.findViewById(R.id.minTemp);
        TextView maxTempText =  rowView.findViewById(R.id.maxTemp);
        TextView descriptionText =  rowView.findViewById(R.id.description);
        TextView weekDay =  rowView.findViewById(R.id.weekDay);
        ImageView imageView = rowView.findViewById(R.id.profile);

        Typeface face = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Regular.ttf");
        degreesText.setTypeface(face);
        phraseText.setTypeface(face);
        minTempText.setTypeface(face);
        maxTempText.setTypeface(face);
        weekDay.setTypeface(face);
        descriptionText.setTypeface(face);

        degreesText.setShadowLayer(3, 5, 5, Color.BLACK);
        phraseText.setShadowLayer(2, 3, 3, Color.BLACK);
        minTempText.setShadowLayer(2, 3, 3, Color.BLACK);
        maxTempText.setShadowLayer(2, 3, 3, Color.BLACK);
        descriptionText.setShadowLayer(2, 3, 3, Color.BLACK);
        weekDay.setShadowLayer(2, 3, 3, Color.BLACK);

        // Making sure the main information is there
        if(degrees.get(position) != null){
            degreesText.setText(degrees.get(position) + "°C");
            phraseText.setText(mPhrase.get(position));
            minTempText.setText("L: " + minTemp.get(position) + "°C");
            maxTempText.setText("H: " + maxTemp.get(position) + "°C");
            descriptionText.setText(desc.get(position));

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy");
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, position);
            String day = sdf.format(calendar.getTime());
            weekDay.setText(day);
            
            // Used Picasso Library to download images, refactor images and to add them to imageViews
            Picasso.get()
                    .load("https://openweathermap.org/img/w/" + imageArray.get(position) + ".png")
                    .into(imageView);
        }

        // Returning the complete ViewGroup
        return rowView;
    }
}