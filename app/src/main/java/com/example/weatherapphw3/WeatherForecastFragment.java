package com.example.weatherapphw3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class WeatherForecastFragment extends Fragment {

    ListView forecastList;
    TextView forecastCityName;
    ArrayList<List> forecastResponses;
    WeatherForecastAdapter adapter;

    private final OkHttpClient client = new OkHttpClient();

    private static final String ARG_CITY = "ARG_CITY";

    private Data.City city;


    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(Data.City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = (Data.City) getArguments().getSerializable(ARG_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        getActivity().setTitle(R.string.weather_forecast);

        forecastCityName = view.findViewById(R.id.forecastCityName);
        forecastCityName.setText(new StringBuilder().append(city.getCity()).append(",").append(city.getCountry()).toString());

        //call to the forecast api function
        getForecast();
        return view;
    }

    //function creates a url using okhttp, uses that url to take the JSON data from openweather api
    //and uses a custom listView to display the data called
    public void getForecast(){
        //creates a url using the HttpUrl builder
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.openweathermap.org")
                .addPathSegments("data/2.5/forecast")
                .addQueryParameter("q", city.getCity())
                .addQueryParameter("units", "imperial")
                .addQueryParameter("APPID", "9371afccce3e0819cdc64281e35e259f")
                .build();
        Log.d("TAG1", url.toString());

        //creates a new request to access the url that was just built
        Request request = new Request.Builder()
                .url(url.toString())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //stops the program if the response is not successful
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }

                    //builds new Gson and creates a new response handler class which will hold all data given by the api call
                    Gson gson = new Gson();
                    ForecastResponse forecastResponse = gson.fromJson(response.body().charStream(), ForecastResponse.class);
                    forecastResponses = forecastResponse.getList();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //grabs a list view and sets a custom array adapter to it
                            forecastList = getView().findViewById(R.id.forecastList);
                            adapter = new WeatherForecastAdapter((getContext()), R.layout.forecast_row_item, forecastResponses);
                            forecastList.setAdapter(adapter);


                        }
                    });

                }
            }
        });
    }
}