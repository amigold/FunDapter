package com.ami.fundapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ami.fundapter.fields.BaseField;
import com.ami.fundapter.fields.CheckableField;
import com.ami.fundapter.fields.ConditionalVisibilityField;
import com.ami.fundapter.fields.DynamicImageField;
import com.ami.fundapter.fields.ProgressBarField;
import com.ami.fundapter.fields.StaticImageField;
import com.ami.fundapter.fields.StringField;

public class FunDapterUtils {

    public static <T> void initViews(View v, GenericViewHolder holder,
                                     BindDictionary<T> dictionary) {
        // init the holder arrays
        holder.stringFields = new TextView[dictionary.getStringFieldCount()];
        holder.dynamicImageFields = new ImageView[dictionary.getDynamicImageFieldCount()];
        holder.conditionalVisibilityFields =
                new View[dictionary.getConditionalVisibilityFieldCount()];
        holder.progressBarFields = new ProgressBar[dictionary.getProgressBarFieldCount()];
        holder.baseFields = new View[dictionary.getBaseFieldCount()];
        holder.checkableFields = new CompoundButton[dictionary.getCheckableFieldCount()];
        holder.staticImageFields = new ImageView[dictionary.getStaticImageFieldCount()];

        // init the string fields
        int i;
        for (i = 0; i < dictionary.getStringFields().size(); i++) {
            StringField<T> field = dictionary.getStringFields().get(i);
            holder.stringFields[i] = (TextView) v.findViewById(field.viewResId);

            // add a typeface if the field has one
            if (field.typeface != null) holder.stringFields[i].setTypeface(field.typeface);
        }

        // init dynamic image fields
        for (i = 0; i < dictionary.getDynamicImageFields().size(); i++) {
            DynamicImageField<T> field = dictionary.getDynamicImageFields().get(i);
            holder.dynamicImageFields[i] = (ImageView) v.findViewById(field.viewResId);
        }

        // init static image fields
        for (i = 0; i < dictionary.getStaticImageFieldCount(); i++) {
            StaticImageField<T> field = dictionary.getStaticImageFields().get(i);
            holder.staticImageFields[i] = (ImageView) v.findViewById(field.viewResId);
        }

        // init conditional visibility fields
        for (i = 0; i < dictionary.getConditionalVisibilityFields().size(); i++) {
            ConditionalVisibilityField<T> field =
                    dictionary.getConditionalVisibilityFields().get(i);
            holder.conditionalVisibilityFields[i] = v.findViewById(field.viewResId);
        }

        // init progress bar fields
        for (i = 0; i < dictionary.getProgressBarFields().size(); i++) {
            ProgressBarField<T> field = dictionary.getProgressBarFields().get(i);
            holder.progressBarFields[i] = (ProgressBar) v.findViewById(field.viewResId);
        }

        //init base fields
        for (i = 0; i < dictionary.getBaseFields().size(); i++) {
            BaseField<T> field = dictionary.getBaseFields().get(i);
            holder.baseFields[i] = v.findViewById(field.viewResId);
        }

        //init checkable fields
        for (i = 0; i < dictionary.getCheckableFields().size(); i++) {
            CheckableField<T> field = dictionary.getCheckableFields().get(i);
            holder.checkableFields[i] = (CompoundButton) v.findViewById(field.viewResId);
        }
    }

    public static <T> void showData(T item, GenericViewHolder holder, int position,
                                    BindDictionary<T> bindDictionary) {

        handleStringFields(item, holder, position, bindDictionary);

        handleDynamicImageFields(item, holder, position, bindDictionary);

        handleStaticImageFields(item, holder, position, bindDictionary);

        handleConditionalFields(item, holder, position, bindDictionary);

        handleProgressFields(item, holder, position, bindDictionary);

        handleBaseFields(item, holder, position, bindDictionary);

        handleCheckableFields(item, holder, position, bindDictionary);
    }

