package com.amaro.openweathermap.city;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.amaro.openweathermap.R;

/**
 * Created by amaro on 15/10/16.
 */

public class CityDetailActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private City mCity;

    private ImageView thumbnail;
    private TextView main;
    private TextView description;
    private TextView temp_icon;
    private TextView temp;
    private TextView temp_max;
    private TextView temp_min;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_detail_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.partly_cloudy_icon);
        setSupportActionBar(toolbar);

        //Setando o botão home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mCity = (City) getIntent().getSerializableExtra("CITY");

        //linkando a view com a actvity
        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        main = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        temp = (TextView) findViewById(R.id.temp);
        temp_icon = (TextView) findViewById(R.id.temp_icon);
        temp_max = (TextView) findViewById(R.id.temp_max);
        temp_min = (TextView) findViewById(R.id.temp_min);

        if(mCity != null){
            toolbar.setTitle(mCity.getName());
            thumbnail.setImageResource(mCity.getIcon());
            main.setText(mCity.getTitle_description());
            description.setText(mCity.getDescription());
            temp.setText(String.format("%.2f", mCity.getTemp())+" c°");
            temp_icon.setText(String.format("%.2f", mCity.getTemp()));
            temp_max.setText(String.format("%.2f", mCity.getMax_temp())+" c°");
            temp_min.setText(String.format("%.2f", mCity.getMin_temp())+" c°");
        }



    }

}
