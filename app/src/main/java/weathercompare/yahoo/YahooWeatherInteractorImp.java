package weathercompare.yahoo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import weathercompare.utils.Utils;

public class YahooWeatherInteractorImp implements YahooWeatherInteractor {

    private YahooWeatherPresenter presenter;
    private String userId;

    public YahooWeatherInteractorImp(YahooWeatherPresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void getRequest(RequestQueue queue, final YahooWeatherInteractor.OnYahooWeatherRequestCallback callback) {
        String url = Utils.linkYahoo;
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
}
