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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cl.ceisufro.weathercompare.R;
import cl.ceisufro.weathercompare.accuweather.adapter.AccuWeatherAdapter;
import cl.ceisufro.weathercompare.models.AccuWeatherConditions;
import cl.ceisufro.weathercompare.utils.Utils;
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
    AccuWeatherPresenter presenter;
    List<AccuWeatherConditions> accuWeatherConditionsArrayList;
    List<AccuWeatherConditions> accuWeatherConditionsNextDaysArrayList;
    AccuWeatherConditions todayAccuWeatherCondition = new AccuWeatherConditions();
    AccuWeatherAdapter accuWeatherAdapter;
    Realm realm;
    RequestQueue queueAccuWeather = null;

    RealmResults<AccuWeatherConditions> accuWeatherConditionsRealmResults;
    @BindView(R.id.list_item_today_current_textview)
    TextView listItemTodayCurrentTextview;
    private Integer currentTemp = null;

//    private RealmChangeListener<RealmResults<AccuWeatherConditions>> realmChangeListener = accuWeatherConditionsRealmResults -> {
//        // Set the cities to the adapter only when async query is loaded.
//        // It will also be called for any future writes made to the Realm.
//        accuWeatherAdapter.setData(accuWeatherConditionsRealmResults);
//    };
//    private Tracker mTracker;

    public static String getFragmentTag() {
        return "accu";
    }

    public AccuWeatherFragment() {
        // Required empty public constructor
    }

//    private void initAdapter() {
//        accuWeatherAdapter = new AccuWeatherAdapter(getActivity(), accuWeatherConditionsNextDaysArrayList);
//        forecastRecycle.setLayoutManager((new LinearLayoutManager(getActivity())));
//        forecastRecycle.setNestedScrollingEnabled(false);
//
////        forecastRecycle.setAdapter(accuWeatherAdapter);
//
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Clear the realm from last time
        //noinspection ConstantConditions
//        Realm.deleteRealm(Realm.getDefaultConfiguration());

        // Create a new empty instance of Realm
        realm = Realm.getDefaultInstance();

        // Obtain the cities in the Realm with asynchronous query.
        accuWeatherConditionsRealmResults = realm.where(AccuWeatherConditions.class).findAllAsync();

        // The RealmChangeListener will be called when the results are asynchronously loaded, and available for use.
//        accuWeatherConditionsRealmResults.addChangeListener(realmChangeListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        unbinder = ButterKnife.bind(this, view);

//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//
//        }
//
        hideLayout();
        showProgress();

        presenter = new AccuWeatherPresenterImpl(this);
        accuWeatherConditionsArrayList = new ArrayList<>();
        accuWeatherConditionsNextDaysArrayList = new ArrayList<>();
//        initAdapter();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queueAccuWeather = Volley.newRequestQueue(getActivity());
        presenter.callAccuWeatherCurrent(queueAccuWeather);
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
//        unbinder.unbind();

