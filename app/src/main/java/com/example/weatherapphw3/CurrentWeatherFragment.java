package com.example.weatherapphw3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class CurrentWeatherFragment extends Fragment {

    private static final String ARG_CITY = "ARG_CITY";

    private final OkHttpClient client = new OkHttpClient();

    private Data.City city;

    TextView titleCurrentWeather;
    TextView tempData;
    TextView maxData;
    TextView minData;
    TextView descriptData;
    TextView humidityData;
    TextView windSpeedData;
    TextView windDegreeData;
    TextView cloudinessData;
    ImageView currentWeatherIcon;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance(Data.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
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
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        getActivity().setTitle(R.string.current_weather);

        titleCurrentWeather = view.findViewById(R.id.titleCurrentWeather);
        titleCurrentWeather.setText(new StringBuilder().append(city.getCity()).append(",").append(city.getCountry()).toString());

        //call to weather api function
        try{
            getWeather();
        }catch (Exception e) {
            e.printStackTrace();
        }

        view.findViewById(R.id.checkForecastButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToForecast(city);
            }
        });


        return view;
    }

    public void getWeather() throws Exception{
        //creates a url using the HttpUrl builder
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.openweathermap.org")
                .addPathSegments("data/2.5/weather")
                .addQueryParameter("q", city.getCity())
                .addQueryParameter("units", "imperial")
                .addQueryParameter("APPID", "9371afccce3e0819cdc64281e35e259f")
                .build();

        Log.d("TAG", url.toString());
        //creates a new request to access the url that was just built
        Request request = new Request.Builder()
                .url(url.toString())
                .build();


        //uses the request to call the api
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //stops the program if the response is not successful
                try (ResponseBody responseBody = response.body()) {
                    if(!response.isSuccessful()){
                        throw new IOException("Unexpected code " + response);
                    }

                    //builds new Gson and creates a new response handler class which will hold all data given by the api call
                    Gson gson = new Gson();
                    CurrentWeatherResponse currentWeatherResponse = gson.fromJson(response.body().charStream(), CurrentWeatherResponse.class);

                    //grabbing all textViews
                    tempData = getView().findViewById(R.id.tempData);
                    maxData = getView().findViewById(R.id.maxData);
                    minData = getView().findViewById(R.id.minData);
                    descriptData = getView().findViewById(R.id.descriptData);
                    humidityData = getView().findViewById(R.id.humidityData);
                    windSpeedData = getView().findViewById(R.id.windSpeedData);
                    windDegreeData = getView().findViewById(R.id.windDegreeData);
                    cloudinessData = getView().findViewById(R.id.cloudinessData);
                    currentWeatherIcon = getView().findViewById(R.id.currentWeatherIcon);
                    //get the icon data from openweather that will be used in the Picasso call
                    String icon = currentWeatherResponse.weather.get(0).getIcon();




                    //changing the text fields on the UI thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //setting all textViews using the called data inside currentWeatherResponse
                            tempData.setText(String.valueOf(currentWeatherResponse.main.getTemp()) + " F");
                            maxData.setText(String.valueOf(currentWeatherResponse.main.getTemp_max()) + " F");
                            minData.setText(String.valueOf(currentWeatherResponse.main.getTemp_min()) + " F");
                            descriptData.setText(currentWeatherResponse.weather.get(0).getDescription());
                            humidityData.setText(String.valueOf(currentWeatherResponse.main.getHumidity()) + "%");
                            windSpeedData.setText(String.valueOf(currentWeatherResponse.wind.getSpeed()) + " miles/hr");
                            windDegreeData.setText(String.valueOf(currentWeatherResponse.wind.getDeg()) + " degrees");
                            cloudinessData.setText(String.valueOf(currentWeatherResponse.clouds.getAll()) + "%");

                            //load png into the weather icon using Picasso
                            Picasso.get()
                                    .load("https://openweathermap.org/img/wn/" + icon + "@2x.png")
                                    .into(currentWeatherIcon);
                            Log.d("TAG", "https://openweathermap.org/img/wn/" + icon + "@2x.png");

                        }
                    });




                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof CurrentWeatherFragment.ICurrentWeatherFragmentListener) {
            mListener = (CurrentWeatherFragment.ICurrentWeatherFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }


    CurrentWeatherFragment.ICurrentWeatherFragmentListener mListener;

    public interface ICurrentWeatherFragmentListener {
        void goToForecast(Data.City city);
    }


}

