/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.malviya.rangoliproject.image_data_store;

import com.malviya.rangoliproject.data_model.Information;
import com.malviya.rangoliproject.R;

import java.util.ArrayList;

/**
 * Created by Prafulla on 10/26/2016.
 */

public class DataFlowerRangoli {
    public static ArrayList<Information> getData() {

        ArrayList<Information> data1 = new ArrayList<>();

        int[] images = {
                R.drawable.flower_r1,
                R.drawable.flower_r2,
                R.drawable.flower_r3,
                R.drawable.flower_r4,
                R.drawable.flower_r5,
                R.drawable.flower_r6,
                R.drawable.flower_r7,
                R.drawable.flower_r8,
                R.drawable.flower_r9,
                R.drawable.flower_r10,
                R.drawable.flower_r11,
                R.drawable.flower_r12,
                R.drawable.flower_r13,
                R.drawable.flower_r14,
                R.drawable.flower_r15,
                R.drawable.flower_r16
        };

        String[] Categories = {"Rangoli 1", "Rangoli 2", "Rangoli 3", "Rangoli 4", "Rangoli 5", "Rangoli 6", "Rangoli 7",
                "Rangoli 8", "Rangoli 9", "Rangoli 10", "Rangoli 11", "Rangoli 12",
                "Rangoli 13", "Rangoli 14", "Rangoli 15", "Rangoli 16"
        };

        for (int i = 0; i < images.length; i++) {

            Information current = new Information();
            current.title = Categories[i];
            current.imageId = images[i];

            data1.add(current);
        }

        return data1;
    }
}
