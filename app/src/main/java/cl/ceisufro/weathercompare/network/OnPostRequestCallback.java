package cl.ceisufro.weathercompare.network;

public interface OnPostRequestCallback {
    void onSuccess(String response);

    void onFailure(String error);
}
