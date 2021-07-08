//package sigma.telkomgroup.controller;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.graphics.PointF;
//import android.graphics.Rect;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.ScaleGestureDetector;
//import android.view.View;
//import android.widget.ImageView;
//
//import java.io.InputStream;
//
//import sigma.telkomgroup.R;
//import sigma.telkomgroup.connection.ConstantUtil;
//
///**
// * Created by biting on 15/04/16.
// */
//public class ControllerZoom extends AppCompatActivity {
//
//    static final int NONE = 0;
//    static final int DRAG = 1;
//    static final int ZOOM = 2;
//    static final float MIN_ZOOM = 0.25f;
//    static final float MAX_ZOOM = 5f;
//    float matrixX = 0, matrixY = 0, width = 0, height = 0;
//    Matrix matrix = new Matrix();
//    Matrix savedMatrix = new Matrix();
//    PointF startPoint = new PointF();
//    PointF midPoint = new PointF();
//    float oldDist = 1f;
//    int mode = NONE;
//    ScaleGestureDetector mScaleDetector;
//    Context context;
//    private float[] matrixValues = new float[9];
//    private float dx, dy;
//    private Toolbar toolbar;
//    private ImageView imageDetail;
//
//    /**
//     * Called when the activity is first created.
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_full_screen);
//        imageDetail = (ImageView) findViewById(R.id.imageViewZoom);
//        showToolbar();
//        showImage();
///**
// * set on touch listner on image
// */
//
//        imageDetail.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//
//                DisplayMetrics displaymetrics = new DisplayMetrics();
//                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//
//                ImageView view = (ImageView) v;
//                switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                    case MotionEvent.ACTION_DOWN:
//                        savedMatrix.set(matrix);
//                        startPoint.set(event.getX(), event.getY());
//                        mode = DRAG;
//                        break;
//                    case MotionEvent.ACTION_POINTER_DOWN:
//                        oldDist = spacing(event);
//                        if (oldDist > 10f) {
//                            savedMatrix.set(matrix);
//                            midPoint(midPoint, event);
//                            mode = ZOOM;
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_POINTER_UP:
//                        mode = NONE;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (mode == DRAG) {
//                            matrix.set(savedMatrix);
//
//                            matrix.getValues(matrixValues);
//                            matrixX = matrixValues[2];
//                            matrixY = matrixValues[5];
//                            width = matrixValues[0] * (view.getDrawable()
//                                    .getIntrinsicWidth());
//                            height = matrixValues[4] * (view.getDrawable()
//                                    .getIntrinsicHeight());
//
//                            dx = event.getX() - startPoint.x;
//                            dy = event.getY() - startPoint.y;
//
//                            //if image will go outside left bound
//                            if (matrixX + dx < 0) {
//                                dx = -matrixX;
//                            }
//                            //if image will go outside right bound
//                            if (matrixX + dx + width > view.getWidth()) {
//                                dx = view.getWidth() - matrixX - width;
//                            }
//                            //if image will go oustside top bound
//                            if (matrixY + dy < 0) {
//                                dy = -matrixY;
//                            }
//                            //if image will go outside bottom bound
//                            if (matrixY + dy + height > view.getHeight()) {
//                                dy = view.getHeight() - matrixY - height;
//                            }
//                            matrix.postTranslate(dx, dy);
//                        } else if (mode == ZOOM) {
//                            Float newDist = spacing(event);
//                            if (newDist > 10f) {
//                                matrix.set(savedMatrix);
//                                float scale = newDist / oldDist;
//                                float[] values = new float[9];
//                                matrix.getValues(values);
//                                float currentScale = values[Matrix.MSCALE_X];
//                                if (scale * currentScale > MAX_ZOOM)
//                                    scale = MAX_ZOOM / currentScale;
//                                else if (scale * currentScale < MIN_ZOOM)
//                                    scale = MIN_ZOOM / currentScale;
//                                matrix.postScale(scale, scale, midPoint.x, midPoint.y);
//                            }
//                        }
//                        break;
//                }
//                limitZoom(matrix);
//                //limitDrag(matrix);
//                view.setImageMatrix(matrix);
//                return true;
//            }
//
//            private void limitZoom(Matrix m) {
//
//                float[] values = new float[9];
//                m.getValues(values);
//                float scaleX = values[Matrix.MSCALE_X];
//                float scaleY = values[Matrix.MSCALE_Y];
//                if (scaleX > MAX_ZOOM) {
//                    scaleX = MAX_ZOOM;
//                } else if (scaleX < MIN_ZOOM) {
//                    scaleX = MIN_ZOOM;
//                }
//
//                if (scaleY > MAX_ZOOM) {
//                    scaleY = MAX_ZOOM;
//                } else if (scaleY < MIN_ZOOM) {
//                    scaleY = MIN_ZOOM;
//                }
//
//                values[Matrix.MSCALE_X] = scaleX;
//                values[Matrix.MSCALE_Y] = scaleY;
//                m.setValues(values);
//            }
//
//            private void limitDrag(Matrix m) {
//                float[] values = new float[9];
//                m.getValues(values);
//                float transX = values[Matrix.MTRANS_X];
//                float transY = values[Matrix.MTRANS_Y];
//                float scaleX = values[Matrix.MSCALE_X];
//                float scaleY = values[Matrix.MSCALE_Y];
//
//                //ImageView iv = (ImageView) findViewById(R.id.imageViewZoom);
//                Rect bounds = imageDetail.getDrawable().getBounds();
//                int viewWidth = getResources().getDisplayMetrics().widthPixels;
//                int viewHeight = getResources().getDisplayMetrics().heightPixels;
//
//                int width = bounds.right - bounds.left;
//                int height = bounds.bottom - bounds.top;
//
//                //default 20
//
//                float minX = (-width + viewWidth) * scaleX;
//                float minY = (-height + viewHeight) * scaleY;
//
//                if (transX > (minX - 20)) {
//                    transX = minX - 20;
//                } else if (transX < minX) {
//                    transX = minX;
//                }
//
//                //80
//                if (transY > (minY - 20)) {
//                    transY = minY - 20;
//                } else if (transY < minY) {
//                    transY = minY;
//                }
//
//                values[Matrix.MTRANS_X] = transX;
//                values[Matrix.MTRANS_Y] = transY;
//                m.setValues(values);
//
//            }
//
//
//            @SuppressLint("FloatMath")
//            private float spacing(MotionEvent event) {
//                float x = event.getX(0) - event.getX(1);
//                float y = event.getY(0) - event.getY(1);
//                return (float) Math.sqrt(x * x + y * y);
//            }
//
//            private void midPoint(PointF point, MotionEvent event) {
//                float x = event.getX(0) + event.getX(1);
//                float y = event.getY(0) + event.getY(1);
//                point.set(x / 2, y / 2);
//            }
//
//        });
//    }
//
//    public void showImage() {
//        String stringExtra = getIntent().getStringExtra(ConstantUtil.MEETING.TAG_IMAGE);
//
//        imageDetail.setImageResource(R.drawable.loading);
//        new DownloadImageTask(imageDetail).execute(stringExtra);
//    }
//
//    private void showToolbar() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        Intent getIntent = getIntent();
//        String title = getIntent.getStringExtra(ConstantUtil.VENUE.TAG_VENUE_TITLE);
//
//        toolbar.inflateMenu(R.menu.menu_main);
//        toolbar.setTitle(title);
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        onBackPressed();
//        return super.onOptionsItemSelected(item);
//    }
//
//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }
//}
