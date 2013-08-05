package com.ami.fundapter;

import android.view.View;

public interface ItemClickListener<T> {
    public void onClick(T item, int position, View view);
}
