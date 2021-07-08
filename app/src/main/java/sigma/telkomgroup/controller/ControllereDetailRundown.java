package sigma.telkomgroup.controller;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;

public class ControllereDetailRundown extends AppCompatActivity {

    Typeface font,fontbold;
    private Toolbar toolbar;
    private TextView judul;
    private TextView isi;
    private TextView tgl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controllere_detail_rundown);
        showDetail();

    }

    public void showDetail(){

        //        TextView
        font = Typeface.createFromAsset(ControllereDetailRundown.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllereDetailRundown.this.getAssets(), "fonts/Montserrat-SemiBold.otf");
        judul = (TextView) findViewById(R.id.tvTitle);
        judul.setTypeface(fontbold);
        isi = (TextView) findViewById(R.id.tvDetailRundown);
        isi.setTypeface(font);
        tgl = (TextView) findViewById(R.id.tvHour);
        tgl.setTypeface(font);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();

        String title = intent.getStringExtra(ConstantUtil.RUNDOWN.TAG_NAME);
        String content = intent.getStringExtra(ConstantUtil.RUNDOWN.TAG_DESC);
        String dates = intent.getStringExtra(ConstantUtil.RUNDOWN.TAG_TIME);

//        Calendar cal = Calendar.getInstance();
//        try {
//            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dates);
//            cal.setTime(d);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        String fdate = new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime());

        judul.setText(title);
        isi.setText(content);
        tgl.setText(dates);

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Rundown Details");

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
