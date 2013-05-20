package com.ami.fundapter;

import android.view.View.OnClickListener;

/**
 * A field to conditionally show a view based on some boolean value. Good for
 * showing list item decorations for certain conditions.
 * 
 * @author Ami
 * 
 * @param <T>
 */
public class ConditionalVisibilityField<T> extends BaseField<T> {

    BooleanExtractor<T> extractor;
    int visibilityIfFalse;

    public ConditionalVisibilityField(int viewResId,
	    BooleanExtractor<T> extractor, int visibilityIfFalse) {
	super(viewResId);
	this.extractor = extractor;
	this.visibilityIfFalse = visibilityIfFalse;
    }

    @Override
    public ConditionalVisibilityField<T> onClick(OnClickListener onClickListener) {

	return (ConditionalVisibilityField<T>) super.onClick(onClickListener);
    }

}
