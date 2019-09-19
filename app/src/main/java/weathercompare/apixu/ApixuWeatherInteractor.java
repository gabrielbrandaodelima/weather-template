package weathercompare.apixu;

import com.android.volley.RequestQueue;

public interface ApixuWeatherInteractor {



    interface OnApixuWeatherRequestCallback {
        void onSuccess(String response);

        void onFailure();

    }
    interface OnApixuWeatherCurrentRequestCallback {

        void onSuccessCurrent(String response);

        void onFailure();
    }


    void getRequest(RequestQueue queue, OnApixuWeatherRequestCallback callback);
    void getCurrentRequest(RequestQueue queue, OnApixuWeatherCurrentRequestCallback callback);
}
