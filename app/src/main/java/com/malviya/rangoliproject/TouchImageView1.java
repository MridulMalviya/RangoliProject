package com.malviya.rangoliproject;/*
package com.malviya.rangoliproject;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

public class TouchImageView extends ImageView {

	Matrix matrix;

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remember some things for zooming
	PointF last = new PointF();
	PointF start = new PointF();
	float minScale = 1f;
	float maxScale = 3f;
	float[] m;

	int viewWidth, viewHeight;
	static final int CLICK = 3;
	float saveScale = 1f;
	protected float origWidth, origHeight;
	int oldMeasuredWidth, oldMeasuredHeight;

	ScaleGestureDetector mScaleDetector;

	Context context;

	public TouchImageView(Context context) {
		super(context);
		sharedConstructing(context);
	}

	public TouchImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		sharedConstructing(context);
	}

	private void sharedConstructing(Context context) {
		super.setClickable(true);
		this.context = context;
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
		matrix = new Matrix();
		m = new float[9];
		setImageMatrix(matrix);
		setScaleType(ScaleType.MATRIX);

						setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mScaleDetector.onTouchEvent(event);
				PointF curr = new PointF(event.getX(), event.getY());

				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					last.set(curr);
					start.set(last);
					mode = DRAG;
					break;

				case MotionEvent.ACTION_MOVE:
					if (mode == DRAG) {
						float deltaX = curr.x - last.x;
						float deltaY = curr.y - last.y;
						float fixTransX = getFixDragTrans(deltaX, viewWidth,
								origWidth * saveScale);
						float fixTransY = getFixDragTrans(deltaY, viewHeight,
								origHeight * saveScale);
						matrix.postTranslate(fixTransX, fixTransY);
						fixTrans();
						last.set(curr.x, curr.y);
					}
					break;

				case MotionEvent.ACTION_UP:
					mode = NONE;
					int xDiff = (int) Math.abs(curr.x - start.x);
					int yDiff = (int) Math.abs(curr.y - start.y);
					if (xDiff < CLICK && yDiff < CLICK)
						performClick();
					break;

				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					break;
				}

				setImageMatrix(matrix);
				invalidate();
				return true; // indicate event was handled
			}

		});
	}

	public void setMaxZoom(float x) {
		maxScale = x;
	}

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			mode = ZOOM;
			return true;
		}

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float mScaleFactor = detector.getScaleFactor();
			float origScale = saveScale;
			saveScale *= mScaleFactor;
			if (saveScale > maxScale) {
				saveScale = maxScale;
				mScaleFactor = maxScale / origScale;
			} else if (saveScale < minScale) {
				saveScale = minScale;
				mScaleFactor = minScale / origScale;
			}

			if (origWidth * saveScale <= viewWidth
					|| origHeight * saveScale <= viewHeight)
				matrix.postScale(mScaleFactor, mScaleFactor, viewWidth / 2,
						viewHeight / 2);
			else
				matrix.postScale(mScaleFactor, mScaleFactor,
						detector.getFocusX(), detector.getFocusY());

			fixTrans();
			return true;
		}
	}

	void fixTrans() {
		matrix.getValues(m);
		float transX = m[Matrix.MTRANS_X];
		float transY = m[Matrix.MTRANS_Y];

		float fixTransX = getFixTrans(transX, viewWidth, origWidth * saveScale);
		float fixTransY = getFixTrans(transY, viewHeight, origHeight
				* saveScale);

		if (fixTransX != 0 || fixTransY != 0)
			matrix.postTranslate(fixTransX, fixTransY);
	}

	float getFixTrans(float trans, float viewSize, float contentSize) {
		float minTrans, maxTrans;

		if (contentSize <= viewSize) {
			minTrans = 0;
			maxTrans = viewSize - contentSize;
		} else {
			minTrans = viewSize - contentSize;
			maxTrans = 0;
		}

		if (trans < minTrans)
			return -trans + minTrans;
		if (trans > maxTrans)
			return -trans + maxTrans;
		return 0;
	}

	float getFixDragTrans(float delta, float viewSize, float contentSize) {
		if (contentSize <= viewSize) {
			return 0;
		}
		return delta;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		viewWidth = MeasureSpec.getSize(widthMeasureSpec);
		viewHeight = MeasureSpec.getSize(heightMeasureSpec);

		//
		// Rescales image on rotation
		//
		if (oldMeasuredHeight == viewWidth && oldMeasuredHeight == viewHeight
				|| viewWidth == 0 || viewHeight == 0)
			return;
		oldMeasuredHeight = viewHeight;
		oldMeasuredWidth = viewWidth;

		if (saveScale == 1) {
			// Fit to screen.
			float scale;

			Drawable drawable = getDrawable();
			if (drawable == null || drawable.getIntrinsicWidth() == 0
					|| drawable.getIntrinsicHeight() == 0)
				return;
			int bmWidth = drawable.getIntrinsicWidth();
			int bmHeight = drawable.getIntrinsicHeight();

			Log.d("bmSize", "bmWidth: " + bmWidth + " bmHeight : " + bmHeight);

			float scaleX = (float) viewWidth / (float) bmWidth;
			float scaleY = (float) viewHeight / (float) bmHeight;
			scale = Math.min(scaleX, scaleY);
			matrix.setScale(scale, scale);

			// Center the image
			float redundantYSpace = (float) viewHeight
					- (scale * (float) bmHeight);
			float redundantXSpace = (float) viewWidth
					- (scale * (float) bmWidth);
			redundantYSpace /= (float) 2;
			redundantXSpace /= (float) 2;

			matrix.postTranslate(redundantXSpace, redundantYSpace);

			origWidth = viewWidth - 2 * redundantXSpace;
			origHeight = viewHeight - 2 * redundantYSpace;
			setImageMatrix(matrix);
		}
		fixTrans();
	}
}*/


