package cl.ceisufro.weathercompare.apixu;

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
import cl.ceisufro.weathercompare.apixu.adapter.ApixuWeatherAdapter;
import cl.ceisufro.weathercompare.models.ApixuWeatherConditions;
import cl.ceisufro.weathercompare.utils.Utils;
import io.realm.Realm;
import io.realm.RealmResults;


public class APIXUWeatherFragment extends Fragment implements ApixuWeatherView {

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
    ApixuWeatherPresenter presenter;
    List<ApixuWeatherConditions> apixuWeatherConditionsArrayList;
    List<ApixuWeatherConditions> apixuWeatherConditionsNextDaysArrayList;
    ApixuWeatherConditions todayApixuWeatherCondition = new ApixuWeatherConditions();
    ApixuWeatherAdapter apixuWeatherAdapter;
    Realm realm;
    RequestQueue queueApixuWeather = null;

    RealmResults<ApixuWeatherConditions> apixuWeatherConditionsRealmResults;
    @BindView(R.id.list_item_today_current_textview)
    TextView listItemTodayCurrentTextview;
    private Integer currentTemp = null;

//    private RealmChangeListener<RealmResults<ApixuWeatherConditions>> realmChangeListener = apixuWeatherConditionsRealmResults -> {
//        // Set the cities to the adapter only when async query is loaded.
//        // It will also be called for any future writes made to the Realm.
//        apixuWeatherAdapter.setData(apixuWeatherConditionsRealmResults);
//    };
//    private Tracker mTracker;

    public static String getFragmentTag() {
        return "apixu";
    }

    public APIXUWeatherFragment() {
        // Required empty public constructor
    }

//    private void initAdapter() {
//        apixuWeatherAdapter = new ApixuWeatherAdapter(getActivity(), apixuWeatherConditionsNextDaysArrayList);
//        forecastRecycle.setLayoutManager((new LinearLayoutManager(getActivity())));
//        forecastRecycle.setNestedScrollingEnabled(false);
//
////        forecastRecycle.setAdapter(apixuWeatherAdapter);
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
        apixuWeatherConditionsRealmResults = realm.where(ApixuWeatherConditions.class).findAllAsync();

        // The RealmChangeListener will be called when the results are asynchronously loaded, and available for use.
//        apixuWeatherConditionsRealmResults.addChangeListener(realmChangeListener);
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

        presenter = new ApixuWeatherPresenterImpl(this);
        apixuWeatherConditionsArrayList = new ArrayList<>();
        apixuWeatherConditionsNextDaysArrayList = new ArrayList<>();
//        initAdapter();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queueApixuWeather = Volley.newRequestQueue(getActivity());
        presenter.callApixuWeather(queueApixuWeather);
//        presenter.callApixuWeatherCurrent(queueApixuWeather);
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

