package com.malviya.rangoliproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.malviya.rangoliproject.R;
import com.malviya.rangoliproject.activity.FullScreenViewActivity;
import com.malviya.rangoliproject.data_model.Information;
import com.malviya.rangoliproject.utilies.Utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder> {

    Bitmap bm;
    File file, file1;
    ByteArrayInputStream byteArrayInputStream;
    ByteArrayOutputStream bytearrayoutputstream;
    FileOutputStream fileoutputstream;
    FileInputStream fileInputStream;
    private Context context;
    private ArrayList<Information> data;
    private LayoutInflater inflater;


    public MyCustomAdapter(Context context, ArrayList<Information> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    /**
     * @return Number of bytes available on External storage
     */
    public static long getAvailableSpaceInBytes() {
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();

        return availableSpace;
    }

    /**
     * @return Number of kilo bytes available on External storage
     */
    public static long getAvailableSpaceInKB() {
        final long SIZE_KB = 1024L;
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return availableSpace / SIZE_KB;
    }

    /**
     * @return Number of Mega bytes available on External storage
     */
    public static long getAvailableSpaceInMB() {
        final long SIZE_KB = 1024L;
        final long SIZE_MB = SIZE_KB * SIZE_KB;
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return availableSpace / SIZE_MB;
    }

    /**
     * @return Number of gega bytes available on External storage
     */
    public static long getAvailableSpaceInGB() {
        final long SIZE_KB = 1024L;
        final long SIZE_GB = SIZE_KB * SIZE_KB * SIZE_KB;
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return availableSpace / SIZE_GB;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.list_item_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {

        myViewHolder.textview.setText(data.get(position).title);

        Bitmap bitmap = Utilities.decodeSampledBitmapFromResource(context.getResources(), data.get(position).imageId);
        myViewHolder.imageView.setImageBitmap(bitmap);

        //final int currentPosition = position;
        final Information infoData = data.get(position);

        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //    Toast.makeText(context, "OnClick Called at position " + position, Toast.LENGTH_SHORT).show();
                Intent image = new Intent(context, FullScreenViewActivity.class);
                image.putExtra("position", position);
                context.startActivity(image);
                //addItem(currentPosition, infoData);
            }
        });


        myViewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                getShareData("Happy Diwali", infoData.title, infoData.imageId);
                /*//store image in external memory
                bytearrayoutputstream = new ByteArrayOutputStream();

                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.rangoli1);
                bm.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream);

                if (isExternalStorageWritable(Environment.getExternalStorageState())) {
                   // file = new File(Environment.getExternalStorageDirectory() + "/" + "Rangoli_Archies");
                    file = new File(Environment.getExternalStorageDirectory() + "/" + infoData.title);
                    try {
                                if(file.exists()) {
                                    if (file.createNewFile()) {
                                        fileoutputstream = new FileOutputStream(file);
                                        fileoutputstream.write(bytearrayoutputstream.toByteArray());
                                        fileoutputstream.flush();
                                        fileoutputstream.close();
                                        Toast.makeText(context, "Image Saved Successfully", Toast.LENGTH_LONG).show();
                                        Toast.makeText(context, "OnLongClick Called at position " + bm, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(context, "Remaining Memory3: " + getAvailableSpaceInMB() + "MB", Toast.LENGTH_SHORT).show();
                                    }
                                }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (isExternalStorageWritable(Environment.getExternalStorageState())) {
                    file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),infoData.title);
                    try {
                        byteArrayInputStream = new ByteArrayInputStream(bytearrayoutputstream.toByteArray());
                        fileInputStream = new FileInputStream(file1);
                        int image_no= fileInputStream.read(bytearrayoutputstream.toByteArray());
                        Bitmap bmp = BitmapFactory.decodeStream(fileInputStream);
                        fileInputStream.close();
                        Toast.makeText(context, "image_no " + image_no, Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Bitmap " + bmp, Toast.LENGTH_SHORT).show();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                return true;
            }
        });

    }

    /**
     * Returns a share intent
     */
    private void getShareData(String subject, String shareText, int shareImg) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sharingIntent.setType("text/plain");
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

        //String imagePath = Environment.getExternalStorageDirectory()
        //  + "/" + shareImg;
        // File imageFileToShare = new File(imagePath);
        Uri uri = Uri.parse("android.resource://com.malviya.rangoliproject/drawable/" + R.drawable.rangoli1);
        //  Uri uri = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.drawable.rangoli1);
        // Toast.makeText(context, "OnLongClick Called at position " + position, Toast.LENGTH_SHORT).show();

        // Uri uri = Uri.fromFile(imageFileToShare);
        Toast.makeText(context, "Image No: " + uri.getPath() + ".jpg", Toast.LENGTH_SHORT).show();
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri.getPath() + ".jpg");

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //******************

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(Information infoData) {

        int currPosition = data.indexOf(infoData);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, Information infoData) {

        data.add(position, infoData);
        notifyItemInserted(position);
    }

    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable(String state) {
        return Environment.MEDIA_MOUNTED.equals(state);
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
    //******************


}
