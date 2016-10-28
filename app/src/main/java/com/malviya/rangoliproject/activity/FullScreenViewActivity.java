/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.malviya.rangoliproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.malviya.rangoliproject.R;
import com.malviya.rangoliproject.adapter.FullScreenImageAdapter;
import com.malviya.rangoliproject.animation.DepthPageTransformer;
import com.malviya.rangoliproject.image_data_store.Data;


public class FullScreenViewActivity extends AppCompatActivity {

    //private Utils utils;
    FullScreenImageAdapter adapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);

        viewPager = (ViewPager) findViewById(R.id.pager);

        //set animation Zoom-out page transformer
        //viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        //Depth page transformer animation
        viewPager.setPageTransformer(true, new DepthPageTransformer());

        //ParallaxPageTransformer animation
        //	viewPager.setPageTransformer(true, new ParallaxPageTransformer());

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);

        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, Data.getData());

        viewPager.setAdapter(adapter);
        // displaying selected image first
        viewPager.setCurrentItem(position);
    }


}
