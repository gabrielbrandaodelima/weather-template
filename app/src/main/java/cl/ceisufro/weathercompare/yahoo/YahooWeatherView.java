package cl.ceisufro.weathercompare.yahoo;


public interface YahooWeatherView {
    void showError(String error);
    void displayWeather();


    void populateWeatherList(String response);

    void showProgress();
    void hideProgress();
    void showLayout();
    void hideLayout();
}
