package sigma.telkomgroup.controller;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import sigma.telkomgroup.BuildConfig;
import sigma.telkomgroup.R;

/**
 * Created by biting on 25/07/16.
 */
public class ControllerAbout extends AppCompatActivity {

    Typeface font,fontbold;
    private TextView textView,textView2;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //        TextView
        font = Typeface.createFromAsset(ControllerAbout.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerAbout.this.getAssets(), "fonts/Montserrat-SemiBold.otf");


//        textView1 = (TextView) findViewById(R.id.nama);
//        textView1.setTypeface(fontbold);
        textView2 = (TextView) findViewById(R.id.powered);
        textView2.setTypeface(fontbold);


        textView = (TextView) findViewById(R.id.version);
        textView.setText("Version "+ BuildConfig.VERSION_NAME);
        textView.setTypeface(font);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("About");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
