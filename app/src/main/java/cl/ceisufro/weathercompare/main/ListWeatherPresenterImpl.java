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
    public void onSuccess(String response) {
        listWeatherView.populateWeatherObjects(response);

    }

    @Override
    public void onFailure() {

        listWeatherView.showError("Hubo un error");
    }


    @Override
    public void listWeatherRequest(RequestQueue queue, int fuenteUid) {

        listWeatherInteractor.listActionRequest(queue, fuenteUid, this);

    }

    @Override
    public void listPromediosRequest(RequestQueue queue) {
        listWeatherInteractor.listPromedios(queue, this);
    }
}
