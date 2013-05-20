package com.ami.fundapter;

import android.view.View.OnClickListener;


/**
 * A field to handle progress bar information. Good for showing user progress. 
 * @author Ami
 *
 * @param <T>
 */
public class ProgressBarField<T> extends BaseField<T> {
    IntegerExtractor<T> progressExtractor;
    IntegerExtractor<T> maxProgressExtractor;

    public ProgressBarField(int viewResId,
	    IntegerExtractor<T> progerssExtractor,
	    IntegerExtractor<T> maxProgressExtractor) {
	super(viewResId);
	this.progressExtractor = progerssExtractor;
	this.maxProgressExtractor = maxProgressExtractor;
    }
    
    @Override
    public ProgressBarField<T> onClick(OnClickListener onClickListener) {
	
	return (ProgressBarField<T>) super.onClick(onClickListener);
    }
}
