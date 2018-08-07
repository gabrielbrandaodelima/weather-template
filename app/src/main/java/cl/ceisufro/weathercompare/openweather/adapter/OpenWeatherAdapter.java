package cl.ceisufro.weathercompare.openweather.adapter;

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
import cl.ceisufro.weathercompare.models.OpenWeatherConditions;
import cl.ceisufro.weathercompare.utils.Utils;

public class OpenWeatherAdapter extends RecyclerView.Adapter<OpenWeatherAdapter.ViewHolder> {

    private Context mContext;

    private List<OpenWeatherConditions> openWeatherConditionsList;

    public OpenWeatherAdapter(Context mContext, List<OpenWeatherConditions> openWeatherConditionsList) {
        this.mContext = mContext;
        this.openWeatherConditionsList = openWeatherConditionsList;
    }

    public void setData(List<OpenWeatherConditions> details) {
        if (details == null) {
            details = Collections.emptyList();
        }
        this.openWeatherConditionsList = details;
        notifyDataSetChanged();
    }

    public void add(OpenWeatherConditions openWeatherCondition) {
        insert(openWeatherCondition, openWeatherConditionsList.size());
        notifyItemInserted(openWeatherConditionsList.size() - 1);
    }

    public void insert(OpenWeatherConditions openWeatherCondition, int position) {
        openWeatherConditionsList.add(position, openWeatherCondition);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        openWeatherConditionsList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = openWeatherConditionsList.size();
        if (size > 1) {
            openWeatherConditionsList.clear();
            notifyItemRangeRemoved(1, size);
        }
    }

    public void addAll(List<OpenWeatherConditions> openWeatherConditionsList) {
        int startindex = openWeatherConditionsList.size();
        Log.e("list", "addAll: " + openWeatherConditionsList.size());
        openWeatherConditionsList.addAll(startindex, openWeatherConditionsList);
        notifyItemRangeInserted(startindex, openWeatherConditionsList.size());
    }

    public void addAllInBeggining(List<OpenWeatherConditions> openWeatherConditionsList) {
        int startindex = 0;
        openWeatherConditionsList.addAll(startindex, openWeatherConditionsList);
        notifyDataSetChanged();
    }

    public void addAllThatChanged(List<OpenWeatherConditions> openWeatherConditionsList) {
        int startindex = openWeatherConditionsList.size();
        int added = 0;
        for (OpenWeatherConditions openWeatherCondition : openWeatherConditionsList) {
            boolean isEqual = false;
            for (int i = 1; i < openWeatherConditionsList.size(); i++) {
                OpenWeatherConditions openWeatherCondition2 = openWeatherConditionsList.get(i);
                if (openWeatherCondition2.equals(openWeatherCondition)) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual) {
                openWeatherConditionsList.add(openWeatherCondition);
                added++;
            }
        }
        Collections.sort(openWeatherConditionsList, new Comparator<OpenWeatherConditions>() {
            int result;

            @Override
            public int compare(OpenWeatherConditions openWeatherConditions1, OpenWeatherConditions openWeatherConditions2) {
                if (openWeatherConditions1.getDateInTimestamp() > openWeatherConditions2.getDateInTimestamp()) {
                    result = 1;
                } else if (openWeatherConditions1.getDateInTimestamp() == openWeatherConditions2.getDateInTimestamp()) {
                    result = 0;

                } else if (openWeatherConditions1.getDateInTimestamp() < openWeatherConditions2.getDateInTimestamp()) {
                    result = -1;
                }
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

        final OpenWeatherConditions openWeatherConditions = openWeatherConditionsList.get(position);
        int dateInTimestamp = openWeatherConditions.getDateInTimestamp();
        String date = Utils.getDateString(dateInTimestamp);
        holder.listItemDateTextview.setText(date);
        holder.listItemForecastTextview.setText(openWeatherConditions.getWeatherMain());
        holder.listItemHighTextview.setText(Math.round(openWeatherConditions.getTempMax()) + "ºC");
        holder.listItemLowTextview.setText(Math.round(openWeatherConditions.getTempMin()) + "ºC");
        switch (openWeatherConditions.getWeatherMain()) {
            case "Clear":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Clear Sky":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Scattered Clouds":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Clouds":
                holder.listItemIcon.setImageResource(R.drawable.ic_cloudy);

                break;
            case "Few Clouds":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Snow":
                holder.listItemIcon.setImageResource(R.drawable.ic_snow);

                break;
            case "Mist":
                holder.listItemIcon.setImageResource(R.drawable.ic_fog);

                break;
            case "Thunderstorm":
                holder.listItemIcon.setImageResource(R.drawable.ic_storm);

                break;
            case "Shower Rain":
                holder.listItemIcon.setImageResource(R.drawable.ic_rain);

                break;
            case "Rain":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_rain);

                break;

            case "Partly Cloudy":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);
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
        return openWeatherConditionsList.size();
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
