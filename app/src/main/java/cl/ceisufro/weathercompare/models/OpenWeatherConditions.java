package cl.ceisufro.weathercompare.models;

import io.realm.RealmObject;

public class OpenWeatherConditions extends RealmObject {
//    private Date date;
    private int dateInTimestamp;

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    private int currentTemp;
    private float tempMax;
    private float tempMin;
    private float tempDay;
    private float tempNight;
    private float tempEve;
    private float tempMorn;
    private float pressure;
    private float humididty;
    private int weatherId;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;
    private float speed;
    private int deg;
    private int clouds;
    private float rain;

    public OpenWeatherConditions() {
    }

    public OpenWeatherConditions(int dateInTimestamp, int currentTemp, float tempMax, float tempMin, float tempDay, float tempNight, float tempEve, float tempMorn, float pressure, float humididty, int weatherId, String weatherMain, String weatherDescription, String weatherIcon, float speed, int deg, int clouds, float rain) {
        this.dateInTimestamp = dateInTimestamp;
        this.currentTemp = currentTemp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.tempDay = tempDay;
        this.tempNight = tempNight;
        this.tempEve = tempEve;
        this.tempMorn = tempMorn;
        this.pressure = pressure;
        this.humididty = humididty;
        this.weatherId = weatherId;
        this.weatherMain = weatherMain;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
        this.speed = speed;
        this.deg = deg;
        this.clouds = clouds;
        this.rain = rain;
    }

    public OpenWeatherConditions(int dateInTimestamp, float tempMax, float tempMin, float tempDay, float tempNight, float tempEve, float tempMorn, float pressure, float humididty, int weatherId, String weatherMain, String weatherDescription, String weatherIcon, float speed, int deg, int clouds) {
        this.dateInTimestamp = dateInTimestamp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.tempDay = tempDay;
        this.tempNight = tempNight;
        this.tempEve = tempEve;
        this.tempMorn = tempMorn;
        this.pressure = pressure;
        this.humididty = humididty;
        this.weatherId = weatherId;
        this.weatherMain = weatherMain;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
        this.speed = speed;
        this.deg = deg;
        this.clouds = clouds;
    }

    public OpenWeatherConditions(int dateInTimestamp, float tempMax, float tempMin, float tempDay, float tempNight, float tempEve, float tempMorn, float pressure, float humididty, int weatherId, String weatherMain, String weatherDescription, String weatherIcon, float speed, int deg, int clouds, float rain) {
        this.dateInTimestamp = dateInTimestamp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.tempDay = tempDay;
        this.tempNight = tempNight;
        this.tempEve = tempEve;
        this.tempMorn = tempMorn;
        this.pressure = pressure;
        this.humididty = humididty;
        this.weatherId = weatherId;
        this.weatherMain = weatherMain;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
        this.speed = speed;
        this.deg = deg;
        this.clouds = clouds;
        this.rain = rain;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    public int getDateInTimestamp() {
        return dateInTimestamp;
    }

    public void setDateInTimestamp(int dateInTimestamp) {
        this.dateInTimestamp = dateInTimestamp;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempDay() {
        return tempDay;
    }

    public void setTempDay(float tempDay) {
        this.tempDay = tempDay;
    }

    public float getTempNight() {
        return tempNight;
    }

    public void setTempNight(float tempNight) {
        this.tempNight = tempNight;
    }

    public float getTempEve() {
        return tempEve;
    }

    public void setTempEve(float tempEve) {
        this.tempEve = tempEve;
    }

    public float getTempMorn() {
        return tempMorn;
    }

    public void setTempMorn(float tempMorn) {
        this.tempMorn = tempMorn;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumididty() {
        return humididty;
    }

    public void setHumididty(float humididty) {
        this.humididty = humididty;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public float getRain() {
        return rain;
    }

    public void setRain(float rain) {
        this.rain = rain;
    }
}
