/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.malviya.rangoliproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malviya.rangoliproject.R;
import com.malviya.rangoliproject.data_model.Information;
import com.malviya.rangoliproject.utilies.TouchImageView;
import com.malviya.rangoliproject.utilies.Utilities;

import java.util.ArrayList;

public class FullScreenImageAdapter extends PagerAdapter implements View.OnClickListener, View.OnLongClickListener {

    private Activity _activity;
    private ArrayList<Information> _imagePaths;
    private LayoutInflater inflater;


    // constructor
    public FullScreenImageAdapter(Activity activity, ArrayList<Information> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
        inflater = LayoutInflater.from(_activity);
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TouchImageView imgDisplay;
        final TouchImageView imgDisplay1;
        TextView mTitle;
        Button btnClose;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container, false);

        //  imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        imgDisplay1 = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        mTitle = (TextView) viewLayout.findViewById(R.id.title_image);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
        mTitle.setText(_imagePaths.get(position).title);
        //	imgDisplay1.setImageResource(_imagePaths.get(position).imageId);
        Bitmap bitmap = Utilities.decodeSampledBitmapFromResource(_activity.getResources(), _imagePaths.get(position).imageId);
        imgDisplay1.setImageBitmap(bitmap);

        // close button click event
        btnClose.setOnClickListener(this);
        container.addView(viewLayout);
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClose:
                _activity.finish();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
