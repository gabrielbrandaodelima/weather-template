package weathercompare.accuweather;

import com.android.volley.RequestQueue;

public interface AccuWeatherInteractor {



    interface OnAccuWeatherRequestCallback {
        void onSuccess(String response);

        void onFailure();

    }
    interface OnAccuWeatherCurrentRequestCallback {

        void onSuccessCurrent(String response);

        void onFailure();
    }


    void getRequest(RequestQueue queue, OnAccuWeatherRequestCallback callback);
    void getCurrentRequest(RequestQueue queue, OnAccuWeatherCurrentRequestCallback callback);
}
