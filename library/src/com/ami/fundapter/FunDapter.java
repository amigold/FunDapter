package com.ami.fundapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * A generic adapter that takes a BindDictionary and data and shows them. Does
 * basic validation for you for all fields and also handles the ViewHolder
 * pattern.
 * 
 * @author Ami G
 * 
 * @param <T>
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
     * @param dataItems
     *            - An arraylist of model items
     * @param layoutResource
     *            - resource ID of a layout to inflate for each item. (Example:
     *            R.layout.list_item)
     * @param dictionary
     *            - The dictionary that will match between fields and data.
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
    public Object getItem(int position) {
	return mDataItems.get(position);
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    private static class GenericViewHolder {
	public View root;
	public TextView[] stringFields;
	public ImageView[] imageFields;
	public View[] conditionalVisibilityFields;
	public ProgressBar[] progressBarFields;
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
	    initViews(v, holder);

	    v.setTag(holder);
	} else {
	    holder = (GenericViewHolder) v.getTag();
	}

	// Show the data
	final T item = mDataItems.get(position);
	showData(item, holder, position);

	return v;
    }

    private void initViews(View v, GenericViewHolder holder) {
	// init the holder arrays
	holder.stringFields = new TextView[mBindDictionary
		.getStringFieldCount()];
	holder.imageFields = new ImageView[mBindDictionary.getImageFieldCount()];
	holder.conditionalVisibilityFields = new View[mBindDictionary
		.getConditionalVisibilityFieldCount()];
	holder.progressBarFields = new ProgressBar[mBindDictionary
		.getProgressBarFieldCount()];

	// init the string fields
	for (int i = 0; i < mBindDictionary.getStringFields().size(); i++) {
	    StringField<T> field = mBindDictionary.getStringFields().get(i);
	    holder.stringFields[i] = (TextView) v.findViewById(field.viewResId);

	    // add a typeface if the field has one
	    if (field.typeface != null)
		holder.stringFields[i].setTypeface(field.typeface);
	}

	// init image fields
	for (int i = 0; i < mBindDictionary.getImageFields().size(); i++) {
	    ImageField<T> field = mBindDictionary.getImageFields().get(i);
	    holder.imageFields[i] = (ImageView) v.findViewById(field.viewResId);
	}

	// init conditional visibility fields
	for (int i = 0; i < mBindDictionary.getConditionalVisibilityFields()
		.size(); i++) {
	    ConditionalVisibilityField<T> field = mBindDictionary
		    .getConditionalVisibilityFields().get(i);
	    holder.conditionalVisibilityFields[i] = (ImageView) v
		    .findViewById(field.viewResId);
	}

	// init progress bar fields
	for (int i = 0; i < mBindDictionary.getProgressBarFields().size(); i++) {
	    ProgressBarField<T> field = mBindDictionary.getProgressBarFields()
		    .get(i);
	    holder.progressBarFields[i] = (ProgressBar) v
		    .findViewById(field.viewResId);
	}
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

	handleStringFields(item, holder, position);

	handleImageFields(item, holder, position);

	handleConditionalFields(item, holder, position);

	handleProgressFields(item, holder, position);
    }

    protected void handleProgressFields(T item, GenericViewHolder holder,
	    int position) {
	// handle progress bars
	for (int i = 0; i < mBindDictionary.getProgressBarFields().size(); i++) {
	    ProgressBarField<T> field = mBindDictionary.getProgressBarFields()
		    .get(i);

	    ProgressBar view = holder.progressBarFields[i];

	    view.setMax(field.maxProgressExtractor.getIntValue(item, position));
	    view.setProgress(field.progressExtractor
		    .getIntValue(item, position));

	    if (field.clickListener != null) {
		view.setOnClickListener(field.clickListener);
	    }
	}

    }

    protected void handleConditionalFields(T item, GenericViewHolder holder,
	    int position) {
	// handle conditionals
	for (int i = 0; i < mBindDictionary.getConditionalVisibilityFields()
		.size(); i++) {
	    ConditionalVisibilityField<T> field = mBindDictionary
		    .getConditionalVisibilityFields().get(i);
	    boolean condition = field.extractor.getBooleanValue(item, position);
	    View view = holder.conditionalVisibilityFields[i];

	    if (view != null) {
		if (condition) {
		    view.setVisibility(View.VISIBLE);
		} else {
		    view.setVisibility(field.visibilityIfFalse);
		}
	    }

	    if (field.clickListener != null) {
		view.setOnClickListener(field.clickListener);
	    }
	}
    }

    protected void handleImageFields(T item, GenericViewHolder holder,
	    int position) {
	// handle image fields
	for (int i = 0; i < mBindDictionary.getImageFields().size(); i++) {
	    ImageField<T> field = mBindDictionary.getImageFields().get(i);
	    String url = field.extractor.getStringValue(item, position);
	    ImageView view = holder.imageFields[i];

	    // call the image loader
	    if (!TextUtils.isEmpty(url) && field.imageLoader != null
		    && view != null) {
		field.imageLoader.loadImage(url, view);
	    }

	    if (field.clickListener != null) {
		view.setOnClickListener(field.clickListener);
	    }
	}
    }

    protected void handleStringFields(T item, GenericViewHolder holder,
	    int position) {
	// handle string fields
	for (int i = 0; i < mBindDictionary.getStringFields().size(); i++) {
	    StringField<T> field = mBindDictionary.getStringFields().get(i);
	    String stringValue = field.extractor.getStringValue(item, position);
	    TextView view = holder.stringFields[i];

	    // fill data
	    if (!TextUtils.isEmpty(stringValue) && view != null) {
		view.setText(stringValue);
		view.setVisibility(View.VISIBLE);
	    } else {
		view.setVisibility(field.visibilityIfNull);
	    }

	    // set textcolor if needed
	    if (field.conditionalTextColorEntry != null) {
		boolean condition = field.conditionalTextColorEntry.getKey()
			.getBooleanValue(item, position);

		if (condition) {
		    view.setTextColor(field.conditionalTextColorEntry
			    .getValue()[0]);
		} else {
		    view.setTextColor(field.conditionalTextColorEntry
			    .getValue()[1]);
		}
	    }

	    // set click listener
	    if (field.clickListener != null) {
		view.setOnClickListener(field.clickListener);
	    }
	}
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
     * @param filter
     *            - a filter implementation for your adapter.
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
