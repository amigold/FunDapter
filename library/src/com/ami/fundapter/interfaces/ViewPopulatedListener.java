package com.ami.fundapter.interfaces;

import android.view.View;

/**
 * This listener is called whenever a field is done being bound and populated by its extractors.
 *
 * i.e. it's called once EVERY time the bound field is created or recycled.
 *
 * This is a catchall to fill any gaps in the fundapter API and give people a mechanism to work around any limitations.
 *
 * Any common uses of this listener should probably get promoted to first class citizens in the API.
 */
public interface ViewPopulatedListener<T> {
    public void onViewPopulated(T item, int position, View view);
}
