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
