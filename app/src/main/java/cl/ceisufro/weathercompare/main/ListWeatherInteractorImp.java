package cl.ceisufro.weathercompare.main;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cl.ceisufro.weathercompare.utils.Utils;
import cl.ceisufro.weathercompare.utils.constants.Constants;

public class ListWeatherInteractorImp implements ListWeatherInteractor {

    private ListWeatherPresenter presenter;
    private String userId;

    public ListWeatherInteractorImp(ListWeatherPresenter presenter) {
        this.presenter = presenter;
    }



    @Override
    public void listActionRequest(RequestQueue queue, final OnListWeatherRequestCallback callback) {


        String url = Utils.LINK_API_CLIMA;

        final JSONObject urlList = new JSONObject();
        try {
            urlList.put("usr", Constants.USR);
            urlList.put("pass", Constants.PASS);
            urlList.put("action", Constants.ACTION_LIST);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        if (urlList.length() != 0) {
            final String urlObjString = gson.toJson(urlList);


            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                            callback.onSuccess(response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.getMessage());
                            callback.onFailure();

                        }
                    }
            ) {

                @Override
                public byte[] getBody() throws AuthFailureError {

                    return urlObjString.getBytes();
                }

                @Override
                public String getBodyContentType() {

                    return "application/json";
                }

            };
// Add the request to the RequestQueue.
            queue.add(postRequest);
        }

    }
}