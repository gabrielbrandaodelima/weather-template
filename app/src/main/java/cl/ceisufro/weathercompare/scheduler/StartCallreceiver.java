package cl.ceisufro.weathercompare.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartCallreceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        UtilScheduler.scheduleJob(context);

    }
}
