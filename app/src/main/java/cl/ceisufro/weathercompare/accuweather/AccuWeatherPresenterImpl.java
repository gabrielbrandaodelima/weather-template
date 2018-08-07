package cl.ceisufro.weathercompare.accuweather;

import android.app.ProgressDialog;

import com.android.volley.RequestQueue;


public class AccuWeatherPresenterImpl implements AccuWeatherPresenter, AccuWeatherInteractor.OnAccuWeatherRequestCallback, AccuWeatherInteractor.OnAccuWeatherCurrentRequestCallback {


    private AccuWeatherView accuWeatherView;
    private AccuWeatherInteractor accuWeatherInteractor;
    private ProgressDialog progressDialog;

    public AccuWeatherPresenterImpl(AccuWeatherView accuWeatherView) {
        this.accuWeatherView = accuWeatherView;
        accuWeatherInteractor = new AccuWeatherInteractorImp(this);
    }


    @Override
    public void onSuccess(String response) {
        accuWeatherView.populateWeatherList(response);

    }

    @Override
    public void onSuccessCurrent(String response) {
        accuWeatherView.getCurrentTemp(response);

    }

    @Override
    public void onFailure() {

        accuWeatherView.showError("Hubo un error");
    }


    @Override
    public void callAccuWeather(RequestQueue queue) {
        accuWeatherInteractor.getRequest(queue, this);

    }

    @Override
    public void callAccuWeatherCurrent(RequestQueue queueAccuWeather) {
        accuWeatherInteractor.getCurrentRequest(queueAccuWeather, this);
    }
}
