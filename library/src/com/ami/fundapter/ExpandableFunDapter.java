package com.ami.fundapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.ami.fundapter.extractors.ChildExtractor;

import java.util.List;

/**
 * A generic adapter that takes a BindDictionary and data and shows them. Does
 * basic validation for you for all fields and also handles the ViewHolder
 * pattern.
 *
 * @param <G>
 * @author Ami G
 */
public class ExpandableFunDapter<G, C> extends BaseExpandableListAdapter {

    protected List<G> mDataItems;
    protected final Context mContext;
    private final int mGroupLayoutResource;
    private final int mChildLayoutResource;
    private int oddColorRes;
    private int evenColorRes;
    private final BindDictionary<G> mGroupDictionary;
    private final BindDictionary<C> mChildDictionary;
    private final ChildExtractor<G, C> mChildExtractor;

    /**
     * A generic adapter that takes a BindDictionary and data and shows them.
     * Does basic validation for you for all fields and also handles the
     * ViewHolder pattern.
     *
     * @param context
     * @param dataItems           - A list of model items
     * @param groupLayoutResource
     * @param childLayoutResource
     */
    public ExpandableFunDapter(Context context, List<G> dataItems,
                               BindDictionary<G> groupDictionary, BindDictionary<C> childDictionary,
                               int groupLayoutResource, int childLayoutResource,
                               ChildExtractor<G, C> childExtractor) {
        this.mContext = context;
        this.mDataItems = dataItems;
        this.mGroupLayoutResource = groupLayoutResource;
        this.mChildLayoutResource = childLayoutResource;
        this.mGroupDictionary = groupDictionary;
        this.mChildDictionary = childDictionary;
        this.mChildExtractor = childExtractor;
    }

    /**
     * Replace the current dataset with a new one and refresh the views. This
     * will call notifyDataSetChanged() for you.
     *
     * @param dataItems
     */
    public void updateData(List<G> dataItems) {
        this.mDataItems = dataItems;
        notifyDataSetChanged();
    }

    private void showGroupData(G item, GenericViewHolder holder, int position) {

        // handles alternating background colors if selected
        if (oddColorRes > 0 && evenColorRes > 0) {
            if (position % 2 == 0) {
                holder.root.setBackgroundColor(mContext.getResources().getColor(evenColorRes));
            } else {
                holder.root.setBackgroundColor(mContext.getResources().getColor(oddColorRes));
            }
        }

        FunDapterUtils.showData(item, holder, position, mGroupDictionary);
    }

    public ExpandableFunDapter<G, C> setAlternatingBackground(int oddColorRes, int evenColorRes) {

        if (oddColorRes <= 0 || evenColorRes <= 0) {
            throw new IllegalArgumentException("Color parameters are illegal");
        }

        this.oddColorRes = oddColorRes;
        this.evenColorRes = evenColorRes;

        return this;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        // Inflate a new view or use a recycled view.
        View v = convertView;
        GenericViewHolder holder;
        if (null == v) {
            LayoutInflater vi =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(mGroupLayoutResource, null);
            holder = new GenericViewHolder();
            holder.root = v;

            // init the sub views and put them in a holder instance
            FunDapterUtils.initViews(v, holder, mGroupDictionary);

            v.setTag(holder);
        } else {
            holder = (GenericViewHolder) v.getTag();
        }

        // Show the data
        final G item = getGroup(groupPosition);
        showGroupData(item, holder, groupPosition);

        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        // Inflate a new view or use a recycled view.
        View v = convertView;
        GenericViewHolder holder;
        if (null == v) {
            LayoutInflater vi =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(mChildLayoutResource, null);
            holder = new GenericViewHolder();
            holder.root = v;

            // init the sub views and put them in a holder instance
            FunDapterUtils.initViews(v, holder, mChildDictionary);

            v.setTag(holder);
        } else {
            holder = (GenericViewHolder) v.getTag();
        }

        // Show the data
        final C item = getChild(groupPosition, childPosition);
        FunDapterUtils.showData(item, holder, groupPosition, mChildDictionary);

        return v;
    }

    @Override
    public C getChild(int groupPosition, int childPosition) {
        return mChildExtractor.extractChild(mDataItems.get(groupPosition), childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildExtractor.getChildrenCount(mDataItems.get(groupPosition));
    }

    @Override
    public G getGroup(int groupPosition) {
        return mDataItems.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        if (mDataItems == null) return 0;

        return mDataItems.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
