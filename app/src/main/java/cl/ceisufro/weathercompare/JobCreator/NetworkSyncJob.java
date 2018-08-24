package cl.ceisufro.weathercompare.JobCreator;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.DataBlock;
import com.johnhiott.darkskyandroidlib.models.DataPoint;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.util.Calendar;

import javax.annotation.Nullable;

import cl.ceisufro.weathercompare.R;
import cl.ceisufro.weathercompare.models.objrequisicion.AccuWeatherObject;
import cl.ceisufro.weathercompare.models.objrequisicion.ApixuWeatherObject;
import cl.ceisufro.weathercompare.models.objrequisicion.DarkSkyWeatherObject;
import cl.ceisufro.weathercompare.models.objrequisicion.OpenWeatherObject;
import cl.ceisufro.weathercompare.models.objrequisicion.YahooWeatherObject;
import cl.ceisufro.weathercompare.network.OnPostRequestCallback;
import cl.ceisufro.weathercompare.network.POSTObjectRequest;
import cl.ceisufro.weathercompare.utils.Utils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit.Callback;
import retrofit.RetrofitError;

public class NetworkSyncJob extends Job {

    public static final String TAG = "job_CALLS_tag";

    private YahooWeatherObject yahooWeatherObject = null;
    private AccuWeatherObject accuWeatherObject = null;
    private OpenWeatherObject openWeatherObject = null;
    private DarkSkyWeatherObject darkSkyWeatherObject = null;
    private ApixuWeatherObject apixuWeatherObject = null;


    private Disposable networkDisposable;

    boolean isConn = false;
    private boolean yahooDone = false;
    private boolean openWeatherDone = false;
    private boolean apixuDone = false;
    private boolean darkSkyDone = false;
    private boolean accuWeatherDone = false;

    interface OnCallCallback {
        void onSuccess(String response);

        void onSuccessDark(WeatherResponse response);

