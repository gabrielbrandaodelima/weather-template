package weathercompare.yahoo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
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
import weathercompare.main.ListWeatherPresenter;
import weathercompare.main.ListWeatherPresenterImpl;
import weathercompare.models.YahooWeatherConditions;
import weathercompare.models.objrequisicion.YahooWeatherObject;
import weathercompare.yahoo.adapter.YahooWeatherAdapter;
import io.realm.Realm;
import io.realm.RealmResults;


public class YahooWeatherFragment extends Fragment implements YahooWeatherView {
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

    YahooWeatherPresenter presenter;
    List<YahooWeatherObject> yahooWeatherObjectList;
    List<YahooWeatherObject> yahooWeatherObjectListNextDays;
    YahooWeatherObject todayYahooWeatherObject;
    YahooWeatherAdapter yahooWeatherAdapter;
    Realm realm;
    RequestQueue queueYAhooWeather = null;
    RealmResults<YahooWeatherConditions> yahooWeatherConditionsRealmResults;
    @BindView(R.id.list_item_today_current_textview)
    TextView listItemTodayCurrentTextview;
    @BindView(R.id.error_animation_view)
    LottieAnimationView errorAnimationView;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.error_text)
    TextView errorText;
    @BindView(R.id.ultimo_dato_layout)
    LinearLayout ultimoDatoLayout;
    private ListWeatherPresenter listWeatherPresenter;

//    private Tracker mTracker;

    public static String getFragmentTag() {
        return "yahoo";
    }

    public YahooWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        Realm.deleteRealm(Realm.getDefaultConfiguration());

        // Create a new empty instance of Realm
        realm = Realm.getDefaultInstance();

        // Obtain the cities in the Realm with asynchronous query.
        yahooWeatherConditionsRealmResults = realm.where(YahooWeatherConditions.class).findAllAsync();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        unbinder = ButterKnife.bind(this, view);
        hideLayout();
        showProgress();

        presenter = new YahooWeatherPresenterImpl(this);
        yahooWeatherObjectList = new ArrayList<>();
        yahooWeatherObjectListNextDays = new ArrayList<>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queueYAhooWeather = Volley.newRequestQueue(getActivity());
