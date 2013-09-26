package com.example.bindableadaptertest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ami.fundapter.interfaces.DynamicImageLoader;

import java.util.ArrayList;

public class ProductListAdapter extends BaseAdapter {
    private ArrayList<Product> productsList;
    private Activity activity;
    private final Typeface tf;
    private DynamicImageLoader imageLoader;

    public ProductListAdapter(Activity context, ArrayList<Product> productList, Typeface tf,
                              DynamicImageLoader loader) {
        super();

        productsList = productList;
        activity = context;
        this.tf = tf;
        imageLoader = loader;

    }

    /**
     * Define the ViewHolder for our adapter
     */
    public static class ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image;
        public TextView price;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // ---------------
        // Boilerplate view inflation and ViewHolder code
        // ---------------
        View v = convertView;
        ViewHolder holder;
        if (v == null) {

            LayoutInflater vi =
                    (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.product_list_item, null);

            holder = new ViewHolder();

            holder.title = (TextView) v.findViewById(R.id.title);
            holder.description = (TextView) v.findViewById(R.id.description);
            holder.price = (TextView) v.findViewById(R.id.price);
            holder.image = (ImageView) v.findViewById(R.id.image);

            v.setTag(holder);
        } else holder = (ViewHolder) v.getTag();
        // ------------------------------------------

        // This is where we actually start filling the data
        final Product product = productsList.get(position);
        if (product != null) {

            // Product title
            if (!TextUtils.isEmpty(product.title)) {
                holder.title.setText(product.title);
                holder.title.setTypeface(tf);
                holder.title.setVisibility(View.VISIBLE);
            } else {
                holder.title.setVisibility(View.GONE);
            }

            // Product description
            if (!TextUtils.isEmpty(product.description)) {
                holder.description.setText(product.description);
                holder.title.setVisibility(View.VISIBLE);
            } else holder.description.setVisibility(View.GONE);

            // Your image loader implementation
            if (imageLoader != null) imageLoader.loadImage(product.imageUrl, holder.image);

            // Product price
            if (product.price != 0) {
                holder.price.setText(product.price + "0" + " $ ");
                holder.price.setVisibility(View.VISIBLE);
            } else holder.price.setVisibility(View.GONE);

        }
        return v;
    }


    // ---------------
    // More boilerplate methods
    // ---------------

    @Override
    public int getCount() {
        return productsList == null ? 0 : productsList.size();
    }

    @Override
    public Object getItem(int position) {
        return productsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
