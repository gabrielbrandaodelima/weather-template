package cl.ceisufro.weathercompare.openweather;

public interface OpenWeatherView {
    void showError(String error);
    void displayWeather();

    void populateWeatherList(String response);

    void showProgress();
    void hideProgress();
    void showLayout();
    void hideLayout();

    void getCurrentTemp(String response);
}
