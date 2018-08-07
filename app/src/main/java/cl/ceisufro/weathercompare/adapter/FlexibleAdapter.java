//package cl.ceisufro.weathercompare.adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cl.ceisufro.weathercompare.R;
//
//public class FlexibleAdapter<T extends FlexibleHolder> extends RecyclerView.Adapter<FlexibleViewHolder> {
//
//    /**
//     * a list of instances(or subclasses) of FlexibleHolders
//     * since these all extends from FH, we have access to the base methods
//     * final here assures null-safety
//     **/
//    protected final List<T> flexibleItems = new ArrayList<>();
//
//    /**
//     * @param parent   the root parent group, use it's context for view inflating
//     * @param viewType this represents the LAYOUT RESOURCE we need to inflate
//     * @return a basic ViewHolder with inflated view as it's root
//     **/
//    @Override
//    public FlexibleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new FlexibleViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
//    }
//
//    /**
//     * @param holder   the default ViewHolder, it's just passes root view to the item
//     * @param position current position of the holder in data set, use to to retrieve an item
//     **/
//    @Override
//    public void onBindViewHolder(FlexibleViewHolder holder, int position) {
//        T item = getItem(position);
//
//        if (holder != null && item != null) {
//            holder.handleItem(item);
//        }
//    }
//
//    /**
//     * @param holder the holder whose views we want to recycle
//     **/
//    @Override
//    public void onViewRecycled(FlexibleViewHolder holder) {
//        holder.recycle();
//        super.onViewRecycled(holder);
//    }
//
//    /**
//     * @param position the current item position(in adapter data set)
//     * @return the layout resource to inflate for the said item
//     * default layout here is just a transparent item divider
//     **/
//    @Override
//    public int getItemViewType(int position) {
//        T item = getItem(position);
//
//        return item != null ? item.getLayout() : R.layout.default_layout;
//    }
//
//    @Override
//    public int getItemCount() {
//        return flexibleItems.size();
//    }
//
//    /**
//     * @param position position in the adapter data set
//     * @return the item for the position within lists's range, or null
//     **/
//    public T getItem(int position) {
//        return position < flexibleItems.size() ? flexibleItems.get(position) : null;
//    }
//
//    /**
//     * @param dataSource the new data source we want to set
//     **/
//    public void setItems(List<T> dataSource) {
//        if (dataSource != null && !dataSource.isEmpty()) {
//            flexibleItems.clear();
//            flexibleItems.addAll(dataSource);
//            notifyDataSetChanged();
//        }
//    }
//
//    /**
//     * @param dataSource the new items we want to add
//     **/
//    public void addItems(List<T> dataSource) {
//        if (dataSource != null && !dataSource.isEmpty()) {
//            flexibleItems.addAll(dataSource);
//            notifyDataSetChanged();
//        }
//    }
//}