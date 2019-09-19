package weathercompare.yahoo;

import android.app.ProgressDialog;

import com.android.volley.RequestQueue;


public class YahooWeatherPresenterImpl implements YahooWeatherPresenter, YahooWeatherInteractor.OnYahooWeatherRequestCallback {


    private YahooWeatherView yahooWeatherView;
    private YahooWeatherInteractor yahooWeatherInteractor;
    private ProgressDialog progressDialog;

    public YahooWeatherPresenterImpl(YahooWeatherView yahooWeatherView) {
        this.yahooWeatherView = yahooWeatherView;
        yahooWeatherInteractor = new YahooWeatherInteractorImp(this);
    }




    @Override
    public void onSuccess(String response) {
        yahooWeatherView.populateWeatherObjects(response);

    }

    @Override
    public void onFailure() {

        yahooWeatherView.showError("Hubo un error");
    }

    @Override
    public void callYahooWeather(RequestQueue queue) {
        yahooWeatherInteractor.getRequest(queue, this);

    }
}
