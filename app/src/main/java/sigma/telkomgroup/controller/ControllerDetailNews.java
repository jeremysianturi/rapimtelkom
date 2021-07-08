package sigma.telkomgroup.controller;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;

/**
 * Created by biting on 21/03/16.
 */
public class ControllerDetailNews extends AppCompatActivity {

    Typeface font,fontbold;
    private Toolbar toolbar;
    private TextView judul;
    private TextView isi;
    private TextView tgl;
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        showDetail();
    }

//    private void displayFirebaseRegId() {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        String regId = pref.getString("regId", null);
//
//        Log.e(TAG, "Firebase reg id: " + regId);
//
//        if (!TextUtils.isEmpty(regId))
//            tgl.setText("Firebase Reg Id: " + regId);
//        else
//            tgl.setText("Firebase Reg Id is not received yet!");
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // register GCM registration complete receiver
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(Config.REGISTRATION_COMPLETE));
//
//        // register new push message receiver
//        // by doing this, the activity will be notified each time a new message arrives
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(Config.PUSH_NOTIFICATION));
//
//        // clear the notification area when the app is opened
//        NotificationUtils.clearNotifications(getApplicationContext());
//    }

//    @Override
//    protected void onPause() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
//        super.onPause();
//    }

    public void showDetail(){

        //        TextView
        font = Typeface.createFromAsset(ControllerDetailNews.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerDetailNews.this.getAssets(), "fonts/Montserrat-SemiBold.otf");
        judul = (TextView) findViewById(R.id.tvTitle);
        judul.setTypeface(fontbold);
        isi = (TextView) findViewById(R.id.textDetailNewsContent);
        isi.setMovementMethod(LinkMovementMethod.getInstance());
        isi.setTypeface(font);
        tgl = (TextView) findViewById(R.id.textDetailNewsDate);
        tgl.setTypeface(font);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
//        public String TAG_TITLE = "rundown";
//        public String TAG_ID = "id";
//        public String TAG_MEET_ID = "meeting_id";
//        public String TAG_NAME = "name";
//        public String TAG_DATE = "rundown_date";
//        public String TAG_TIME = "rundown_time";
//        public String TAG_DESC = "description";

        String title = intent.getStringExtra(ConstantUtil.NEWS.TAG_TITLE_CONTENT);
        String content = intent.getStringExtra(ConstantUtil.NEWS.TAG_CONTENT);
        String dates = intent.getStringExtra(ConstantUtil.NEWS.TAG_DATE);

        Calendar cal = Calendar.getInstance();
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dates);
            cal.setTime(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String fdate = new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime());

        judul.setText(title);
        isi.setText(content);
        tgl.setText(fdate);

//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                // checking for type intent filter
//                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
//                    // gcm successfully registered
//                    // now subscribe to `global` topic to receive app wide notifications
//                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
//
//                    displayFirebaseRegId();
//
//                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
//                    // new push notification is received
//
//                    String message = intent.getStringExtra("message");
//
//                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
//
//                    txtMessage.setText(message);
//                }
//            }
//        };
//
//        displayFirebaseRegId();

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Detail News");

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
