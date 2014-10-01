package com.example.bindableadaptertest;

import android.widget.ImageView;

import com.ami.fundapter.interfaces.DynamicImageLoader;

public class MockImageLoader implements DynamicImageLoader {

    @Override
    public void loadImage(String url, ImageView view) {
	//does nothing! :)
    }

}
