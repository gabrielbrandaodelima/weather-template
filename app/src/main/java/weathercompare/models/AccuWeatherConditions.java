package cl.ceisufro.weathercompare.models;

import io.realm.RealmObject;

public class AccuWeatherConditions extends RealmObject {
    private int date;
    private int currentTemp;
    private int tempMax;
    private int tempMin;
    private String dayPhrase;
    private String nightPhrase;

    public AccuWeatherConditions() {
    }

    public AccuWeatherConditions(int date, int tempMax, int tempMin, String dayPhrase, String nightPhrase) {
        this.date = date;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.dayPhrase = dayPhrase;
        this.nightPhrase = nightPhrase;
    }

    public AccuWeatherConditions(int date, int currentTemp, int tempMax, int tempMin, String dayPhrase, String nightPhrase) {
        this.date = date;
        this.currentTemp = currentTemp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.dayPhrase = dayPhrase;
        this.nightPhrase = nightPhrase;
    }



    public String getDayPhrase() {
        return dayPhrase;
    }

    public void setDayPhrase(String dayPhrase) {
        this.dayPhrase = dayPhrase;
    }

    public String getNightPhrase() {
        return nightPhrase;
    }

    public void setNightPhrase(String nightPhrase) {
        this.nightPhrase = nightPhrase;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
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
