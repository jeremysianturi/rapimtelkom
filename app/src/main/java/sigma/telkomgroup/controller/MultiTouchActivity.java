package sigma.telkomgroup.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.InputStream;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;

/**
 * Created by biting on 20/04/16.
 */
public class MultiTouchActivity extends AppCompatActivity {

    private ImageView imageDetail;
    private Toolbar toolbar;
    TouchImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_full_screen);
        img = new TouchImageView(this);
        //img.setImageResource(R.drawable.rapim_splash);
        //img.setMaxZoom(4f);

        showImage();
        showToolbar();
    }

    public void showImage() {
        String stringExtra = getIntent().getStringExtra(ConstantUtil.MEETING.TAG_IMAGE);
        Log.d("hasil string extra", stringExtra);
        //img.setImageResource(R.drawable.rapim_splash);
        //img.setMaxZoom(4f);
        //imageDetail = img;
        new DownloadImageTask(img).execute(stringExtra);
    }

    private void showToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent getIntent = getIntent();
        String title = getIntent.getStringExtra(ConstantUtil.VENUE.TAG_VENUE_TITLE);

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle(title);

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
