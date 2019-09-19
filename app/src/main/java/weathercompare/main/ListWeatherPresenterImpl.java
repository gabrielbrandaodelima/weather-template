package weathercompare.main;

import android.app.ProgressDialog;

import com.android.volley.ClientError;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;


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
    public void onFailure(VolleyError error) {
        if (error.getClass().equals(TimeoutError.class)) {

            listWeatherView.showError("Timeout error en el servidor");
        } else if (error.getClass().equals(ClientError.class)) {

            listWeatherView.showError("Servidor se calió");
        } else {
            listWeatherView.showError("Hubo un error, o servidor se calió");

        }
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
