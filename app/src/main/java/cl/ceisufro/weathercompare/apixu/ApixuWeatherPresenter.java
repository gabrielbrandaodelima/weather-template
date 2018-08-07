package cl.ceisufro.weathercompare.apixu;

import com.android.volley.RequestQueue;


public interface ApixuWeatherPresenter {

    void callApixuWeather(RequestQueue queue);

    void callApixuWeatherCurrent(RequestQueue queueOpenWeather);
}
