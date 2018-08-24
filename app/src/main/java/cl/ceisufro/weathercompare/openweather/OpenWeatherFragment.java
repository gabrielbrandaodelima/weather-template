package cl.ceisufro.weathercompare.openweather;

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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cl.ceisufro.weathercompare.R;
import cl.ceisufro.weathercompare.main.ListWeatherPresenter;
import cl.ceisufro.weathercompare.main.ListWeatherPresenterImpl;
import cl.ceisufro.weathercompare.models.OpenWeatherConditions;
import cl.ceisufro.weathercompare.models.objrequisicion.OpenWeatherObject;
import cl.ceisufro.weathercompare.openweather.adapter.OpenWeatherAdapter;
import io.realm.Realm;
import io.realm.RealmResults;


public class OpenWeatherFragment extends Fragment implements OpenWeatherView {

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
    List<OpenWeatherObject> openWeatherConditionsArrayList;
    List<OpenWeatherObject> openWeatherConditionsNextDaysArrayList;
    OpenWeatherObject todayOpenWeatherCondition = new OpenWeatherObject();
    OpenWeatherAdapter openWeatherAdapter;
    Realm realm;
    RequestQueue queueOpenWeather = null;

    RealmResults<OpenWeatherConditions> openWeatherConditionsRealmResults;
    @BindView(R.id.list_item_today_current_textview)
    TextView listItemTodayCurrentTextview;
    private Integer currentTemp = null;

//    private RealmChangeListener<RealmResults<OpenWeatherConditions>> realmChangeListener = openWeatherConditionsRealmResults -> {
//        // Set the cities to the adapter only when async query is loaded.
//        // It will also be called for any future writes made to the Realm.
//        openWeatherAdapter.setData(openWeatherConditionsRealmResults);
//    };
//    private Tracker mTracker;

    public static String getFragmentTag() {
        return "open";
    }

    public OpenWeatherFragment() {
        // Required empty public constructor
    }

//    private void initAdapter() {
//        openWeatherAdapter = new OpenWeatherAdapter(getActivity(), openWeatherConditionsNextDaysArrayList);
//        forecastRecycle.setLayoutManager((new LinearLayoutManager(getActivity())));
//        forecastRecycle.setNestedScrollingEnabled(false);
//
////        forecastRecycle.setAdapter(openWeatherAdapter);
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
        openWeatherConditionsRealmResults = realm.where(OpenWeatherConditions.class).findAllAsync();

        // The RealmChangeListener will be called when the results are asynchronously loaded, and available for use.
//        openWeatherConditionsRealmResults.addChangeListener(realmChangeListener);
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

        presenter = new ListWeatherPresenterImpl(this);
        openWeatherConditionsArrayList = new ArrayList<>();
        openWeatherConditionsNextDaysArrayList = new ArrayList<>();
//        initAdapter();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queueOpenWeather = Volley.newRequestQueue(getActivity());
        presenter.listWeatherRequest(queueOpenWeather, 5);
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
    public void displayLayout() {
        
    }

    @Override
    public void displayWeather() {

        hideProgress();
        showLayout();
    }

