package com.amaro.openweathermap.util;

import android.app.Application;
import android.content.Context;

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


}
