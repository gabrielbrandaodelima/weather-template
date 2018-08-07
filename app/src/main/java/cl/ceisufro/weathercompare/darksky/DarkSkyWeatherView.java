package cl.ceisufro.weathercompare.darksky;


import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

public interface DarkSkyWeatherView {
    void showError(String error);
    void displayWeather();


    void populateWeatherList(WeatherResponse response);

    void showProgress();
    void hideProgress();
    void showLayout();
    void hideLayout();
}
