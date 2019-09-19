package cl.ceisufro.weathercompare.accuweather;

import cl.ceisufro.weathercompare.main.ListWeatherView;

public interface AccuWeatherView extends ListWeatherView {
    void showError(String error);
    void displayWeather();

    void populateWeatherList(String response);

    void showProgress();
    void hideProgress();
    void showLayout();
    void hideLayout();

    void getCurrentTemp(String response);
}
