package weathercompare.accuweather;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import weathercompare.utils.Utils;

public class AccuWeatherInteractorImp implements AccuWeatherInteractor {

    private AccuWeatherPresenter presenter;
    private String userId;

    public AccuWeatherInteractorImp(AccuWeatherPresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void getRequest(RequestQueue queue, final OnAccuWeatherRequestCallback callback) {
        String url = Utils.linkAccuWeatherForecast;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        callback.onSuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure();

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @Override
    public void getCurrentRequest(RequestQueue queue, final OnAccuWeatherCurrentRequestCallback callback) {
        String url = Utils.linkAccuWeatherCurrent;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        callback.onSuccessCurrent(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure();

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
