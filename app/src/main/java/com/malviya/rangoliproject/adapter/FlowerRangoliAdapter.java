package com.malviya.rangoliproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.malviya.rangoliproject.R;
import com.malviya.rangoliproject.activity.FlowerRangoliFullScreenActivity;
import com.malviya.rangoliproject.constants.ConstantMessage;
import com.malviya.rangoliproject.data_model.Information;
import com.malviya.rangoliproject.utilies.Utilities;

import java.util.ArrayList;

/**
 * Created by Prafulla on 10/26/2016.
 */

public class FlowerRangoliAdapter extends RecyclerView.Adapter<FlowerRangoliAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Information> data;
    private LayoutInflater inflater;

    public FlowerRangoliAdapter(Context context, ArrayList<Information> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public FlowerRangoliAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.list_item_row, parent, false);

        FlowerRangoliAdapter.MyViewHolder holder = new FlowerRangoliAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(FlowerRangoliAdapter.MyViewHolder myViewHolder, final int position) {

        myViewHolder.textview.setText(data.get(position).title);
        Bitmap bitmap = Utilities.decodeSampledBitmapFromResource(context.getResources(), data.get(position).imageId);
        myViewHolder.imageView.setImageBitmap(bitmap);
        //final int currentPosition = position;
        final Information infoData = data.get(position);

        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.navigateToAnotherActivity(context, FlowerRangoliFullScreenActivity.class, position);

            }
        });

        myViewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String image_path = Utilities.saveImageOnExternalMemory(context, infoData);
                Utilities.shareImage(context, image_path, ConstantMessage.MESSAGE, ConstantMessage.HYPERLINK);
                return true;
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textview;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            textview = (TextView) itemView.findViewById(R.id.txv_row);
            imageView = (ImageView) itemView.findViewById(R.id.img_row);
        }
    }
}
