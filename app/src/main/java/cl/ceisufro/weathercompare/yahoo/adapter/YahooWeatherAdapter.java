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
import cl.ceisufro.weathercompare.models.objrequisicion.YahooWeatherObject;

public class YahooWeatherAdapter extends RecyclerView.Adapter<YahooWeatherAdapter.ViewHolder> {

    private Context mContext;

    private List<YahooWeatherObject> yahooWeatherObjectList;

    public YahooWeatherAdapter(Context mContext, List<YahooWeatherObject> yahooWeatherObjectList) {
        this.mContext = mContext;
        this.yahooWeatherObjectList = yahooWeatherObjectList;
    }
    public void setData(List<YahooWeatherObject> details) {
        if (details == null) {
            details = Collections.emptyList();
        }
        this.yahooWeatherObjectList = details;
        notifyDataSetChanged();
    }
    public void add(YahooWeatherObject openWeatherCondition) {
        insert(openWeatherCondition, yahooWeatherObjectList.size());
        notifyItemInserted(yahooWeatherObjectList.size() - 1);
    }

    public void insert(YahooWeatherObject openWeatherCondition, int position) {
        yahooWeatherObjectList.add(position, openWeatherCondition);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        yahooWeatherObjectList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = yahooWeatherObjectList.size();
        if (size > 1) {
            yahooWeatherObjectList.clear();
            notifyItemRangeRemoved(1, size);
        }
    }

    public void addAll(List<YahooWeatherObject> openWeatherConditionsList) {
        int startindex = openWeatherConditionsList.size();
        Log.e("list", "addAll: " + openWeatherConditionsList.size());
        openWeatherConditionsList.addAll(startindex, openWeatherConditionsList);
        notifyItemRangeInserted(startindex, openWeatherConditionsList.size());
    }

    public void addAllInBeggining(List<YahooWeatherObject> weatherConditionsList) {
        int startindex = 0;
        weatherConditionsList.addAll(startindex, weatherConditionsList);
        notifyDataSetChanged();
    }

    public void addAllThatChanged(List<YahooWeatherObject> weatherConditionsList) {
        int startindex = weatherConditionsList.size();
        int added = 0;
        for (YahooWeatherObject openWeatherCondition : weatherConditionsList) {
            boolean isEqual = false;
            for (int i = 1; i < weatherConditionsList.size(); i++) {
                YahooWeatherObject weatherConditions = weatherConditionsList.get(i);
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
        Collections.sort(weatherConditionsList, new Comparator<YahooWeatherObject>() {
            int result;

            @Override
            public int compare(YahooWeatherObject openWeatherConditions1, YahooWeatherObject openWeatherConditions2) {
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

        final YahooWeatherObject yahooWeatherObject = yahooWeatherObjectList.get(position);
        holder.listItemDateTextview.setText(yahooWeatherObject.getFechahoraConsulta());
        holder.listItemForecastTextview.setText(yahooWeatherObject.getCondActualDia());
        holder.listItemHighTextview.setText(yahooWeatherObject.gettMax() + "ºC");
        holder.listItemLowTextview.setText(yahooWeatherObject.gettMin() + "ºC");
        switch (yahooWeatherObject.getCondActualDia()) {
            case "Sunny":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Mostly Sunny":
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
            case "Rain and Snow":
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
            case "Cloudy":
                holder.listItemIcon.setImageResource(R.drawable.ic_cloudy);
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

    }

    @Override
    public int getItemCount() {
        return yahooWeatherObjectList.size();
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
