package weathercompare.darksky;

import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

public interface DarkSkyWeatherInteractor {


    void getRequest(String lat, String lng, OnDarkSkyWeatherRequestCallback callback);

    interface OnDarkSkyWeatherRequestCallback {
        void onSuccess(WeatherResponse response);

        void onFailure();
    }


}
