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


public class Data {
    public static ArrayList<Information> getData() {

        ArrayList<Information> data = new ArrayList<>();

        int[] images = {
                R.drawable.rangoli1,
                R.drawable.rangoli2,
                R.drawable.rangoli3,
                R.drawable.rangoli4,
                R.drawable.rangoli5,
                R.drawable.rangoli6,
                R.drawable.rangoli7,
                R.drawable.rangoli8,
                R.drawable.rangoli9,
                R.drawable.rangoli11,
                R.drawable.rangoli12,
                R.drawable.rangoli13,
                R.drawable.rangoli14,
                R.drawable.rangoli15,
                R.drawable.rangoli16
        };

        String[] Categories = {"Rangoli 1", "Rangoli 2", "Rangoli 3", "Rangoli 4", "Rangoli 5", "Rangoli 6", "Rangoli 7",
                "Rangoli 8", "Rangoli 9", "Rangoli 11", "Rangoli 12",
                "DeRangolier 13", "Rangoli 14", "Rangoli 15", "Rangoli 16"
        };

        for (int i = 0; i < images.length; i++) {

            Information current = new Information();
            current.title = Categories[i];
            current.imageId = images[i];
            data.add(current);
        }

        return data;
    }

}
