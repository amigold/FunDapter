package com.ami.fundapter.fields;

import com.ami.fundapter.interfaces.ItemClickListener;
import com.ami.fundapter.interfaces.ViewPopulatedListener;

public class BaseField<T> {
    public int viewResId;
    public ItemClickListener<T> clickListener;
    public ViewPopulatedListener<T> populatedListener;

    public BaseField(int viewResId) {
        this.viewResId = viewResId;
    }

    public BaseField<T> onClick(ItemClickListener<T> onClickListener) {
        clickListener = onClickListener;
        return this;
    }

    /**
     * allows one to do random things to any given field after it is populated
     * don't use this unless you really need to...
     */
    public BaseField<T> onPopulated(ViewPopulatedListener<T> onDrawListener) {
        populatedListener = onDrawListener;
        return this;
    }
}
