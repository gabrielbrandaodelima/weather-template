package cl.ceisufro.weathercompare.application;

import android.app.Activity;
import android.content.Context;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by luiz on 04/09/17.
 */

public class WeatherCompareApp extends android.app.Application {

    // region Members
    private static Context appContext;
    private static String appDir;

    private String appId = " ";
    private String server = " ";
    private String customFont = "fonts/ ";

    // endregion Members

    // region Acessors

    public static Context getAppContext() {
        return appContext;
    }

    public static Activity mCurrentActivity = null;

    public static Activity getCurrentActivity(){
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }

    // endRegion Acessors

    // region Lifecycle Methods

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        appDir = getCacheDir().getAbsolutePath() + "/";
        File dir = new File(appDir);
        dir.mkdirs();
        //  AndroidNetworking.initialize(appContext);

        Realm.init(appContext);
        RealmConfiguration config = new RealmConfiguration.Builder().name("weatherProvidersComparison").build();
        Realm.setDefaultConfiguration(config);

    }

    // endregion Lifecyle Methods

    // region Methods


    public static String getAppDir() {
        return appDir;
    }


    // endregion methods


}
