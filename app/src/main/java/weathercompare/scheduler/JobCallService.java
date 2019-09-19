//package cl.ceisufro.weathercompare.scheduler;
//
//import android.app.job.JobParameters;
//import android.app.job.JobService;
//import android.content.Intent;
//import android.util.Log;
//
//public class JobCallService extends JobService {
//    private static final String TAG = "SyncService";
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.i(TAG, "Service created");
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG, "Service destroyed");
//    }
//    /**
//     * When the app's MainActivity is created, it starts this service. This is so that the
//     * activity and this service can communicate back and forth. See "setUiCallback()"
//     */
//
//    @Override
//    public boolean onStartJob(JobParameters params) {
//        Intent service = new Intent(getApplicationContext(), JobCallService.class);
//        getApplicationContext().startService(service);
//        UtilScheduler.scheduleJob(getApplicationContext()); // reschedule the job
//        return true;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters params) {
//        return true;
//    }
//
//}