    private static <T> void handleCheckableFields(final T item, GenericViewHolder holder,
                                                  final int position,
                                                  BindDictionary<T> dictionary) {
        // handle base fields
        for (int i = 0; i < dictionary.getCheckableFields().size(); i++) {
            final CheckableField<T> field = dictionary.getCheckableFields().get(i);
            CompoundButton view = holder.checkableFields[i];

            view.setOnCheckedChangeListener(null);
            view.setChecked(field.checkedExtractor.getBooleanValue(item, position));

            setClickListener(item, position, field, view);

            if (field.checkedChangeListener != null) {
                view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        field.checkedChangeListener
                                .onCheckedChangedListener(item, position, compoundButton, b);
                    }
                });
            }

            doOnPopulated(item, position, field, view);
        }
    }

    private static <T> void handleBaseFields(final T item, GenericViewHolder holder,
                                             final int position, BindDictionary<T> dictionary) {
        // handle base fields
        for (int i = 0; i < dictionary.getBaseFields().size(); i++) {
            final BaseField<T> field = dictionary.getBaseFields().get(i);

            View view = holder.baseFields[i];

            setClickListener(item, position, field, view);

            doOnPopulated(item, position, field, view);
        }
    }

    private static <T> void setClickListener(final T item, final int position,
                                             final BaseField<T> field, View view) {
        if (field.clickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    field.clickListener.onClick(item, position, view);
                }
            });
        }
    }

    private static <T> void handleProgressFields(T item, GenericViewHolder holder, int position,
                                                 BindDictionary<T> dictionary) {
        // handle progress bars
        for (int i = 0; i < dictionary.getProgressBarFields().size(); i++) {
            ProgressBarField<T> field = dictionary.getProgressBarFields().get(i);

            ProgressBar view = holder.progressBarFields[i];

            view.setMax(field.maxProgressExtractor.getIntValue(item, position));
            view.setProgress(field.progressExtractor.getIntValue(item, position));

            setClickListener(item, position, field, view);

            doOnPopulated(item, position, field, view);
        }

    }

    private static <T> void handleConditionalFields(T item, GenericViewHolder holder, int position,
                                                    BindDictionary<T> dictionary) {
        // handle conditionals
        for (int i = 0; i < dictionary.getConditionalVisibilityFields().size(); i++) {
            ConditionalVisibilityField<T> field =
                    dictionary.getConditionalVisibilityFields().get(i);
            boolean condition = field.extractor.getBooleanValue(item, position);
            View view = holder.conditionalVisibilityFields[i];

            if (view != null) {
                if (condition) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(field.visibilityIfFalse);
                }
            }

            setClickListener(item, position, field, view);
            doOnPopulated(item, position, field, view);
        }
    }

    private static <T> void handleDynamicImageFields(T item, GenericViewHolder holder, int position,
                                                     BindDictionary<T> dictionary) {
        // handle image fields
        for (int i = 0; i < dictionary.getDynamicImageFields().size(); i++) {
            DynamicImageField<T> field = dictionary.getDynamicImageFields().get(i);
            String url = field.extractor.getStringValue(item, position);
            ImageView view = holder.dynamicImageFields[i];

            // call the image loader
            if ((!TextUtils.isEmpty(url) || field.allowNullUrl) && field.dynamicImageLoader != null && view != null) {
                field.dynamicImageLoader.loadImage(url, view);
            }

            setClickListener(item, position, field, view);
            doOnPopulated(item, position, field, view);
        }
    }

    private static <T> void handleStaticImageFields(T item, GenericViewHolder holder, int position,
                                                    BindDictionary<T> dictionary) {
        // handle image fields
        for (int i = 0; i < dictionary.getStaticImageFieldCount(); i++) {
            StaticImageField<T> field = dictionary.getStaticImageFields().get(i);
            ImageView view = holder.staticImageFields[i];

            // call the image loader
            if (item != null && field.staticImageLoader != null && view != null) {
                field.staticImageLoader.loadImage(item, view, position);
            }

            setClickListener(item, position, field, view);
            doOnPopulated(item, position, field, view);
        }
    }

    private static <T> void handleStringFields(T item, GenericViewHolder holder, int position,
                                               BindDictionary<T> dictionary) {
        // handle string fields
        for (int i = 0; i < dictionary.getStringFields().size(); i++) {
            StringField<T> field = dictionary.getStringFields().get(i);
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
                boolean condition =
                        field.conditionalTextColorEntry.getKey().getBooleanValue(item, position);

                if (condition) {
                    view.setTextColor(field.conditionalTextColorEntry.getValue()[0]);
                } else {
                    view.setTextColor(field.conditionalTextColorEntry.getValue()[1]);
                }
            }

            setClickListener(item, position, field, view);
            doOnPopulated(item, position, field, view);
        }
    }

    private static <T> void doOnPopulated(T item, int position, BaseField<T> field, View view) {
        if(field.populatedListener != null) {
            field.populatedListener.onViewPopulated(item, position, view);
        }
    }

}
