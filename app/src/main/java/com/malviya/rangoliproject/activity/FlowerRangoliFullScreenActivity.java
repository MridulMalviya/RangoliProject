package com.malviya.rangoliproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.malviya.rangoliproject.image_data_store.DataFlowerRangoli;
import com.malviya.rangoliproject.adapter.FullScreenImageAdapter;
import com.malviya.rangoliproject.R;
import com.malviya.rangoliproject.animation.DepthPageTransformer;

/**
 * Created by Prafulla on 10/26/2016.
 */

public class FlowerRangoliFullScreenActivity extends AppCompatActivity {

    //private Utils utils;
    FullScreenImageAdapter adapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        adapter = new FullScreenImageAdapter(FlowerRangoliFullScreenActivity.this, DataFlowerRangoli.getData());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}
