package weathercompare.main;

import com.android.volley.RequestQueue;

public interface ListWeatherPresenter {
    void listWeatherRequest(RequestQueue queue, int fuenteUid);
    void listPromediosRequest(RequestQueue queue);

}
