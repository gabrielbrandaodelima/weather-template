package cl.ceisufro.weathercompare.main;


public interface ListWeatherView {
    void showError(String error);
    void displayWeather();


    void populateWeatherList(Object response);

    void showProgress();
    void hideProgress();
    void showLayout();
    void hideLayout();
}
