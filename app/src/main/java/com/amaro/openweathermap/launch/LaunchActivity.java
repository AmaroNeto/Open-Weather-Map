package com.amaro.openweathermap.launch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.amaro.openweathermap.R;
import com.amaro.openweathermap.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by amaro on 16/10/16.
 */

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);




        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {



                if(ActivityCompat.checkSelfPermission(LaunchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LaunchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){


                    ActivityCompat.requestPermissions(LaunchActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                            1);

                }else{

                    Intent it = new Intent();
                    it.setClass(LaunchActivity.this, MainActivity.class);
                    startActivity(it);
                    finish();

                }



            }
        }, 2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent it = new Intent();
                    it.setClass(LaunchActivity.this, MainActivity.class);
                    startActivity(it);
                    finish();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(LaunchActivity.this, "Some Permission was denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

}
