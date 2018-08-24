package cl.ceisufro.weathercompare.main;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.johnhiott.darkskyandroidlib.ForecastApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.ceisufro.weathercompare.JobCreator.NetworkSyncJob;
import cl.ceisufro.weathercompare.R;
import cl.ceisufro.weathercompare.accuweather.AccuWeatherFragment;
import cl.ceisufro.weathercompare.alarm.AlarmFragment;
import cl.ceisufro.weathercompare.apixu.APIXUWeatherFragment;
import cl.ceisufro.weathercompare.darksky.DarkSkyWeatherFragment;
import cl.ceisufro.weathercompare.models.AccuWeatherConditions;
import cl.ceisufro.weathercompare.models.ApixuWeatherConditions;
import cl.ceisufro.weathercompare.models.DarkSkyWeatherConditions;
import cl.ceisufro.weathercompare.models.OpenWeatherConditions;
import cl.ceisufro.weathercompare.models.YahooWeatherConditions;
import cl.ceisufro.weathercompare.openweather.OpenWeatherFragment;
import cl.ceisufro.weathercompare.utils.Utils;
import cl.ceisufro.weathercompare.yahoo.YahooWeatherFragment;

//import cl.ceisufro.weathercompare.network.YahooWeatherRequest;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ListWeatherView {


    private static final int REQUEST_CODE = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.action_bar_title)
    TextView actionBarTitle;
    @BindView(R.id.main_layout)
    FrameLayout mainLayout;
    @BindView(R.id.info_layout)
    ImageView infoLayout;
    @BindView(R.id.bluepen_layout)
    ImageView bluepenLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.loading_main_txt_view)
    TextView loadingMainTxtView;
    @BindView(R.id.loading_main_progress_layout)
    LinearLayout loadingMainProgressLayout;
    private boolean APIXUok = false;
    private boolean YahooOk = false;
    private boolean OpenWeatherOK = false;
    private boolean DarkSkyOk = false;
    private boolean AccuweatherOk = false;

    NavigationView navigationView;
    private Fragment mFragmentToSet = null;
    Intent thisIntent;
    private Fragment selectedFragment;
    private String currentTag;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private YahooWeatherFragment yahooWeatherFragment;
    private AlarmManager alarmMgr;
    PendingIntent alarmIntent;

    List<YahooWeatherConditions> yahooWeatherConditionsList = new ArrayList<>();
    List<OpenWeatherConditions> openWeatherConditionsList = new ArrayList<>();
    List<DarkSkyWeatherConditions> darkskyWeatherConditionsList = new ArrayList<>();
    List<ApixuWeatherConditions> apixuWeatherConditionsList = new ArrayList<>();
    List<AccuWeatherConditions> accuWeatherConditionsList = new ArrayList<>();
    private RequestQueue queueList;
    ListWeatherPresenter listWeatherPresenter;


    private int mLastJobId;

    private static final String LAST_JOB_ID = "LAST_JOB_ID";
    private JobManager mJobManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }

        mJobManager = JobManager.instance();

        // Get a Realm instance for this thread

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ForecastApi.create(Utils.keyDarkSky);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();

        if (savedInstanceState != null) {
            mLastJobId = savedInstanceState.getInt(LAST_JOB_ID, 0);
            currentTag = savedInstanceState.getString("fragment");
            changeFragment(selectedFragment);

        } else {

            setMainFragmentSelected();
        }
        onNewIntent(getIntent());
        queueList = Volley.newRequestQueue(getApplicationContext());

        hideLayout();
        showProgress();

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (mFragmentToSet != null) {
                    changeFragment(mFragmentToSet);
//                    EventBus.getDefault().postSticky(currentTag);
                    mFragmentToSet = null;

                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        listWeatherPresenter = new ListWeatherPresenterImpl(this);
        listWeatherPresenter.listWeatherRequest(queueList);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mainLayout.removeAllViews();
        if (selectedFragment == yahooWeatherFragment) {
            transaction.hide(yahooWeatherFragment);
        } else {
            transaction.remove(selectedFragment);
        }
        transaction.add(R.id.main_layout, fragment);
        transaction.commit();
        selectedFragment = fragment;
    }

    private void setMainFragmentSelected() {

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mainLayout.removeAllViews();
        if (selectedFragment != null) {
            transaction.remove(selectedFragment);
        }
        if (yahooWeatherFragment == null) {
            yahooWeatherFragment = new YahooWeatherFragment();
            Bundle args = new Bundle();
            yahooWeatherFragment.setArguments(args);
            transaction.add(R.id.main_layout, yahooWeatherFragment, YahooWeatherFragment.getFragmentTag());
        } else {
            Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(YahooWeatherFragment.getFragmentTag());
            transaction.show(fragmentByTag);

        }
        selectedFragment = yahooWeatherFragment;

        transaction.commit();

        currentTag = YahooWeatherFragment.getFragmentTag();

    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LAST_JOB_ID, mLastJobId);

        outState.putString("fragment", currentTag);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//
