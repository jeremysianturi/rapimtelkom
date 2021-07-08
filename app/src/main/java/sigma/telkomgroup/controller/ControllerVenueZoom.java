package sigma.telkomgroup.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by biting on 15/04/16.
 */
public class ControllerVenueZoom extends AppCompatActivity {

    Typeface font,fontbold;
    private Toolbar toolbar;
    private ImageView imageDetail;
//    CustomImageView mImageView;
    private PhotoViewAttacher attacher;
//    TextView tvTitle, tvCapt;
private ZoomageView demoView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        String stringExtra = getIntent().getStringExtra(ConstantUtil.VENUE.TAG_IMAGE);

        demoView = findViewById(R.id.myZoomageView);
        downloadImage(this, stringExtra, demoView);
//         mImageView = (CustomImageView)findViewById(R.id.customImageVIew1);
//        mImage.setBitmap(your bitmap);
//        imageDetail = (ImageView) findViewById(R.id.imageViewZoom);
//        tvTitle = (TextView) findViewById(R.id.tvTitle);
//        tvCapt = (TextView) findViewById(R.id.tvCapt);
//        attacher = new PhotoViewAttacher(imageDetail);
//        attacher.update();
//        showImage();
        showToolbar();
    }
    private void downloadImage(Context context, String url, ImageView image) {
        Picasso.get()
                .load(url)
                .error(R.drawable.logo_rapim)
                .into(image);
    }
//    public void showImage() {
////        imageDetail.setImageResource(R.drawable.loading);
//        new DownloadImageTask(mImageView).execute(stringExtra);
//    }

    private void showToolbar() {

        font = Typeface.createFromAsset(ControllerVenueZoom.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerVenueZoom.this.getAssets(), "fonts/Montserrat-SemiBold.otf");
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        Intent getIntent = getIntent();
        String title = getIntent.getStringExtra(ConstantUtil.VENUE.TAG_VENUE_TITLE);
        String capt = getIntent.getStringExtra(ConstantUtil.VENUE.TAG_VENUE_CAPTION);
        TextView judul = (TextView) findViewById(R.id.zoom);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvCapt = (TextView) findViewById(R.id.tvCapt);
        judul.setTypeface(fontbold);
        tvTitle.setTypeface(fontbold);
        tvCapt.setTypeface(fontbold);
        tvTitle.setText(title);
        tvCapt.setText(capt);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Detail Venue");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
