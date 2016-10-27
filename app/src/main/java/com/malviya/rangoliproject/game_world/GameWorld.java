package com.malviya.rangoliproject.game_world;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.malviya.rangoliproject.R;
import com.malviya.rangoliproject.constants.Constant;
import com.malviya.rangoliproject.interfaces.IGameRules;
import com.malviya.rangoliproject.main.GameCanvas;
import com.malviya.rangoliproject.utilies.Utility;

import static android.view.KeyEvent.KEYCODE_DPAD_LEFT;
import static android.view.MotionEvent.ACTION_BUTTON_PRESS;
import static android.view.MotionEvent.ACTION_BUTTON_RELEASE;

/**
 * Created by Admin on 23-10-2016.
 */

public class GameWorld extends BaseView implements IGameRules, Constant {
    private final Context mContext;
    float count = 0;
    private float x = -1, y = -1;
    private float DiyaX, DiyaY, DiyaW, DiyaH, initDiyaX, initDiyaY;
    private boolean isDiyaStart;
    private double initDiyaX1;
    private double initDiyaY1;
    private boolean isLampOn;
    private int isTopLeftBellRinging;
    private float top_left_bell_x;
    private float top_left_bell_y;
    private float top_left_bell_w;
    private float top_left_bell_h;
    private boolean isTopLeftBellRingingAnimStart;
    private float mala_counter = 1;
    private boolean isMalaVisible;
    private float mala_btn_x = 0;
    private float mala_btn_y = 0;
    private float mala_btn_w;
    private float mala_btn_h;
    private boolean isBellRing;
    private boolean isMalaDone;
    private boolean isArtiDone;
    private int hint_pointer_y = 5;
    private boolean is_hint_pointer_blinking;


    public GameWorld(Context context) {
        super(context);
        mContext = context;
        init();
        // Toast.makeText(mContext, getResources().getText(R.string.msg_ring_the_bell), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init() {
        reInit();
    }

    @Override
    public void reInit() {
        mala_btn_x = Constant.MALA_BTN_X;
        DiyaW = BitmapFactory.decodeResource(getResources(), R.drawable.lamp_off).getWidth();
        DiyaH = BitmapFactory.decodeResource(getResources(), R.drawable.lamp_off).getHeight();
        mala_btn_y = initDiyaY = DiyaY = (GameCanvas.DEVICE_HEIGHT - Constant.DIYA_BOTTOM_MARGIN) + 150; //getHeight()-100;
        initDiyaY1 = Constant.DIYA_CENTER_Y;
        mala_btn_w = BitmapFactory.decodeResource(getResources(), R.drawable.mala_btn).getWidth();
        mala_btn_h = BitmapFactory.decodeResource(getResources(), R.drawable.mala_btn).getHeight();

        initDiyaX = DiyaX = (GameCanvas.DEVICE_WIDTH - (DiyaW / 2)) / 2;
        initDiyaX1 = initDiyaX;
        isLampOn = false;
        //for top_left_bell
        top_left_bell_x = Constant.TOP_LEFT_BELL_X;
        top_left_bell_y = Constant.TOP_LEFT_BELL_Y;
        top_left_bell_w = BitmapFactory.decodeResource(getResources(), R.drawable.bell_1).getWidth();
        top_left_bell_h = BitmapFactory.decodeResource(getResources(), R.drawable.bell_1).getWidth();
        isTopLeftBellRinging = 0;
    }

