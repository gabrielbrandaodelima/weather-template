package weathercompare.apixu;

import android.app.ProgressDialog;

import com.android.volley.RequestQueue;


public class ApixuWeatherPresenterImpl implements ApixuWeatherPresenter, ApixuWeatherInteractor.OnApixuWeatherRequestCallback, ApixuWeatherInteractor.OnApixuWeatherCurrentRequestCallback {


    private ApixuWeatherView apixuWeatherView;
    private ApixuWeatherInteractor apixuWeatherInteractor;
    private ProgressDialog progressDialog;

    public ApixuWeatherPresenterImpl(ApixuWeatherView apixuWeatherView) {
        this.apixuWeatherView = apixuWeatherView;
        apixuWeatherInteractor = new ApixuWeatherInteractorImp(this);
    }


    @Override
    public void onSuccess(String response) {
        apixuWeatherView.populateWeatherList(response);

    }

    @Override
    public void onSuccessCurrent(String response) {
        apixuWeatherView.getCurrentTemp(response);

    }

    @Override
    public void onFailure() {

        apixuWeatherView.showError("Hubo un error");
    }


    @Override
    public void callApixuWeather(RequestQueue queue) {
        apixuWeatherInteractor.getRequest(queue, this);

    }

    @Override
    public void callApixuWeatherCurrent(RequestQueue queueApixuWeather) {
        apixuWeatherInteractor.getCurrentRequest(queueApixuWeather, this);
    }
}
