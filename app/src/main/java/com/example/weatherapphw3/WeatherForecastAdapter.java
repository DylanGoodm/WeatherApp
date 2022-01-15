package com.example.weatherapphw3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeatherForecastAdapter extends ArrayAdapter<List> {


    public WeatherForecastAdapter(@NonNull Context context, int resource, @NonNull ArrayList<List> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.forecast_row_item, parent, false);
        }
        List response = getItem(position);

        //grabs all textViews that need to be set
        TextView forecastTime = convertView.findViewById(R.id.forecastTime);
        TextView forecastTemp = convertView.findViewById(R.id.forecastTemp);
        TextView forecastTempMax = convertView.findViewById(R.id.forecastMaxTemp);
        TextView forecastTempMin = convertView.findViewById(R.id.forecastMinTemp);
        TextView forecastHumidity = convertView.findViewById(R.id.forecastHumidity);
        TextView forecastDescription = convertView.findViewById(R.id.forecastDescription);
        ImageView forecastWeatherIcon = convertView.findViewById(R.id.forecastWeatherIcon);

        //setting all textViews with data from the list item
        forecastTime.setText(response.getDt_txt());
        forecastTemp.setText(response.main.getTemp() + " F");
        forecastTempMax.setText("Max: " + response.main.getTemp_max() + " F");
        forecastTempMin.setText("Min: " + response.main.getTemp_min() + " F");
        forecastHumidity.setText("Humidity: " + response.main.getHumidity() + "%");
        forecastDescription.setText(String.valueOf(response.weather.get(0).getDescription()));

        //getting icon data from item to use in Picasso call
        String icon = response.weather.get(0).getIcon();

        Picasso.get()
                .load("https://openweathermap.org/img/wn/" + icon + "@2x.png")
                .into(forecastWeatherIcon);



        return convertView;
    }
}