//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//
//        }
        unbinder.unbind();
    }


    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void displayWeather() {

        hideProgress();
        showLayout();
    }

    @Override
    public void populateWeatherList(String response) {
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(response);

        JsonArray jsonArrayForecast = json.get("list").getAsJsonArray();
        for (JsonElement accuWeatherDateCondition :
                jsonArrayForecast) {
            JsonElement dateInTimestamp = accuWeatherDateCondition.getAsJsonObject().get("dt");
            JsonElement temp = accuWeatherDateCondition.getAsJsonObject().get("temp");
            JsonElement tempDay = temp.getAsJsonObject().get("day");
            JsonElement tempMin = temp.getAsJsonObject().get("min");
            JsonElement tempMax = temp.getAsJsonObject().get("max");
            JsonElement tempNight = temp.getAsJsonObject().get("night");
            JsonElement tempEve = temp.getAsJsonObject().get("eve");
            JsonElement tempMorn = temp.getAsJsonObject().get("morn");
            JsonElement pressure = accuWeatherDateCondition.getAsJsonObject().get("pressure");
            JsonElement humidity = accuWeatherDateCondition.getAsJsonObject().get("humidity");
            JsonElement weather = accuWeatherDateCondition.getAsJsonObject().get("weather");
            JsonElement weatherId = weather.getAsJsonArray().get(0).getAsJsonObject().get("id");
            JsonElement weatherMain = weather.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("main");
            JsonElement weatherDescription = weather.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("description");
            JsonElement weatherIcon = weather.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("icon");
            JsonElement speed = accuWeatherDateCondition.getAsJsonObject().get("speed");
            JsonElement deg = accuWeatherDateCondition.getAsJsonObject().get("deg");
            JsonElement clouds = accuWeatherDateCondition.getAsJsonObject().get("clouds");
            JsonElement rain = accuWeatherDateCondition.getAsJsonObject().get("rain");


            AccuWeatherConditions accuWeatherConditions;
//            int dateTimestamp = dateInTimestamp.getAsInt();
//            if (rain == null) {
//                accuWeatherConditions = new AccuWeatherConditions(dateInTimestamp.getAsInt(), tempMax.getAsFloat(), tempMin.getAsFloat(), tempDay.getAsFloat(), tempNight.getAsFloat(),
//                        tempEve.getAsFloat(), tempMorn.getAsFloat(), pressure.getAsFloat(), humidity.getAsFloat()
//                        , weatherId.getAsInt(), weatherMain.getAsString(), weatherDescription.getAsString(), weatherIcon.getAsString(),
//                        speed.getAsFloat(), deg.getAsInt(), clouds.getAsInt()
//
//                );
//
//
//            } else {
//                accuWeatherConditions = new AccuWeatherConditions(dateInTimestamp.getAsInt(), tempMax.getAsFloat(), tempMin.getAsFloat(), tempDay.getAsFloat(), tempNight.getAsFloat(),
//                        tempEve.getAsFloat(), tempMorn.getAsFloat(), pressure.getAsFloat(), humidity.getAsFloat()
//                        , weatherId.getAsInt(), weatherMain.getAsString(), weatherDescription.getAsString(), weatherIcon.getAsString(),
//                        speed.getAsFloat(), deg.getAsInt(), clouds.getAsInt(), rain.getAsFloat()
//
//                );
//
//            }
//            accuWeatherConditionsArrayList.add(accuWeatherConditions);

        }
        todayAccuWeatherCondition = accuWeatherConditionsArrayList.get(0);

        todayAccuWeatherCondition.setCurrentTemp(currentTemp);
        int dateInTimestamp = todayAccuWeatherCondition.getDate();
        Date dateToday = Utils.getDate(dateInTimestamp);
        String dateTodayString = Utils.getDateString(dateInTimestamp);

        listItemTodayDateTextview.setText(dateTodayString);
        listItemTodayCurrentTextview.setText(todayAccuWeatherCondition.getCurrentTemp() + "ºC");
        listItemTodayForecastTextview.setText(todayAccuWeatherCondition.getDayPhrase());
        listItemTodayHighTextview.setText(Math.round(todayAccuWeatherCondition.getTempMax()) + "ºC");
        listItemTodayLowTextview.setText(Math.round(todayAccuWeatherCondition.getTempMin()) + "ºC");
        switch (todayAccuWeatherCondition.getDayPhrase()) {
            case "Sunny":
                listItemTodayIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Clear":
                listItemTodayIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Mostly Clear":
                listItemTodayIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Mostly Sunny":
                listItemTodayIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Partly Sunny":
                listItemTodayIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Intermittent Clouds":
                listItemTodayIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Hazy Sunshine":
                listItemTodayIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Mostly Cloudy":
                listItemTodayIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Cloudy":
                listItemTodayIcon.setImageResource(R.drawable.ic_cloudy);

                break;
            case "Few Clouds":
                listItemTodayIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Snow":
                listItemTodayIcon.setImageResource(R.drawable.ic_snow);

                break;
            case "Mostly Cloudy w/ Snow":
                listItemTodayIcon.setImageResource(R.drawable.ic_snow);

                break;
            case "Fog":
                listItemTodayIcon.setImageResource(R.drawable.ic_fog);

                break;
            case "T-Storms":
                listItemTodayIcon.setImageResource(R.drawable.ic_storm);

                break;
            case "Mostly Cloudy w/ T-Storms":
                listItemTodayIcon.setImageResource(R.drawable.ic_storm);

                break;
            case "Partly Sunny w/ T-Storms":
                listItemTodayIcon.setImageResource(R.drawable.ic_storm);

                break;
            case "Mostly Cloudy w/ Showers":
                listItemTodayIcon.setImageResource(R.drawable.ic_light_rain);

                break;

            case "Partly Sunny w/ Showers":
                listItemTodayIcon.setImageResource(R.drawable.ic_light_rain);
                break;

            case "Scattered Showers":
                listItemTodayIcon.setImageResource(R.drawable.ic_light_rain);
                break;
            case "Showers":
                listItemTodayIcon.setImageResource(R.drawable.ic_rain);
                break;
            case "Rain":
                listItemTodayIcon.setImageResource(R.drawable.ic_rain);
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
    public void getCurrentTemp(String response) {
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(response);
        JsonElement temp = json.get("main").getAsJsonObject().get("temp");
        currentTemp = temp.getAsInt();
        presenter.callAccuWeather(queueAccuWeather);
    }


}
