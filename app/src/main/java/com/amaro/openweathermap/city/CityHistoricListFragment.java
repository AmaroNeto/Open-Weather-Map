package com.amaro.openweathermap.city;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amaro.openweathermap.R;
import com.amaro.openweathermap.repository.CityController;
import com.amaro.openweathermap.util.DividerItemDecoration;

import java.util.List;

/**
 * Created by amaro on 17/10/16.
 */

public class CityHistoricListFragment  extends Fragment {

    private static final String TAG = "CityListFragment";

    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected CityAdapter mAdapter;

    private List<City> mCities;
    private CityController cityController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cityController = CityController.getInstance();

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.city_list, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.city_recycleview);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mAdapter = new CityAdapter(getActivity(),getmCities(),true);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public List<City> getmCities(){

        List<City> cities = cityController.getAllHistoricCities();
        return cities;

    }

    @Override
    public void onResume(){
        super.onResume();

        //mAdapter = new CityAdapter(getActivity(),getmCities());
        //mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
