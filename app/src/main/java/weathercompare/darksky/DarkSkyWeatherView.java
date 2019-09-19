package cl.ceisufro.weathercompare.darksky;


import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import cl.ceisufro.weathercompare.main.ListWeatherView;

public interface DarkSkyWeatherView extends ListWeatherView{
    void showError(String error);
    void displayWeather();


    void populateWeatherList(WeatherResponse response);

    void showProgress();
    void hideProgress();
    void showLayout();
    void hideLayout();
}
