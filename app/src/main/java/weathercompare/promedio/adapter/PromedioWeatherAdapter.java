package cl.ceisufro.weathercompare.promedio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.ceisufro.weathercompare.R;
import cl.ceisufro.weathercompare.models.objrequisicion.PromedioObject;

public class PromedioWeatherAdapter extends RecyclerView.Adapter<PromedioWeatherAdapter.ViewHolder> {

    private Context mContext;

    private List<PromedioObject> promedioObjectList;

    public PromedioWeatherAdapter(Context mContext, List<PromedioObject> promedioObjectList) {
        this.mContext = mContext;
        this.promedioObjectList = promedioObjectList;
    }
    public void setData(List<PromedioObject> details) {
        if (details == null) {
            details = Collections.emptyList();
        }
        this.promedioObjectList = details;
        notifyDataSetChanged();
    }
    public void add(PromedioObject openWeatherCondition) {
        insert(openWeatherCondition, promedioObjectList.size());
        notifyItemInserted(promedioObjectList.size() - 1);
    }

    public void insert(PromedioObject openWeatherCondition, int position) {
        promedioObjectList.add(position, openWeatherCondition);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        promedioObjectList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = promedioObjectList.size();
        if (size > 1) {
            promedioObjectList.clear();
            notifyItemRangeRemoved(1, size);
        }
    }

    public void addAll(List<PromedioObject> openWeatherConditionsList) {
        int startindex = openWeatherConditionsList.size();
        Log.e("list", "addAll: " + openWeatherConditionsList.size());
        openWeatherConditionsList.addAll(startindex, openWeatherConditionsList);
        notifyItemRangeInserted(startindex, openWeatherConditionsList.size());
    }

    public void addAllInBeggining(List<PromedioObject> weatherConditionsList) {
        int startindex = 0;
        weatherConditionsList.addAll(startindex, weatherConditionsList);
        notifyDataSetChanged();
    }

    public void addAllThatChanged(List<PromedioObject> weatherConditionsList) {
        int startindex = weatherConditionsList.size();
        int added = 0;
        for (PromedioObject openWeatherCondition : weatherConditionsList) {
            boolean isEqual = false;
            for (int i = 1; i < weatherConditionsList.size(); i++) {
                PromedioObject weatherConditions = weatherConditionsList.get(i);
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
        Collections.sort(weatherConditionsList, new Comparator<PromedioObject>() {
            int result;

            @Override
            public int compare(PromedioObject openWeatherConditions1, PromedioObject openWeatherConditions2) {
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
        View view = inflater.inflate(R.layout.list_item_promedio, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PromedioObject promedioObject = promedioObjectList.get(position);
        holder.listPromedioDate.setText(promedioObject.getFechahora());
        holder.listPromedioTActual.setText(promedioObject.getPromedioTempActual() + "ºC");
        holder.listPromedioHighTxtView.setText(promedioObject.getPromedioTempMax() + "ºC");
        holder.listPromedioLowTxtView.setText(promedioObject.getPromedioTempMin() + "ºC");
        holder.listPromedioPresionTxtView.setText(promedioObject.getPromedioPresion()+" mb");
        holder.listPromedioHumedadTxtView.setText(promedioObject.getPromedioHumedad()+" %");
        holder.listPromedioVvientoTxtView.setText(promedioObject.getPromedioVviento()+" km/h");


    }

    @Override
    public int getItemCount() {
        return promedioObjectList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_promedio_date_textview)
        TextView listPromedioDate;
        @BindView(R.id.list_promedio_actual_textview)
        TextView listPromedioTActual;
        @BindView(R.id.list_promedio_high_textview)
        TextView listPromedioHighTxtView;
        @BindView(R.id.list_promedio_low_textview)
        TextView listPromedioLowTxtView;
        @BindView(R.id.list_promedio_presion_textview)
        TextView listPromedioPresionTxtView;
        @BindView(R.id.list_promedio_humedad_textview)
        TextView listPromedioHumedadTxtView;
        @BindView(R.id.list_promedio_vviento_textview)
        TextView listPromedioVvientoTxtView;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
