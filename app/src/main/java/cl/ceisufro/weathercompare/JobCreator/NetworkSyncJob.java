package cl.ceisufro.weathercompare.JobCreator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.evernote.android.job.Job;
import com.google.gson.Gson;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.util.Calendar;

import javax.annotation.Nullable;

import cl.ceisufro.weathercompare.models.objrequisicion.AccuWeatherObject;
import cl.ceisufro.weathercompare.models.objrequisicion.ApixuWeatherObject;
import cl.ceisufro.weathercompare.models.objrequisicion.DarkSkyWeatherObject;
import cl.ceisufro.weathercompare.models.objrequisicion.OpenWeatherObject;
import cl.ceisufro.weathercompare.models.objrequisicion.PromedioObject;
import cl.ceisufro.weathercompare.models.objrequisicion.YahooWeatherObject;
import cl.ceisufro.weathercompare.network.OnPostRequestCallback;
import cl.ceisufro.weathercompare.network.POSTObjectRequest;
import cl.ceisufro.weathercompare.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;

public class NetworkSyncJob extends Job {

    public static final String TAG = "job_CALLS_tag";

    private YahooWeatherObject yahooWeatherObject = null;
    private AccuWeatherObject accuWeatherObject = null;
    private OpenWeatherObject openWeatherObject = null;
    private DarkSkyWeatherObject darkSkyWeatherObject = null;
    private ApixuWeatherObject apixuWeatherObject = null;

    interface OnCallCallback {
        void onSuccess(String response);

        void onSuccessDark(WeatherResponse response);

        void onError(@Nullable String response);
    }

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        // run your job here

        POSTObjectRequest postObjectRequest = new POSTObjectRequest();
        RequestQueue queue = Volley.newRequestQueue(getContext());


        Gson gson = new Gson();
        PromedioObject promedioObject = new PromedioObject();
        promedioObject.setFechahora(Calendar.getInstance().getTime().toString());
        promedioObject.setPromedioTempActual(13.2f);
        promedioObject.setPromedioTempMax(13.2f);
        promedioObject.setPromedioTempMin(1.2f);
        promedioObject.setPromedioPresion(11.2f);
        promedioObject.setPromedioHumedad(13);
        promedioObject.setPromedioVviento(23.2f);

        String promedioObjectString = gson.toJson(promedioObject);

