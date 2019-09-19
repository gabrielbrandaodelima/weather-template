package cl.ceisufro.weathercompare.promedio;

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
import cl.ceisufro.weathercompare.main.ListWeatherPresenter;
import cl.ceisufro.weathercompare.main.ListWeatherPresenterImpl;
import cl.ceisufro.weathercompare.main.ListWeatherView;
import cl.ceisufro.weathercompare.models.objrequisicion.PromedioObject;
import cl.ceisufro.weathercompare.promedio.adapter.PromedioWeatherAdapter;
import io.realm.Realm;
import io.realm.RealmResults;


public class PromedioWeatherFragment extends Fragment implements ListWeatherView {

    ListWeatherPresenter presenter;
    List<PromedioObject> promedioObjectList;
    List<PromedioObject> promedioObjectListNextDays;
    PromedioObject promedioObject;
    PromedioWeatherAdapter promedioWeatherAdapter;
    Realm realm;
    RequestQueue requestQueue = null;
    RealmResults<PromedioObject> promedioObjectRealmResults;
    @BindView(R.id.progress_promedio)
    ProgressBar progressPromedio;
    @BindView(R.id.list_promedio_today_date_textview)
    TextView listPromedioTodayDateTextview;
    @BindView(R.id.list_promedio_today_current_textview)
    TextView listPromedioTodayCurrentTextview;
    @BindView(R.id.list_promedio_today_high_textview)
    TextView listPromedioTodayHighTextview;
    @BindView(R.id.list_promedio_today_low_textview)
    TextView listPromedioTodayLowTextview;
    @BindView(R.id.list_promedio_presion_promedio_textview)
    TextView listPromedioPresionPromedioTextview;
    @BindView(R.id.list_promedio_today_humedad_textview)
    TextView listPromedioTodayHumedadTextview;
    @BindView(R.id.list_promedio_vviento_promedio_textview)
    TextView listPromedioVvientoPromedioTextview;
    @BindView(R.id.promedio_recycle)
    RecyclerView promedioRecycle;
    @BindView(R.id.promedio_nested_scroll_view)
    NestedScrollView promedioNestedScrollView;
    @BindView(R.id.error_animation_view)
    LottieAnimationView errorAnimationView;
    @BindView(R.id.error_text)
    TextView errorText;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.ultimo_dato_layout)
    LinearLayout ultimoDatoLayout;
    private ListWeatherPresenter listWeatherPresenter;
    Unbinder unbinder;
//    private Tracker mTracker;

    public static String getFragmentTag() {
        return "promedios";
    }

    public PromedioWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        Realm.deleteRealm(Realm.getDefaultConfiguration());

        // Create a new empty instance of Realm
        realm = Realm.getDefaultInstance();

        // Obtain the cities in the Realm with asynchronous query.
        promedioObjectRealmResults = realm.where(PromedioObject.class).findAllAsync();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promedio, container, false);
        unbinder = ButterKnife.bind(this, view);
        hideLayout();
        showProgress();

        presenter = new ListWeatherPresenterImpl(this);
        promedioObjectList = new ArrayList<>();
        promedioObjectListNextDays = new ArrayList<>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());
//        presenter.callYahooWeather(requestQueue);

        listWeatherPresenter = new ListWeatherPresenterImpl(this);
        listWeatherPresenter.listPromediosRequest(requestQueue);
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
        if (progressPromedio != null) {

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

        if (progressPromedio != null) {

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
//            promedioObjectList.add(yahooWeatherForecast);
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
//        promedioObjectListNextDays = promedioObjectList.subList(1, promedioObjectList.size());
//        LinearLayoutManager layoutManager =
//                new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        yahooWeatherAdapter = new YahooWeatherAdapter(getActivity(), promedioObjectListNextDays);
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
        if (progressPromedio.getVisibility() == View.GONE) {

            progressPromedio.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideProgress() {
        if (progressPromedio.getVisibility() == View.VISIBLE) {

            progressPromedio.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLayout() {
        promedioNestedScrollView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayout() {

        promedioNestedScrollView.setVisibility(View.GONE);

    }

    @Override
    public void populateWeatherObjects(String response) {
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(response);

        JsonArray jsonArray = json.get("data").getAsJsonArray();

        for (JsonElement yahooDayForecast :
                jsonArray) {
            String fechahora = yahooDayForecast.getAsJsonObject().get("fechahora").getAsString();
            float promedioTempActual = yahooDayForecast.getAsJsonObject().get("promedioTempActual").getAsFloat();
            float promedioTempMax = yahooDayForecast.getAsJsonObject().get("promedioTempMax").getAsFloat();
            float promedioTempMin = yahooDayForecast.getAsJsonObject().get("promedioTempMin").getAsFloat();
            float promedioPresion = yahooDayForecast.getAsJsonObject().get("promedioPresion").getAsFloat();
            int promedioHumedad = yahooDayForecast.getAsJsonObject().get("promedioHumedad").getAsInt();
            float promedioVviento = yahooDayForecast.getAsJsonObject().get("promedioVviento").getAsFloat();

            PromedioObject promedioObject = new PromedioObject();
            promedioObject.setFechahora(fechahora);
            promedioObject.setPromedioTempActual(promedioTempActual);
            promedioObject.setPromedioTempMax(promedioTempMax);
            promedioObject.setPromedioTempMin(promedioTempMin);
            promedioObject.setPromedioPresion(promedioPresion);
            promedioObject.setPromedioHumedad(promedioHumedad);
            promedioObject.setPromedioVviento(promedioVviento);

            promedioObjectList.add(0, promedioObject);
        }
        PromedioObject lastWeatherObject = new PromedioObject();
        lastWeatherObject = promedioObjectList.get(0);
        if (listPromedioTodayDateTextview != null) {

            listPromedioTodayDateTextview.setText(lastWeatherObject.getFechahora());
            listPromedioTodayCurrentTextview.setText(lastWeatherObject.getPromedioTempActual() + "ºC");
            listPromedioTodayHighTextview.setText(lastWeatherObject.getPromedioTempMax() + "ºC");
            listPromedioTodayLowTextview.setText(lastWeatherObject.getPromedioTempMin() + "ºC");
            listPromedioPresionPromedioTextview.setText(lastWeatherObject.getPromedioPresion() + " mb");
            listPromedioTodayHumedadTextview.setText(lastWeatherObject.getPromedioHumedad() + " %");
            listPromedioVvientoPromedioTextview.setText(lastWeatherObject.getPromedioVviento() + " km/h");

        }

        promedioObjectListNextDays = promedioObjectList.subList(1, promedioObjectList.size());

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        promedioWeatherAdapter = new PromedioWeatherAdapter(getActivity(), promedioObjectListNextDays);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());
        promedioRecycle.addItemDecoration(dividerItemDecoration);
        promedioRecycle.setLayoutManager(layoutManager);
        promedioRecycle.setNestedScrollingEnabled(false);
        promedioRecycle.setLayoutParams(layoutParams);
        promedioRecycle.setAdapter(promedioWeatherAdapter);


        displayLayout();
//        JsonObject actualCondition = jsonObjectForecast.get("item").getAsJsonObject().get("condition").getAsJsonObject();
//        JsonElement actualCode = actualCondition.get("code");
//        JsonElement actualTemp = actualCondition.get("temp");
//        JsonElement actualText = actualCondition.get("text");
//        JsonElement actualDate = actualCondition.get("date");


    }
}
