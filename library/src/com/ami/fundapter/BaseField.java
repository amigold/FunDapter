package com.ami.fundapter;

import android.view.View.OnClickListener;

public abstract class BaseField<T> {
    int viewResId;
    OnClickListener clickListener;

    public BaseField(int viewResId) {
	this.viewResId = viewResId;
    }

    public BaseField<T> onClick(OnClickListener onClickListener) {
	clickListener = onClickListener;
	
	return this;
    }
}