    @Override
    public void populateWeatherList(String response) {
//        JsonParser parser = new JsonParser();
//        JsonObject json = (JsonObject) parser.parse(response);
//
//        JsonArray jsonArrayForecast = json.get("list").getAsJsonArray();
//        for (JsonElement openWeatherDateCondition :
//                jsonArrayForecast) {
//            JsonElement dateInTimestamp = openWeatherDateCondition.getAsJsonObject().get("dt");
//            JsonElement temp = openWeatherDateCondition.getAsJsonObject().get("temp");
//            JsonElement tempDay = temp.getAsJsonObject().get("day");
//            JsonElement tempMin = temp.getAsJsonObject().get("min");
//            JsonElement tempMax = temp.getAsJsonObject().get("max");
//            JsonElement tempNight = temp.getAsJsonObject().get("night");
//            JsonElement tempEve = temp.getAsJsonObject().get("eve");
//            JsonElement tempMorn = temp.getAsJsonObject().get("morn");
//            JsonElement pressure = openWeatherDateCondition.getAsJsonObject().get("pressure");
//            JsonElement humidity = openWeatherDateCondition.getAsJsonObject().get("humidity");
//            JsonElement weather = openWeatherDateCondition.getAsJsonObject().get("weather");
//            JsonElement weatherId = weather.getAsJsonArray().get(0).getAsJsonObject().get("id");
//            JsonElement weatherMain = weather.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("main");
//            JsonElement weatherDescription = weather.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("description");
//            JsonElement weatherIcon = weather.getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject().get("icon");
//            JsonElement speed = openWeatherDateCondition.getAsJsonObject().get("speed");
//            JsonElement deg = openWeatherDateCondition.getAsJsonObject().get("deg");
//            JsonElement clouds = openWeatherDateCondition.getAsJsonObject().get("clouds");
//            JsonElement rain = openWeatherDateCondition.getAsJsonObject().get("rain");
//
//
//            OpenWeatherConditions openWeatherConditions;
////            int dateTimestamp = dateInTimestamp.getAsInt();
//            if (rain == null) {
//                openWeatherConditions = new OpenWeatherConditions(dateInTimestamp.getAsInt(), tempMax.getAsFloat(), tempMin.getAsFloat(), tempDay.getAsFloat(), tempNight.getAsFloat(),
//                        tempEve.getAsFloat(), tempMorn.getAsFloat(), pressure.getAsFloat(), humidity.getAsFloat()
//                        , weatherId.getAsInt(), weatherMain.getAsString(), weatherDescription.getAsString(), weatherIcon.getAsString(),
//                        speed.getAsFloat(), deg.getAsInt(), clouds.getAsInt()
//
//                );
//
//
//            } else {
//                openWeatherConditions = new OpenWeatherConditions(dateInTimestamp.getAsInt(), tempMax.getAsFloat(), tempMin.getAsFloat(), tempDay.getAsFloat(), tempNight.getAsFloat(),
//                        tempEve.getAsFloat(), tempMorn.getAsFloat(), pressure.getAsFloat(), humidity.getAsFloat()
//                        , weatherId.getAsInt(), weatherMain.getAsString(), weatherDescription.getAsString(), weatherIcon.getAsString(),
//                        speed.getAsFloat(), deg.getAsInt(), clouds.getAsInt(), rain.getAsFloat()
//
//                );
//
//            }
//            openWeatherConditionsArrayList.add(openWeatherConditions);
//
//        }
//        todayOpenWeatherCondition = openWeatherConditionsArrayList.get(0);
//
//        todayOpenWeatherCondition.setCurrentTemp(currentTemp);
//        int dateInTimestamp = todayOpenWeatherCondition.getDateInTimestamp();
////        Date dateToday = Utils.getDate(dateInTimestamp);
//        String dateTodayString = Utils.getDateString(dateInTimestamp);
//        if (listItemTodayDateTextview != null) {
//
//            listItemTodayDateTextview.setText(dateTodayString);
//            listItemTodayCurrentTextview.setText(todayOpenWeatherCondition.getCurrentTemp() + "ºC");
//            listItemTodayForecastTextview.setText(todayOpenWeatherCondition.getWeatherMain());
//            listItemTodayHighTextview.setText(Math.round(todayOpenWeatherCondition.getTempMax()) + "ºC");
//            listItemTodayLowTextview.setText(Math.round(todayOpenWeatherCondition.getTempMin()) + "ºC");
//        }
//        switch (todayOpenWeatherCondition.getWeatherMain()) {
//            case "Clear":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "Clear Sky":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "Scattered Clouds":
//                listItemTodayIcon.setImageResource(R.drawable.art_clouds);
//
//                break;
//            case "Clouds":
//                listItemTodayIcon.setImageResource(R.drawable.art_clouds);
//
//                break;
//            case "Few Clouds":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
//
//                break;
//            case "Snow":
//                listItemTodayIcon.setImageResource(R.drawable.art_snow);
//
//                break;
//            case "Mist":
//                listItemTodayIcon.setImageResource(R.drawable.art_fog);
//
//                break;
//            case "Thunderstorm":
//                listItemTodayIcon.setImageResource(R.drawable.art_storm);
//
//                break;
//            case "Shower Rain":
//                listItemTodayIcon.setImageResource(R.drawable.art_rain);
//
//                break;
//            case "Rain":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
//
//                break;
//
//            case "Partly Cloudy":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
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
//
//        }
//        openWeatherConditionsNextDaysArrayList = openWeatherConditionsArrayList.subList(1, openWeatherConditionsArrayList.size());
//        LinearLayoutManager layoutManager =
//                new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        openWeatherAdapter = new OpenWeatherAdapter(getActivity(), openWeatherConditionsNextDaysArrayList);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
//                layoutManager.getOrientation());
//        forecastRecycle.addItemDecoration(dividerItemDecoration);
//        forecastRecycle.setLayoutManager(layoutManager);
//        forecastRecycle.setNestedScrollingEnabled(false);
//        forecastRecycle.setLayoutParams(layoutParams);
//        forecastRecycle.setAdapter(openWeatherAdapter);
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
            OpenWeatherObject openWeatherObject = new OpenWeatherObject();
            openWeatherObject.setFechahoraConsulta(fechahoraConsulta);
            openWeatherObject.settActual(tActual);
            openWeatherObject.settMax(tMax);
            openWeatherObject.settMin(tMin);
            openWeatherObject.setPresion(presion);
            openWeatherObject.setHumedad(humedad);
            openWeatherObject.setvViento(vViento);

            if (!condActualDia.isEmpty()) {
                openWeatherObject.setCondActualDia(condActualDia);

            }
            if (!condActualNoche.isEmpty()) {
                openWeatherObject.setCondActualNoche(condActualNoche);

            }
            if (!condDia.isEmpty()) {
                openWeatherObject.setCondDia(condDia);

            }
            if (!condNoche.isEmpty()) {
                openWeatherObject.setCondNoche(condNoche);

            }

            openWeatherConditionsArrayList.add(0,openWeatherObject);
        }
        OpenWeatherObject lastOpenWeatherObject = new OpenWeatherObject();
        lastOpenWeatherObject = openWeatherConditionsArrayList.get(0);
        if (listItemTodayDateTextview != null) {

            listItemTodayDateTextview.setText(lastOpenWeatherObject.getFechahoraConsulta());
            listItemTodayForecastTextview.setText(lastOpenWeatherObject.getCondDia());
            listItemTodayCurrentTextview.setText(lastOpenWeatherObject.gettActual()+"ºC");
            listItemTodayHighTextview.setText(lastOpenWeatherObject.gettMax()+"ºC");
            listItemTodayLowTextview.setText(lastOpenWeatherObject.gettMin()+"ºC");
        }
        switch (lastOpenWeatherObject.getCondDia()) {

            case "Clear":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Clear Sky":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Scattered Clouds":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);

