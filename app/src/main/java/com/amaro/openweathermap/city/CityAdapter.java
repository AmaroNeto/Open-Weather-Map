package com.amaro.openweathermap.city;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amaro.openweathermap.R;

import java.util.List;

/**
 * Classe que define um adapter para uma lista de cidades.
 *
 * Created by amaro on 15/10/16.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder>{


    private Context context;
    private List<City> mCities;

    public CityAdapter(Context ctx){
        context = ctx;
    }

    public CityAdapter(Context ctx, List<City> cities){
        context = ctx;
        mCities = cities;
    }

    public void setFileList(List<City> files){
        mCities = files;
    }

    @Override
    public CityAdapter.CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.city_list_item,parent,false);

        CityViewHolder holder = new CityViewHolder(v);
        v.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {

        City obj = mCities.get(position);

        holder.title.setText( obj.getName() );
        holder.data = obj;

    }

    public City getItemAtPosition(int position) {
        return mCities.get(position);
    }

    @Override
    public int getItemCount() {
        return mCities != null ? mCities.size() : 0;
    }

    class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{


        TextView title;
        City data;

        public CityViewHolder(View parent){
            super(parent);

            title = (TextView) parent.findViewById(R.id.city_title);


        }


        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
