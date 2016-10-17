package com.amaro.openweathermap.util;

import com.amaro.openweathermap.R;

/**
 * Created by amaro on 16/10/16.
 */

public class Util {


    /**
     * Converte de Fahrenheit para Celsius
     * @param fah
     * @return
     */
    public static Double FahrenheitToCelsius(Double fah )
    {
        return  ((fah - 32) * 5) / 9;


    }

    /**
     * Referencia o icone correspondente em int no android.
     * @return
     */
    public static int iconStringToIconInt(String icon){

        int result = R.mipmap._15;

        if(icon.equals("01d")){

            result = R.mipmap._33;

        }else if(icon.equals("02d")){

            result = R.mipmap._32;

        }else if(icon.equals("03d")){
            result = R.mipmap._37;

        }else if(icon.equals("04d")){

            result = R.mipmap._37;

        }else if(icon.equals("09d")){
            result = R.mipmap._34;

        }else if(icon.equals("10d")){
            result = R.mipmap._11;

        }else if(icon.equals("11d")){
            result = R.mipmap._18;

        }else if(icon.equals("13d")){
            result = R.mipmap._24;

        }else if(icon.equals("50d")){
            result = R.mipmap._30;

        }else if(icon.equals("01n")){
            result = R.mipmap._38;

        }else if(icon.equals("02n")){
            result = R.mipmap._37;

        }else if(icon.equals("03n")){
            result = R.mipmap._37;

        }else if(icon.equals("04n")){
            result = R.mipmap._37;

        }else if(icon.equals("09n")){
            result = R.mipmap._34;
        }else if(icon.equals("10n")){
            result = R.mipmap._12;

        }else if(icon.equals("11n")){
            result = R.mipmap._23;

        }else if(icon.equals("13n")){
            result = R.mipmap._17;
        }else if(icon.equals("50n")){
            result = R.mipmap._30;
        }

        return result;
    }
}
