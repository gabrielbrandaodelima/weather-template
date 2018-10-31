package cl.ceisufro.weathercompare.darksky;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cl.ceisufro.weathercompare.R;
import cl.ceisufro.weathercompare.darksky.adapter.DarkSkyWeatherAdapter;
import cl.ceisufro.weathercompare.main.ListWeatherPresenter;
import cl.ceisufro.weathercompare.main.ListWeatherPresenterImpl;
import cl.ceisufro.weathercompare.models.objrequisicion.DarkSkyWeatherObject;
import cl.ceisufro.weathercompare.models.objrequisicion.WeatherObject;
import io.realm.Realm;
import io.realm.RealmResults;


public class DarkSkyWeatherFragment extends Fragment implements DarkSkyWeatherView {
    @BindView(R.id.progress_forecast)
    ProgressBar progressForecast;
    @BindView(R.id.list_item_today_date_textview)
    TextView listItemTodayDateTextview;
    @BindView(R.id.list_item_today_high_textview)
    TextView listItemTodayHighTextview;
    @BindView(R.id.list_item_today_low_textview)
    TextView listItemTodayLowTextview;
    @BindView(R.id.list_item_today_icon)
    ImageView listItemTodayIcon;
    @BindView(R.id.list_item_today_forecast_textview)
    TextView listItemTodayForecastTextview;
    @BindView(R.id.forecast_recycle)
    RecyclerView forecastRecycle;
    @BindView(R.id.forecast_nested_scroll_view)
    NestedScrollView forecastNestedScrollView;
    Unbinder unbinder;

    ListWeatherPresenter presenter;
    List<WeatherObject> darkskyWeatherConditionsList;
    List<WeatherObject> darkskyWeatherConditionsListNextDays;
    WeatherObject todayWeatherObject;
    DarkSkyWeatherAdapter darkskyWeatherAdapter;
    Realm realm;
    RequestQueue queueDarkSkyWeather = null;
    RealmResults<DarkSkyWeatherObject> darkskyWeatherConditionsRealmResults;
    @BindView(R.id.list_item_today_current_textview)
    TextView listItemTodayCurrentTextview;
    @BindView(R.id.error_animation_view)
    LottieAnimationView errorAnimationView;
    @BindView(R.id.error_text)
    TextView errorText;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.ultimo_dato_layout)
    LinearLayout ultimoDatoLayout;

//    private Tracker mTracker;

    public static String getFragmentTag() {
        return "dark";
    }

    public DarkSkyWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        Realm.deleteRealm(Realm.getDefaultConfiguration());

        // Create a new empty instance of Realm
        realm = Realm.getDefaultInstance();

        // Obtain the cities in the Realm with asynchronous query.
        darkskyWeatherConditionsRealmResults = realm.where(DarkSkyWeatherObject.class).findAllAsync();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        unbinder = ButterKnife.bind(this, view);
        hideLayout();
        showProgress();

        presenter = new ListWeatherPresenterImpl(this);
        darkskyWeatherConditionsList = new ArrayList<>();
        darkskyWeatherConditionsListNextDays = new ArrayList<>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queueDarkSkyWeather = Volley.newRequestQueue(getActivity());
        presenter.listWeatherRequest(queueDarkSkyWeather, 2);
