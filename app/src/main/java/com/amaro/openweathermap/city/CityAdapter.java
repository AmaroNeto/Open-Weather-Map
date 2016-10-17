package com.amaro.openweathermap.city;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public void onBindViewHolder(final CityViewHolder holder, int position) {

        final City obj = mCities.get(position);

        holder.title.setText( obj.getName() );
        //holder.icon.setImageResource(obj.getIcon());
        holder.data = obj;

        final ImageView icon = holder.icon;

        //Carrega os icones em background para não sobrecarregar a main
        holder.loadImage = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                return BitmapFactory.decodeStream(context.getResources().openRawResource(obj.getIcon()), null, options);
            }
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                icon.setImageBitmap(bitmap);
            }
        };
        holder.loadImage.execute();

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
        ImageView icon;
        AsyncTask<Void, Void, Bitmap> loadImage;

        City data;

        public CityViewHolder(View parent){
            super(parent);

            title = (TextView) parent.findViewById(R.id.city_title);
            icon = (ImageView) parent.findViewById(R.id.icon);

            parent.setOnClickListener(this);
            parent.setOnLongClickListener(this);

        }


        @Override
        public void onClick(View v) {

            Log.d("OWM","Clicou "+data.getName());

            //Vai para a pagina de detalhes
            Intent it = new Intent(context,CityDetailActivity.class);
            it.putExtra("CITY",data);

            //Animação
            View sharedView = icon;
            String transitionName = context.getString(R.string.transition_icon_image);
            ActivityOptions transitionActivityOptions = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, sharedView, transitionName);
            }

            context.startActivity(it,transitionActivityOptions.toBundle());

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