    @Override
    public boolean onPressedKey(int keyCode, KeyEvent event) {
        Log.d("gameworld", " keycode " + keyCode);
        Log.d("gameworld", " keyevent " + event.getAction());
        switch (keyCode) {
            case KEYCODE_DPAD_LEFT:
                return true;
            case KeyEvent.ACTION_UP:
                return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        Log.d("gameworld", " onTouch " + event.getAction() + "X: " + x + " Y: " + y);
        switch (event.getAction()) {
            case ACTION_BUTTON_PRESS:
                return true;
            case ACTION_BUTTON_RELEASE:
                return true;
        }
        return false;
    }

    @Override
    public void cycle(Paint paint) {
        //Log.d("gameworld","x,y "+x+" : "+y+" : "+DiyaX+" : "+DiyaY+" : "+DiyaW+" : "+DiyaH);
        if (Utility.isCollide(x, y, DiyaX, DiyaY, DiyaW, DiyaH)) {
            if (!isBellRing) {
                Toast.makeText(mContext, getResources().getText(R.string.msg_ring_the_bell), Toast.LENGTH_SHORT).show();
                return;
            } else if (!isMalaDone) {
                Toast.makeText(mContext, getResources().getText(R.string.msg_mala_start), Toast.LENGTH_SHORT).show();
                return;
            }
            if (isLampOn) {
                paint.setColor(Color.RED);
                isDiyaStart = true;
            } else {
                isLampOn = true;
                Toast.makeText(mContext, R.string.msg_lamp_on, Toast.LENGTH_SHORT).show();
            }
        } else {
            paint.setColor(Color.BLUE);
        }
        rotateDiya();
        //-----------------------------------------------
        //for top_left_bell_ringing
        if (Utility.isCollide(x, y, top_left_bell_x, top_left_bell_y, top_left_bell_w, top_left_bell_h)) {
            isTopLeftBellRingingAnimStart = true;
            isBellRing = true;
        }

        if (isTopLeftBellRingingAnimStart) {
            if (isTopLeftBellRinging < Constant.BALL_TIME) {
                isTopLeftBellRinging += 1;
            } else {
                isTopLeftBellRinging = 0;
                isTopLeftBellRingingAnimStart = false;
            }
        }

        //for mala_btn
        if (Utility.isCollide(x, y, mala_btn_x, mala_btn_y, mala_btn_w, mala_btn_h)) {
            if (!isBellRing) {
                Toast.makeText(mContext, getResources().getText(R.string.msg_ring_the_bell), Toast.LENGTH_SHORT).show();
                return;
            }
            isMalaVisible = true;
            isMalaDone = true;
        }
    }


    private void rotateDiya() {
        if (isDiyaStart) {
            if (count < Constant.DIYA_COUNT) {
                //Point p  = getPointOnCicle(45,50);
                DiyaX = (float) (initDiyaX1 + (Math.cos(count) * DIYA_X_ROTATION));
                DiyaY = (float) (initDiyaY1 + (Math.sin(count) * DIYA_Y_ROTATION));
                //DiyaY = p.y;//initDiyaY + ((DiyaY-(float) (Math.sin(45)*2))%initDiyaY);
                count += Constant.DIYA_SPEED;
            } else {
                count = 0;
                isDiyaStart = false;
                x = 0;
                y = 0;
                DiyaX = (GameCanvas.DEVICE_WIDTH - (DiyaW / 2)) / 2;
                DiyaY = GameCanvas.DEVICE_HEIGHT - Constant.DIYA_BOTTOM_MARGIN;
                isArtiDone = true;
                Toast.makeText(mContext, getResources().getText(R.string.msg_aarti_done), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg), 0, 0, paint);
        if (is_hint_pointer_blinking = !is_hint_pointer_blinking) {
            hint_pointer_y = 5;
        } else {
            hint_pointer_y = -5;
        }
        if (!isBellRing) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arrow), top_left_bell_x, top_left_bell_y - top_left_bell_h + hint_pointer_y, paint);
        } else if (!isMalaDone) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arrow), mala_btn_x, mala_btn_y - mala_btn_h - 10 + hint_pointer_y, paint);
        } else if (!isLampOn) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arrow), DiyaX, DiyaY - DiyaH - 10 + hint_pointer_y, paint);
        }

        //for mala
        if (isMalaVisible && (int) mala_counter % 2 == 0) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mala), Constant.mala_x, Constant.mala_y, paint);
        }

        if (mala_counter == 0) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mala), Constant.mala_x, Constant.mala_y, paint);

        }
        if (isMalaVisible) {
            if (mala_counter >= Constant.MAX_MALA_ANIMATION_TIME) {
                isMalaVisible = false;
                mala_counter = 0;
                Toast.makeText(mContext, getResources().getText(R.string.msg_after_mala_done), Toast.LENGTH_SHORT).show();
            } else {
                mala_counter += Constant.MALA_COUNTER_SPEED;
            }
        }
        //Log.d("gameworld", " isMalaVisible " + isMalaVisible);
        //Log.d("gameworld", " mala_counter " + mala_counter);

        if (mala_counter != 0) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mala_btn), mala_btn_x, mala_btn_y, paint);
        }
        //------------------------------------------------------------------------------------------------------------------
        //for top left bell
        if (isTopLeftBellRinging % 3 == 0) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bell_1), top_left_bell_x, top_left_bell_y, paint);
        } else if (isTopLeftBellRinging % 3 == 1) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bell_2), top_left_bell_x, top_left_bell_y, paint);
        } else {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bell_3), top_left_bell_x, top_left_bell_y, paint);
        }

        if (isLampOn) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lamp_on), DiyaX, DiyaY - 115, paint);
        } else {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lamp_off), DiyaX, DiyaY, paint);
        }


        //canvas.drawText("x " +(int)x + " y " +(int)y, x, y, paint);

        x = -1;
        y = -1;
    }


    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void release() {

    }


}
