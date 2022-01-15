package com.example.weatherapphw3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class CitiesFragment extends Fragment {

    ListView citiesList;
    CitiesArrayAdapter adapter;

    public CitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cities, container, false);

        getActivity().setTitle(R.string.cities);

        //grabs a list view and sets a custom array adapter to it
        citiesList = view.findViewById(R.id.citiesList);
        adapter = new CitiesArrayAdapter(getContext(), R.layout.city_row_layout, Data.cities);
        citiesList.setAdapter(adapter);


        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //gets the city that has been clicked in the list and extracts its name and country
                //and sends them to the main activity
                Data.City city = Data.cities.get(position);
                mListener.goToCurrentWeather(city);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ICitiesFragmentListener) {
            mListener = (ICitiesFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    ICitiesFragmentListener mListener;

    public interface ICitiesFragmentListener {
        void goToCurrentWeather(Data.City city);
    }
}