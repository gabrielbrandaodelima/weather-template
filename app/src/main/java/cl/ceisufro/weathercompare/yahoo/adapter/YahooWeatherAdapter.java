package cl.ceisufro.weathercompare.yahoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.ceisufro.weathercompare.R;
import cl.ceisufro.weathercompare.models.YahooWeatherConditions;

public class YahooWeatherAdapter extends RecyclerView.Adapter<YahooWeatherAdapter.ViewHolder> {

    private Context mContext;

    private List<YahooWeatherConditions> yahooWeatherConditionsList;

    public YahooWeatherAdapter(Context mContext, List<YahooWeatherConditions> yahooWeatherConditionsList) {
        this.mContext = mContext;
        this.yahooWeatherConditionsList = yahooWeatherConditionsList;
    }
    public void setData(List<YahooWeatherConditions> details) {
        if (details == null) {
            details = Collections.emptyList();
        }
        this.yahooWeatherConditionsList = details;
        notifyDataSetChanged();
    }
    public void add(YahooWeatherConditions openWeatherCondition) {
        insert(openWeatherCondition, yahooWeatherConditionsList.size());
        notifyItemInserted(yahooWeatherConditionsList.size() - 1);
    }

    public void insert(YahooWeatherConditions openWeatherCondition, int position) {
        yahooWeatherConditionsList.add(position, openWeatherCondition);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        yahooWeatherConditionsList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = yahooWeatherConditionsList.size();
        if (size > 1) {
            yahooWeatherConditionsList.clear();
            notifyItemRangeRemoved(1, size);
        }
    }

    public void addAll(List<YahooWeatherConditions> openWeatherConditionsList) {
        int startindex = openWeatherConditionsList.size();
        Log.e("list", "addAll: " + openWeatherConditionsList.size());
        openWeatherConditionsList.addAll(startindex, openWeatherConditionsList);
        notifyItemRangeInserted(startindex, openWeatherConditionsList.size());
    }

    public void addAllInBeggining(List<YahooWeatherConditions> weatherConditionsList) {
        int startindex = 0;
        weatherConditionsList.addAll(startindex, weatherConditionsList);
        notifyDataSetChanged();
    }

    public void addAllThatChanged(List<YahooWeatherConditions> weatherConditionsList) {
        int startindex = weatherConditionsList.size();
        int added = 0;
        for (YahooWeatherConditions openWeatherCondition : weatherConditionsList) {
            boolean isEqual = false;
            for (int i = 1; i < weatherConditionsList.size(); i++) {
                YahooWeatherConditions weatherConditions = weatherConditionsList.get(i);
                if (weatherConditions.equals(openWeatherCondition)) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual) {
                weatherConditionsList.add(openWeatherCondition);
                added++;
            }
        }
        Collections.sort(weatherConditionsList, new Comparator<YahooWeatherConditions>() {
            int result;

            @Override
            public int compare(YahooWeatherConditions openWeatherConditions1, YahooWeatherConditions openWeatherConditions2) {
//                if (openWeatherConditions1.getDateInTimestamp() > openWeatherConditions2.getDateInTimestamp()) {
//                    result = 1;
//                } else if (openWeatherConditions1.getDateInTimestamp() == openWeatherConditions2.getDateInTimestamp()) {
//                    result = 0;
//
//                } else if (openWeatherConditions1.getDateInTimestamp() < openWeatherConditions2.getDateInTimestamp()) {
//                    result = -1;
//                }
                return result;
            }

        });
        if (added != 0) {
//            notifyItemRangeInserted(startindex, added);
            notifyDataSetChanged();
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_forecast, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final YahooWeatherConditions yahooWeatherConditions = yahooWeatherConditionsList.get(position);
        holder.listItemDateTextview.setText(yahooWeatherConditions.getDay() + ", " + yahooWeatherConditions.getDate());
        holder.listItemForecastTextview.setText(yahooWeatherConditions.getText());
        holder.listItemHighTextview.setText(yahooWeatherConditions.getTempMax() + "ºC");
        holder.listItemLowTextview.setText(yahooWeatherConditions.getTempMin() + "ºC");
        switch (yahooWeatherConditions.getText()) {
            case "Sunny":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Clear":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Fair":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Snow":
                holder.listItemIcon.setImageResource(R.drawable.ic_snow);

                break;
            case "Foggy":
                holder.listItemIcon.setImageResource(R.drawable.ic_fog);

                break;
            case "Thunderstorms":
                holder.listItemIcon.setImageResource(R.drawable.ic_storm);

                break;
            case "Scattered Thunderstorms":
                holder.listItemIcon.setImageResource(R.drawable.ic_storm);

                break;
            case "Rain":
                holder.listItemIcon.setImageResource(R.drawable.ic_rain);

                break;

            case "Partly Cloudy":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);
                break;
            case "Mostly Cloudy":
                holder.listItemIcon.setImageResource(R.drawable.ic_cloudy);
                break;

            case "Scattered Showers":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_rain);
                break;
            case "Showers":
                holder.listItemIcon.setImageResource(R.drawable.ic_rain);
                break;
            default:
                break;

        }
//        holder.noticeDate.setText(notice.getDATE());
//        holder.noticeTxt.setText(notice.getNAME());
//        Picasso.with(mContext).load(notice.getImage()).placeholder(R.drawable.placeholdernewsquadrada).fit().into(holder.noticeImg);
//        holder.noticeTxt.setText(notice.getTitle());
//        Date date = notice.getDate();
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        holder.noticeDate.setText(df.format(date));
//
//        holder.noticeFrameLyt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(mContext, " position " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(mContext, NewsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("notice", openWeatherConditions);
//                i.putExtras(bundle);
//                mContext.startActivity(i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return yahooWeatherConditionsList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_item_icon)
        ImageView listItemIcon;
        @BindView(R.id.list_item_date_textview)
        TextView listItemDateTextview;
        @BindView(R.id.list_item_forecast_textview)
        TextView listItemForecastTextview;
        @BindView(R.id.list_item_high_textview)
        TextView listItemHighTextview;
        @BindView(R.id.list_item_low_textview)
        TextView listItemLowTextview;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
