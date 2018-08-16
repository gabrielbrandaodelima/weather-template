package cl.ceisufro.weathercompare.main;

import android.app.ProgressDialog;

import com.android.volley.RequestQueue;


public class ListWeatherPresenterImpl implements ListWeatherPresenter, ListWeatherInteractor.OnListWeatherRequestCallback {


    private ListWeatherView listWeatherView;
    private ListWeatherInteractor listWeatherInteractor;
    private ProgressDialog progressDialog;

    public ListWeatherPresenterImpl(ListWeatherView listWeatherView) {
        this.listWeatherView = listWeatherView;
        listWeatherInteractor = new ListWeatherInteractorImp(this);
    }



    @Override
    public void onSuccess(Object response) {
        listWeatherView.populateWeatherList(response);

    }

    @Override
    public void onFailure() {

        listWeatherView.showError("Hubo un error");
    }


    @Override
    public void listWeatherRequest(RequestQueue queue) {
        listWeatherInteractor.listActionRequest(queue, this);

    }
}
