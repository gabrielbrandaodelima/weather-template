package cl.ceisufro.weathercompare.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import cl.ceisufro.weathercompare.utils.Utils;


public class POSTObjectRequest {

    public POSTObjectRequest() {

    }

    public void sendRequest(RequestQueue queue, final String weatherObject, final OnPostRequestCallback callback) {

        String url = Utils.LINK_API_CLIMA;

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
                        callback.onFailure(error.getMessage());

                    }
                }
        ) {

            @Override
            public byte[] getBody() throws AuthFailureError {

                return weatherObject.getBytes();
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
