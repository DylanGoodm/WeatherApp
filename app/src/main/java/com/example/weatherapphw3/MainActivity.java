package com.example.weatherapphw3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements CitiesFragment.ICitiesFragmentListener, CurrentWeatherFragment.ICurrentWeatherFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new CitiesFragment())
                .commit();
    }

    @Override
    public void goToCurrentWeather(Data.City city) {
        Log.d("TAG", city.getCity());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, CurrentWeatherFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToForecast(Data.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, WeatherForecastFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }
}