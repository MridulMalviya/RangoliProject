/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.malviya.rangoliproject.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.malviya.rangoliproject.constants.Constant;
import com.malviya.rangoliproject.game_screens.ScreenFactory;


/**
 * Created by Admin on 23-10-2016.
 */

public class GameCanvas extends AppCompatActivity {
    public static int DEVICE_WIDTH;
    public static int DEVICE_HEIGHT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        DEVICE_WIDTH = displayMetrics.widthPixels;
        DEVICE_HEIGHT = displayMetrics.heightPixels;
        Log.d("DEVICE_WIDTH ", "DEVICE_WIDTH: " + DEVICE_WIDTH);
        Log.d("DEVICE_WIDTH ", "DEVICE_HEIGHT: " + DEVICE_HEIGHT);
        setContentView(ScreenFactory.getInstance(this, Constant.SCREEN_GAME));
    }

    @Override
    protected void onStart() {
        super.onStart();
        ScreenFactory.getInstance(this, Constant.SCREEN_GAME).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScreenFactory.getInstance(this, Constant.SCREEN_GAME).pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ScreenFactory.getInstance(this, Constant.SCREEN_GAME).stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScreenFactory.getInstance(this, Constant.SCREEN_GAME).release();
    }
}
