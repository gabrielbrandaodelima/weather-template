package cl.ceisufro.weathercompare.network;

import javax.annotation.Nullable;

public interface OnPostRequestCallback {
    void onSuccess(String response);

    void onFailure(@Nullable String error);
}