//        presenter.callDarkSkyRequest(Utils.latTemuco, Utils.longTemuco);

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showError(String error) {
        if (progressForecast != null) {

            displayTimeoutError();
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }

    }

    public void displayTimeoutError() {

        hideProgress();
        showTimeoutError();
    }

    public void showTimeoutError() {
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayLayout() {

    }

    @Override
    public void displayWeather() {

        if (progressForecast != null) {

            hideProgress();
            showLayout();
        }
    }

    @Override
    public void populateWeatherList(WeatherResponse response) {
//        DataPoint currentlyDataPoint = response.getCurrently();
//        DataBlock dailyDataBlock = response.getDaily();
//
////        JsonObject currentlyCondition = json.get("currently").getAsJsonObject();
////        JsonElement currentlyTemperature = currentlyCondition.get("temperature");
//
//
////        JsonArray forecastDailyArraylist = json.get("daily").getAsJsonArray();
//        for (DataPoint dailyDataPoint :
//                dailyDataBlock.getData()) {
//            int time = (int) dailyDataPoint.getTime();
//
//            String summary = dailyDataPoint.getSummary();
//            String icon = dailyDataPoint.getIcon();
//            int temperatureHigh = (int) dailyDataPoint.getTemperatureMax();
//            int temperatureLow = (int) dailyDataPoint.getTemperatureMin();
//            String pressure = dailyDataPoint.getPressure();
//            String speed = dailyDataPoint.getWindSpeed();
//            String humididty = dailyDataPoint.getHumidity();
//
//            DarkSkyWeatherConditions darkskyWeatherForecast = new DarkSkyWeatherConditions(time, temperatureHigh, temperatureLow, summary, icon, pressure, humididty, speed);
//            darkskyWeatherConditionsList.add(darkskyWeatherForecast);
//        }
//
//        todayWeatherObject = new DarkSkyWeatherConditions();
//        todayWeatherObject.setCurrentTemp((int) currentlyDataPoint.getTemperature());
//        DarkSkyWeatherConditions darkSkyWeatherCondition = darkskyWeatherConditionsList.get(0);
//        int todayTempHigh = darkSkyWeatherCondition.getTemperatureHigh();
//        int todayTempLow = darkSkyWeatherCondition.getTemperatureLow();
//        todayWeatherObject.setDate(darkSkyWeatherCondition.getDate());
//        todayWeatherObject.setPressure(darkSkyWeatherCondition.getPressure());
//        todayWeatherObject.setHumididty(darkSkyWeatherCondition.getHumididty());
//        todayWeatherObject.setIcon(darkSkyWeatherCondition.getIcon());
//        todayWeatherObject.setSpeed(darkSkyWeatherCondition.getSpeed());
//        todayWeatherObject.setSummary(darkSkyWeatherCondition.getSummary());
//        todayWeatherObject.setTemperatureHigh(todayTempHigh);
//        todayWeatherObject.setTemperatureLow(todayTempLow);
//
//        int dateInTimestamp = todayWeatherObject.getDate();
//        String dateTodayString = Utils.getDateString(dateInTimestamp);
//
//        if (listItemTodayDateTextview != null) {
//
//            listItemTodayDateTextview.setText(dateTodayString);
//            listItemTodayForecastTextview.setText(todayWeatherObject.getSummary());
//            listItemTodayCurrentTextview.setText(todayWeatherObject.getCurrentTemp() + "ºC");
//            listItemTodayHighTextview.setText(todayWeatherObject.getTemperatureHigh() + "ºC");
//            listItemTodayLowTextview.setText(todayWeatherObject.getTemperatureLow() + "ºC");
//        }
//        switch (todayWeatherObject.getIcon()) {
//            case "Sunny":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "clear-day":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "clear-night":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "Fair":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
//
//                break;
//            case "snow":
//                listItemTodayIcon.setImageResource(R.drawable.art_snow);
//
//                break;
//            case "fog":
//                listItemTodayIcon.setImageResource(R.drawable.art_fog);
//
//                break;
//            case "Thunderstorms":
//                listItemTodayIcon.setImageResource(R.drawable.art_storm);
//
//                break;
//            case "Scattered Thunderstorms":
//                listItemTodayIcon.setImageResource(R.drawable.art_storm);
//
//                break;
//            case "rain":
//                listItemTodayIcon.setImageResource(R.drawable.art_rain);
//
//                break;
//
//            case "partly-cloudy-day":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
//                break;
//            case "partly-cloudy-night":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
//                break;
//            case "cloudy":
//                listItemTodayIcon.setImageResource(R.drawable.art_clouds);
//                break;
//
//            case "Scattered Showers":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
//                break;
//            case "Showers":
//                listItemTodayIcon.setImageResource(R.drawable.art_rain);
//                break;
//            default:
//                break;
//        }
//
//        darkskyWeatherConditionsListNextDays = darkskyWeatherConditionsList.subList(1, darkskyWeatherConditionsList.size());
//        LinearLayoutManager layoutManager =
//                new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        darkskyWeatherAdapter = new DarkSkyWeatherAdapter(getActivity(), darkskyWeatherConditionsListNextDays);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
//                layoutManager.getOrientation());
//        forecastRecycle.addItemDecoration(dividerItemDecoration);
//        forecastRecycle.setLayoutManager(layoutManager);
//        forecastRecycle.setNestedScrollingEnabled(false);
//        forecastRecycle.setLayoutParams(layoutParams);
//        forecastRecycle.setAdapter(darkskyWeatherAdapter);
//
//
//        displayWeather();
    }

    @Override
    public void showProgress() {
        if (progressForecast.getVisibility() == View.GONE) {

            progressForecast.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideProgress() {
        if (progressForecast.getVisibility() == View.VISIBLE) {

            progressForecast.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLayout() {
        forecastNestedScrollView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayout() {

        forecastNestedScrollView.setVisibility(View.GONE);

    }

    @Override
    public void populateWeatherObjects(String response) {
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(response);

        JsonArray jsonArray = json.get("data").getAsJsonArray();

        for (JsonElement element :
                jsonArray) {
            String fechahoraConsulta = element.getAsJsonObject().get("fechahoraConsulta").getAsString();
            String condActualDia = element.getAsJsonObject().get("condActualDia").getAsString();
            String condActualNoche = element.getAsJsonObject().get("condActualNoche").getAsString();
            String condDia = element.getAsJsonObject().get("condDia").getAsString();
            String condNoche = element.getAsJsonObject().get("condNoche").getAsString();
            float tActual = element.getAsJsonObject().get("tActual").getAsFloat();
            float tMax = element.getAsJsonObject().get("tMax").getAsFloat();
            float tMin = element.getAsJsonObject().get("tMin").getAsFloat();
            float presion = element.getAsJsonObject().get("presion").getAsFloat();
            int humedad = element.getAsJsonObject().get("humedad").getAsInt();
            float vViento = element.getAsJsonObject().get("vViento").getAsFloat();
            WeatherObject darkSkyWeatherObject = new WeatherObject();
            darkSkyWeatherObject.setFechahoraConsulta(fechahoraConsulta);
            darkSkyWeatherObject.settActual(tActual);
            darkSkyWeatherObject.settMax(tMax);
            darkSkyWeatherObject.settMin(tMin);
            darkSkyWeatherObject.setPresion(presion);
            darkSkyWeatherObject.setHumedad(humedad);
            darkSkyWeatherObject.setvViento(vViento);

            if (!condActualDia.isEmpty()) {
                darkSkyWeatherObject.setCondActualDia(condActualDia);

            }
            if (!condActualNoche.isEmpty()) {
                darkSkyWeatherObject.setCondActualNoche(condActualNoche);

            }
            if (!condDia.isEmpty()) {
                darkSkyWeatherObject.setCondDia(condDia);

            }
            if (!condNoche.isEmpty()) {
                darkSkyWeatherObject.setCondNoche(condNoche);

            }

            darkskyWeatherConditionsList.add(0, darkSkyWeatherObject);
        }
        WeatherObject lastWeatherObject = new WeatherObject();
        lastWeatherObject = darkskyWeatherConditionsList.get(0);
        if (listItemTodayDateTextview != null) {

            listItemTodayDateTextview.setText(lastWeatherObject.getFechahoraConsulta());
            listItemTodayForecastTextview.setText(lastWeatherObject.getCondActualDia());
            listItemTodayCurrentTextview.setText(lastWeatherObject.gettActual() + "ºC");
            listItemTodayHighTextview.setText(lastWeatherObject.gettMax() + "ºC");
            listItemTodayLowTextview.setText(lastWeatherObject.gettMin() + "ºC");

        }
        switch (lastWeatherObject.getCondActualDia()) {
            case "Despejado":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "clear-night":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Fair":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);

                break;
            case "snow":
                listItemTodayIcon.setImageResource(R.drawable.art_snow);

                break;
            case "fog":
                listItemTodayIcon.setImageResource(R.drawable.art_fog);

                break;
            case "Thunderstorms":
                listItemTodayIcon.setImageResource(R.drawable.art_storm);

                break;
            case "Scattered Thunderstorms":
                listItemTodayIcon.setImageResource(R.drawable.art_storm);

                break;
            case "Lluvia":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);

                break;
            case "Llovizna":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);

                break;

            case "Parcialmente Nublado":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
                break;
            case "Nublado":
                listItemTodayIcon.setImageResource(R.drawable.art_clouds);
                break;
            case "Mayormente Nublado":
                listItemTodayIcon.setImageResource(R.drawable.art_clouds);
                break;

            case "Lluvia Ligera":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
                break;
            case "Lluvia Ligera y Pocos Vientos":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
                break;
            case "Showers":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);
                break;
            default:
                break;

        }

        darkskyWeatherConditionsListNextDays = darkskyWeatherConditionsList.subList(1, darkskyWeatherConditionsList.size());

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        darkskyWeatherAdapter = new DarkSkyWeatherAdapter(getActivity(), darkskyWeatherConditionsListNextDays);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());
        forecastRecycle.addItemDecoration(dividerItemDecoration);
        forecastRecycle.setLayoutManager(layoutManager);
        forecastRecycle.setNestedScrollingEnabled(false);
        forecastRecycle.setLayoutParams(layoutParams);
        forecastRecycle.setAdapter(darkskyWeatherAdapter);


        displayWeather();

    }
}
