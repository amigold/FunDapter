package com.ami.fundapter.fields;


import com.ami.fundapter.extractors.StringExtractor;

import android.view.View;

public abstract class BaseStringField<T> extends BaseField<T> {

    public StringExtractor<T> extractor;
    public int visibilityIfNull = View.VISIBLE;

    public BaseStringField(int viewResId, StringExtractor<T> extractor) {
        super(viewResId);
        this.extractor = extractor;
    }

    /**
     * Which visibility value to set to the view if the data is null? For
     * example when you want to hide text fields or images with no data.
     * Defaults to View.VISIBLE
     *
     * @param visibilityIfNull
     * @return
     */
    public BaseStringField<T> visibilityIfNull(int visibilityIfNull) {
        this.visibilityIfNull = visibilityIfNull;
        return this;
    }
}