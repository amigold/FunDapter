package com.example.bindableadaptertest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ami.fundapter.BindDictionary;
import com.ami.fundapter.FunDapter;
import com.ami.fundapter.extractors.StringExtractor;
import com.ami.fundapter.interfaces.DynamicImageLoader;
import com.ami.fundapter.interfaces.FunDapterFilter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ListView list;
    private Typeface tfBold;
    private TextView searchField;
    private Button switchToExpandableBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // load some custom font
        tfBold = Typeface.createFromAsset(getAssets(), "arialbd.ttf");

        // Parse the sample JSON data from the asset file
        String path = "products.json";
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Product>>() {
        }.getType();

        String rawJson = readFile(path);

        ArrayList<Product> prodList = null;
        try {
            prodList = gson.fromJson(rawJson, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Show our data
        // initOldAdapter(prodList);
        initFunDapter(prodList);

    }

    private void initViews() {
        list = (ListView) findViewById(R.id.pager);
        searchField = (TextView) findViewById(R.id.searchField);
        switchToExpandableBtn = (Button) findViewById(R.id.switchToExpandableBtn);
        switchToExpandableBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expandable = new Intent();
                expandable.setClass(MainActivity.this, MyExpandableActivity.class);
                startActivity(expandable);
            }
        });
    }

    @SuppressWarnings("unused")
    private void initOldAdapter(ArrayList<Product> prodList) {

        // Create the adapter class and give it the data, font and an image
        // loader implementation
        list.setAdapter(new ProductListAdapter(this, prodList, tfBold, new MockImageLoader()));
    }

    /**
     * This is where we create the adapter. Instead of writing a whole class for
     * it we simply define which fields we are going to fill with data.
     *
     * @param prodList
     */
    public void initFunDapter(ArrayList<Product> prodList) {

        BindDictionary<Product> prodDict = buildDictionary(tfBold);

        // Create our adapter giving it a product list, resource to inflate and
        // dictionary
        FunDapter<Product> adapter =
                new FunDapter<Product>(this, prodList, R.layout.product_list_item, prodDict);
        list.setAdapter(adapter);

        // initialize the textfilter for our list
        initTextFilter(adapter);
    }

    public static BindDictionary<Product> buildDictionary(Typeface tf) {
        // create the binding dictionary
        BindDictionary<Product> prodDict = new BindDictionary<Product>();
        prodDict.addStringField(R.id.title, new StringExtractor<Product>() {
            @Override
            public String getStringValue(Product item, int position) {
                return item.title;
            }
        }).typeface(tf).visibilityIfNull(View.GONE);
        prodDict.addStringField(R.id.description, new StringExtractor<Product>() {

            @Override
            public String getStringValue(Product item, int position) {
                return item.description;
            }
        });
        prodDict.addStringField(R.id.price, new StringExtractor<Product>() {

            @Override
            public String getStringValue(Product item, int position) {
                return item.price + " $";
            }
        });
        prodDict.addDynamicImageField(R.id.image, new StringExtractor<Product>() {

                    @Override
                    public String getStringValue(Product item, int position) {
                        return item.imageUrl;
                    }
                }, new DynamicImageLoader() {
                    @Override
                    public void loadImage(String url, ImageView view) {
                        // INSERT IMAGE LOADER LIBRARY HERE
                    }
                }
        );
        return prodDict;
    }

    private void initTextFilter(final FunDapter<Product> adapter) {

        // init the filter in the adapter
        adapter.initFilter(new FunDapterFilter<Product>() {
            @Override
            public List<Product> filter(String filterConstraint,
                                             List<Product> originalList) {

                ArrayList<Product> filtered = new ArrayList<Product>();

                for (int i = 0; i < originalList.size(); i++) {
                    Product product = originalList.get(i);
                    if (product.title.startsWith(filterConstraint)) {
                        filtered.add(product);
                    }
                }

                return filtered;
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // now we can use the regular ListView API for filtering:
                adapter.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
    }

    public String readFile(String filename) {
        StringBuilder b = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(getAssets().open(filename)));
            String str;
            while ((str = in.readLine()) != null) {
                b.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return b.toString();

    }

}
