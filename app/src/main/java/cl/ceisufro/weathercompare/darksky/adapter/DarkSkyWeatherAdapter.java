package cl.ceisufro.weathercompare.darksky.adapter;

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
import cl.ceisufro.weathercompare.models.DarkSkyWeatherConditions;
import cl.ceisufro.weathercompare.utils.Utils;

public class DarkSkyWeatherAdapter extends RecyclerView.Adapter<DarkSkyWeatherAdapter.ViewHolder> {

    private Context mContext;

    private List<DarkSkyWeatherConditions> darkskyWeatherConditionsList;

    public DarkSkyWeatherAdapter(Context mContext, List<DarkSkyWeatherConditions> darkskyWeatherConditionsList) {
        this.mContext = mContext;
        this.darkskyWeatherConditionsList = darkskyWeatherConditionsList;
    }
    public void setData(List<DarkSkyWeatherConditions> details) {
        if (details == null) {
            details = Collections.emptyList();
        }
        this.darkskyWeatherConditionsList = details;
        notifyDataSetChanged();
    }
    public void add(DarkSkyWeatherConditions openWeatherCondition) {
        insert(openWeatherCondition, darkskyWeatherConditionsList.size());
        notifyItemInserted(darkskyWeatherConditionsList.size() - 1);
    }

    public void insert(DarkSkyWeatherConditions openWeatherCondition, int position) {
        darkskyWeatherConditionsList.add(position, openWeatherCondition);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        darkskyWeatherConditionsList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = darkskyWeatherConditionsList.size();
        if (size > 1) {
            darkskyWeatherConditionsList.clear();
            notifyItemRangeRemoved(1, size);
        }
    }

    public void addAll(List<DarkSkyWeatherConditions> openWeatherConditionsList) {
        int startindex = openWeatherConditionsList.size();
        Log.e("list", "addAll: " + openWeatherConditionsList.size());
        openWeatherConditionsList.addAll(startindex, openWeatherConditionsList);
        notifyItemRangeInserted(startindex, openWeatherConditionsList.size());
    }

    public void addAllInBeggining(List<DarkSkyWeatherConditions> weatherConditionsList) {
        int startindex = 0;
        weatherConditionsList.addAll(startindex, weatherConditionsList);
        notifyDataSetChanged();
    }

    public void addAllThatChanged(List<DarkSkyWeatherConditions> weatherConditionsList) {
        int startindex = weatherConditionsList.size();
        int added = 0;
        for (DarkSkyWeatherConditions openWeatherCondition : weatherConditionsList) {
            boolean isEqual = false;
            for (int i = 1; i < weatherConditionsList.size(); i++) {
                DarkSkyWeatherConditions weatherConditions = weatherConditionsList.get(i);
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
        Collections.sort(weatherConditionsList, new Comparator<DarkSkyWeatherConditions>() {
            int result;

            @Override
            public int compare(DarkSkyWeatherConditions darkSkyWeatherConditions, DarkSkyWeatherConditions skyWeatherConditions) {
//                if (darkSkyWeatherConditions.getDate() > skyWeatherConditions.getDate()) {
//                    result = 1;
//                } else if (darkSkyWeatherConditions.getDateInTimestamp() == skyWeatherConditions.getDateInTimestamp()) {
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

        final DarkSkyWeatherConditions darkskyWeatherConditions = darkskyWeatherConditionsList.get(position);
        int dateInTimestamp = darkskyWeatherConditions.getDate();
        String date = Utils.getDateString(dateInTimestamp);
        holder.listItemDateTextview.setText(date);
        holder.listItemForecastTextview.setText(darkskyWeatherConditions.getSummary());
        holder.listItemHighTextview.setText(darkskyWeatherConditions.getTemperatureHigh() + "ºC");
        holder.listItemLowTextview.setText(darkskyWeatherConditions.getTemperatureLow() + "ºC");
        switch (darkskyWeatherConditions.getIcon()) {
            case "Sunny":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "clear-day":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "clear-night":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Fair":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "snow":
                holder.listItemIcon.setImageResource(R.drawable.ic_snow);

                break;
            case "fog":
                holder.listItemIcon.setImageResource(R.drawable.ic_fog);

                break;
            case "Thunderstorms":
                holder.listItemIcon.setImageResource(R.drawable.ic_storm);

                break;
            case "Scattered Thunderstorms":
                holder.listItemIcon.setImageResource(R.drawable.ic_storm);

                break;
            case "rain":
                holder.listItemIcon.setImageResource(R.drawable.ic_rain);

                break;

            case "partly-cloudy-day":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);
                break;
            case "partly-cloudy-night":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);
                break;
            case "cloudy":
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
        return darkskyWeatherConditionsList.size();
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