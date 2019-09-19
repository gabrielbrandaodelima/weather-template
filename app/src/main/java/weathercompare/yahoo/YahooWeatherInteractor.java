package weathercompare.yahoo;

import com.android.volley.RequestQueue;

public interface YahooWeatherInteractor {


    interface OnYahooWeatherRequestCallback {
        void onSuccess(String response);

        void onFailure();
    }


    void getRequest(RequestQueue queue, OnYahooWeatherRequestCallback callback);
}
