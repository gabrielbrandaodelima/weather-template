package cl.ceisufro.weathercompare.main;

import com.android.volley.RequestQueue;

public interface ListWeatherInteractor {


    void listActionRequest(RequestQueue queue, OnListWeatherRequestCallback callback);

    interface OnListWeatherRequestCallback {
        void onSuccess(Object response);

        void onFailure();
    }


}
