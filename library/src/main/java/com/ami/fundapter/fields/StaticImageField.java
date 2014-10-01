package com.ami.fundapter.fields;

import com.ami.fundapter.interfaces.ItemClickListener;
import com.ami.fundapter.interfaces.StaticImageLoader;


/**
 * This class allows enable fetching images from resources.
 *
 * @param <T>
 * @author Ami G
 */
public class StaticImageField<T> extends BaseField<T> {

    public StaticImageLoader<T> staticImageLoader;

    /**
     * @param viewResId         - The resource ID of the view you want to bind to (Example:
     *                          R.id.image).
     * @param staticImageLoader - A previously set-up image fetcher that will load images from
     *                          a static place, such as resources or disk.
     */
    public StaticImageField(int viewResId, StaticImageLoader<T> staticImageLoader) {
        super(viewResId);
        this.staticImageLoader = staticImageLoader;
    }

    @Override
    public StaticImageField<T> onClick(ItemClickListener<T> onClickListener) {

        return (StaticImageField<T>) super.onClick(onClickListener);
    }
}
