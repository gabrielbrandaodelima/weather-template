package cl.ceisufro.weathercompare.apixu.adapter;

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
import cl.ceisufro.weathercompare.models.objrequisicion.WeatherObject;

public class ApixuWeatherAdapter extends RecyclerView.Adapter<ApixuWeatherAdapter.ViewHolder> {

    private Context mContext;

    private List<WeatherObject> apixuWeatherConditionsList;

    public ApixuWeatherAdapter(Context mContext, List<WeatherObject> apixuWeatherConditionsList) {
        this.mContext = mContext;
        this.apixuWeatherConditionsList = apixuWeatherConditionsList;
    }

    public void setData(List<WeatherObject> details) {
        if (details == null) {
            details = Collections.emptyList();
        }
        this.apixuWeatherConditionsList = details;
        notifyDataSetChanged();
    }

    public void add(WeatherObject apixuWeatherCondition) {
        insert(apixuWeatherCondition, apixuWeatherConditionsList.size());
        notifyItemInserted(apixuWeatherConditionsList.size() - 1);
    }

    public void insert(WeatherObject apixuWeatherCondition, int position) {
        apixuWeatherConditionsList.add(position, apixuWeatherCondition);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        apixuWeatherConditionsList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = apixuWeatherConditionsList.size();
        if (size > 1) {
            apixuWeatherConditionsList.clear();
            notifyItemRangeRemoved(1, size);
        }
    }

    public void addAll(List<WeatherObject> apixuWeatherConditionsList) {
        int startindex = apixuWeatherConditionsList.size();
        Log.e("list", "addAll: " + apixuWeatherConditionsList.size());
        apixuWeatherConditionsList.addAll(startindex, apixuWeatherConditionsList);
        notifyItemRangeInserted(startindex, apixuWeatherConditionsList.size());
    }

    public void addAllInBeggining(List<WeatherObject> apixuWeatherConditionsList) {
        int startindex = 0;
        apixuWeatherConditionsList.addAll(startindex, apixuWeatherConditionsList);
        notifyDataSetChanged();
    }

    public void addAllThatChanged(List<WeatherObject> apixuWeatherConditionsList) {
        int startindex = apixuWeatherConditionsList.size();
        int added = 0;
        for (WeatherObject apixuWeatherCondition : apixuWeatherConditionsList) {
            boolean isEqual = false;
            for (int i = 1; i < apixuWeatherConditionsList.size(); i++) {
                WeatherObject apixuWeatherCondition2 = apixuWeatherConditionsList.get(i);
                if (apixuWeatherCondition2.equals(apixuWeatherCondition)) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual) {
                apixuWeatherConditionsList.add(apixuWeatherCondition);
                added++;
            }
        }
        Collections.sort(apixuWeatherConditionsList, new Comparator<WeatherObject>() {
            int result;

            @Override
            public int compare(WeatherObject apixuWeatherConditions1, WeatherObject apixuWeatherConditions2) {
//                if (apixuWeatherConditions1.getDateInTimestamp() > apixuWeatherConditions2.getDateInTimestamp()) {
//                    result = 1;
//                } else if (apixuWeatherConditions1.getDateInTimestamp() == apixuWeatherConditions2.getDateInTimestamp()) {
//                    result = 0;
//
//                } else if (apixuWeatherConditions1.getDateInTimestamp() < apixuWeatherConditions2.getDateInTimestamp()) {
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

        final WeatherObject apixuWeatherConditions = apixuWeatherConditionsList.get(position);
        holder.listItemDateTextview.setText(apixuWeatherConditions.getFechahoraConsulta());
        holder.listItemForecastTextview.setText(apixuWeatherConditions.getCondDia());
        holder.listItemHighTextview.setText(Math.round(apixuWeatherConditions.gettMax()) + "ºC");
        holder.listItemLowTextview.setText(Math.round(apixuWeatherConditions.gettMin()) + "ºC");
        switch (apixuWeatherConditions.getCondDia()) {
            case "Sunny":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Clear":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Mostly clear":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Mostly sunny":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Partly sunny":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Intermittent clouds":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Hazy sunshine":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Partly cloudy":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Mostly cloudy":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Cloudy":
                holder.listItemIcon.setImageResource(R.drawable.ic_cloudy);

                break;
            case "Few clouds":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Snow":
                holder.listItemIcon.setImageResource(R.drawable.ic_snow);

                break;
            case "Mostly Cloudy w/ Snow":
                holder.listItemIcon.setImageResource(R.drawable.ic_snow);

                break;
            case "Fog":
                holder.listItemIcon.setImageResource(R.drawable.ic_fog);

                break;
            case "T-Storms":
                holder.listItemIcon.setImageResource(R.drawable.ic_storm);

                break;
            case "Mostly Cloudy w/ T-Storms":
                holder.listItemIcon.setImageResource(R.drawable.ic_storm);

                break;
            case "Partly Sunny w/ T-Storms":
                holder.listItemIcon.setImageResource(R.drawable.ic_storm);

                break;
            case "Mostly Cloudy w/ Showers":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_rain);

                break;

            case "Partly Sunny w/ Showers":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_rain);
                break;

            case "Scattered Showers":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_rain);
                break;
            case "Moderate or heavy rain shower":
                holder.listItemIcon.setImageResource(R.drawable.ic_rain);
                break;
            case "Showers":
                holder.listItemIcon.setImageResource(R.drawable.ic_rain);
                break;
            case "Patchy rain possible":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_rain);
                break;
            case "Light rain shower":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_rain);
                break;
            case "Rain":
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
//                bundle.putParcelable("notice", apixuWeatherConditions);
//                i.putExtras(bundle);
//                mContext.startActivity(i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return apixuWeatherConditionsList.size();
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
