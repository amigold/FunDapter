package com.example.bindableadaptertest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ami.fundapter.BindDictionary;
import com.ami.fundapter.FunDapter;
import com.ami.fundapter.ImageLoader;
import com.ami.fundapter.StringExtractor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends FragmentActivity {

    private ListView list;
    private Typeface tfBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	list = (ListView) findViewById(R.id.pager);

	//load some custom font
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

	//Show our data
	// initAdapter(prodList);
	initFunDapter(prodList);

    }

    private void initAdapter(ArrayList<Product> prodList) {

	// Create the adapter class and give it the data, font and an image
	// loader implementation
	list.setAdapter(new ProductListAdapter(this, prodList, tfBold,
		new MockImageLoader()));
    }

    /**
     * This is where we create the adapter. Instead of writing a whole class for
     * it we simply define which fields we are going to fill with data.
     * 
     * @param prodList
     */
    protected void initFunDapter(ArrayList<Product> prodList) {

	// create the binding dictionary
	BindDictionary<Product> prodDict = new BindDictionary<Product>();
	prodDict.addStringField(R.id.title, new StringExtractor<Product>() {
	    @Override
	    public String getStringValue(Product item, int position) {
		return item.title;
	    }
	}).typeface(tfBold).visibilityIfNull(View.GONE);
	prodDict.addStringField(R.id.description,
		new StringExtractor<Product>() {

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
	prodDict.addImageField(R.id.image, new StringExtractor<Product>() {

	    @Override
	    public String getStringValue(Product item, int position) {
		return item.imageUrl;
	    }
	}, new ImageLoader() {
	    @Override
	    public void loadImage(String url, ImageView view) {
		// INSERT IMAGE LOADER LIBRARY HERE
	    }
	}).onClick(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		Toast.makeText(MainActivity.this, "clicked the image!",
			Toast.LENGTH_SHORT).show();
	    }
	});

	// Create our adapter giving it a product list, resource to inflate and
	// dictionary
	FunDapter<Product> adapter = new FunDapter<Product>(this, prodList,
		R.layout.product_list_item, prodDict);
	list.setAdapter(adapter);
    }

    public String readFile(String filename) {
	StringBuilder b = new StringBuilder();
	BufferedReader in = null;
	try {
	    in = new BufferedReader(new InputStreamReader(getAssets().open(
		    filename)));
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
