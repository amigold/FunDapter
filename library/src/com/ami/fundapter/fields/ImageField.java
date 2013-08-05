package com.ami.fundapter.fields;

import com.ami.fundapter.ImageLoader;
import com.ami.fundapter.ItemClickListener;
import com.ami.fundapter.extractors.StringExtractor;

import android.view.View.OnClickListener;


/**
 * This class depends on Google's image caching library to enable fetching
 * images from the web in a ListView.
 *
 * @param <T>
 * @author Ami G
 */
public class ImageField<T> extends BaseStringField<T> {

    public ImageLoader imageLoader;

    /**
     * @param viewResId   - The resource ID of the view you want to bind to (Example:
     *                    R.id.image).
     * @param extractor   - An implementation that will extract the URL from the model
     *                    object.
     * @param imageLoader - A previously set-up image fetcher that will load images from
     *                    the provided URL.
     */
    public ImageField(int viewResId, StringExtractor<T> extractor,
                      ImageLoader imageLoader) {
        super(viewResId, extractor);
        this.imageLoader = imageLoader;
    }

    @Override
    public ImageField<T> onClick(ItemClickListener<T> onClickListener) {

        return (ImageField<T>) super.onClick(onClickListener);
    }
}
