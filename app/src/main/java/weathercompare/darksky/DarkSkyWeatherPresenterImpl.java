package weathercompare.darksky;

import android.app.ProgressDialog;

import com.johnhiott.darkskyandroidlib.models.WeatherResponse;


public class DarkSkyWeatherPresenterImpl implements DarkSkyWeatherPresenter, DarkSkyWeatherInteractor.OnDarkSkyWeatherRequestCallback {


    private DarkSkyWeatherView darkSkyWeatherView;
    private DarkSkyWeatherInteractor darkSkyWeatherInteractor;
    private ProgressDialog progressDialog;

    public DarkSkyWeatherPresenterImpl(DarkSkyWeatherView darkSkyWeatherView) {
        this.darkSkyWeatherView = darkSkyWeatherView;
        darkSkyWeatherInteractor = new DarkSkyWeatherInteractorImp(this);
    }


    @Override
    public void onSuccess(WeatherResponse response) {
        darkSkyWeatherView.populateWeatherList(response);

    }

    @Override
    public void onFailure() {

        darkSkyWeatherView.showError("Hubo un error");
    }


    @Override
    public void callDarkSkyRequest(final String lat, String lng) {
        darkSkyWeatherInteractor.getRequest(lat, lng, this);

    }
}
