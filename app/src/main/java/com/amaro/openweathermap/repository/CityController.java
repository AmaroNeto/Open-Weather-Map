package com.amaro.openweathermap.repository;

import com.amaro.openweathermap.city.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio (Volatil) de Cidades
 *
 * Created by amaro on 16/10/16.
 */

public class CityController {

    private static CityController instance;
    private List<City> cities;
    private List<City> favorites;

    private CityController(){
        cities = new ArrayList<City>();
        favorites = new ArrayList<City>();
    }

    public static CityController getInstance(){

        if(instance == null){
           instance = new CityController();
        }

        return instance;
    }

    public void addCity(City city){
        cities.add(city);
    }

    public List<City> getAllCities(){
        return cities;
    }

    public void cleanCities(){
        cities.clear();
    }

}