        postObjectRequest.sendPromedioRequest(queue, promedioObjectString, new OnPostRequestCallback() {
            @Override
            public void onSuccess(String response) {
                Log.e("response", response);
            }

            @Override
            public void onFailure(@Nullable String error) {

            }
        });

//        Toast.makeText(getContext(), "JOB EXECUTADOOOOOO", Toast.LENGTH_LONG).show();

//        PowerManager pm = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
//        wl.acquire();
//
//        final String linkAccuWeatherCurrent = Utils.linkAccuWeatherCurrent;
//        String linkAccuWeatherForecast1day = Utils.linkAccuWeatherForecast1day;
//        String linkAPIXUCurrent = Utils.linkAPIXU;
//        String linkOpenWeatherCurrent = Utils.linkOpenWeatherCurrent;
//        String linkYahoo = Utils.linkYahoo;
//
//        callCurrent(getContext(), linkYahoo, new OnCallCallback() {
//            @Override
//            public void onSuccess(String response) {
//                JsonParser parser = new JsonParser();
//                JsonObject json = (JsonObject) parser.parse(response);
//
//                JsonObject jsonObjectForecast = json.get("query").getAsJsonObject().get("results").getAsJsonObject().get("channel").getAsJsonObject();
//                JsonObject actualCondition = jsonObjectForecast.get("item").getAsJsonObject().get("condition").getAsJsonObject();
//                JsonElement actualvViento = jsonObjectForecast.get("wind").getAsJsonObject().get("speed");
//                JsonElement actualHumedad = jsonObjectForecast.get("atmosphere").getAsJsonObject().get("humidity");
//                JsonElement actualPresion = jsonObjectForecast.get("atmosphere").getAsJsonObject().get("pressure");
//                JsonElement actualTemp = actualCondition.get("temp");
//                JsonElement actualCondDia = actualCondition.get("text");
//                JsonElement actualDate = actualCondition.get("date");
//                JsonArray forecastArraylist = jsonObjectForecast.get("item").getAsJsonObject().get("forecast").getAsJsonArray();
//                JsonElement todayTempHigh = forecastArraylist.get(0).getAsJsonObject().get("high");
//                JsonElement todayTempLow = forecastArraylist.get(0).getAsJsonObject().get("low");
//                JsonElement condDia = forecastArraylist.get(0).getAsJsonObject().get("text");
//
//                yahooWeatherObject = new YahooWeatherObject();
//                yahooWeatherObject.setCondActualDia(actualCondDia.getAsString());
//                yahooWeatherObject.setCondDia(condDia.getAsString());
//                yahooWeatherObject.setFechahoraConsulta(actualDate.getAsString());
//                yahooWeatherObject.setvViento(actualvViento.getAsFloat());
//                yahooWeatherObject.settActual(actualTemp.getAsFloat());
//                yahooWeatherObject.settMin(todayTempLow.getAsFloat());
//                yahooWeatherObject.settMax(todayTempHigh.getAsFloat());
//
//                double pressionInMb = Utils.convertYahooPressureMistake(actualPresion.getAsFloat());
//
//                yahooWeatherObject.setPresion((float) pressionInMb);
//                yahooWeatherObject.setHumedad(actualHumedad.getAsInt());
//
//                if (yahooWeatherObject != null) {
//
//                    POSTObjectRequest postObjectRequest = new POSTObjectRequest();
//                    RequestQueue queue = Volley.newRequestQueue(getContext());
//
//
//                    Gson gson = new Gson();
//                    String yahooWeatherObjectString = gson.toJson(yahooWeatherObject);
//                    postObjectRequest.sendRequest(queue, yahooWeatherObjectString, new OnPostRequestCallback() {
//                        @Override
//                        public void onSuccess(String response) {
////                                Toast.makeText(getContext(), "Yahoo " + response, Toast.LENGTH_SHORT).show();
//                            // Put here YOUR code.
//
//                            yahooWeatherObject = null;
//                        }
//
//                        @Override
//                        public void onFailure(String error) {
//                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                }
//
//
//            }
//
//            @Override
//            public void onSuccessDark(WeatherResponse response) {
//
//            }
//
//            @Override
//            public void onError(String response) {
//
//            }
//        });
//        callCurrent(getContext(), linkOpenWeatherCurrent, new OnCallCallback() {
//            @Override
//            public void onSuccess(String response) {
//                JsonParser parser = new JsonParser();
//                JsonObject json = (JsonObject) parser.parse(response);
//
//                JsonElement condDia = json.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main");
//                JsonElement condActualDia = json.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description");
//
//                JsonElement tActual = json.get("main").getAsJsonObject().get("temp");
//                JsonElement presion = json.get("main").getAsJsonObject().get("pressure");
//                JsonElement humedad = json.get("main").getAsJsonObject().get("humidity");
//                JsonElement tMin = json.get("main").getAsJsonObject().get("temp_min");
//                JsonElement tMax = json.get("main").getAsJsonObject().get("temp_max");
//                JsonElement vViento = json.get("wind").getAsJsonObject().get("speed");
//                JsonElement date = json.get("dt");
//
//                int dateInTimestamp = date.getAsInt();
//                String dateTodayString = Utils.getDateAndHourString(dateInTimestamp);
//                openWeatherObject = new OpenWeatherObject();
//                openWeatherObject.setCondDia(condDia.getAsString());
//                openWeatherObject.setCondActualDia(condActualDia.getAsString());
//                openWeatherObject.settActual(tActual.getAsFloat());
//                openWeatherObject.settMax(tMax.getAsFloat());
//                openWeatherObject.settMin(tMin.getAsFloat());
//                openWeatherObject.setPresion(presion.getAsFloat());
//                openWeatherObject.setHumedad(humedad.getAsInt());
//                openWeatherObject.setvViento(vViento.getAsFloat());
//                openWeatherObject.setFechahoraConsulta(dateTodayString);
//                if (openWeatherObject != null) {
//
//                    POSTObjectRequest postObjectRequest = new POSTObjectRequest();
//                    RequestQueue queue = Volley.newRequestQueue(getContext());
//
//
//                    Gson gson = new Gson();
//                    String openWeatherObjectString = gson.toJson(openWeatherObject);
//                    postObjectRequest.sendRequest(queue, openWeatherObjectString, new OnPostRequestCallback() {
//                        @Override
//                        public void onSuccess(String response) {
////                                Toast.makeText(getContext(), "Open " + response, Toast.LENGTH_SHORT).show();
//                            // Put here YOUR code.
//                            openWeatherObject = null;
//                        }
//
//                        @Override
//                        public void onFailure(String error) {
//                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onSuccessDark(WeatherResponse response) {
//
//            }
//
//            @Override
//            public void onError(String response) {
//
//            }
//        });
//        callCurrent(getContext(), linkAPIXUCurrent, new OnCallCallback() {
//            @Override
//            public void onSuccess(String response) {
//
//                JsonParser parser = new JsonParser();
//                JsonObject json = (JsonObject) parser.parse(response);
//
//                JsonObject current = json.get("current").getAsJsonObject();
//                JsonElement fechaHora = current.get("last_updated");
//                JsonElement tActual = current.get("temp_c");
//                JsonElement isDay = current.get("is_day");
//                JsonElement vViento = current.get("wind_kph");
//                JsonElement presion = current.get("pressure_mb");
//                JsonElement humedad = current.get("humidity");
//
//                JsonArray forecast = json.get("forecast").getAsJsonObject().get("forecastday").getAsJsonArray();
//                JsonElement tempMin = forecast.get(0).getAsJsonObject().get("day").getAsJsonObject().get("mintemp_c");
//                JsonElement tempMax = forecast.get(0).getAsJsonObject().get("day").getAsJsonObject().get("maxtemp_c");
//                JsonElement condDia = forecast.get(0).getAsJsonObject().get("day").getAsJsonObject().get("condition").getAsJsonObject().get("text");
//
//                apixuWeatherObject = new ApixuWeatherObject();
//                apixuWeatherObject.setFechahoraConsulta(fechaHora.getAsString());
//                apixuWeatherObject.settActual(tActual.getAsFloat());
//                apixuWeatherObject.setvViento(vViento.getAsFloat());
//                apixuWeatherObject.setPresion(presion.getAsFloat());
//                apixuWeatherObject.setHumedad(humedad.getAsInt());
//                apixuWeatherObject.settMin(tempMin.getAsFloat());
//                apixuWeatherObject.settMax(tempMax.getAsFloat());
//                apixuWeatherObject.setCondDia(condDia.getAsString());
//
//                if (isDay.getAsInt() == 1) {
//
//                    apixuWeatherObject.setCondActualDia(current.get("condition").getAsJsonObject().get("text").getAsString());
//
//                } else {
//                    apixuWeatherObject.setCondActualNoche(current.get("condition").getAsJsonObject().get("text").getAsString());
//
//                }
//
//                if (apixuWeatherObject != null) {
//
//                    POSTObjectRequest postObjectRequest = new POSTObjectRequest();
//                    RequestQueue queue = Volley.newRequestQueue(getContext());
//
//
//                    Gson gson = new Gson();
//                    String apixuWeatherObjectString = gson.toJson(apixuWeatherObject);
//                    postObjectRequest.sendRequest(queue, apixuWeatherObjectString, new OnPostRequestCallback() {
//                        @Override
//                        public void onSuccess(String response) {
////                                Toast.makeText(getContext(), "Apixu " + response, Toast.LENGTH_SHORT).show();
//                            // Put here YOUR code.
//                            apixuWeatherObject = null;
//                        }
//
//                        @Override
//                        public void onFailure(String error) {
//                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onSuccessDark(WeatherResponse response) {
//
//            }
//
//            @Override
//            public void onError(String response) {
//
//            }
//        });
//        callCurrent(getContext(), linkAccuWeatherForecast1day, new OnCallCallback() {
//            @Override
//            public void onSuccess(String response) {
//
//                JsonParser parser = new JsonParser();
//                JsonObject json = (JsonObject) parser.parse(response);
//
//                JsonArray jsonArrayForecast = json.get("DailyForecasts").getAsJsonArray();
//
////                        JsonElement dateInTimestamp = jsonArrayForecast.get(0).getAsJsonObject().get("EpochDate");
//                final JsonElement tMin = jsonArrayForecast.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Minimum").getAsJsonObject().get("Value");
//                final JsonElement tMax = jsonArrayForecast.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Maximum").getAsJsonObject().get("Value");
//                final JsonElement condDia = jsonArrayForecast.get(0).getAsJsonObject().get("Day").getAsJsonObject().get("IconPhrase");
//                final JsonElement condNoche = jsonArrayForecast.get(0).getAsJsonObject().get("Night").getAsJsonObject().get("IconPhrase");
//
//
//                callCurrent(getContext(), linkAccuWeatherCurrent, new OnCallCallback() {
//                    @Override
//                    public void onSuccess(String response) {
//
//                        JsonParser parser = new JsonParser();
//                        JsonArray current = (JsonArray) parser.parse(response);
//                        JsonElement fechaHora = current.get(0).getAsJsonObject().get("EpochTime");
//                        JsonElement isDay = current.get(0).getAsJsonObject().get("IsDayTime");
//                        JsonElement tActual = current.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Metric").getAsJsonObject().get("Value");
//                        JsonElement humedad = current.get(0).getAsJsonObject().get("RelativeHumidity");
//                        JsonElement vViento = current.get(0).getAsJsonObject().get("Wind").getAsJsonObject().get("Speed").getAsJsonObject().get("Metric").getAsJsonObject().get("Value");
//                        JsonElement presion = current.get(0).getAsJsonObject().get("Pressure").getAsJsonObject().get("Metric").getAsJsonObject().get("Value");
//
//                        int dateInTimestamp = fechaHora.getAsInt();
//                        String dateTodayString = Utils.getDateAndHourString(dateInTimestamp);
//                        accuWeatherObject = new AccuWeatherObject();
//                        accuWeatherObject.settMin(tMin.getAsFloat());
//                        accuWeatherObject.settMax(tMax.getAsFloat());
//                        accuWeatherObject.setCondDia(condDia.getAsString());
//                        accuWeatherObject.setCondNoche(condNoche.getAsString());
//
//                        accuWeatherObject.setFechahoraConsulta(dateTodayString);
//                        accuWeatherObject.settActual(tActual.getAsFloat());
//                        accuWeatherObject.setHumedad(humedad.getAsInt());
//                        accuWeatherObject.setPresion(presion.getAsFloat());
//                        accuWeatherObject.setvViento(vViento.getAsFloat());
//
//
//                        if (isDay.getAsBoolean()) {
//
//                            accuWeatherObject.setCondActualDia(current.get(0).getAsJsonObject().get("WeatherText").getAsString());
//
//                        } else {
//                            accuWeatherObject.setCondActualNoche(current.get(0).getAsJsonObject().get("WeatherText").getAsString());
//
//                        }
//
//                        if (accuWeatherObject != null) {
//
//                            POSTObjectRequest postObjectRequest = new POSTObjectRequest();
//                            RequestQueue queue = Volley.newRequestQueue(getContext());
//
//
//                            Gson gson = new Gson();
//                            String accuWeatherObjectString = gson.toJson(accuWeatherObject);
//                            postObjectRequest.sendRequest(queue, accuWeatherObjectString, new OnPostRequestCallback() {
//                                @Override
//                                public void onSuccess(String response) {
////                                        Toast.makeText(getContext(), "Accu " + response, Toast.LENGTH_SHORT).show();
//                                    // Put here YOUR code.
//                                    accuWeatherObject = null;
//                                }
//
//                                @Override
//                                public void onFailure(String error) {
//                                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onSuccessDark(WeatherResponse response) {
//
//                    }
//
//                    @Override
//                    public void onError(String response) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onSuccessDark(WeatherResponse response) {
//
//            }
//
//            @Override
//            public void onError(String response) {
//
//            }
//        });
//        callCurrentDarkSky(new OnCallCallback() {
//            @Override
//            public void onSuccess(String response) {
//
//            }
//
//            @Override
//            public void onSuccessDark(WeatherResponse response) {
//
//                DataPoint currentlyDataPoint = response.getCurrently();
//                long fecha = currentlyDataPoint.getTime();
//                String condActualDia = currentlyDataPoint.getSummary();
//                float tActual = (float) currentlyDataPoint.getTemperature();
//                float humedad = Float.parseFloat(currentlyDataPoint.getHumidity());
//                float presion = Float.parseFloat(currentlyDataPoint.getPressure());
//                float vViento = Float.parseFloat(currentlyDataPoint.getWindSpeed());
//
//                DataBlock dailyDataBlock = response.getDaily();
//
//                String condDia = dailyDataBlock.getData().get(0).getSummary();
//                float tMax = (float) dailyDataBlock.getData().get(0).getTemperatureMax();
//                float tMin = (float) dailyDataBlock.getData().get(0).getTemperatureMin();
//
//                darkSkyWeatherObject = new DarkSkyWeatherObject();
//
//
//                String dateTodayString = Utils.getDateAndHourString(fecha);
//                darkSkyWeatherObject.setFechahoraConsulta(dateTodayString);
//                darkSkyWeatherObject.setCondActualDia(condActualDia);
//                darkSkyWeatherObject.settActual(tActual);
//                darkSkyWeatherObject.setHumedad(humedad);
//                darkSkyWeatherObject.setPresion(presion);
//                darkSkyWeatherObject.setvViento(vViento);
//                darkSkyWeatherObject.setvViento(vViento);
//                darkSkyWeatherObject.settMax(tMax);
//                darkSkyWeatherObject.settMin(tMin);
//                darkSkyWeatherObject.setCondDia(condDia);
//
//                if (darkSkyWeatherObject != null) {
//                    POSTObjectRequest postObjectRequest = new POSTObjectRequest();
//                    RequestQueue queue = Volley.newRequestQueue(getContext());
//
//
//                    Gson gson = new Gson();
//                    String darkSkyWeatherObjectString = gson.toJson(darkSkyWeatherObject);
//                    postObjectRequest.sendRequest(queue, darkSkyWeatherObjectString, new OnPostRequestCallback() {
//                        @Override
//                        public void onSuccess(String response) {
////                                Toast.makeText(getContext(), "Dark " + response, Toast.LENGTH_SHORT).show();
//                            // Put here YOUR code.
//                            darkSkyWeatherObject = null;
//                        }
//
//                        @Override
//                        public void onFailure(String error) {
//                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onError(String response) {
//
//            }
//        });
//
//
//        wl.release();


        return Result.SUCCESS;
    }


    private void callCurrent(Context context, String link, final OnCallCallback callback) {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        callback.onSuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(null);

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void callCurrentDarkSky(final OnCallCallback callback) {

        RequestBuilder weather = new RequestBuilder();

        com.johnhiott.darkskyandroidlib.models.Request request = new com.johnhiott.darkskyandroidlib.models.Request();
        request.setLat(Utils.latTemuco);
        request.setLng(Utils.longTemuco);
        request.setUnits(com.johnhiott.darkskyandroidlib.models.Request.Units.SI);
        request.setLanguage(com.johnhiott.darkskyandroidlib.models.Request.Language.SPANISH);

        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, retrofit.client.Response response) {
                //Do something
                callback.onSuccessDark(weatherResponse);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callback.onError(null);

            }
        });
    }

}
