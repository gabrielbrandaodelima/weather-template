package cl.ceisufro.weathercompare.accuweather;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cl.ceisufro.weathercompare.R;
import cl.ceisufro.weathercompare.accuweather.adapter.AccuWeatherAdapter;
import cl.ceisufro.weathercompare.main.ListWeatherPresenter;
import cl.ceisufro.weathercompare.main.ListWeatherPresenterImpl;
import cl.ceisufro.weathercompare.models.objrequisicion.WeatherObject;
import io.realm.Realm;
import io.realm.RealmResults;


public class AccuWeatherFragment extends Fragment implements AccuWeatherView {

    @BindView(R.id.progress_forecast)
    ProgressBar progressForecast;
    @BindView(R.id.forecast_recycle)
    RecyclerView forecastRecycle;
    @BindView(R.id.forecast_nested_scroll_view)
    NestedScrollView forecastNestedScrollView;
    Unbinder unbinder;
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
    ListWeatherPresenter presenter;
    List<WeatherObject> accuWeatherConditionsArrayList;
    List<WeatherObject> accuWeatherConditionsNextDaysArrayList;
    WeatherObject todayAccuWeatherCondition = new WeatherObject();
    AccuWeatherAdapter accuWeatherAdapter;
    Realm realm;
    RequestQueue queueAccuWeather = null;

    RealmResults<WeatherObject> accuWeatherConditionsRealmResults;
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
    private Integer currentTemp = null;


    public static String getFragmentTag() {
        return "accu";
    }

    public AccuWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Clear the realm from last time
        //noinspection ConstantConditions

        // Create a new empty instance of Realm
        realm = Realm.getDefaultInstance();

        // Obtain the cities in the Realm with asynchronous query.
        accuWeatherConditionsRealmResults = realm.where(WeatherObject.class).findAllAsync();

        // The RealmChangeListener will be called when the results are asynchronously loaded, and available for use.
//        accuWeatherConditionsRealmResults.addChangeListener(realmChangeListener);
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
        accuWeatherConditionsArrayList = new ArrayList<>();
        accuWeatherConditionsNextDaysArrayList = new ArrayList<>();
//        initAdapter();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queueAccuWeather = Volley.newRequestQueue(getActivity());
        presenter.listWeatherRequest(queueAccuWeather, 4);
//        presenter.callAccuWeatherCurrent(queueAccuWeather);
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
    public void populateWeatherList(String response) {
//        JsonParser parser = new JsonParser();
//        JsonObject json = (JsonObject) parser.parse(response);
//
//        JsonArray jsonArrayForecast = json.get("DailyForecasts").getAsJsonArray();
//
//        for (JsonElement accuWeatherDateCondition :
//                jsonArrayForecast) {
//            JsonElement dateInTimestamp = accuWeatherDateCondition.getAsJsonObject().get("EpochDate");
//            JsonElement tempMin = accuWeatherDateCondition.getAsJsonObject().get("Temperature").getAsJsonObject().get("Minimum").getAsJsonObject().get("Value");
//            JsonElement tempMax = accuWeatherDateCondition.getAsJsonObject().get("Temperature").getAsJsonObject().get("Maximum").getAsJsonObject().get("Value");
//            JsonElement dayPhrase = accuWeatherDateCondition.getAsJsonObject().get("Day").getAsJsonObject().get("IconPhrase");
//            JsonElement nightPhrase = accuWeatherDateCondition.getAsJsonObject().get("Night").getAsJsonObject().get("IconPhrase");
//
//
//            WeatherObject accuWeatherConditions = new WeatherObject(dateInTimestamp.getAsInt(), tempMax.getAsInt(), tempMin.getAsInt(), dayPhrase.getAsString(), nightPhrase.getAsString());
//
//
//            accuWeatherConditionsArrayList.add(accuWeatherConditions);
//
//        }
//        todayAccuWeatherCondition = accuWeatherConditionsArrayList.get(0);
//
//        todayAccuWeatherCondition.setCurrentTemp(currentTemp);
//        int dateInTimestamp = todayAccuWeatherCondition.getDate();
//        Date dateToday = Utils.getDate(dateInTimestamp);
//        String dateTodayString = Utils.getDateString(dateInTimestamp);
//
//        if (listItemTodayCurrentTextview != null) {
//            listItemTodayDateTextview.setText(dateTodayString);
//            listItemTodayCurrentTextview.setText(todayAccuWeatherCondition.getCurrentTemp() + "ºC");
//            listItemTodayForecastTextview.setText(todayAccuWeatherCondition.getDayPhrase());
//            listItemTodayHighTextview.setText(Math.round(todayAccuWeatherCondition.getTempMax()) + "ºC");
//            listItemTodayLowTextview.setText(Math.round(todayAccuWeatherCondition.getTempMin()) + "ºC");
//
//        }
//        switch (todayAccuWeatherCondition.getDayPhrase()) {
//            case "Sunny":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "Clear":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "Mostly clear":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "Mostly sunny":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "Partly sunny":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "Intermittent clouds":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
//
//                break;
//            case "Hazy sunshine":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
//
//                break;
//            case "Mostly cloudy":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
//
//                break;
//            case "Cloudy":
//                listItemTodayIcon.setImageResource(R.drawable.art_clouds);
//
//                break;
//            case "Few clouds":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
//
//                break;
//            case "Snow":
//                listItemTodayIcon.setImageResource(R.drawable.art_snow);
//
//                break;
//            case "Mostly Cloudy w/ Snow":
//                listItemTodayIcon.setImageResource(R.drawable.art_snow);
//
//                break;
//            case "Fog":
//                listItemTodayIcon.setImageResource(R.drawable.art_fog);
//
//                break;
//            case "T-Storms":
//                listItemTodayIcon.setImageResource(R.drawable.art_storm);
//
//                break;
//            case "Mostly Cloudy w/ T-Storms":
//                listItemTodayIcon.setImageResource(R.drawable.art_storm);
//
//                break;
//            case "Partly Sunny w/ T-Storms":
//                listItemTodayIcon.setImageResource(R.drawable.art_storm);
//
//                break;
//            case "Mostly Cloudy w/ Showers":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
//
//                break;
//
//            case "Partly Sunny w/ Showers":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
//                break;
//
//            case "Scattered Showers":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
//                break;
//            case "Showers":
//                listItemTodayIcon.setImageResource(R.drawable.art_rain);
//                break;
//            case "Rain":
//                listItemTodayIcon.setImageResource(R.drawable.art_rain);
//                break;
//            default:
//                break;
//
//        }
//        accuWeatherConditionsNextDaysArrayList = accuWeatherConditionsArrayList.subList(1, accuWeatherConditionsArrayList.size());
//        LinearLayoutManager layoutManager =
//                new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        accuWeatherAdapter = new AccuWeatherAdapter(getActivity(), accuWeatherConditionsNextDaysArrayList);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
//                layoutManager.getOrientation());
//        forecastRecycle.addItemDecoration(dividerItemDecoration);
//        forecastRecycle.setLayoutManager(layoutManager);
//        forecastRecycle.setNestedScrollingEnabled(false);
//        forecastRecycle.setLayoutParams(layoutParams);
//        forecastRecycle.setAdapter(accuWeatherAdapter);
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

            accuWeatherConditionsArrayList.add(0, darkSkyWeatherObject);
        }
        WeatherObject lastWeatherObject = new WeatherObject();
        lastWeatherObject = accuWeatherConditionsArrayList.get(0);
        if (listItemTodayDateTextview != null) {

            listItemTodayDateTextview.setText(lastWeatherObject.getFechahoraConsulta());
            listItemTodayForecastTextview.setText(lastWeatherObject.getCondDia());
            listItemTodayCurrentTextview.setText(lastWeatherObject.gettActual() + "ºC");
            listItemTodayHighTextview.setText(lastWeatherObject.gettMax() + "ºC");
            listItemTodayLowTextview.setText(lastWeatherObject.gettMin() + "ºC");
        }
        switch (lastWeatherObject.getCondDia()) {
            case "Soleado":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Mayormente soleado":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Mostly clear":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Mostly sunny":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Parcialmente soleado":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Parcialmente soleado con chaparrones":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);

                break;
            case "Nubes intermitentes":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);

