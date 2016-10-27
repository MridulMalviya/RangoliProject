package com.malviya.rangoliproject.game_world;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Admin on 23-10-2016.
 */
public abstract class BaseView extends View {

    public int DEVICE_WIDTH;
    public int DEVICE_HEIGHT;
    private Paint mPaint;


    public BaseView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(80);
        mPaint.setColor(Color.RED);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (true) {
            cycle(mPaint);
            render(canvas, mPaint);
            invalidate();  //force a redraw
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DEVICE_WIDTH = widthMeasureSpec;
        DEVICE_HEIGHT = heightMeasureSpec;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return onPressedKey(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return onPressedKey(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return onTouch(event);
    }

    public abstract boolean onPressedKey(int keyCode, KeyEvent event);

    public abstract boolean onTouch(MotionEvent event);

    public abstract void cycle(Paint paint);

    public abstract void render(Canvas canvas, Paint mPaint);

    public abstract void start();

    public abstract void pause();

    public abstract void stop();

    public abstract void release();

}
