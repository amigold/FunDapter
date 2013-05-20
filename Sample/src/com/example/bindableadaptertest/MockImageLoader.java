package com.example.bindableadaptertest;

import android.widget.ImageView;

import com.ami.fundapter.ImageLoader;

public class MockImageLoader implements ImageLoader {

    @Override
    public void loadImage(String url, ImageView view) {
	//does nothing! :)
    }

}
