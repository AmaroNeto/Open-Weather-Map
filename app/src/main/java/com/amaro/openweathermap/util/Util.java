package com.amaro.openweathermap.util;

/**
 * Created by amaro on 16/10/16.
 */

public class Util {


    /**
     * Converte de Fahrenheit para Celsius
     * @param fah
     * @return
     */
    public static float FahrenheitToCelsius(float fah )
    {
        return  ((fah - 32) * 5) / 9;


    }
}
