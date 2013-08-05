package com.ami.fundapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ami.fundapter.fields.ConditionalVisibilityField;
import com.ami.fundapter.fields.ImageField;
import com.ami.fundapter.fields.ProgressBarField;
import com.ami.fundapter.fields.StringField;

public class FunDapterUtils {

    public static <T> void initViews(View v, GenericViewHolder holder,
                                     BindDictionary<T> dictionary) {
        // init the holder arrays
        holder.stringFields = new TextView[dictionary.getStringFieldCount()];
        holder.imageFields = new ImageView[dictionary.getImageFieldCount()];
        holder.conditionalVisibilityFields = new View[dictionary
                .getConditionalVisibilityFieldCount()];
        holder.progressBarFields = new ProgressBar[dictionary
                .getProgressBarFieldCount()];

        // init the string fields
        for (int i = 0; i < dictionary.getStringFields().size(); i++) {
            StringField<T> field = dictionary.getStringFields().get(i);
            holder.stringFields[i] = (TextView) v.findViewById(field.viewResId);

            // add a typeface if the field has one
            if (field.typeface != null)
                holder.stringFields[i].setTypeface(field.typeface);
        }

        // init image fields
        for (int i = 0; i < dictionary.getImageFields().size(); i++) {
            ImageField<T> field = dictionary.getImageFields().get(i);
            holder.imageFields[i] = (ImageView) v.findViewById(field.viewResId);
        }

        // init conditional visibility fields
        for (int i = 0; i < dictionary.getConditionalVisibilityFields().size(); i++) {
            ConditionalVisibilityField<T> field = dictionary
                    .getConditionalVisibilityFields().get(i);
            holder.conditionalVisibilityFields[i] = v
                    .findViewById(field.viewResId);
        }

        // init progress bar fields
        for (int i = 0; i < dictionary.getProgressBarFields().size(); i++) {
            ProgressBarField<T> field = dictionary.getProgressBarFields()
                    .get(i);
            holder.progressBarFields[i] = (ProgressBar) v
                    .findViewById(field.viewResId);
        }
    }

    public static <T> void showData(T item, GenericViewHolder holder,
                                    int position, BindDictionary<T> bindDictionary) {

        handleStringFields(item, holder, position, bindDictionary);

        handleImageFields(item, holder, position, bindDictionary);

        handleConditionalFields(item, holder, position, bindDictionary);

        handleProgressFields(item, holder, position, bindDictionary);
    }

    private static <T> void handleProgressFields(T item,
                                                 GenericViewHolder holder, int position, BindDictionary<T> dictionary) {
        // handle progress bars
        for (int i = 0; i < dictionary.getProgressBarFields().size(); i++) {
            ProgressBarField<T> field = dictionary.getProgressBarFields()
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

    private static <T> void handleConditionalFields(T item,
                                                    GenericViewHolder holder, int position, BindDictionary<T> dictionary) {
        // handle conditionals
        for (int i = 0; i < dictionary.getConditionalVisibilityFields().size(); i++) {
            ConditionalVisibilityField<T> field = dictionary
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

    private static <T> void handleImageFields(T item, GenericViewHolder holder,
                                              int position, BindDictionary<T> dictionary) {
        // handle image fields
        for (int i = 0; i < dictionary.getImageFields().size(); i++) {
            ImageField<T> field = dictionary.getImageFields().get(i);
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

    private static <T> void handleStringFields(T item,
                                               GenericViewHolder holder, int position, BindDictionary<T> dictionary) {
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

}
