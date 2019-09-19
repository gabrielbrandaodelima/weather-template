package weathercompare.models;

import io.realm.RealmObject;

public class ApixuWeatherConditions extends RealmObject {
    private int currentTemp;
    private int tempMax;
    private int tempMin;
    private int date;
    private String conditionText;
    public ApixuWeatherConditions() {
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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public ApixuWeatherConditions(int tempMax, int tempMin, int date, String conditionText) {
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.date = date;
        this.conditionText = conditionText;
    }

    public ApixuWeatherConditions(int currentTemp, int tempMax, int tempMin, int date, String conditionText) {

        this.currentTemp = currentTemp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.date = date;
        this.conditionText = conditionText;
    }
}
