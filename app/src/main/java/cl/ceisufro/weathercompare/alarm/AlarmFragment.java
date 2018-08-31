//package cl.ceisufro.weathercompare.alarm;
//
//import android.app.AlarmManager;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CompoundButton;
//import android.widget.Switch;
//import android.widget.TextView;
//
//import org.greenrobot.eventbus.EventBus;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.Unbinder;
//import cl.ceisufro.weathercompare.R;
//
//public class AlarmFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    @BindView(R.id.requisicion_txt_view)
//    TextView requisicionTxtView;
//    @BindView(R.id.requisicion_switch)
//    Switch requisicionSwitch;
//    Unbinder unbinder;
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    private boolean isOn;
//
//    public AlarmFragment() {
//        // Required empty public constructor
//    }
//
//    public static String getFragmentTag() {
//        return "alarm";
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        return view;
//    }
//
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//
//        final SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
//        isOn = sharedPreferences.getBoolean("isOn", false);
////        final Alarm alarm = new Alarm();
//
//
//
//        if (isOn) {
//
//
//            requisicionTxtView.setText("Activado");
//            requisicionSwitch.setText("Desactivar");
//            requisicionSwitch.setTextColor(getActivity().getResources().getColor(R.color.colorRed));
//            requisicionTxtView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
//            requisicionSwitch.setChecked(true);
//
//
//        } else {
//            requisicionTxtView.setText("Desactivado");
//            requisicionSwitch.setText("Activar");
//            requisicionSwitch.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
//            requisicionTxtView.setTextColor(getActivity().getResources().getColor(R.color.colorRed));
//            requisicionSwitch.setChecked(false);
//
//        }
//
//        requisicionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
////                    alarm.setAlarm(getActivity(), 0);
//                    EventBus.getDefault().postSticky("setJob");
//
//
//
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("isOn", true);
//                    editor.apply();
//                    requisicionTxtView.setText("Activado");
//                    requisicionSwitch.setText("Desactivar");
//                    requisicionSwitch.setTextColor(getActivity().getResources().getColor(R.color.colorRed));
//                    requisicionTxtView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
//                    requisicionSwitch.setChecked(true);
//
//
//                } else {
////                    alarm.cancelAlarm(getActivity(), 0);
//
//                    EventBus.getDefault().postSticky("cancelJob");
//
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("isOn", false);
//                    editor.apply();
//                    requisicionTxtView.setText("Desactivado");
//                    requisicionSwitch.setText("Activar");
//                    requisicionSwitch.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
//                    requisicionTxtView.setTextColor(getActivity().getResources().getColor(R.color.colorRed));
//                    requisicionSwitch.setChecked(false);
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//}
