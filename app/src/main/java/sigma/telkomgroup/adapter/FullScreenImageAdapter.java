//package sigma.telkomgroup.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//
//import java.io.File;
//import java.util.ArrayList;
//
//import sigma.telkomgroup.R;
//import sigma.telkomgroup.controller.TouchImageView;
//
///**
// * Created by biting on 20/04/16.
// */
//public class FullScreenImageAdapter extends PagerAdapter {
//
//    private Activity _activity;
//    private ArrayList<String> _imagePaths;
//    private LayoutInflater inflater;
//    private String img;
//
//    // constructor
//    public FullScreenImageAdapter(Activity activity,
//                                  ArrayList<String> imagePaths) {
//        this._activity = activity;
//        this._imagePaths = imagePaths;
//    }
//
//    @Override
//    public int getCount() {
//        return this._imagePaths.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == ((RelativeLayout) object);
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        TouchImageView imgDisplay;
//        //Button btnClose;
//
//        inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View viewLayout = inflater.inflate(R.layout.activity_full_screen, container, false);
//
//        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imageViewZoom);
//        //btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
//        Log.d("selected image", _imagePaths.get(position));
//        img = _imagePaths.get(position);
//        imgDisplay.setImageBitmap(bitmap);
//
//                File file= new File(img);//new File(Environment.getExternalStorageDirectory()+ arrPath[i]);
//                Log.d("File Name",file.getName());
//                _activity.finish();
//        // close button click event
//
//
//        ((ViewPager) container).addView(viewLayout);
//
//        return viewLayout;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        ((ViewPager) container).removeView((RelativeLayout) object);
//
//    }
//
//
//
//}