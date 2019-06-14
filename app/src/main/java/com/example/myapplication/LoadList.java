package com.example.myapplication;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoadList extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> degrees, mPhrase, desc, minTemp, maxTemp, imageArray;
    private final int layout;


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

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(layout, null,true);
        
        TextView degreesText =  rowView.findViewById(R.id.degreesText);
        TextView phraseText =  rowView.findViewById(R.id.phraseText);
        TextView minTempText =  rowView.findViewById(R.id.minTemp);
        TextView maxTempText =  rowView.findViewById(R.id.maxTemp);
        TextView descriptionText =  rowView.findViewById(R.id.description);
        ImageView imageView = rowView.findViewById(R.id.profile);

        Log.i("Degress", degrees.get(position) + "   " + position);

        if(degrees.get(position) != null){
            degreesText.setText(degrees.get(position) + "°C");
            phraseText.setText(mPhrase.get(position));
            minTempText.setText(minTemp.get(position) + "°C");
            maxTempText.setText(maxTemp.get(position) + "°C");
            descriptionText.setText(desc.get(position));

            Picasso.get()
                    .load("https://openweathermap.org/img/w/" + imageArray.get(position) + ".png")
                    .resize(50, 50)
                    .centerCrop()
                    .into(imageView);
        }


        return rowView;
    }
}