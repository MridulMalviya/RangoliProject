package com.malviya.rangoliproject.game_screens;

import android.content.Context;

import com.malviya.rangoliproject.constants.Constant;
import com.malviya.rangoliproject.game_world.BaseView;
import com.malviya.rangoliproject.game_world.GameWorld;


/**
 * Created by Admin on 23-10-2016.
 */

public class ScreenFactory {

    private static ScreenFactory mInstance;
    private static GameWorld view;
    private BaseView mView;

    private ScreenFactory() {

    }

    public static BaseView getInstance(Context pContext, int screen) {
        switch (screen) {
            case Constant.SCREEN_MENU:
                break;
            case Constant.SCREEN_GAME:
                view = new GameWorld(pContext);
                return view;
            case Constant.SCREEN_PAUSE:
                break;
        }

        return view;
    }

}
