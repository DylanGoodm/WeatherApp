package com.example.weatherapphw3;

import java.util.ArrayList;

public class ForecastResponse {

    String cod;
    int message;
    int cnt;
    ArrayList<List> list;
    City city;

    public ArrayList<List> getList() {
        return list;
    }

    public void setList(ArrayList<List> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ForecastResponse{" +
                "cod='" + cod + '\'' +
                ", message=" + message +
                ", cnt=" + cnt +
                ", list=" + list +
                ", city=" + city +
                '}';
    }
}
