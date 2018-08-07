package cl.ceisufro.weathercompare.accuweather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cl.ceisufro.weathercompare.R;


public class AccuWeatherFragment extends Fragment {

    private List<Object> objects;
    List<Object> objectList;
//    private Tracker mTracker;

    public static String getFragmentTag() {
        return "accu";
    }

    public AccuWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//
//        }
//
//        newsFragmentLayout.setVisibility(View.INVISIBLE);
//        newsProgress.setVisibility(View.VISIBLE);
//        noNewsText.setText("Não há notícias temporariamente.");
//        noNewsText.setVisibility(View.INVISIBLE);
//        newsAnimationView.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        firstNoticeFrameLayout.setVisibility(View.INVISIBLE);

//        mTracker = DeputadoApplication.getDefaultTracker();
//        Log.e("News: ", "Setting screen name: " + "Notícias");
//        mTracker.setScreenName("Notícias");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Log.e("Profile ", "Setting event view: " + "Notícias");
        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Tela");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Notícias");
//        DeputadoApplication.getFirebaseAnalytisInstance().logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();

//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//
//        }
    }

//    @OnClick(R.id.first_notice_frame_layout)
//    public void onViewClicked() {
////        Toast.makeText(getActivity(), "Primeria noticia", Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(getActivity(), NewsActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("notice", firstNotice);
//        i.putExtras(bundle);
//        getActivity().startActivity(i);
//    }

}
