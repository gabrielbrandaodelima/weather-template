package weathercompare.apixu;

import weathercompare.main.ListWeatherView;

public interface ApixuWeatherView extends ListWeatherView {
    void showError(String error);
    void displayWeather();

    void populateWeatherList(String response);

    void showProgress();
    void hideProgress();
    void showLayout();
    void hideLayout();

    void getCurrentTemp(String response);
}
