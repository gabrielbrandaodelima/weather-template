package cl.ceisufro.weathercompare.main;

import com.android.volley.RequestQueue;

public interface ListWeatherInteractor {


    void listActionRequest(RequestQueue queue, int fuenteUid, OnListWeatherRequestCallback callback);

    void listPromedios(RequestQueue queue, OnListWeatherRequestCallback callback);


    interface OnListWeatherRequestCallback {
        void onSuccess(String response);

        void onFailure();
    }


}