/* private void saveImage() {
        OutputStream outputStream;
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.diwali_special_5);
        Log.i("Directory","bitmap"+bitmap);
        String  filePath = Environment.getExternalStorageDirectory().getPath();
        Log.i("Directory","file path"+filePath);
        File dir = new File(filePath +"/RangoliApp");
        Log.i("Directory","file dir"+dir);
        if(!dir.exists()){
            if(dir.mkdirs()){
                Log.i("Directory","RangoliApp Directory created!!");
            }
            else{
                Log.i("Directory","RangoliApp Directory not created!!");
            }
        }
        else
        {
            Log.i("Directory","RangoliApp Directory is already created!!");
            File file =new File(dir,"sandesh.jpg");
            File pic = Environment.getExternalStoragePublicDirectory(filePath);

            Uri uri=null;
            String URL="http://www.imnepal.com/wp-content/uploads/2015/10/cultural-diwali-Candle-greeting-cards-wishing-wallpapers-images-pictures.jpg";
           // uri =Uri.parse(dir.toString()+"/"+"sandesh.jpg");
            uri =Uri.parse(URL);

            Log.i("Directory","Uri Path"+uri);
            Intent in = new Intent(Intent.ACTION_SEND);
            in.setType("image/*");
            in.putExtra(Intent.EXTRA_STREAM,String.valueOf(uri));
            startActivity(Intent.createChooser(in, "Share via"));

            try{
                outputStream=new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,400,outputStream);
                outputStream.flush();
                outputStream.close();


            }catch (Exception e){
                e.getMessage();
            }

        }

    }
*/

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

/*
* */

/*  @Override
    public int getItemCount() {
        return data.size();
    }

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


*/

/*
    /* Checks if external storage is available for read and write */
/*private boolean isExternalStorageWritable(String state) {
        return Environment.MEDIA_MOUNTED.equals(state);
        }
        */

/*
* /* case R.id.linearViewVertical:
                linearViewVertical();
                break;*/
          /*  case R.id.gridView:
               gridViewLayout();
                break;*/
         /*   case R.id.staggeredViewHorizontal:
                staggeredViewHorizontal();
                break;
            case R.id.staggeredViewVertical:
                staggeredViewVertical();
*/

/* /**
     * Returns a share intent
     */
/*
private void getShareData(String subject, String shareText, String shareImg) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sharingIntent.setType("text/plain");
        sharingIntent.setType("image*/
/*");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        if (shareImg != null) {
        String imagePath = Environment.getExternalStorageDirectory()
        + "/" + shareImg;
        File imageFileToShare = new File(imagePath);
        Uri uri = Uri.fromFile(imageFileToShare);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        }

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }

private void staggeredViewVertical() {
        StaggeredGridLayoutManager mStaggeredVerticalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
        recyclerView.setLayoutManager(mStaggeredVerticalLayoutManager);
        }

private void staggeredViewHorizontal() {
        StaggeredGridLayoutManager mStaggeredHorizontalLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL); // (int spanCount, int orientation)
        mStaggeredHorizontalLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(mStaggeredHorizontalLayoutManager);
        }

private void gridViewLayout() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2); // (Context context, int spanCount)
        mGridLayoutManager.setSmoothScrollbarEnabled(true);
        mGridLayoutManager.setAutoMeasureEnabled(true);
        mGridLayoutManager.supportsPredictiveItemAnimations();
        recyclerView.setLayoutManager(mGridLayoutManager);
        }

private void linearViewVertical() {
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this); // (Context context)
        mLinearLayoutManagerVertical.setSmoothScrollbarEnabled(true);
        mLinearLayoutManagerVertical.setAutoMeasureEnabled(true);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
        }

private void linearViewHorizontal() {
        LinearLayoutManager mLinearLayoutManagerHorizontal = new LinearLayoutManager(this); // (Context context)
        mLinearLayoutManagerHorizontal.setSmoothScrollbarEnabled(true);
        mLinearLayoutManagerHorizontal.setAutoMeasureEnabled(true);
        mLinearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerHorizontal);
        }
*/
