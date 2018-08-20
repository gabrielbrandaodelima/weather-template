package cl.ceisufro.weathercompare.main;


public interface ListWeatherView {
    void showError(String error);
    void displayLayout();


    void showProgress();
    void hideProgress();
    void showLayout();
    void hideLayout();
    void populateWeatherObjects(String response);
}
