package cl.ceisufro.weathercompare.openweather;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import cl.ceisufro.weathercompare.utils.Utils;

public class OpenWeatherInteractorImp implements OpenWeatherInteractor {

    private OpenWeatherPresenter presenter;
    private String userId;

    public OpenWeatherInteractorImp(OpenWeatherPresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void getRequest(RequestQueue queue, final OnOpenWeatherRequestCallback callback) {
        String url = Utils.linkOpenWeather;

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
    public void getCurrentRequest(RequestQueue queue, final OnOpenWeatherCurrentRequestCallback callback) {
        String url = Utils.linkOpenWeatherCurrent;

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
