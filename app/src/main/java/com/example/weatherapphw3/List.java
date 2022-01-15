package com.example.weatherapphw3;

import java.util.ArrayList;

public class List {

    int dt;
    Main main;
    ArrayList<Weather> weather;
    Clouds clouds;
    Wind wind;
    int visibility;
    float pop;
    Rain rain;
    Sys sys;
    String dt_txt;

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    @Override
    public String toString() {
        return "List{" +
                "dt=" + dt +
                ", main=" + main +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", wind=" + wind +
                ", visibility=" + visibility +
                ", pop=" + pop +
                ", rain=" + rain +
                ", sys=" + sys +
                ", dt_txt='" + dt_txt + '\'' +
                '}';
    }
}
