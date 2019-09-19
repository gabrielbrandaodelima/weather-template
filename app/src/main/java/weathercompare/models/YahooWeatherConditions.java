package cl.ceisufro.weathercompare.models;

import io.realm.RealmObject;

public class YahooWeatherConditions extends RealmObject {
//    private Date id;
    private String date;
    private String day;
    private String text;
    private int code;
    private int currentTemp;
    private int tempMax;
    private int tempMin;

    public YahooWeatherConditions(String date, String day, String text, int code, int tempMax, int tempMin) {
        this.date = date;
        this.day = day;
        this.text = text;
        this.code = code;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    public YahooWeatherConditions(String date, String text, int code, int currentTemp) {
        this.date = date;
        this.text = text;
        this.code = code;
        this.currentTemp = currentTemp;
    }

    public YahooWeatherConditions(String date, String text, int code, int currentTemp, int tempMax, int tempMin) {
        this.date = date;
        this.text = text;
        this.code = code;
        this.currentTemp = currentTemp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public YahooWeatherConditions(String date, String day, String text, int code, int currentTemp, int tempMax, int tempMin) {

        this.date = date;
        this.day = day;
        this.text = text;
        this.code = code;
        this.currentTemp = currentTemp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    public YahooWeatherConditions() {
    }


}
