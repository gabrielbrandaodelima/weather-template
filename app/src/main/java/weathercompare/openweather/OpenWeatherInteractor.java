package weathercompare.openweather;

import com.android.volley.RequestQueue;

public interface OpenWeatherInteractor {



    interface OnOpenWeatherRequestCallback {
        void onSuccess(String response);

        void onFailure();

    }
    interface OnOpenWeatherCurrentRequestCallback {

        void onSuccessCurrent(String response);

        void onFailure();
    }


    void getRequest(RequestQueue queue, OnOpenWeatherRequestCallback callback);
    void getCurrentRequest(RequestQueue queue, OnOpenWeatherCurrentRequestCallback callback);
}
