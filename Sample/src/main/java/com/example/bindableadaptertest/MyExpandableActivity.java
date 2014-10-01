package com.example.bindableadaptertest;

import android.app.ExpandableListActivity;
import android.graphics.Typeface;
import android.os.Bundle;

import com.ami.fundapter.BindDictionary;
import com.ami.fundapter.ExpandableFunDapter;
import com.ami.fundapter.extractors.ChildExtractor;
import com.ami.fundapter.extractors.StringExtractor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyExpandableActivity extends ExpandableListActivity {

    private Typeface tfBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	tfBold = Typeface.createFromAsset(getAssets(), "arialbd.ttf");

	// Parse the sample JSON data from the asset file
	String path = "categories.json";
	Gson gson = new Gson();
	Type listType = new TypeToken<List<Category>>() {
	}.getType();

	String rawJson = readFile(path);

	ArrayList<Category> prodList = null;
	try {
	    prodList = gson.fromJson(rawJson, listType);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	// Show our data
	// initOldAdapter(prodList);
	initFunDapter(prodList);

    }

    /**
     * Example for easily initializing an expandable list adapter
     * 
     * @param prodList
     */
    private void initFunDapter(ArrayList<Category> prodList) {
	
	//Reuse the product dictionary from the MainActivity
	BindDictionary<Product> childDictionary = MainActivity
		.buildDictionary(tfBold);

	//Build a dictionary for the group views
	BindDictionary<Category> groupDictionary = new BindDictionary<Category>();
	groupDictionary.addStringField(android.R.id.text1,
		new StringExtractor<Category>() {
		    @Override
		    public String getStringValue(Category item, int position) {
			return item.title;
		    }
		});

	ExpandableFunDapter<Category, Product> adapter = new ExpandableFunDapter<Category, Product>(
		this, prodList, groupDictionary, childDictionary,
		android.R.layout.simple_expandable_list_item_1,
		R.layout.product_list_item,
		new ChildExtractor<Category, Product>() {

		    @Override
		    public Product extractChild(Category group,
			    int childPosition) {
			return group.products.get(childPosition);
		    }

		    @Override
		    public int getChildrenCount(Category group) {

			if (group.products == null)
			    return 0;

			return group.products.size();
		    }
		});

	setListAdapter(adapter);
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
