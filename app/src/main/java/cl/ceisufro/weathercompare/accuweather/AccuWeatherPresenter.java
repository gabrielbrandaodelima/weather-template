package cl.ceisufro.weathercompare.accuweather;

import com.android.volley.RequestQueue;


public interface AccuWeatherPresenter {

    void callAccuWeather(RequestQueue queue);

    void callAccuWeatherCurrent(RequestQueue queueOpenWeather);
}
