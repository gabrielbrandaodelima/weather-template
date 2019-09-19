package weathercompare.models;

import io.realm.RealmObject;

public class DarkSkyWeatherConditions extends RealmObject {
    private int date;
    private int currentTemp;
    private int temperatureHigh;
    private int temperatureLow;
    private String summary;
    private String icon;
    private String pressure;
    private String humididty;
    private String speed;

    public DarkSkyWeatherConditions() {
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

    public int getTemperatureHigh() {
        return temperatureHigh;
    }

    public void setTemperatureHigh(int temperatureHigh) {
        this.temperatureHigh = temperatureHigh;
    }

    public int getTemperatureLow() {
        return temperatureLow;
    }

    public void setTemperatureLow(int temperatureLow) {
        this.temperatureLow = temperatureLow;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumididty() {
        return humididty;
    }

    public void setHumididty(String humididty) {
        this.humididty = humididty;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public DarkSkyWeatherConditions(int date, int temperatureHigh, int temperatureLow, String summary, String icon, String pressure, String humididty, String speed) {
        this.date = date;
        this.temperatureHigh = temperatureHigh;
        this.temperatureLow = temperatureLow;
        this.summary = summary;
        this.icon = icon;
        this.pressure = pressure;
        this.humididty = humididty;
        this.speed = speed;
    }

    public DarkSkyWeatherConditions(int date, int currentTemp, int temperatureHigh, int temperatureLow, String summary, String icon, String pressure, String humididty, String speed) {
        this.date = date;
        this.currentTemp = currentTemp;
        this.temperatureHigh = temperatureHigh;
        this.temperatureLow = temperatureLow;
        this.summary = summary;
        this.icon = icon;
        this.pressure = pressure;
        this.humididty = humididty;
        this.speed = speed;
    }

}
