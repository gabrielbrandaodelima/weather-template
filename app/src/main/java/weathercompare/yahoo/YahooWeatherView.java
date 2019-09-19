package weathercompare.yahoo;


import weathercompare.main.ListWeatherView;

public interface YahooWeatherView extends ListWeatherView{
    void showError(String error);
    void displayWeather();


    void showProgress();
    void hideProgress();
    void showLayout();
    void hideLayout();
}
