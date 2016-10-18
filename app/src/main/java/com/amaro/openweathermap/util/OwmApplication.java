package com.amaro.openweathermap.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by amaro on 17/10/16.
 */

public class OwmApplication extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }

    public static boolean checkInternetConnection() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

}