        JsonObject current = json.get("current").getAsJsonObject();
        JsonArray forecast = json.get("forecast").getAsJsonObject().get("forecastday").getAsJsonArray();
        int currentTemp = current.get("temp_c").getAsInt();
        for (JsonElement apixuWeatherDateCondition :
                forecast) {
            JsonElement date = apixuWeatherDateCondition.getAsJsonObject().get("date_epoch");
            JsonElement tempMin = apixuWeatherDateCondition.getAsJsonObject().get("day").getAsJsonObject().get("mintemp_c");
            JsonElement tempMax = apixuWeatherDateCondition.getAsJsonObject().get("day").getAsJsonObject().get("maxtemp_c");
            JsonElement condition = apixuWeatherDateCondition.getAsJsonObject().get("day").getAsJsonObject().get("condition").getAsJsonObject().get("text");
//            JsonElement tempNight = temp.getAsJsonObject().get("night");
//            JsonElement tempEve = temp.getAsJsonObject().get("eve");
//            JsonElement tempMorn = temp.getAsJsonObject().get("morn");
//            JsonElement pressure = apixuWeatherDateCondition.getAsJsonObject().get("pressure");
//            JsonElement humidity = apixuWeatherDateCondition.getAsJsonObject().get("humidity");
//            JsonElement weather = apixuWeatherDateCondition.getAsJsonObject().get("weather");
//            JsonElement weatherId = weather.getAsJsonArray().get(0).getAsJsonObject().get("id");
//            JsonElement weatherMain = weather.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("main");
//            JsonElement weatherDescription = weather.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("description");
//            JsonElement weatherIcon = weather.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("icon");
//            JsonElement speed = apixuWeatherDateCondition.getAsJsonObject().get("speed");
//            JsonElement deg = apixuWeatherDateCondition.getAsJsonObject().get("deg");
//            JsonElement clouds = apixuWeatherDateCondition.getAsJsonObject().get("clouds");
//            JsonElement rain = apixuWeatherDateCondition.getAsJsonObject().get("rain");


            ApixuWeatherConditions apixuWeatherConditions = new ApixuWeatherConditions(currentTemp, tempMax.getAsInt(), tempMin.getAsInt(), date.getAsInt(), condition.getAsString());


//            int dateTimestamp = dateInTimestamp.getAsInt();
//            if (rain == null) {
//                apixuWeatherConditions = new ApixuWeatherConditions(dateInTimestamp.getAsInt(), tempMax.getAsFloat(), tempMin.getAsFloat(), tempDay.getAsFloat(), tempNight.getAsFloat(),
//                        tempEve.getAsFloat(), tempMorn.getAsFloat(), pressure.getAsFloat(), humidity.getAsFloat()
//                        , weatherId.getAsInt(), weatherMain.getAsString(), weatherDescription.getAsString(), weatherIcon.getAsString(),
//                        speed.getAsFloat(), deg.getAsInt(), clouds.getAsInt()
//
//                );
//
//
//            } else {
//                apixuWeatherConditions = new ApixuWeatherConditions(dateInTimestamp.getAsInt(), tempMax.getAsFloat(), tempMin.getAsFloat(), tempDay.getAsFloat(), tempNight.getAsFloat(),
//                        tempEve.getAsFloat(), tempMorn.getAsFloat(), pressure.getAsFloat(), humidity.getAsFloat()
//                        , weatherId.getAsInt(), weatherMain.getAsString(), weatherDescription.getAsString(), weatherIcon.getAsString(),
//                        speed.getAsFloat(), deg.getAsInt(), clouds.getAsInt(), rain.getAsFloat()
//
//                );
//
//            }
            apixuWeatherConditionsArrayList.add(apixuWeatherConditions);

        }
        todayApixuWeatherCondition = apixuWeatherConditionsArrayList.get(0);

//        todayApixuWeatherCondition.setCurrentTemp(currentTemp);
        int dateInTimestamp = todayApixuWeatherCondition.getDate();
        Date dateToday = Utils.getDate(dateInTimestamp);
        String dateTodayString = Utils.getDateString(dateInTimestamp);
//
        listItemTodayDateTextview.setText(dateTodayString);
        listItemTodayCurrentTextview.setText(todayApixuWeatherCondition.getCurrentTemp() + "ºC");
        listItemTodayForecastTextview.setText(todayApixuWeatherCondition.getConditionText());
        listItemTodayHighTextview.setText(Math.round(todayApixuWeatherCondition.getTempMax()) + "ºC");
        listItemTodayLowTextview.setText(Math.round(todayApixuWeatherCondition.getTempMin()) + "ºC");
        switch (todayApixuWeatherCondition.getConditionText()) {
            case "Sunny":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Clear":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Mostly clear":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Mostly sunny":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Partly sunny":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Intermittent clouds":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);

                break;
            case "Hazy sunshine":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);

                break;
            case "Partly cloudy":
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
            case "Mostly Cloudy w/ Snow":
                listItemTodayIcon.setImageResource(R.drawable.art_snow);

                break;
            case "Fog":
                listItemTodayIcon.setImageResource(R.drawable.art_fog);

                break;
            case "T-Storms":
                listItemTodayIcon.setImageResource(R.drawable.art_storm);

                break;
            case "Mostly Cloudy w/ T-Storms":
                listItemTodayIcon.setImageResource(R.drawable.art_storm);

                break;
            case "Partly Sunny w/ T-Storms":
                listItemTodayIcon.setImageResource(R.drawable.art_storm);

                break;
            case "Mostly Cloudy w/ Showers":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);

                break;

            case "Partly Sunny w/ Showers":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
                break;

            case "Scattered Showers":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
                break;
            case "Moderate or heavy rain shower":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);
                break;
            case "Showers":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);
                break;
            case "Patchy rain possible":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
                break;
            case "Rain":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);
                break;
            default:
                break;

        }
        apixuWeatherConditionsNextDaysArrayList = apixuWeatherConditionsArrayList.subList(1, apixuWeatherConditionsArrayList.size());
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        apixuWeatherAdapter = new ApixuWeatherAdapter(getActivity(), apixuWeatherConditionsNextDaysArrayList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());
        forecastRecycle.addItemDecoration(dividerItemDecoration);
        forecastRecycle.setLayoutManager(layoutManager);
        forecastRecycle.setNestedScrollingEnabled(false);
        forecastRecycle.setLayoutParams(layoutParams);
        forecastRecycle.setAdapter(apixuWeatherAdapter);


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
//        JsonParser parser = new JsonParser();
//        JsonArray json = (JsonArray) parser.parse(response);
//        JsonElement temp = json.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Metric").getAsJsonObject().get("Value");
//
//        currentTemp = temp.getAsInt();
//        presenter.callApixuWeather(queueApixuWeather);
    }


}
