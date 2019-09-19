package cl.ceisufro.weathercompare.apixu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import cl.ceisufro.weathercompare.utils.Utils;

public class ApixuWeatherInteractorImp implements ApixuWeatherInteractor {

    private ApixuWeatherPresenter presenter;
    private String userId;

    public ApixuWeatherInteractorImp(ApixuWeatherPresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void getRequest(RequestQueue queue, final OnApixuWeatherRequestCallback callback) {
        String url = Utils.linkAPIXU;

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
    public void getCurrentRequest(RequestQueue queue, final OnApixuWeatherCurrentRequestCallback callback) {
        String url = Utils.linkAPIXU;

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
