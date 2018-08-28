package cl.ceisufro.weathercompare.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

//    public static String tokenClimatempo = "dfb44dc0f19afb55def7eb8852bafc99";
    public static String keyAPIXU = "ebc53b04f7664455933214540180108";
    public static String yahooWeatherClientID = "dj0yJmk9U0N3MEh4T1hvSVJRJmQ9WVdrOU0yNTFlblJCTnpZbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0yMQ--";
    public static String yahooWeatherClientSecret = "faea785c2adc872d36a5a6b2fdec9420be8e864a";
    public static String yahooTemucoWOEID = "349871";
    public static String yahooWeatherAPPID = "3nuztA76";
    public static String keyOpenWeather = "a130a22f1886d04a563953f22faf3980";
    public static String keyDarkSky = "04bd2a806143d389e5814a6ae0cc6b5c";
//    public static String keyAccuWeather = "oQc90sQxt6TfDUACaUMvw1WW5k0pno77";  FIRST KEY

//    public static String keyAccuWeather = "MLPJM9uoLicKgPSMfr0etmM8jZdm1TWO"; SECOND KEY
//    public static String keyAccuWeather = "gocXFyTJhu5bFWovlqVM1a12gQ3OkAjL"; THIRD KEY
//    public static String keyAccuWeather = "COjjKSqzglqBIimkNGagwLeH3dM7tWKJ"; FOURTH KEY
    public static String keyAccuWeather = "XR74WI9IE2EQL4GW0MKjfGk7zAGd6mSa";
    public static String idTemucoOpenWeather = "3870011";
    public static String idTemucoAccuWeather = "52485";
    public static String latTemuco = "-38.7359";
    public static String longTemuco = "-72.5904";
//    public static String linkClimatempo = "http://apiadvisor.climatempo.com.br/api/v1/forecast/locale/3477/days/15?token=" + tokenClimatempo;
    public static String linkOpenWeather = "http://api.openweathermap.org/data/2.5/forecast/daily?id=3870011&units=metric&APPID=a130a22f1886d04a563953f22faf3980";
    public static String linkOpenWeatherCurrent = "http://api.openweathermap.org/data/2.5/weather?id="+idTemucoOpenWeather+"&units=metric&APPID=a130a22f1886d04a563953f22faf3980";
    public static String linkAccuWeatherCurrent = "http://dataservice.accuweather.com/currentconditions/v1/52485?&apikey="+keyAccuWeather+"&language=es-cl&details=true";
    public static String linkAccuWeatherForecast = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/52485?&apikey="+keyAccuWeather+"&metric=true";
    public static String linkAccuWeatherForecast1day = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/52485?&apikey="+keyAccuWeather+"&metric=true&language=es-cl";
    public static String linkYahoo = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20%3D%20%22349871%22%20and%20u%20%3D%20'c'&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    public static String linkAPIXU = "https://api.apixu.com/v1/forecast.json?key="+keyAPIXU+"&q=Temuco&days=7";
    public static String linkAPIXUCurrent = "https://api.apixu.com/v1/current.json?key=ebc53b04f7664455933214540180108&q=Temuco";
    public static String linkDarkSky = "https://api.darksky.net/forecast/" + keyDarkSky+"/"+latTemuco+","+longTemuco+"?units=si";


    public static String LINK_API_CLIMA = "http://moscu.ceisufro.cl/ApiClima/index.php?eID=api_clima_clima";
    public static String LINK_API_PROMEDIO = "http://moscu.ceisufro.cl/ApiClima/index.php?eID=api_clima_promedio";


    public static double convertYahooPressureMistake(double value) {
        Double correctedValueInMb = value/33.8639;

        return correctedValueInMb.floatValue();
    }

    public static String getDateString(long timestamp) {

//        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//        cal.setTimeInMillis(timestamp * 1000L);
//        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
//    DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//        final String time_chat_s = df.format(time_stamp_server*1000);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp*1000L);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }
    public static String getDateAndHourString(long timestamp) {

//        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//        cal.setTimeInMillis(timestamp * 1000L);
//        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
//    DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//        final String time_chat_s = df.format(time_stamp_server*1000);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp*1000L);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(d);
    }
//    DateFormat df = new SimpleDateFormat("HH:mm", Locale.US);
//    final String time_chat_s = df.format(time_stamp_value);
//
    public static Date getDate(int time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();//get your local time zone.
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        sdf.setTimeZone(tz);//set time zone.
        Date d = new Date(time*1000);
        String localTime = sdf.format(d);
        Date date = new Date();
        try {
            date = sdf.parse(localTime);//get local date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}

