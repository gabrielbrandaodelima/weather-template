package cl.ceisufro.weathercompare.accuweather.adapter;

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
import cl.ceisufro.weathercompare.models.AccuWeatherConditions;

public class AccuWeatherAdapter extends RecyclerView.Adapter<AccuWeatherAdapter.ViewHolder> {

    private Context mContext;

    private List<AccuWeatherConditions> accuWeatherConditionsList;

    public AccuWeatherAdapter(Context mContext, List<AccuWeatherConditions> accuWeatherConditionsList) {
        this.mContext = mContext;
        this.accuWeatherConditionsList = accuWeatherConditionsList;
    }

    public void setData(List<AccuWeatherConditions> details) {
        if (details == null) {
            details = Collections.emptyList();
        }
        this.accuWeatherConditionsList = details;
        notifyDataSetChanged();
    }

    public void add(AccuWeatherConditions accuWeatherCondition) {
        insert(accuWeatherCondition, accuWeatherConditionsList.size());
        notifyItemInserted(accuWeatherConditionsList.size() - 1);
    }

    public void insert(AccuWeatherConditions accuWeatherCondition, int position) {
        accuWeatherConditionsList.add(position, accuWeatherCondition);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        accuWeatherConditionsList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = accuWeatherConditionsList.size();
        if (size > 1) {
            accuWeatherConditionsList.clear();
            notifyItemRangeRemoved(1, size);
        }
    }

    public void addAll(List<AccuWeatherConditions> accuWeatherConditionsList) {
        int startindex = accuWeatherConditionsList.size();
        Log.e("list", "addAll: " + accuWeatherConditionsList.size());
        accuWeatherConditionsList.addAll(startindex, accuWeatherConditionsList);
        notifyItemRangeInserted(startindex, accuWeatherConditionsList.size());
    }

    public void addAllInBeggining(List<AccuWeatherConditions> accuWeatherConditionsList) {
        int startindex = 0;
        accuWeatherConditionsList.addAll(startindex, accuWeatherConditionsList);
        notifyDataSetChanged();
    }

    public void addAllThatChanged(List<AccuWeatherConditions> accuWeatherConditionsList) {
        int startindex = accuWeatherConditionsList.size();
        int added = 0;
        for (AccuWeatherConditions accuWeatherCondition : accuWeatherConditionsList) {
            boolean isEqual = false;
            for (int i = 1; i < accuWeatherConditionsList.size(); i++) {
                AccuWeatherConditions accuWeatherCondition2 = accuWeatherConditionsList.get(i);
                if (accuWeatherCondition2.equals(accuWeatherCondition)) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual) {
                accuWeatherConditionsList.add(accuWeatherCondition);
                added++;
            }
        }
        Collections.sort(accuWeatherConditionsList, new Comparator<AccuWeatherConditions>() {
            int result;

            @Override
            public int compare(AccuWeatherConditions accuWeatherConditions1, AccuWeatherConditions accuWeatherConditions2) {
//                if (accuWeatherConditions1.getDateInTimestamp() > accuWeatherConditions2.getDateInTimestamp()) {
//                    result = 1;
//                } else if (accuWeatherConditions1.getDateInTimestamp() == accuWeatherConditions2.getDateInTimestamp()) {
//                    result = 0;
//
//                } else if (accuWeatherConditions1.getDateInTimestamp() < accuWeatherConditions2.getDateInTimestamp()) {
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

        final AccuWeatherConditions accuWeatherConditions = accuWeatherConditionsList.get(position);
//        int dateInTimestamp = accuWeatherConditions.getDateInTimestamp();
//        String date = Utils.getDateString(dateInTimestamp);
//        holder.listItemDateTextview.setText(date);
        holder.listItemForecastTextview.setText(accuWeatherConditions.getDayPhrase());
        holder.listItemHighTextview.setText(Math.round(accuWeatherConditions.getTempMax()) + "ºC");
        holder.listItemLowTextview.setText(Math.round(accuWeatherConditions.getTempMin()) + "ºC");
        switch (accuWeatherConditions.getDayPhrase()) {
            case "Sunny":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Clear":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Mostly Clear":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Mostly Sunny":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Partly Sunny":
                holder.listItemIcon.setImageResource(R.drawable.ic_clear);

                break;
            case "Intermittent Clouds":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Hazy Sunshine":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Mostly Cloudy":
                holder.listItemIcon.setImageResource(R.drawable.ic_light_clouds);

                break;
            case "Cloudy":
                holder.listItemIcon.setImageResource(R.drawable.ic_cloudy);

                break;
            case "Few Clouds":
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
            case "Showers":
                holder.listItemIcon.setImageResource(R.drawable.ic_rain);
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
//                bundle.putParcelable("notice", accuWeatherConditions);
//                i.putExtras(bundle);
//                mContext.startActivity(i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return accuWeatherConditionsList.size();
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
