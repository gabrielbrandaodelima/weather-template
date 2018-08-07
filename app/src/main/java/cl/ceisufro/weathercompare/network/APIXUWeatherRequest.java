//package cl.ceisufro.weathercompare.network;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//
//import cl.ceisufro.weathercompare.utils.Utils;
//
//
//public class APIXUWeatherRequest {
//
//    public APIXUWeatherRequest() {
//
//    }
//
//    public void getRequest(RequestQueue queue, final OnAPIXUSuccessCallback callback) {
//// Request a string response from the provided URL.
//        String url = Utils.linkAPIXU;
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//
//                        callback.onSuccess(response);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                callback.onFailure();
//
//            }
//        });
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
//
//    }
//
//
//}
