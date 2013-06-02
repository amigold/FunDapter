package com.ami.fundapter.fields;

import android.view.View.OnClickListener;

public abstract class BaseField<T> {
    public int viewResId;
    public OnClickListener clickListener;

    public BaseField(int viewResId) {
	this.viewResId = viewResId;
    }

    public BaseField<T> onClick(OnClickListener onClickListener) {
	clickListener = onClickListener;
	
	return this;
    }
}