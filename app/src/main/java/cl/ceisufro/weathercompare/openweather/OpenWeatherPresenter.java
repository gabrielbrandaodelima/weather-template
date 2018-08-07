package cl.ceisufro.weathercompare.openweather;

import com.android.volley.RequestQueue;


public interface OpenWeatherPresenter {

    void callOpenWeather(RequestQueue queue);

    void callOpenWeatherCurrent(RequestQueue queueOpenWeather);
}
