package com.example.weatherapphw3;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CitiesArrayAdapter extends ArrayAdapter<Data.City> {

    public CitiesArrayAdapter(@NonNull Context context, int resource, @NonNull List<Data.City> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.city_row_layout, parent, false);
        }
        //takes the city at a particular position of the list and sets its name
        Data.City city = getItem(position);
        TextView citiesName = convertView.findViewById(R.id.cityName);
        citiesName.setText(new StringBuilder().append(city.getCity()).append(",").append(city.getCountry()).toString());

        return convertView;
    }
}
