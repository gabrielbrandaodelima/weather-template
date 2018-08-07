package cl.ceisufro.weathercompare.models;

import java.util.Date;

import io.realm.RealmObject;

public class AccuWeatherConditions extends RealmObject {
    private Date id;
    private String date;
    private int currentTemp;
    private int tempMax;
    private int tempMin;

    public AccuWeatherConditions() {
    }

    public AccuWeatherConditions(Date id, String date, int currentTemp, int tempMax, int tempMin) {
        this.id = id;
        this.date = date;
        this.currentTemp = currentTemp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    public Date getId() {
        return id;
    }

    public void setId(Date id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

}
