//package cl.ceisufro.weathercompare.JobCreator;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
//import com.evernote.android.job.Job;
//import com.evernote.android.job.JobCreator;
//import com.evernote.android.job.JobManager;
//
//public class NetworkJobCreator implements JobCreator {
//    @Nullable
//    @Override
//    public Job create(@NonNull String tag) {
//
//        switch (tag) {
//            case NetworkSyncJob.TAG:
//                return new NetworkSyncJob();
//            default:
//                return null;
//        }
//    }
//
//    public static final class AddReceiver extends AddJobCreatorReceiver {
//        @Override
//        protected void addJobCreator(@NonNull Context context, @NonNull JobManager manager) {
//            // manager.addJobCreator(new DemoJobCreator());
//        }
//    }
//}
