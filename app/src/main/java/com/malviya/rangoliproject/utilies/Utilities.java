/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.malviya.rangoliproject.utilies;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;

import com.malviya.rangoliproject.R;
import com.malviya.rangoliproject.data_model.Information;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Prafulla on 10/27/2016.
 */

public class Utilities {

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 400, 400);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * get uri to any resource type
     *
     * @param context - context
     * @param resId   - resource id
     * @return - Uri to resource by given id
     * @throws Resources.NotFoundException if the given ID does not exist.
     */
    public static final Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException {
        /** Return a Resources instance for your application's package. */
        Resources res = context.getResources();
        /**
         * Creates a Uri which parses the given encoded URI string.
         * @param uriString an RFC 2396-compliant, encoded URI
         * @throws NullPointerException if uriString is null
         * @return Uri for this given uri string
         */
        Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
        /** return uri */
        return resUri;
    }

    public static String saveImageOnExternalMemory(final Context context, final Information infoData) {
        //create directory if not exist
        final File dir = new File(Environment.getExternalStorageDirectory() + File.separator + context.getResources().getString(R.string.folder_name));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), infoData.imageId);
        final File output = new File(dir, infoData.title + ".jpg");
        OutputStream os = null;

        try {
            os = new FileOutputStream(output);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

            final Handler handler = new Handler();

            //this code will scan the image so that it will appear in your gallery when you open next time
            MediaScannerConnection.scanFile(context, new String[]{output.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(final String path, Uri uri) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Toast.makeText(context, context.getResources().getString(R.string.str_save_image_text) + dir.getPath()+infoData.title+".jpg", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
            );
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return output.getPath();
    }

    public static void shareImage(Context context, String path, String Message, String Link) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, Message + "\n\nLink : " + Link);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "Share image via:"));

    }

    public static void shareImageWithWhatsApp(Context context, String path) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
        share.setPackage("com.whatsapp");//package name of the app
        context.startActivity(Intent.createChooser(share, "Share Image"));
    }

    public static void shareWishToAll(final Context context, int imageID, String msg, String hyperlink) {

        final File dir = new File(Environment.getExternalStorageDirectory() + File.separator + context.getResources().getString(R.string.folder_name));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Bitmap bit = BitmapFactory.decodeResource(context.getResources(), imageID);
        final File output = new File(dir, imageID + ".jpg");
        OutputStream ostrm = null;
        try {
            ostrm = new FileOutputStream(output);
            bit.compress(Bitmap.CompressFormat.JPEG, 100, ostrm);
            ostrm.flush();
            ostrm.close();

            final Handler handler = new Handler();
            //this code will scan the image so that it will appear in your gallery when you open next time
            MediaScannerConnection.scanFile(context, new String[]{output.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(final String path, Uri uri) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //  Toast.makeText(context, context.getResources().getString(R.string.str_save_image_text) + dir.getPath() , Toast.LENGTH_LONG).show();
                                }

                            });
                        }

                    }
            );
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Utilities.shareImage(context, output.getPath(), msg, hyperlink);
    }

    public static void navigateToAnotherActivity(Context context, Class navigateClass, int position) {
        Intent image = new Intent(context, navigateClass);
        image.putExtra("position", position);
        context.startActivity(image);
    }

}
