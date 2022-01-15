package com.example.weatherapphw3;

public class Main {
    float temp, temp_min, temp_max, humidity;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Main{" +
                "temp=" + temp +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                ", humidity=" + humidity +
                '}';
    }
}
