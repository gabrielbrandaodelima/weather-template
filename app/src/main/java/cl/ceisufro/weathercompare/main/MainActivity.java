package cl.ceisufro.weathercompare.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.ceisufro.weathercompare.R;
import cl.ceisufro.weathercompare.accuweather.AccuWeatherFragment;
import cl.ceisufro.weathercompare.apixu.APIXUWeatherFragment;
import cl.ceisufro.weathercompare.darksky.DarkSkyWeatherFragment;
import cl.ceisufro.weathercompare.openweather.OpenWeatherFragment;
import cl.ceisufro.weathercompare.yahoo.YahooWeatherFragment;

//import cl.ceisufro.weathercompare.network.YahooWeatherRequest;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    RequestQueue queueOpenWeather = null;
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
        // Get a Realm instance for this thread

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        setMainFragmentSelected();
        onNewIntent(getIntent());


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
//        EveEventBus.getDefault().postSticky(currentTag);

    }

//    private void callAccuWeather() {
//        AccuWeatherRequest request = new AccuWeatherRequest();
//        request.getRequest(queueOpenWeather, new OnAccuWeatherSuccessCallback() {
//
//                    @Override
//                    public void onSuccess(String response) {
//                        JsonParser parser = new JsonParser();
//                        JsonArray jsonArray = (JsonArray) parser.parse(response);
//
//                        JsonElement element = jsonArray.get(0);
//
//                        JsonObject tempElement = element.getAsJsonObject();
//
//                        JsonObject temperature = (JsonObject) tempElement.getAsJsonObject().get("Temperature");
//                        JsonObject metric = (JsonObject) temperature.get("Metric");
//                        JsonElement tempC = metric.get("Value");
////                        JsonElement temperature = tempElement.get("Temperature");
////                        JsonElement temperature = tempElement.get("Temperature");
////                        JsonElement temperature = tempElement.get("Temperature");
////0
//                        AccuweatherOk = true;
//                        verifyRequests();
//
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        Toast.makeText(getApplicationContext(), "Hubo un error", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//        );
//
//
//    }

    private void verifyRequests() {
        if (AccuweatherOk && YahooOk && OpenWeatherOK && DarkSkyOk && APIXUok) {

//            hideLoading();
//            showView();
        }
    }

//    private void callAPIXUWeather() {
//        APIXUWeatherRequest request = new APIXUWeatherRequest();
//        request.getRequest(queueOpenWeather, new OnAPIXUSuccessCallback() {
//
//                    @Override
//                    public void onSuccess(String response) {
//                        JsonParser parser = new JsonParser();
//                        JsonObject json = (JsonObject) parser.parse(response);
//
//                        JsonElement element = json.get("current");
//                        JsonElement tempElement = element.getAsJsonObject().get("temp_c");
//
//                        APIXUok = true;
//                        verifyRequests();
//
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        Toast.makeText(getApplicationContext(), "Hubo un error", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//        );
//
//    }

//    private void callYahooWeather() {
//        YahooWeatherRequest request = new YahooWeatherRequest();
//        request.getRequest(queueOpenWeather, new OnYahooSuccessCallback() {
//
//
//                    @Override
//                    public void onSuccess(String response) {
//
//                        JsonParser parser = new JsonParser();
//                        JsonObject json = (JsonObject) parser.parse(response);
//
//                        JsonObject element = (JsonObject) json.get("query");
//                        JsonElement results = element.getAsJsonObject().get("results");
//                        JsonElement channel = results.getAsJsonObject().get("channel");
//                        JsonElement item = channel.getAsJsonObject().get("item");
//                        JsonElement condition = item.getAsJsonObject().get("condition");
//                        JsonElement tempElement = condition.getAsJsonObject().get("temp");
//
//                        YahooOk = true;
//                        verifyRequests();
//
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        Toast.makeText(getApplicationContext(), "Hubo un error", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//        );
//
//    }

//    private void callOpenWeather(RequestQueue queueOpenWeather) {
//        // Instantiate the RequestQueue.
//        OpenWeatherRequest request = new OpenWeatherRequest();
//
//        request.getRequest(queueOpenWeather, new OnOpenWeatherSuccessCallback() {
//            @Override
//            public void onSuccess(String response) {
//
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });
//
//
//    }

//    public void callDarkSky() {
//        DarkSkyRequestBuilder darkSkyRequestBuilder = new DarkSkyRequestBuilder();
//
//        darkSkyRequestBuilder.getRequest(Utils.latTemuco, Utils.longTemuco,  new OnDarkSkySuccessCallback() {
//            @Override
//            public void onSuccess(WeatherResponse weatherResponse) {
//
//                DarkSkyOk = true;
//                verifyRequests();
////                hideLoading();
////                showView();
//
//            }
//
//            @Override
//            public void onFailure() {
//                Toast.makeText(getApplicationContext(), "Hubo un error", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        Parcelable parcelable = intent.getParcelableExtra("responnse");
//        darkSkyTemp.setText(String.valueOf(((int) weatherResponse.getCurrently().getTemperature()))+"ºC");
    }

//    public void showView() {
//        mainRelativeLayout.setVisibility(View.VISIBLE);
//
//    }
//
//    public void hideView() {
//        mainRelativeLayout.setVisibility(View.GONE);
//
//    }
//
//    public void showLoading() {
//        loadingProgress.setVisibility(View.VISIBLE);
//
//    }
//
//    public void hideLoading() {
//        loadingProgress.setVisibility(View.GONE);
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_refresh) {
////            hideView();
////            showLoading();
//            callDarkSky();
//            callAPIXUWeather();
////            callYahooWeather();
//            callAccuWeather();
//            callOpenWeather(queueOpenWeather);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {

            if (intent.getExtras() != null) {

                String jtext = intent.getStringExtra("json");

                if (jtext != null && jtext.length() > 0) {
                    try {
                        JSONObject jText = new JSONObject(jtext);
                        String link = jText.getString("link");
                        String title = jText.getString("title");
                        if (link != null && link.length() > 0) {
                            Intent webviewIntent = new Intent(this, WebView.class);
                            webviewIntent.putExtra("link", link);
                            webviewIntent.putExtra("title", title);
                            startActivity(webviewIntent);
                            this.setIntent(null);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JSONException", e.getMessage());
                    }

                }
            }

        }
        Intent intent2 = getIntent();
        if (intent2 != null) {

            Bundle extras = getIntent().getExtras();
            if (extras != null) {

                String fragment = extras.getString("reportFragment");
                if (fragment != null && fragment.equals("report")) {


//                    if (!currentTag.equals(ReportFragment.getFragmentTag())) {
//                        mFragmentToSet = new ReportFragment();
//                        currentTag = ReportFragment.getFragmentTag();
//                    }

                }
            }
        }

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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
