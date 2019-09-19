//package cl.ceisufro.weathercompare.alarm;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//
//import cl.ceisufro.weathercompare.main.MainActivity;
//
//public class Alarm {
//
//    public void setAlarm(Context context, int requestCode) {
//        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent i = new Intent(context, MainActivity.CallReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_HOUR, pi); // Millisec * Second * Minute
//
//    }
//
//    public void cancelAlarm(Context context, int requestCode) {
//        Intent intent = new Intent(context, MainActivity.CallReceiver.class);
//        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(sender);
//    }
//}