                break;
            case "Clouds":
                listItemTodayIcon.setImageResource(R.drawable.art_clouds);

                break;
            case "Few Clouds":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);

                break;
            case "Snow":
                listItemTodayIcon.setImageResource(R.drawable.art_snow);

                break;
            case "Mist":
                listItemTodayIcon.setImageResource(R.drawable.art_fog);

                break;
            case "Thunderstorm":
                listItemTodayIcon.setImageResource(R.drawable.art_storm);

                break;
            case "Shower Rain":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);

                break;
            case "Rain":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);

                break;

            case "Partly Cloudy":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
                break;

            case "Scattered Showers":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
                break;
            case "Showers":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);
                break;
            default:
                break;
        }

        openWeatherConditionsNextDaysArrayList = openWeatherConditionsArrayList.subList(1, openWeatherConditionsArrayList.size());

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        openWeatherAdapter = new OpenWeatherAdapter(getActivity(), openWeatherConditionsNextDaysArrayList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());
        forecastRecycle.addItemDecoration(dividerItemDecoration);
        forecastRecycle.setLayoutManager(layoutManager);
        forecastRecycle.setNestedScrollingEnabled(false);
        forecastRecycle.setLayoutParams(layoutParams);
        forecastRecycle.setAdapter(openWeatherAdapter);


        displayWeather();

    }

    @Override
    public void getCurrentTemp(String response) {
//        JsonParser parser = new JsonParser();
//        JsonObject json = (JsonObject) parser.parse(response);
//        JsonElement temp = json.get("main").getAsJsonObject().get("temp");
//        currentTemp = temp.getAsInt();
//        presenter.callOpenWeather(queueOpenWeather);
    }


}
