package cl.ceisufro.weathercompare.darksky;

public interface DarkSkyWeatherPresenter {
    void callDarkSkyRequest(final String lat, String lng);
}
