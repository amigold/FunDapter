package com.ami.fundapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

/**
 * A generic adapter that takes a BindDictionary and data and shows them. Does
 * basic validation for you for all fields and also handles the ViewHolder
 * pattern.
 *
 * @param <T>
 * @author Ami G
 */
public class FunDapter<T> extends BaseAdapter implements Filterable {

    protected ArrayList<T> mDataItems;
    protected ArrayList<T> mOrigDataItems;
    protected final Context mContext;
    private final int mLayoutResource;
    private final BindDictionary<T> mBindDictionary;
    private int oddColorRes;
    private int evenColorRes;
    private FunDapterFilter<T> funDapterFilter;
    private Filter lessonFilter;

    /**
     * A generic adapter that takes a BindDictionary and data and shows them.
     * Does basic validation for you for all fields and also handles the
     * ViewHolder pattern.
     *
     * @param context
     * @param dataItems      - An arraylist of model items
     * @param layoutResource - resource ID of a layout to inflate for each item. (Example:
     *                       R.layout.list_item)
     * @param dictionary     - The dictionary that will match between fields and data.
     */
    public FunDapter(Context context, ArrayList<T> dataItems,
                     int layoutResource, BindDictionary<T> dictionary) {
        this.mContext = context;
        this.mDataItems = dataItems;
        this.mOrigDataItems = dataItems;
        this.mLayoutResource = layoutResource;
        this.mBindDictionary = dictionary;
    }

    /**
     * Replace the current dataset with a new one and refresh the views. This
     * will call notifyDataSetChanged() for you.
     *
     * @param dataItems
     */
    public void updateData(ArrayList<T> dataItems) {
        this.mDataItems = dataItems;
        this.mOrigDataItems = dataItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mDataItems == null)
            return 0;

        return mDataItems.size();
    }

    @Override
    public T getItem(int position) {
        return mDataItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflate a new view or use a recycled view.
        View v = convertView;
        GenericViewHolder holder;
        if (null == v) {
            LayoutInflater vi = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(mLayoutResource, null);
            holder = new GenericViewHolder();
            holder.root = v;

            // init the sub views and put them in a holder instance
            FunDapterUtils.initViews(v, holder, mBindDictionary);

            v.setTag(holder);
        } else {
            holder = (GenericViewHolder) v.getTag();
        }

        // Show the data
        final T item = getItem(position);
        showData(item, holder, position);

        return v;
    }

    private void showData(T item, GenericViewHolder holder, int position) {

        // handles alternating background colors if selected
        if (oddColorRes > 0 && evenColorRes > 0) {
            if (position % 2 == 0) {
                holder.root.setBackgroundColor(mContext.getResources()
                        .getColor(evenColorRes));
            } else {
                holder.root.setBackgroundColor(mContext.getResources()
                        .getColor(oddColorRes));
            }
        }

        FunDapterUtils.showData(item, holder, position, mBindDictionary);
    }

    public FunDapter<T> setAlternatingBackground(int oddColorRes,
                                                 int evenColorRes) {

        if (oddColorRes <= 0 || evenColorRes <= 0) {
            throw new IllegalArgumentException("Color parameters are illegal");
        }

        this.oddColorRes = oddColorRes;
        this.evenColorRes = evenColorRes;

        return this;
    }

    @Override
    public Filter getFilter() {
        return lessonFilter;
    }

    /**
     * Use this method to enable filtering in the adapter.
     *
     * @param filter - a filter implementation for your adapter.
     */
    public void initFilter(FunDapterFilter<T> filter) {

        if (filter == null)
            throw new IllegalArgumentException(
                    "Cannot pass a null filter to FunDapter");

        this.funDapterFilter = filter;

        lessonFilter = new Filter() {

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {

                @SuppressWarnings("unchecked")
                ArrayList<T> list = (ArrayList<T>) results.values;

                if (results.count == 0) {
                    resetData();
                } else {
                    mDataItems = list;
                }

                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {

                    // No constraint - no point in filtering.
                    results.values = mOrigDataItems;
                    results.count = mOrigDataItems.size();
                } else {
                    // Perform the filtering operation

                    ArrayList<T> filter = funDapterFilter.filter(
                            constraint.toString(), mOrigDataItems);

                    results.count = filter.size();
                    results.values = filter;

                }

                return results;
            }
        };
    }

    public void resetData() {
        mDataItems = mOrigDataItems;
    }

}
