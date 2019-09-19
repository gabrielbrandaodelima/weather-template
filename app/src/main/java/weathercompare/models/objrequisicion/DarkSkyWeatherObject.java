package weathercompare.models.objrequisicion;

import weathercompare.utils.constants.Constants;
import io.realm.RealmObject;

public class DarkSkyWeatherObject extends RealmObject {

    private String usr = Constants.USR;
    private String pass = Constants.PASS;
    private String action = Constants.ACTION_CREATE;
    private int fuente = 2;
    private String fechahoraConsulta;
    private String condActualDia;
    private String condActualNoche;
    private String condDia;
    private String condNoche;
    private float tActual;
    private float tMax;
    private float tMin;
    private float presion;
    private float humedad;
    private float vViento;

    public DarkSkyWeatherObject() {
    }

    public int getFuente() {
        return fuente;
    }

    public void setFuente(int fuente) {
        this.fuente = fuente;
    }


    public float gettActual() {
        return tActual;
    }

    public void settActual(float tActual) {
        this.tActual = tActual;
    }

    public float gettMax() {
        return tMax;
    }

    public void settMax(float tMax) {
        this.tMax = tMax;
    }

    public float gettMin() {
        return tMin;
    }

    public void settMin(float tMin) {
        this.tMin = tMin;
    }

    public float getPresion() {
        return presion;
    }

    public void setPresion(float presion) {
        this.presion = presion;
    }

    public float getHumedad() {
        return humedad;
    }

    public void setHumedad(float humedad) {
        this.humedad = humedad;
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

    public String getFechahoraConsulta() {
        return fechahoraConsulta;
    }

    public void setFechahoraConsulta(String fechahoraConsulta) {
        this.fechahoraConsulta = fechahoraConsulta;
    }

    public String getCondActualDia() {
        return condActualDia;
    }

    public void setCondActualDia(String condActualDia) {
        this.condActualDia = condActualDia;
    }

    public String getCondActualNoche() {
        return condActualNoche;
    }

    public void setCondActualNoche(String condActualNoche) {
        this.condActualNoche = condActualNoche;
    }

    public String getCondDia() {
        return condDia;
    }

    public void setCondDia(String condDia) {
        this.condDia = condDia;
    }

    public String getCondNoche() {
        return condNoche;
    }

    public void setCondNoche(String condNoche) {
        this.condNoche = condNoche;
    }

    public float getvViento() {
        return vViento;
    }

    public void setvViento(float vViento) {
        this.vViento = vViento;
    }
}
