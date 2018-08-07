//package cl.ceisufro.weathercompare.openweather.adapter;
//
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import javax.annotation.Nonnull;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import cl.ceisufro.weathercompare.R;
//import cl.ceisufro.weathercompare.adapter.FlexibleHolder;
//
//public class OpenWeatherConditionItemHolder implements FlexibleHolder {
//    private final String dateTextView;
//    private final String forecastTextView;
//    private final String highTxtView;
//    private final String minTxtView;
//    @BindView(R.id.list_item_icon)
//    ImageView listItemIcon;
//    @BindView(R.id.list_item_date_textview)
//    TextView listItemDateTextview;
//    @BindView(R.id.list_item_forecast_textview)
//    TextView listItemForecastTextview;
//    @BindView(R.id.list_item_high_textview)
//    TextView listItemHighTextview;
//    @BindView(R.id.list_item_low_textview)
//    TextView listItemLowTextview;
//
//    public OpenWeatherConditionItemHolder(String dateTextView, String forecastTextView, String highTxtView, String minTxtView) {
//
//        this.dateTextView = dateTextView;
//        this.forecastTextView = forecastTextView;
//        this.highTxtView = highTxtView;
//        this.minTxtView = minTxtView;
//    }
//
//    @Override
//    public int getLayout() {
//        return R.layout.list_item_forecast;
//    }
//
//    @Override
//    public void displayView(@Nonnull View rootView) {
//        ButterKnife.bind(this, rootView);
//
//        listItemDateTextview.setText(dateTextView);
//        listItemForecastTextview.setText(forecastTextView);
//        listItemHighTextview.setText(highTxtView);
//        listItemLowTextview.setText(minTxtView);
//    }
//
//    @Override
//    public void recycle() {
//
//    }
//}