                break;
            case "Hazy sunshine":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);

                break;
            case "Mostly cloudy":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);

                break;
            case "Cloudy":
                listItemTodayIcon.setImageResource(R.drawable.art_clouds);

                break;
            case "Few clouds":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);

                break;
            case "Snow":
                listItemTodayIcon.setImageResource(R.drawable.art_snow);

                break;
            case "Mostly cloudy w/ snow":
                listItemTodayIcon.setImageResource(R.drawable.art_snow);

                break;
            case "Fog":
                listItemTodayIcon.setImageResource(R.drawable.art_fog);

                break;
            case "T-Storms":
                listItemTodayIcon.setImageResource(R.drawable.art_storm);

                break;
            case "Mostly cloudy w/ T-Storms":
                listItemTodayIcon.setImageResource(R.drawable.art_storm);

                break;
            case "Partly sunny w/ T-Storms":
                listItemTodayIcon.setImageResource(R.drawable.art_storm);

                break;
            case "Mostly cloudy w/ Showers":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);

                break;

            case "Partly sunny w/ Showers":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
                break;

            case "Scattered showers":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
                break;
            case "Chaparrones":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);
                break;
            case "Nublado":
                listItemTodayIcon.setImageResource(R.drawable.art_clouds);
                break;
            case "Lluvias":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);
                break;
            case "Mayormente nublado con chaparrones":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);
                break;
            case "Mayormente nublado":
                listItemTodayIcon.setImageResource(R.drawable.art_clouds);
                break;
            default:
                break;

        }

        accuWeatherConditionsNextDaysArrayList = accuWeatherConditionsArrayList.subList(1, accuWeatherConditionsArrayList.size());

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        accuWeatherAdapter = new AccuWeatherAdapter(getActivity(), accuWeatherConditionsNextDaysArrayList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());
        forecastRecycle.addItemDecoration(dividerItemDecoration);
        forecastRecycle.setLayoutManager(layoutManager);
        forecastRecycle.setNestedScrollingEnabled(false);
        forecastRecycle.setLayoutParams(layoutParams);
        forecastRecycle.setAdapter(accuWeatherAdapter);


        displayWeather();

    }

    @Override
    public void getCurrentTemp(String response) {
//        JsonParser parser = new JsonParser();
//        JsonArray json = (JsonArray) parser.parse(response);
//        JsonElement temp = json.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Metric").getAsJsonObject().get("Value");
//
//        currentTemp = temp.getAsInt();
//        presenter.callAccuWeather(queueAccuWeather);
    }


}
