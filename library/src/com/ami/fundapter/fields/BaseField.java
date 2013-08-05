package com.ami.fundapter.fields;

import android.view.View.OnClickListener;

import com.ami.fundapter.ItemClickListener;

public class BaseField<T> {
    public int viewResId;
    public ItemClickListener<T> clickListener;

    public BaseField(int viewResId) {
        this.viewResId = viewResId;
    }

    public BaseField<T> onClick(ItemClickListener<T> onClickListener) {
        clickListener = onClickListener;

        return this;
    }
}