        void onError(@Nullable String response);
    }

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        // run your job here
        setAllFalse();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("CREANDO JOB")
                .setAutoCancel(false)
                .setContentText("job")
                .setTicker("job")
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationManager.IMPORTANCE_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((2), notificationBuilder.build());
        PowerManager pm = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "llamada");
        wl.acquire(1 * 60000L);
        KeyguardManager keyguardManager = (KeyguardManager) getContext().getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();
        ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkDisposable = ReactiveNetwork
                .observeNetworkConnectivity(getContext())
                .filter(ConnectivityPredicate.hasState(NetworkInfo.State.CONNECTED))
                .flatMapSingle(connectivity -> ReactiveNetwork.checkInternetConnectivity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> {
                    // isConnected can be true or false

                    if (isConnected) {

                        callAPIs();
                    }
                });


        return Result.SUCCESS;
    }

    private void callAPIs() {
        final String linkAccuWeatherCurrent = Utils.linkAccuWeatherCurrent;
        String linkAccuWeatherForecast1day = Utils.linkAccuWeatherForecast1day;
        String linkAPIXUCurrent = Utils.linkAPIXU;
        String linkOpenWeatherCurrent = Utils.linkOpenWeatherCurrent;
        String linkYahoo = Utils.linkYahoo;
//
        callCurrent(getContext(), linkYahoo, new OnCallCallback() {
            @Override
            public void onSuccess(String response) {
                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(response);

                JsonObject jsonObjectForecast = json.get("query").getAsJsonObject().get("results").getAsJsonObject().get("channel").getAsJsonObject();
                JsonObject actualCondition = jsonObjectForecast.get("item").getAsJsonObject().get("condition").getAsJsonObject();
                JsonElement actualvViento = jsonObjectForecast.get("wind").getAsJsonObject().get("speed");
                JsonElement actualHumedad = jsonObjectForecast.get("atmosphere").getAsJsonObject().get("humidity");
                JsonElement actualPresion = jsonObjectForecast.get("atmosphere").getAsJsonObject().get("pressure");
                JsonElement actualTemp = actualCondition.get("temp");
                JsonElement actualCondDia = actualCondition.get("text");
                JsonElement actualDate = actualCondition.get("date");
                JsonArray forecastArraylist = jsonObjectForecast.get("item").getAsJsonObject().get("forecast").getAsJsonArray();
                JsonElement todayTempHigh = forecastArraylist.get(0).getAsJsonObject().get("high");
                JsonElement todayTempLow = forecastArraylist.get(0).getAsJsonObject().get("low");
                JsonElement condDia = forecastArraylist.get(0).getAsJsonObject().get("text");

                yahooWeatherObject = new YahooWeatherObject();
                yahooWeatherObject.setCondActualDia(actualCondDia.getAsString());
                yahooWeatherObject.setCondDia(condDia.getAsString());
                yahooWeatherObject.setFechahoraConsulta(Calendar.getInstance().getTime().toString());
                yahooWeatherObject.setvViento(actualvViento.getAsFloat());
                yahooWeatherObject.settActual(actualTemp.getAsFloat());
                yahooWeatherObject.settMin(todayTempLow.getAsFloat());
                yahooWeatherObject.settMax(todayTempHigh.getAsFloat());

                double pressionInMb = Utils.convertYahooPressureMistake(actualPresion.getAsFloat());

                yahooWeatherObject.setPresion((float) pressionInMb);
                yahooWeatherObject.setHumedad(actualHumedad.getAsInt());

                if (yahooWeatherObject != null) {

                    POSTObjectRequest postObjectRequest = new POSTObjectRequest();
                    RequestQueue queue = Volley.newRequestQueue(getContext());


                    Gson gson = new Gson();
                    final String yahooWeatherObjectString = gson.toJson(yahooWeatherObject);
                    postObjectRequest.sendRequest(queue, yahooWeatherObjectString, new OnPostRequestCallback() {
                        @Override
                        public void onSuccess(String response) {
//                                Toast.makeText(getContext(), "Yahoo " + response, Toast.LENGTH_SHORT).show();
                            // Put here YOUR code.
                            yahooDone = true;
                            createPromedio();
                        }

                        @Override
                        public void onFailure(String error) {
//                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }

            @Override
            public void onSuccessDark(WeatherResponse response) {

            }

            @Override
            public void onError(String response) {

            }
        });
        callCurrent(getContext(), linkOpenWeatherCurrent, new OnCallCallback() {
            @Override
            public void onSuccess(String response) {
                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(response);

                JsonElement condDia = json.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main");
                JsonElement condActualDia = json.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description");

                JsonElement tActual = json.get("main").getAsJsonObject().get("temp");
                JsonElement presion = json.get("main").getAsJsonObject().get("pressure");
                JsonElement humedad = json.get("main").getAsJsonObject().get("humidity");
                JsonElement tMin = json.get("main").getAsJsonObject().get("temp_min");
                JsonElement tMax = json.get("main").getAsJsonObject().get("temp_max");
                JsonElement vViento = json.get("wind").getAsJsonObject().get("speed");
                JsonElement date = json.get("dt");

                int dateInTimestamp = date.getAsInt();
                String dateTodayString = Utils.getDateAndHourString(dateInTimestamp);
                openWeatherObject = new OpenWeatherObject();
                openWeatherObject.setCondDia(condDia.getAsString());
                openWeatherObject.setCondActualDia(condActualDia.getAsString());
                openWeatherObject.settActual(tActual.getAsFloat());
                openWeatherObject.settMax(tMax.getAsFloat());
                openWeatherObject.settMin(tMin.getAsFloat());
                openWeatherObject.setPresion(presion.getAsFloat());
                openWeatherObject.setHumedad(humedad.getAsInt());
                openWeatherObject.setvViento(vViento.getAsFloat());
                openWeatherObject.setFechahoraConsulta(Calendar.getInstance().getTime().toString());
                if (openWeatherObject != null) {

                    POSTObjectRequest postObjectRequest = new POSTObjectRequest();
                    RequestQueue queue = Volley.newRequestQueue(getContext());


                    Gson gson = new Gson();
                    String openWeatherObjectString = gson.toJson(openWeatherObject);
                    postObjectRequest.sendRequest(queue, openWeatherObjectString, new OnPostRequestCallback() {
                        @Override
                        public void onSuccess(String response) {
//                                Toast.makeText(getContext(), "Open " + response, Toast.LENGTH_SHORT).show();
                            // Put here YOUR code.
                            openWeatherDone = true;
                            createPromedio();
                        }

                        @Override
                        public void onFailure(String error) {
//                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onSuccessDark(WeatherResponse response) {

            }

            @Override
            public void onError(String response) {

            }
        });
        callCurrent(getContext(), linkAPIXUCurrent, new OnCallCallback() {
            @Override
            public void onSuccess(String response) {

                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(response);

                JsonObject current = json.get("current").getAsJsonObject();
                JsonElement fechaHora = current.get("last_updated");
                JsonElement tActual = current.get("temp_c");
                JsonElement isDay = current.get("is_day");
                JsonElement vViento = current.get("wind_kph");
                JsonElement presion = current.get("pressure_mb");
                JsonElement humedad = current.get("humidity");

                JsonArray forecast = json.get("forecast").getAsJsonObject().get("forecastday").getAsJsonArray();
                JsonElement tempMin = forecast.get(0).getAsJsonObject().get("day").getAsJsonObject().get("mintemp_c");
                JsonElement tempMax = forecast.get(0).getAsJsonObject().get("day").getAsJsonObject().get("maxtemp_c");
                JsonElement condDia = forecast.get(0).getAsJsonObject().get("day").getAsJsonObject().get("condition").getAsJsonObject().get("text");

                apixuWeatherObject = new ApixuWeatherObject();
                apixuWeatherObject.setFechahoraConsulta(Calendar.getInstance().getTime().toString());
                apixuWeatherObject.settActual(tActual.getAsFloat());
                apixuWeatherObject.setvViento(vViento.getAsFloat());
                apixuWeatherObject.setPresion(presion.getAsFloat());
                apixuWeatherObject.setHumedad(humedad.getAsInt());
                apixuWeatherObject.settMin(tempMin.getAsFloat());
                apixuWeatherObject.settMax(tempMax.getAsFloat());
                apixuWeatherObject.setCondDia(condDia.getAsString());

                if (isDay.getAsInt() == 1) {

                    apixuWeatherObject.setCondActualDia(current.get("condition").getAsJsonObject().get("text").getAsString());

                } else {
                    apixuWeatherObject.setCondActualNoche(current.get("condition").getAsJsonObject().get("text").getAsString());

                }

                if (apixuWeatherObject != null) {

                    POSTObjectRequest postObjectRequest = new POSTObjectRequest();
                    RequestQueue queue = Volley.newRequestQueue(getContext());


                    Gson gson = new Gson();
                    String apixuWeatherObjectString = gson.toJson(apixuWeatherObject);
                    postObjectRequest.sendRequest(queue, apixuWeatherObjectString, new OnPostRequestCallback() {
                        @Override
                        public void onSuccess(String response) {
//                                Toast.makeText(getContext(), "Apixu " + response, Toast.LENGTH_SHORT).show();
                            // Put here YOUR code.
                            apixuDone = true;
                            createPromedio();
                        }

                        @Override
                        public void onFailure(String error) {
//                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onSuccessDark(WeatherResponse response) {

            }

            @Override
            public void onError(String response) {

            }
        });
        callCurrent(getContext(), linkAccuWeatherForecast1day, new OnCallCallback() {
            @Override
            public void onSuccess(String response) {

                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(response);

                JsonArray jsonArrayForecast = json.get("DailyForecasts").getAsJsonArray();

//                        JsonElement dateInTimestamp = jsonArrayForecast.get(0).getAsJsonObject().get("EpochDate");
                final JsonElement tMin = jsonArrayForecast.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Minimum").getAsJsonObject().get("Value");
                final JsonElement tMax = jsonArrayForecast.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Maximum").getAsJsonObject().get("Value");
                final JsonElement condDia = jsonArrayForecast.get(0).getAsJsonObject().get("Day").getAsJsonObject().get("IconPhrase");
                final JsonElement condNoche = jsonArrayForecast.get(0).getAsJsonObject().get("Night").getAsJsonObject().get("IconPhrase");


                callCurrent(getContext(), linkAccuWeatherCurrent, new OnCallCallback() {
                    @Override
                    public void onSuccess(String response) {

                        JsonParser parser = new JsonParser();
                        JsonArray current = (JsonArray) parser.parse(response);
                        JsonElement fechaHora = current.get(0).getAsJsonObject().get("EpochTime");
                        JsonElement isDay = current.get(0).getAsJsonObject().get("IsDayTime");
                        JsonElement tActual = current.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Metric").getAsJsonObject().get("Value");
                        JsonElement humedad = current.get(0).getAsJsonObject().get("RelativeHumidity");
                        JsonElement vViento = current.get(0).getAsJsonObject().get("Wind").getAsJsonObject().get("Speed").getAsJsonObject().get("Metric").getAsJsonObject().get("Value");
                        JsonElement presion = current.get(0).getAsJsonObject().get("Pressure").getAsJsonObject().get("Metric").getAsJsonObject().get("Value");

                        int dateInTimestamp = fechaHora.getAsInt();
                        String dateTodayString = Utils.getDateAndHourString(dateInTimestamp);
                        accuWeatherObject = new AccuWeatherObject();
                        accuWeatherObject.settMin(tMin.getAsFloat());
                        accuWeatherObject.settMax(tMax.getAsFloat());
                        accuWeatherObject.setCondDia(condDia.getAsString());
                        accuWeatherObject.setCondNoche(condNoche.getAsString());

                        accuWeatherObject.setFechahoraConsulta(Calendar.getInstance().getTime().toString());
                        accuWeatherObject.settActual(tActual.getAsFloat());
                        accuWeatherObject.setHumedad(humedad.getAsInt());
                        accuWeatherObject.setPresion(presion.getAsFloat());
                        accuWeatherObject.setvViento(vViento.getAsFloat());


                        if (isDay.getAsBoolean()) {

                            accuWeatherObject.setCondActualDia(current.get(0).getAsJsonObject().get("WeatherText").getAsString());

                        } else {
                            accuWeatherObject.setCondActualNoche(current.get(0).getAsJsonObject().get("WeatherText").getAsString());

                        }

                        if (accuWeatherObject != null) {

                            POSTObjectRequest postObjectRequest = new POSTObjectRequest();
                            RequestQueue queue = Volley.newRequestQueue(getContext());


                            Gson gson = new Gson();
                            String accuWeatherObjectString = gson.toJson(accuWeatherObject);
                            postObjectRequest.sendRequest(queue, accuWeatherObjectString, new OnPostRequestCallback() {
                                @Override
                                public void onSuccess(String response) {
//                                        Toast.makeText(getContext(), "Accu " + response, Toast.LENGTH_SHORT).show();
                                    // Put here YOUR code.
                                    accuWeatherDone = true;
                                    createPromedio();
                                }

                                @Override
                                public void onFailure(String error) {
//                                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

                                }
                            });
                        }


                    }

                    @Override
                    public void onSuccessDark(WeatherResponse response) {

                    }

                    @Override
                    public void onError(String response) {

                    }
                });
            }

            @Override
            public void onSuccessDark(WeatherResponse response) {

            }

            @Override
            public void onError(String response) {

            }
        });
        callCurrentDarkSky(new OnCallCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onSuccessDark(WeatherResponse response) {

                DataPoint currentlyDataPoint = response.getCurrently();
                long fecha = currentlyDataPoint.getTime();
                String condActualDia = currentlyDataPoint.getSummary();
                float tActual = (float) currentlyDataPoint.getTemperature();
                float humedad = Float.parseFloat(currentlyDataPoint.getHumidity());
                float presion = Float.parseFloat(currentlyDataPoint.getPressure());
                float vViento = Float.parseFloat(currentlyDataPoint.getWindSpeed());

                DataBlock dailyDataBlock = response.getDaily();

                String condDia = dailyDataBlock.getData().get(0).getSummary();
                float tMax = (float) dailyDataBlock.getData().get(0).getTemperatureMax();
                float tMin = (float) dailyDataBlock.getData().get(0).getTemperatureMin();

                darkSkyWeatherObject = new DarkSkyWeatherObject();


                String dateTodayString = Utils.getDateAndHourString(fecha);
                darkSkyWeatherObject.setFechahoraConsulta(Calendar.getInstance().getTime().toString());
                darkSkyWeatherObject.setCondActualDia(condActualDia);
                darkSkyWeatherObject.settActual(tActual);
                darkSkyWeatherObject.setHumedad(humedad);
                darkSkyWeatherObject.setPresion(presion);
                darkSkyWeatherObject.setvViento(vViento);
                darkSkyWeatherObject.setvViento(vViento);
                darkSkyWeatherObject.settMax(tMax);
                darkSkyWeatherObject.settMin(tMin);
                darkSkyWeatherObject.setCondDia(condDia);

                if (darkSkyWeatherObject != null) {
                    POSTObjectRequest postObjectRequest = new POSTObjectRequest();
                    RequestQueue queue = Volley.newRequestQueue(getContext());


                    Gson gson = new Gson();
                    String darkSkyWeatherObjectString = gson.toJson(darkSkyWeatherObject);
                    postObjectRequest.sendRequest(queue, darkSkyWeatherObjectString, new OnPostRequestCallback() {
                        @Override
                        public void onSuccess(String response) {
//                                Toast.makeText(getContext(), "Dark " + response, Toast.LENGTH_SHORT).show();
                            // Put here YOUR code.
                            darkSkyDone = true;
                            createPromedio();
                        }

                        @Override
                        public void onFailure(String error) {
//                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }

            @Override
            public void onError(String response) {

            }
        });
    }

    private void setAllFalse() {
        yahooDone = false;
        accuWeatherDone = false;
        apixuDone = false;
        darkSkyDone = false;
        openWeatherDone = false;
    }

    private void calculateAndCreatePromedio() {
        if (yahooDone && openWeatherDone && darkSkyDone && apixuDone && accuWeatherDone) {
            if (yahooWeatherObject != null && accuWeatherObject != null && darkSkyWeatherObject != null && apixuWeatherObject != null && openWeatherObject != null) {

//                float somaTactual = yahooWeatherObject.gettActual() + accuWeatherObject.gettActual() + darkSkyWeatherObject.gettActual() + apixuWeatherObject.gettActual() + openWeatherObject.gettActual();
//                float promedioTactual = somaTactual / 5;
//                float somaTMax = yahooWeatherObject.gettMax() + accuWeatherObject.gettMax() + darkSkyWeatherObject.gettMax() + apixuWeatherObject.gettMax() + openWeatherObject.gettMax();
//                float promedioTMax = somaTMax / 5;
//                float somaTmin = yahooWeatherObject.gettMin() + accuWeatherObject.gettMin() + darkSkyWeatherObject.gettMin() + apixuWeatherObject.gettMin() + openWeatherObject.gettMin();
//                float promedioTmin = somaTmin / 5;
//                float somaPresion = yahooWeatherObject.getPresion() + accuWeatherObject.getPresion() + darkSkyWeatherObject.getPresion() + apixuWeatherObject.getPresion() + openWeatherObject.getPresion();
//                float promedioPresion = somaPresion / 5;
//                int somaHumedad = (int) (yahooWeatherObject.getHumedad() + accuWeatherObject.getHumedad() + apixuWeatherObject.getHumedad() + openWeatherObject.getHumedad());
//                int promedioHumedad = somaHumedad / 4; // ignorando DarkSky humedad == 1
//                float somavViento = yahooWeatherObject.getvViento() + accuWeatherObject.getvViento() + darkSkyWeatherObject.getvViento() + apixuWeatherObject.getvViento() + openWeatherObject.getvViento();
//                float promedioVviento = somavViento / 5;

//                createPromedio(promedioTactual, promedioTMax, promedioTmin, promedioPresion, promedioHumedad, promedioVviento);
            }

        }


    }

    private void createPromedio() {

        if (yahooDone && openWeatherDone && darkSkyDone && apixuDone && accuWeatherDone) {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getContext())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("CREANDO PROMEDIO")
                    .setAutoCancel(false)
                    .setContentText("promedio")
                    .setTicker("promedio")
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH);


            NotificationManager notificationManager =
                    (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((0), notificationBuilder.build());

            POSTObjectRequest postObjectRequest = new POSTObjectRequest();
            RequestQueue queue = Volley.newRequestQueue(getContext());


            postObjectRequest.createPromedioRequest(queue, new OnPostRequestCallback() {
                @Override
                public void onSuccess(String response) {
//                Log.e("response", response);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getContext())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("PROMEDIO CREADO")
                            .setContentText(response)
                            .setAutoCancel(false)
                            .setContentText("promedio")
                            .setTicker("promedio")
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setPriority(NotificationManager.IMPORTANCE_HIGH);

                    NotificationManager notificationManager =
                            (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify((1), notificationBuilder.build());
                    yahooWeatherObject = null;
                    apixuWeatherObject = null;
                    openWeatherObject = null;
                    accuWeatherObject = null;
                    darkSkyWeatherObject = null;
                    new JobRequest.Builder(NetworkSyncJob.TAG)
                            .setExact(60 * 60000L)
                            .build()
                            .schedule();
                }

                @Override
                public void onFailure(@Nullable String error) {
//                Log.i("error: ", error);
                }
            });
        }
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
