package cl.ceisufro.weathercompare.models.objrequisicion;

import cl.ceisufro.weathercompare.utils.constants.Constants;
import io.realm.RealmObject;

public class PromedioObject extends RealmObject {

    private String usr = Constants.USR;
    private String pass = Constants.PASS;
    private String action = Constants.ACTION_CREATE;
    private String fechahora;
    private float promedioTempActual;
    private float promedioTempMax;
    private float promedioTempMin;
    private float promedioPresion;
    private int promedioHumedad;
    private float promedioVviento;

    public PromedioObject() {

    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    public float getPromedioTempActual() {
        return promedioTempActual;
    }

    public void setPromedioTempActual(float promedioTempActual) {
        this.promedioTempActual = promedioTempActual;
    }

    public float getPromedioTempMax() {
        return promedioTempMax;
    }

    public void setPromedioTempMax(float promedioTempMax) {
        this.promedioTempMax = promedioTempMax;
    }

    public float getPromedioTempMin() {
        return promedioTempMin;
    }

    public void setPromedioTempMin(float promedioTempMin) {
        this.promedioTempMin = promedioTempMin;
    }

    public float getPromedioPresion() {
        return promedioPresion;
    }

    public void setPromedioPresion(float promedioPresion) {
        this.promedioPresion = promedioPresion;
    }

    public int getPromedioHumedad() {
        return promedioHumedad;
    }

    public void setPromedioHumedad(int promedioHumedad) {
        this.promedioHumedad = promedioHumedad;
    }

    public float getPromedioVviento() {
        return promedioVviento;
    }

    public void setPromedioVviento(float promedioVviento) {
        this.promedioVviento = promedioVviento;
    }
}
