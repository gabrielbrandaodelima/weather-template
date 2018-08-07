package cl.ceisufro.weathercompare.openweather;

import android.app.ProgressDialog;

import com.android.volley.RequestQueue;


public class OpenWeatherPresenterImpl implements OpenWeatherPresenter, OpenWeatherInteractor.OnOpenWeatherRequestCallback, OpenWeatherInteractor.OnOpenWeatherCurrentRequestCallback {


    private OpenWeatherView openWeatherView;
    private OpenWeatherInteractor openWeatherInteractor;
    private ProgressDialog progressDialog;

    public OpenWeatherPresenterImpl(OpenWeatherView openWeatherView) {
        this.openWeatherView = openWeatherView;
        openWeatherInteractor = new OpenWeatherInteractorImp(this);
    }


    @Override
    public void onSuccess(String response) {
        openWeatherView.populateWeatherList(response);

    }

    @Override
    public void onSuccessCurrent(String response) {
        openWeatherView.populateCurrent(response);

    }

    @Override
    public void onFailure() {

        openWeatherView.showError("Hubo un error");
    }


    @Override
    public void callOpenWeather(RequestQueue queue) {
        openWeatherInteractor.getRequest(queue, this);

    }

    @Override
    public void callOpenWeatherCurrent(RequestQueue queueOpenWeather) {
        openWeatherInteractor.getCurrentRequest(queueOpenWeather, this);
    }
}
