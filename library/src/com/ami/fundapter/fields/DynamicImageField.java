package com.ami.fundapter.fields;

import com.ami.fundapter.interfaces.DynamicImageLoader;
import com.ami.fundapter.interfaces.ItemClickListener;
import com.ami.fundapter.extractors.StringExtractor;


/**
 * This class allows enable fetching images from the web in a ListView.
 *
 * By default the DynamicImageLoader instance is not called if the StringExtractor returns null.
 *
 * @param <T>
 * @author Ami G
 */
public class DynamicImageField<T> extends BaseStringField<T> {

    public DynamicImageLoader dynamicImageLoader;
    public boolean allowNullUrl;

    /**
     * @param viewResId          - The resource ID of the view you want to bind to (Example:
     *                           R.id.image).
     * @param extractor          - An implementation that will extract the URL from the model
     *                           object.
     * @param dynamicImageLoader - A previously set-up image fetcher that will load images from
     *                           the provided URL.
     */
    public DynamicImageField(int viewResId, StringExtractor<T> extractor,
                             DynamicImageLoader dynamicImageLoader) {
        super(viewResId, extractor);
        this.dynamicImageLoader = dynamicImageLoader;
    }

    /**
     * if you want the dynamicImageLoader to be called even when the URL returned from the StringExtractor is null.
     */
    public DynamicImageField<T> allowNullUrl() {
        allowNullUrl = true;
        return this;
    }

    @Override
    public DynamicImageField<T> onClick(ItemClickListener<T> onClickListener) {

        return (DynamicImageField<T>) super.onClick(onClickListener);
    }
}
