package cl.ceisufro.weathercompare.darksky;

import android.util.Log;

import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import retrofit.Callback;
import retrofit.RetrofitError;

import static android.content.ContentValues.TAG;

public class DarkSkyWeatherInteractorImp implements DarkSkyWeatherInteractor {

    private DarkSkyWeatherPresenter presenter;
    private String userId;

    public DarkSkyWeatherInteractorImp(DarkSkyWeatherPresenter presenter) {
        this.presenter = presenter;
    }



    @Override
    public void getRequest(String lat, String lng, final OnDarkSkyWeatherRequestCallback callback) {
        RequestBuilder weather = new RequestBuilder();

        com.johnhiott.darkskyandroidlib.models.Request request = new com.johnhiott.darkskyandroidlib.models.Request();
        request.setLat(lat);
        request.setLng(lng);
        request.setUnits(com.johnhiott.darkskyandroidlib.models.Request.Units.SI);
        request.setLanguage(com.johnhiott.darkskyandroidlib.models.Request.Language.SPANISH);


        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, retrofit.client.Response response) {
                //Do something
                callback.onSuccess(weatherResponse);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(TAG, "Error while calling: " + retrofitError.getUrl());
                callback.onFailure();

            }
        });
    }
}