//        presenter.callYahooWeather(queueYAhooWeather);

        listWeatherPresenter = new ListWeatherPresenterImpl(this);
        listWeatherPresenter.listWeatherRequest(queueYAhooWeather, 3);
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

    //    @Override
    public void populateWeatherList(String response) {
//        JsonParser parser = new JsonParser();
//        JsonObject json = (JsonObject) parser.parse(response);
//
//        JsonObject jsonObjectForecast = json.get("query").getAsJsonObject().get("results").getAsJsonObject().get("channel").getAsJsonObject();
//        JsonObject actualCondition = jsonObjectForecast.get("item").getAsJsonObject().get("condition").getAsJsonObject();
//        JsonElement actualCode = actualCondition.get("code");
//        JsonElement actualTemp = actualCondition.get("temp");
//        JsonElement actualText = actualCondition.get("text");
//        JsonElement actualDate = actualCondition.get("date");
//        JsonArray forecastArraylist = jsonObjectForecast.get("item").getAsJsonObject().get("forecast").getAsJsonArray();
//        for (JsonElement yahooDayForecast :
//                forecastArraylist) {
//            JsonElement code = yahooDayForecast.getAsJsonObject().get("code");
//            JsonElement date = yahooDayForecast.getAsJsonObject().get("date");
//            JsonElement day = yahooDayForecast.getAsJsonObject().get("day");
//            JsonElement high = yahooDayForecast.getAsJsonObject().get("high");
//            JsonElement low = yahooDayForecast.getAsJsonObject().get("low");
//            JsonElement text = yahooDayForecast.getAsJsonObject().get("text");
//            YahooWeatherConditions yahooWeatherForecast = new YahooWeatherConditions(date.getAsString(), day.getAsString(), text.getAsString(), code.getAsInt(), high.getAsInt(), low.getAsInt());
//            yahooWeatherObjectList.add(yahooWeatherForecast);
//        }
//
//
//        JsonElement todayTempHigh = forecastArraylist.get(0).getAsJsonObject().get("high");
//        JsonElement todayTempLow = forecastArraylist.get(0).getAsJsonObject().get("low");
//        YahooWeatherConditions todayYahooWeatherCondition = new YahooWeatherConditions(actualDate.getAsString(), actualText.getAsString(), actualCode.getAsInt(), actualTemp.getAsInt(), todayTempHigh.getAsInt(), todayTempLow.getAsInt());
//        if (listItemTodayDateTextview != null) {
//
//            listItemTodayDateTextview.setText(todayYahooWeatherCondition.getDate());
//            listItemTodayForecastTextview.setText(todayYahooWeatherCondition.getText());
//            listItemTodayCurrentTextview.setText(todayYahooWeatherCondition.getCurrentTemp()+"ºC");
//            listItemTodayHighTextview.setText(todayYahooWeatherCondition.getTempMax()+"ºC");
//            listItemTodayLowTextview.setText(todayYahooWeatherCondition.getTempMin()+"ºC");
//        }
//        switch (todayYahooWeatherCondition.getText()) {
//            case "Mostly Sunny":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "Sunny":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "Clear":
//                listItemTodayIcon.setImageResource(R.drawable.art_clear);
//
//                break;
//            case "Fair":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
//
//                break;
//            case "Snow":
//                listItemTodayIcon.setImageResource(R.drawable.art_snow);
//
//                break;
//            case "Foggy":
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
//            case "Rain":
//                listItemTodayIcon.setImageResource(R.drawable.art_rain);
//
//                break;
//
//            case "Partly Cloudy":
//                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
//                break;
//            case "Mostly Cloudy":
//                listItemTodayIcon.setImageResource(R.drawable.art_clouds);
//                break;
//            case "Cloudy":
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
//
//        }
//
//        yahooWeatherObjectListNextDays = yahooWeatherObjectList.subList(1, yahooWeatherObjectList.size());
//        LinearLayoutManager layoutManager =
//                new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        yahooWeatherAdapter = new YahooWeatherAdapter(getActivity(), yahooWeatherObjectListNextDays);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
//                layoutManager.getOrientation());
//        forecastRecycle.addItemDecoration(dividerItemDecoration);
//        forecastRecycle.setLayoutManager(layoutManager);
//        forecastRecycle.setNestedScrollingEnabled(false);
//        forecastRecycle.setLayoutParams(layoutParams);
//        forecastRecycle.setAdapter(yahooWeatherAdapter);
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

        for (JsonElement yahooDayForecast :
                jsonArray) {
            String fechahoraConsulta = yahooDayForecast.getAsJsonObject().get("fechahoraConsulta").getAsString();
            String condActualDia = yahooDayForecast.getAsJsonObject().get("condActualDia").getAsString();
            String condActualNoche = yahooDayForecast.getAsJsonObject().get("condActualNoche").getAsString();
            String condDia = yahooDayForecast.getAsJsonObject().get("condDia").getAsString();
            String condNoche = yahooDayForecast.getAsJsonObject().get("condNoche").getAsString();
            float tActual = yahooDayForecast.getAsJsonObject().get("tActual").getAsFloat();
            float tMax = yahooDayForecast.getAsJsonObject().get("tMax").getAsFloat();
            float tMin = yahooDayForecast.getAsJsonObject().get("tMin").getAsFloat();
            float presion = yahooDayForecast.getAsJsonObject().get("presion").getAsFloat();
            int humedad = yahooDayForecast.getAsJsonObject().get("humedad").getAsInt();
            float vViento = yahooDayForecast.getAsJsonObject().get("vViento").getAsFloat();
            YahooWeatherObject yahooWeatherObject = new YahooWeatherObject();
            yahooWeatherObject.setFechahoraConsulta(fechahoraConsulta);
            yahooWeatherObject.settActual(tActual);
            yahooWeatherObject.settMax(tMax);
            yahooWeatherObject.settMin(tMin);
            yahooWeatherObject.setPresion(presion);
            yahooWeatherObject.setHumedad(humedad);
            yahooWeatherObject.setvViento(vViento);

            if (!condActualDia.isEmpty()) {
                yahooWeatherObject.setCondActualDia(condActualDia);

            }
            if (!condActualNoche.isEmpty()) {
                yahooWeatherObject.setCondActualNoche(condActualNoche);

            }
            if (!condDia.isEmpty()) {
                yahooWeatherObject.setCondDia(condDia);

            }
            if (!condNoche.isEmpty()) {
                yahooWeatherObject.setCondNoche(condNoche);

            }

            yahooWeatherObjectList.add(0, yahooWeatherObject);
        }
        YahooWeatherObject lastYahooWeatherObject = new YahooWeatherObject();
        lastYahooWeatherObject = yahooWeatherObjectList.get(0);
        if (listItemTodayDateTextview != null) {

            listItemTodayDateTextview.setText(lastYahooWeatherObject.getFechahoraConsulta());
            listItemTodayForecastTextview.setText(lastYahooWeatherObject.getCondActualDia());
            listItemTodayCurrentTextview.setText(lastYahooWeatherObject.gettActual() + "ºC");
            listItemTodayHighTextview.setText(lastYahooWeatherObject.gettMax() + "ºC");
            listItemTodayLowTextview.setText(lastYahooWeatherObject.gettMin() + "ºC");

            TransitionManager.beginDelayedTransition(ultimoDatoLayout);
        }
        switch (lastYahooWeatherObject.getCondActualDia()) {
            case "Mostly Sunny":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Sunny":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Clear":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Mostly clear":
                listItemTodayIcon.setImageResource(R.drawable.art_clear);

                break;
            case "Fair":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);

                break;
            case "Snow":
                listItemTodayIcon.setImageResource(R.drawable.art_snow);

                break;
            case "Foggy":
                listItemTodayIcon.setImageResource(R.drawable.art_fog);

                break;
            case "Thunderstorms":
                listItemTodayIcon.setImageResource(R.drawable.art_storm);

                break;
            case "Scattered Thunderstorms":
                listItemTodayIcon.setImageResource(R.drawable.art_storm);

                break;
            case "Rain":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);

                break;

            case "Partly Cloudy":
                listItemTodayIcon.setImageResource(R.drawable.art_light_clouds);
                break;
            case "Mostly Cloudy":
                listItemTodayIcon.setImageResource(R.drawable.art_clouds);
                break;
            case "Cloudy":
                listItemTodayIcon.setImageResource(R.drawable.art_clouds);

                break;

            case "Scattered Showers":
                listItemTodayIcon.setImageResource(R.drawable.art_light_rain);
                break;
            case "Showers":
                listItemTodayIcon.setImageResource(R.drawable.art_rain);
                break;
            case "Breezy":
                listItemTodayIcon.setImageResource(R.drawable.art_fog);
                break;
            default:
                break;

        }

        yahooWeatherObjectListNextDays = yahooWeatherObjectList.subList(1, yahooWeatherObjectList.size());
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        yahooWeatherAdapter = new YahooWeatherAdapter(getActivity(), yahooWeatherObjectListNextDays);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());
        forecastRecycle.addItemDecoration(dividerItemDecoration);
        forecastRecycle.setLayoutManager(layoutManager);
        forecastRecycle.setNestedScrollingEnabled(false);
        forecastRecycle.setLayoutParams(layoutParams);
        forecastRecycle.setAdapter(yahooWeatherAdapter);


        displayWeather();

    }
}