//        super.onNewIntent(intent);
//        Parcelable parcelable = intent.getParcelableExtra("responnse");
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }

        super.onStart();

    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(String event) {
        if (event.equals("setJob")) {

            new JobRequest.Builder(NetworkSyncJob.TAG)
                    .startNow()
                    .build()
                    .schedule();
//            mLastJobId = new JobRequest.Builder(NetworkSyncJob.TAG)
//                    .setPeriodic(JobRequest.MIN_INTERVAL, JobRequest.MIN_FLEX)
//                    .setUpdateCurrent(true)
//                    .build()
//                    .schedule();

        } else if (event.equals("cancelJob")) {

            mJobManager.cancelAll();
        }

    }


    @Override
    protected void onStop() {

        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (currentTag.equals(YahooWeatherFragment.getFragmentTag())) {
            actionBarTitle.setText("Yahoo Weather");
            navigationView.getMenu().findItem(R.id.nav_yahoo).setChecked(true);

        } else if (currentTag.equals(OpenWeatherFragment.getFragmentTag())) {
            actionBarTitle.setText("OpenWeather");
            navigationView.getMenu().findItem(R.id.nav_open).setChecked(true);

        } else if (currentTag.equals(DarkSkyWeatherFragment.getFragmentTag())) {
            actionBarTitle.setText("DarkSky");
            navigationView.getMenu().findItem(R.id.nav_dark).setChecked(true);

        } else if (currentTag.equals(AccuWeatherFragment.getFragmentTag())) {
            actionBarTitle.setText("AccuWeather");
            navigationView.getMenu().findItem(R.id.nav_accu).setChecked(true);

        } else if (currentTag.equals(APIXUWeatherFragment.getFragmentTag())) {
            actionBarTitle.setText("APIXU");
            navigationView.getMenu().findItem(R.id.nav_apixu).setChecked(true);

        } else if (currentTag.equals(AlarmFragment.getFragmentTag())) {
            actionBarTitle.setText("Requisición Periodica");
            navigationView.getMenu().findItem(R.id.nav_alarm).setChecked(true);

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (id == R.id.nav_yahoo) {
            actionBarTitle.setText("Yahoo Weather");
            if (!currentTag.equals(YahooWeatherFragment.getFragmentTag())) {

                mFragmentToSet = new YahooWeatherFragment();
//                Bundle args = new Bundle();
//                args.putBoolean("isMain", true);
//                mFragmentToSet.setArguments(args);
                currentTag = YahooWeatherFragment.getFragmentTag();
//                callYahooWeather(); // nuestro servidor
            }

        } else if (id == R.id.nav_open) {
            actionBarTitle.setText("OpenWeather");

            if (!currentTag.equals(OpenWeatherFragment.getFragmentTag())) {
                mFragmentToSet = new OpenWeatherFragment();
                currentTag = OpenWeatherFragment.getFragmentTag();
//                callOpenWeather(queueOpenWeather);
            }

        } else if (id == R.id.nav_dark) {


            actionBarTitle.setText("DarkSky");
            if (!currentTag.equals(DarkSkyWeatherFragment.getFragmentTag())) {
                mFragmentToSet = new DarkSkyWeatherFragment();
                currentTag = DarkSkyWeatherFragment.getFragmentTag();
//                    callDarkSky();
            }


        } else if (id == R.id.nav_accu) {

//            createDialogDev();
//            alertDialog.show();
            actionBarTitle.setText("AccuWeather");
            if (!currentTag.equals(AccuWeatherFragment.getFragmentTag())) {
                mFragmentToSet = new AccuWeatherFragment();
                currentTag = AccuWeatherFragment.getFragmentTag();
//                callAccuWeather();

            }

        } else if (id == R.id.nav_apixu) {

            actionBarTitle.setText("APIXU");
            if (!currentTag.equals(APIXUWeatherFragment.getFragmentTag())) {
                mFragmentToSet = new APIXUWeatherFragment();
                currentTag = APIXUWeatherFragment.getFragmentTag();
//                callAPIXUWeather();
            }

        } else if (id == R.id.nav_alarm) {

            actionBarTitle.setText("Requisición Periodica");
            if (!currentTag.equals(AlarmFragment.getFragmentTag())) {
                mFragmentToSet = new AlarmFragment();
                currentTag = AlarmFragment.getFragmentTag();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void displayLayout() {

        hideProgress();
        showLayout();
    }

    @Override
    public void showProgress() {
        if (loadingMainProgressLayout.getVisibility() == View.GONE) {

            loadingMainProgressLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideProgress() {
        if (loadingMainProgressLayout.getVisibility() == View.VISIBLE) {

            loadingMainProgressLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLayout() {
        mainLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayout() {

        mainLayout.setVisibility(View.GONE);

    }

    @Override
    public void populateWeatherObjects(String response) {
//        JsonParser parser = new JsonParser();
//        JsonObject json = (JsonObject) parser.parse(response);
//
//        JsonArray weatherList = json.get("data").getAsJsonArray();
//
//        for (JsonElement weatherObj :
//                weatherList) {
//            JsonObject weathObj = weatherObj.getAsJsonObject();
//
//        }

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
//        }
//
//
//        JsonElement todayTempHigh = forecastArraylist.get(0).getAsJsonObject().get("high");
//        JsonElement todayTempLow = forecastArraylist.get(0).getAsJsonObject().get("low");
//        YahooWeatherConditions todayYahooWeatherCondition = new YahooWeatherConditions(actualDate.getAsString(), actualText.getAsString(), actualCode.getAsInt(), actualTemp.getAsInt(), todayTempHigh.getAsInt(), todayTempLow.getAsInt());

        displayLayout();

    }

//    public static class CallReceiver extends BroadcastReceiver {
//        private YahooWeatherObject yahooWeatherObject = null;
//        private AccuWeatherObject accuWeatherObject = null;
//        private OpenWeatherObject openWeatherObject = null;
//        private DarkSkyWeatherObject darkSkyWeatherObject = null;
//        private ApixuWeatherObject apixuWeatherObject = null;
//
//        interface OnCallCallback {
//            void onSuccess(String response);
//
//            void onSuccessDark(WeatherResponse response);
//
//            void onError(@Nullable String response);
//        }
//
//        @Override
//        public void onReceive(final Context context, Intent intent) {
//            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
//            wl.acquire();
//
//            final String linkAccuWeatherCurrent = Utils.linkAccuWeatherCurrent;
//            String linkAccuWeatherForecast1day = Utils.linkAccuWeatherForecast1day;
//            String linkAPIXUCurrent = Utils.linkAPIXU;
//            String linkOpenWeatherCurrent = Utils.linkOpenWeatherCurrent;
//            String linkYahoo = Utils.linkYahoo;
//
//            callCurrent(context, linkYahoo, new OnCallCallback() {
//                @Override
//                public void onSuccess(String response) {
//                    JsonParser parser = new JsonParser();
//                    JsonObject json = (JsonObject) parser.parse(response);
//
//                    JsonObject jsonObjectForecast = json.get("query").getAsJsonObject().get("results").getAsJsonObject().get("channel").getAsJsonObject();
//                    JsonObject actualCondition = jsonObjectForecast.get("item").getAsJsonObject().get("condition").getAsJsonObject();
//                    JsonElement actualvViento = jsonObjectForecast.get("wind").getAsJsonObject().get("speed");
//                    JsonElement actualHumedad = jsonObjectForecast.get("atmosphere").getAsJsonObject().get("humidity");
//                    JsonElement actualPresion = jsonObjectForecast.get("atmosphere").getAsJsonObject().get("pressure");
//                    JsonElement actualTemp = actualCondition.get("temp");
//                    JsonElement actualCondDia = actualCondition.get("text");
//                    JsonElement actualDate = actualCondition.get("date");
//                    JsonArray forecastArraylist = jsonObjectForecast.get("item").getAsJsonObject().get("forecast").getAsJsonArray();
//                    JsonElement todayTempHigh = forecastArraylist.get(0).getAsJsonObject().get("high");
//                    JsonElement todayTempLow = forecastArraylist.get(0).getAsJsonObject().get("low");
//                    JsonElement condDia = forecastArraylist.get(0).getAsJsonObject().get("text");
//
//                    yahooWeatherObject = new YahooWeatherObject();
//                    yahooWeatherObject.setCondActualDia(actualCondDia.getAsString());
//                    yahooWeatherObject.setCondDia(condDia.getAsString());
//                    yahooWeatherObject.setFechahoraConsulta(Calendar.getInstance().getTime().toString());
//                    yahooWeatherObject.setvViento(actualvViento.getAsFloat());
//                    yahooWeatherObject.settActual(actualTemp.getAsFloat());
//                    yahooWeatherObject.settMin(todayTempLow.getAsFloat());
//                    yahooWeatherObject.settMax(todayTempHigh.getAsFloat());
//
//                    double pressionInMb = Utils.convertYahooPressureMistake(actualPresion.getAsFloat());
//
//                    yahooWeatherObject.setPresion((float) pressionInMb);
//                    yahooWeatherObject.setHumedad(actualHumedad.getAsInt());
//
//                    if (yahooWeatherObject != null) {
//
//                        POSTObjectRequest postObjectRequest = new POSTObjectRequest();
//                        RequestQueue queue = Volley.newRequestQueue(context);
//
//
//                        Gson gson = new Gson();
//                        String yahooWeatherObjectString = gson.toJson(yahooWeatherObject);
//                        postObjectRequest.sendRequest(queue, yahooWeatherObjectString, new OnPostRequestCallback() {
//                            @Override
//                            public void onSuccess(String response) {
////                                Toast.makeText(context, "Yahoo " + response, Toast.LENGTH_SHORT).show();
//                                // Put here YOUR code.
//
//                                yahooWeatherObject = null;
//                            }
//
//                            @Override
//                            public void onFailure(String error) {
////                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                    }
//
//
//                }
//
//                @Override
//                public void onSuccessDark(WeatherResponse response) {
//
//                }
//
//                @Override
//                public void onError(String response) {
//
//                }
//            });
//            callCurrent(context, linkOpenWeatherCurrent, new OnCallCallback() {
//                @Override
//                public void onSuccess(String response) {
//                    JsonParser parser = new JsonParser();
//                    JsonObject json = (JsonObject) parser.parse(response);
//
//                    JsonElement condDia = json.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main");
//                    JsonElement condActualDia = json.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description");
//
//                    JsonElement tActual = json.get("main").getAsJsonObject().get("temp");
//                    JsonElement presion = json.get("main").getAsJsonObject().get("pressure");
//                    JsonElement humedad = json.get("main").getAsJsonObject().get("humidity");
//                    JsonElement tMin = json.get("main").getAsJsonObject().get("temp_min");
//                    JsonElement tMax = json.get("main").getAsJsonObject().get("temp_max");
//                    JsonElement vViento = json.get("wind").getAsJsonObject().get("speed");
//                    JsonElement date = json.get("dt");
//
//                    int dateInTimestamp = date.getAsInt();
//                    String dateTodayString = Utils.getDateAndHourString(dateInTimestamp);
//                    openWeatherObject = new OpenWeatherObject();
//                    openWeatherObject.setCondDia(condDia.getAsString());
//                    openWeatherObject.setCondActualDia(condActualDia.getAsString());
//                    openWeatherObject.settActual(tActual.getAsFloat());
//                    openWeatherObject.settMax(tMax.getAsFloat());
//                    openWeatherObject.settMin(tMin.getAsFloat());
//                    openWeatherObject.setPresion(presion.getAsFloat());
//                    openWeatherObject.setHumedad(humedad.getAsInt());
//                    openWeatherObject.setvViento(vViento.getAsFloat());
//                    openWeatherObject.setFechahoraConsulta(Calendar.getInstance().getTime().toString());
//                    if (openWeatherObject != null) {
//
//                        POSTObjectRequest postObjectRequest = new POSTObjectRequest();
//                        RequestQueue queue = Volley.newRequestQueue(context);
//
//
//                        Gson gson = new Gson();
//                        String openWeatherObjectString = gson.toJson(openWeatherObject);
//                        postObjectRequest.sendRequest(queue, openWeatherObjectString, new OnPostRequestCallback() {
//                            @Override
//                            public void onSuccess(String response) {
////                                Toast.makeText(context, "Open " + response, Toast.LENGTH_SHORT).show();
//                                // Put here YOUR code.
//                                openWeatherObject = null;
//                            }
//
//                            @Override
//                            public void onFailure(String error) {
////                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onSuccessDark(WeatherResponse response) {
//
//                }
//
//                @Override
//                public void onError(String response) {
//
//                }
//            });
//            callCurrent(context, linkAPIXUCurrent, new OnCallCallback() {
//                @Override
//                public void onSuccess(String response) {
//
//                    JsonParser parser = new JsonParser();
//                    JsonObject json = (JsonObject) parser.parse(response);
//
//                    JsonObject current = json.get("current").getAsJsonObject();
//                    JsonElement fechaHora = current.get("last_updated");
//                    JsonElement tActual = current.get("temp_c");
//                    JsonElement isDay = current.get("is_day");
//                    JsonElement vViento = current.get("wind_kph");
//                    JsonElement presion = current.get("pressure_mb");
//                    JsonElement humedad = current.get("humidity");
//
//                    JsonArray forecast = json.get("forecast").getAsJsonObject().get("forecastday").getAsJsonArray();
//                    JsonElement tempMin = forecast.get(0).getAsJsonObject().get("day").getAsJsonObject().get("mintemp_c");
//                    JsonElement tempMax = forecast.get(0).getAsJsonObject().get("day").getAsJsonObject().get("maxtemp_c");
//                    JsonElement condDia = forecast.get(0).getAsJsonObject().get("day").getAsJsonObject().get("condition").getAsJsonObject().get("text");
//
//                    apixuWeatherObject = new ApixuWeatherObject();
//                    apixuWeatherObject.setFechahoraConsulta(Calendar.getInstance().getTime().toString());
//                    apixuWeatherObject.settActual(tActual.getAsFloat());
//                    apixuWeatherObject.setvViento(vViento.getAsFloat());
//                    apixuWeatherObject.setPresion(presion.getAsFloat());
//                    apixuWeatherObject.setHumedad(humedad.getAsInt());
//                    apixuWeatherObject.settMin(tempMin.getAsFloat());
//                    apixuWeatherObject.settMax(tempMax.getAsFloat());
//                    apixuWeatherObject.setCondDia(condDia.getAsString());
//
//                    if (isDay.getAsInt() == 1) {
//
//                        apixuWeatherObject.setCondActualDia(current.get("condition").getAsJsonObject().get("text").getAsString());
//
//                    } else {
//                        apixuWeatherObject.setCondActualNoche(current.get("condition").getAsJsonObject().get("text").getAsString());
//
//                    }
//
//                    if (apixuWeatherObject != null) {
//
//                        POSTObjectRequest postObjectRequest = new POSTObjectRequest();
//                        RequestQueue queue = Volley.newRequestQueue(context);
//
//
//                        Gson gson = new Gson();
//                        String apixuWeatherObjectString = gson.toJson(apixuWeatherObject);
//                        postObjectRequest.sendRequest(queue, apixuWeatherObjectString, new OnPostRequestCallback() {
//                            @Override
//                            public void onSuccess(String response) {
////                                Toast.makeText(context, "Apixu " + response, Toast.LENGTH_SHORT).show();
//                                // Put here YOUR code.
//                                apixuWeatherObject = null;
//                            }
//
//                            @Override
//                            public void onFailure(String error) {
////                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onSuccessDark(WeatherResponse response) {
//
//                }
//
//                @Override
//                public void onError(String response) {
//
//                }
//            });
//            callCurrent(context, linkAccuWeatherForecast1day, new OnCallCallback() {
//                @Override
//                public void onSuccess(String response) {
//
//                    JsonParser parser = new JsonParser();
//                    JsonObject json = (JsonObject) parser.parse(response);
//
//                    JsonArray jsonArrayForecast = json.get("DailyForecasts").getAsJsonArray();
//
////                        JsonElement dateInTimestamp = jsonArrayForecast.get(0).getAsJsonObject().get("EpochDate");
//                    final JsonElement tMin = jsonArrayForecast.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Minimum").getAsJsonObject().get("Value");
//                    final JsonElement tMax = jsonArrayForecast.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Maximum").getAsJsonObject().get("Value");
//                    final JsonElement condDia = jsonArrayForecast.get(0).getAsJsonObject().get("Day").getAsJsonObject().get("IconPhrase");
//                    final JsonElement condNoche = jsonArrayForecast.get(0).getAsJsonObject().get("Night").getAsJsonObject().get("IconPhrase");
//
//
//                    callCurrent(context, linkAccuWeatherCurrent, new OnCallCallback() {
//                        @Override
//                        public void onSuccess(String response) {
//
//                            JsonParser parser = new JsonParser();
//                            JsonArray current = (JsonArray) parser.parse(response);
//                            JsonElement fechaHora = current.get(0).getAsJsonObject().get("EpochTime");
//                            JsonElement isDay = current.get(0).getAsJsonObject().get("IsDayTime");
//                            JsonElement tActual = current.get(0).getAsJsonObject().get("Temperature").getAsJsonObject().get("Metric").getAsJsonObject().get("Value");
//                            JsonElement humedad = current.get(0).getAsJsonObject().get("RelativeHumidity");
//                            JsonElement vViento = current.get(0).getAsJsonObject().get("Wind").getAsJsonObject().get("Speed").getAsJsonObject().get("Metric").getAsJsonObject().get("Value");
//                            JsonElement presion = current.get(0).getAsJsonObject().get("Pressure").getAsJsonObject().get("Metric").getAsJsonObject().get("Value");
//
//                            int dateInTimestamp = fechaHora.getAsInt();
//                            String dateTodayString = Utils.getDateAndHourString(dateInTimestamp);
//                            accuWeatherObject = new AccuWeatherObject();
//                            accuWeatherObject.settMin(tMin.getAsFloat());
//                            accuWeatherObject.settMax(tMax.getAsFloat());
//                            accuWeatherObject.setCondDia(condDia.getAsString());
//                            accuWeatherObject.setCondNoche(condNoche.getAsString());
//
//                            accuWeatherObject.setFechahoraConsulta(Calendar.getInstance().getTime().toString());
//                            accuWeatherObject.settActual(tActual.getAsFloat());
//                            accuWeatherObject.setHumedad(humedad.getAsInt());
//                            accuWeatherObject.setPresion(presion.getAsFloat());
//                            accuWeatherObject.setvViento(vViento.getAsFloat());
//
//
//                            if (isDay.getAsBoolean()) {
//
//                                accuWeatherObject.setCondActualDia(current.get(0).getAsJsonObject().get("WeatherText").getAsString());
//
//                            } else {
//                                accuWeatherObject.setCondActualNoche(current.get(0).getAsJsonObject().get("WeatherText").getAsString());
//
//                            }
//
//                            if (accuWeatherObject != null) {
//
//                                POSTObjectRequest postObjectRequest = new POSTObjectRequest();
//                                RequestQueue queue = Volley.newRequestQueue(context);
//
//
//                                Gson gson = new Gson();
//                                String accuWeatherObjectString = gson.toJson(accuWeatherObject);
//                                postObjectRequest.sendRequest(queue, accuWeatherObjectString, new OnPostRequestCallback() {
//                                    @Override
//                                    public void onSuccess(String response) {
////                                        Toast.makeText(context, "Accu " + response, Toast.LENGTH_SHORT).show();
//                                        // Put here YOUR code.
//                                        accuWeatherObject = null;
//                                    }
//
//                                    @Override
//                                    public void onFailure(String error) {
////                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//
//                                    }
//                                });
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onSuccessDark(WeatherResponse response) {
//
//                        }
//
//                        @Override
//                        public void onError(String response) {
//
//                        }
//                    });
//                }
//
//                @Override
//                public void onSuccessDark(WeatherResponse response) {
//
//                }
//
//                @Override
//                public void onError(String response) {
//
//                }
//            });
//            callCurrentDarkSky(new OnCallCallback() {
//                @Override
//                public void onSuccess(String response) {
//
//                }
//
//                @Override
//                public void onSuccessDark(WeatherResponse response) {
//
//                    DataPoint currentlyDataPoint = response.getCurrently();
//                    long fecha = currentlyDataPoint.getTime();
//                    String condActualDia = currentlyDataPoint.getSummary();
//                    float tActual = (float) currentlyDataPoint.getTemperature();
//                    float humedad = Float.parseFloat(currentlyDataPoint.getHumidity());
//                    float presion = Float.parseFloat(currentlyDataPoint.getPressure());
//                    float vViento = Float.parseFloat(currentlyDataPoint.getWindSpeed());
//
//                    DataBlock dailyDataBlock = response.getDaily();
//
//                    String condDia = dailyDataBlock.getData().get(0).getSummary();
//                    float tMax = (float) dailyDataBlock.getData().get(0).getTemperatureMax();
//                    float tMin = (float) dailyDataBlock.getData().get(0).getTemperatureMin();
//
//                    darkSkyWeatherObject = new DarkSkyWeatherObject();
//
//
//                    String dateTodayString = Utils.getDateAndHourString(fecha);
//                    darkSkyWeatherObject.setFechahoraConsulta(Calendar.getInstance().getTime().toString());
//                    darkSkyWeatherObject.setCondActualDia(condActualDia);
//                    darkSkyWeatherObject.settActual(tActual);
//                    darkSkyWeatherObject.setHumedad(humedad);
//                    darkSkyWeatherObject.setPresion(presion);
//                    darkSkyWeatherObject.setvViento(vViento);
//                    darkSkyWeatherObject.setvViento(vViento);
//                    darkSkyWeatherObject.settMax(tMax);
//                    darkSkyWeatherObject.settMin(tMin);
//                    darkSkyWeatherObject.setCondDia(condDia);
//
//                    if (darkSkyWeatherObject != null) {
//                        POSTObjectRequest postObjectRequest = new POSTObjectRequest();
//                        RequestQueue queue = Volley.newRequestQueue(context);
//
//
//                        Gson gson = new Gson();
//                        String darkSkyWeatherObjectString = gson.toJson(darkSkyWeatherObject);
//                        postObjectRequest.sendRequest(queue, darkSkyWeatherObjectString, new OnPostRequestCallback() {
//                            @Override
//                            public void onSuccess(String response) {
////                                Toast.makeText(context, "Dark " + response, Toast.LENGTH_SHORT).show();
//                                // Put here YOUR code.
//                                darkSkyWeatherObject = null;
//                            }
//
//                            @Override
//                            public void onFailure(String error) {
////                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                    }
//
//                }
//
//                @Override
//                public void onError(String response) {
//
//                }
//            });
//
//            wl.release();
//        }
//
//        private void callCurrent(Context context, String link, final OnCallCallback callback) {
//
//            RequestQueue queue = Volley.newRequestQueue(context);
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, link,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//
//
//                            callback.onSuccess(response);
//
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    callback.onError(null);
//
//                }
//            });
//
//// Add the request to the RequestQueue.
//            queue.add(stringRequest);
//
//        }
//
//        private void callCurrentDarkSky(final OnCallCallback callback) {
//
//            RequestBuilder weather = new RequestBuilder();
//
//            com.johnhiott.darkskyandroidlib.models.Request request = new com.johnhiott.darkskyandroidlib.models.Request();
//            request.setLat(Utils.latTemuco);
//            request.setLng(Utils.longTemuco);
//            request.setUnits(com.johnhiott.darkskyandroidlib.models.Request.Units.SI);
//            request.setLanguage(com.johnhiott.darkskyandroidlib.models.Request.Language.SPANISH);
//
//            weather.getWeather(request, new Callback<WeatherResponse>() {
//                @Override
//                public void success(WeatherResponse weatherResponse, retrofit.client.Response response) {
//                    //Do something
//                    callback.onSuccessDark(weatherResponse);
//                }
//
//                @Override
//                public void failure(RetrofitError retrofitError) {
//                    callback.onError(null);
//
//                }
//            });
//        }
//    }

}
