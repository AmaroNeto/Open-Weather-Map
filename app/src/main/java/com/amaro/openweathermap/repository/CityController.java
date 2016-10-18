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
    private List<City> historic;

    private CityController(){
        cities = new ArrayList<City>();
        historic = new ArrayList<City>();
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

    public List<City> getAllHistoricCities(){
        return historic;
    }

    public void cleanCities(){
        cities.clear();
    }


    public void addCityToHistoric(City city){

        historic.add(city);
    }

    public void deleteCityfromHistoric(String id){
        City cityToRemove = null;

        for(City city : historic){

            if(city.getId().equals(id)){
                cityToRemove = city;
            }

        }

        if(cityToRemove != null){
            historic.remove(cityToRemove);
        }
    }

    public boolean existInHistoric(String id){


        for(City city : historic){

            if(city.getId().equals(id)){
                return true;
            }

        }

        return false;
    }

}
