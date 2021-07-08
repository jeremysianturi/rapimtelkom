package sigma.telkomgroup.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.model.ModelBanner;
import sigma.telkomgroup.model.ModelNews;
import sigma.telkomgroup.utils.AppSingleton;
import sigma.telkomgroup.utils.SessionManager;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

/**
 * Created by biting on 22/07/16.
 */
public class ControllerHome extends AppCompatActivity {

    public static final String ALERT_DLG = "MyPrefsFile";

    Typeface font,fontbold;
    private boolean doubleBackToExitPressedOnce = false;
    private LinearLayout btn_1;
    private LinearLayout btn_2;
    private LinearLayout btn_3;
    private LinearLayout btn_4;
    private LinearLayout btn_5;
    private LinearLayout btn_6;
    private LinearLayout btn_7;
    private LinearLayout btn_8;

    private Toolbar mToolbar;
    private BadgeView badgeView;
    private dbConnection connection;
    private ModelNews model;
    private SessionManager session;
    private HashMap<String, String> user;
    private String user_id;
    private String jumlah;

    private List<ModelNews> jumList;
//    private ViewFlipper viewFlipper;
    private ModelBanner modelBanner;
    private ArrayList<ModelBanner> listBanner;
    private ArrayList<String> listGambar;
    private TextView tvGreeting, tvName;
    private String gambar, name, nik, password;
    private float lastX;
    Calendar calendar = Calendar.getInstance();
    String monthResult = null, weekResult = null, paramMonth = null;
//    private ViewFlipper viewFlipper;
    private CarouselView carouselView;
    String[] lisImage;


    //    CarouselView carouselView;
    int[] sampleImages = {R.drawable.a, R.drawable.b};

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_2018);
//        viewFlipper = (ViewFlipper) findViewById(R.id.layoutSwitcher);
//        displayFirebaseRegId();
        session = new SessionManager(ControllerHome.this);
        user = session.getUserDetails();
        System.out.println("user value : " + user);

        user_id = user.get(ConstantUtil.LOGIN.TAG_ID);
        System.out.println("Check user_id value: " + user_id);

        name = user.get(ConstantUtil.LOGIN.TAG_NAME);
        System.out.println("Check name value: " + name);

        nik = user.get(ConstantUtil.LOGIN.TAG_USER_NAME);
        System.out.println("Check nik value: " + nik);

        password = user.get(ConstantUtil.LOGIN.TAG_PASS);
        System.out.println("Check password value: " + password);

        tvGreeting = (TextView) findViewById(R.id.tvGreeting);
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(name);
        carouselView = findViewById(R.id.carouselView);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.CAMERA,
                Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INSTALL_PACKAGES,
                Manifest.permission.REQUEST_INSTALL_PACKAGES,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_COARSE_LOCATION};


        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            tvGreeting.setText("Good Morning");
//            Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            tvGreeting.setText("Good Afternoon");
//            Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            tvGreeting.setText("Good Evening");
//            Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            tvGreeting.setText("Good Night");
//            Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
        }

        System.out.println("Check password length: " + password.length());
        if (password.length()==10){

            String fourLastDgt = password.substring(6,10);
            System.out.println("Four last digit: "+ fourLastDgt);

            SharedPreferences settings = getSharedPreferences(ALERT_DLG, 0);
            boolean dialogShown = settings.getBoolean("dialogShown", false);

            if (!dialogShown && password.equals("Telkom" + fourLastDgt)) {

                new AlertDialog.Builder(ControllerHome.this)
                        .setMessage("Selamat Datang Peserta RAPIM TelkomGroup\n" +
                                "Silahkan untuk mengganti Password terlebih dahulu ?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ControllerHome.this,ControllerChangePassword.class);
                                startActivity(intent);
                            }
                        })
                        .show();

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("dialogShown", true);
                editor.commit();
            }
        }

        session = new SessionManager(ControllerHome.this);
        session.checkLogin();
        user = session.getUserDetails();
        user_id = user.get(ConstantUtil.LOGIN.TAG_ID);
//        viewFlipper = (ViewFlipper) findViewById(R.id.layoutSwitcher);

        showBanner();
//        carouselView = (CarouselView) findViewById(R.id.carouselView);
//        carouselView.setPageCount(sampleImages.length);
//        carouselView.setImageListener(imageListener);
//        CarouselView goto_details= (CarouselView) findViewById(R.id.carouselView);
        TextView tvDate = (TextView) findViewById(R.id.tvDate);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String dayResult = null;
        switch (day) {
            case Calendar.SUNDAY:
                dayResult = "Sunday";
//                weekResult = "Weekend";
                break;
            case Calendar.MONDAY:
                dayResult = "Monday";
//                weekResult = "Weekday";
                break;
            case Calendar.TUESDAY:
                dayResult = "Tuesday";
//                weekResult = "Weekday";
                break;
            case Calendar.WEDNESDAY:
                dayResult = "Wednesday";
//                weekResult = "Weekday";
                break;
            case Calendar.THURSDAY:
                dayResult = "Thursday";
//                weekResult = "Weekday";
                break;
            case Calendar.FRIDAY:
                dayResult = "Friday";
//                weekResult = "Weekday";
                break;
            case Calendar.SATURDAY:
                dayResult = "Saturday";
//                weekResult = "Weekend";
                break;
        }

        int a = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
                monthResult = "January";
                paramMonth = "01";
                break;
            case Calendar.FEBRUARY:
                monthResult = "February";
                paramMonth = "02";
                break;
            case Calendar.MARCH:
                monthResult = "March";
                paramMonth = "03";
                break;
            case Calendar.APRIL:
                monthResult = "April";
                paramMonth = "04";
                break;
            case Calendar.MAY:
                monthResult = "May";
                paramMonth = "05";
                break;
            case Calendar.JUNE:
                monthResult = "June";
                paramMonth = "06";
                break;
            case Calendar.JULY:
                monthResult = "July";
                paramMonth = "07";
                break;
            case Calendar.AUGUST:
                monthResult = "August";
                paramMonth = "08";
                break;
            case Calendar.SEPTEMBER:
                monthResult = "September";
                paramMonth = "09";
                break;
            case Calendar.OCTOBER:
                monthResult = "October";
                paramMonth = "10";
                break;
            case Calendar.NOVEMBER:
                monthResult = "November";
                paramMonth = "11";
                break;
            case Calendar.DECEMBER:
                monthResult = "December";
                paramMonth = "12";
                break;
        }
        tvDate.setText(dayResult+", "+a+" "+monthResult+" "+year);


//        goto_details.setImageClickListener(new ImageClickListener() {
//            @Override
//            public void onClick(int position) {
//                Intent i = new Intent(ControllerHome.this, DetailBannerActivity.class);
//                startActivity(i);
//            }
//        });

//        showBanner();
//        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent touchevent) {
//                switch (touchevent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        lastX = touchevent.getX();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        float currentX = touchevent.getX();
//                        // Handling left to right screen swap.
//                        if (lastX < currentX) {
//                            // If there aren't any other children, just break.
//                            if (viewFlipper.getDisplayedChild() == 0)
//                                break;
//                            // Next screen comes in from left.
//                            viewFlipper.setInAnimation(ControllerHome.this.getApplicationContext(), R.anim.slide_in_from_left);
//                            // Current screen goes out from right.
//                            viewFlipper.setOutAnimation(ControllerHome.this.getApplicationContext(), R.anim.slide_out_to_right);
//                            // Display next screen.
//                            viewFlipper.showNext();
//                        }
//
//                        // Handling right to left screen swap.
//                        if (lastX > currentX) {
//                            // If there is a child (to the left), kust break.
//                            if (viewFlipper.getDisplayedChild() == 1)
//                                break;
//                            // Next screen comes in from right.
//                            viewFlipper.setInAnimation(ControllerHome.this.getApplicationContext(), R.anim.slide_in_from_right);
//                            // Current screen goes out from left.
//                            viewFlipper.setOutAnimation(ControllerHome.this.getApplicationContext(), R.anim.slide_out_to_left);
//                            // Display previous screen.
//                            viewFlipper.showPrevious();
//                        }
//                        break;
//                }
//                return true;
//            }
//        });

//        carouselView = (CarouselView) findViewById(R.id.carouselView);
//        carouselView.setPageCount(sampleImages.length);
//        carouselView.setImageListener(imageListener);
//        CarouselView goto_details= (CarouselView) findViewById(R.id.carouselView);
//        goto_details.setImageClickListener(new ImageClickListener() {
//            @Override
//            public void onClick(int position) {
//                Intent i = new Intent(ControllerHome.this, DetailBannerActivity.class);
//                startActivity(i);
//            }
//        });


        btn_1 = (LinearLayout) findViewById(R.id.layout_1);
        btn_2 = (LinearLayout) findViewById(R.id.layout_2);
        btn_3 = (LinearLayout) findViewById(R.id.layout_3);
        btn_4 = (LinearLayout) findViewById(R.id.layout_4);
        btn_5 = (LinearLayout) findViewById(R.id.layout_5);
        btn_6 = (LinearLayout) findViewById(R.id.layout_6);
        btn_7 = (LinearLayout) findViewById(R.id.layout_7);
        btn_8 = (LinearLayout) findViewById(R.id.layout_8) ;

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsIntent();
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rundownIntent();
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                venueIntent();
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alretexit = new
                        AlertDialog.Builder(ControllerHome.this);
//                alretexit.setMessage("You're going to open LinkAja").setCancelable(false)
                alretexit.setMessage("You're going to open Qr Code").setCancelable(false)
                        .setPositiveButton("Yes",
                                new AlertDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        // untuk ke link aja
//                                        launchNewActivity(ControllerHome.this, "com.telkom.mwallet");

                                        // untuk scan qr code
                                        qrCode();
                                    }
                                })
                        .setNegativeButton("No",
                                new AlertDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                    }
                                });
                AlertDialog a = alretexit.create();
                a.show();
//                finish();
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentIntent();
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelIntent();
            }
        });
        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfieIntent();
            }
        });
        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
            }
        });

        System.out.println("ewknj "+FirebaseInstanceId.getInstance().getToken());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        ImageView image = (ImageView) findViewById(R.id.imageView2);
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new android.app.AlertDialog.Builder(ControllerHome.this).setMessage("Are you sure you want to exit?").setCancelable(true)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                session.logoutUser();
//                            }
//                        }).setNegativeButton("No", null).show();
//            }
//        });

//        TextView
        font = Typeface.createFromAsset(ControllerHome.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerHome.this.getAssets(), "fonts/Montserrat-SemiBold.otf");

        TextView news= (TextView) findViewById(R.id.text_news);
        news.setTypeface(fontbold);
        TextView rundown= (TextView) findViewById(R.id.text_rundown);
        rundown.setTypeface(fontbold);
        TextView content= (TextView) findViewById(R.id.text_content);
        content.setTypeface(fontbold);
        TextView presence= (TextView) findViewById(R.id.text_presence);
        presence.setTypeface(fontbold);
        TextView galleries= (TextView) findViewById(R.id.text_galleries);
        galleries.setTypeface(fontbold);
//        TextView feedback= (TextView) findViewById(R.id.text_feedback);
//        feedback.setTypeface(fontbold);
        TextView selfie= (TextView) findViewById(R.id.text_selfie);
        selfie.setTypeface(fontbold);
        TextView venue= (TextView) findViewById(R.id.text_venue);
        venue.setTypeface(fontbold);
        TextView about= (TextView) findViewById(R.id.text_about);
        about.setTypeface(fontbold);
        TextView juduls = (TextView) findViewById(R.id.name);
        juduls.setTypeface(fontbold);
        final TextView tvHour = (TextView) findViewById(R.id.tvHour);

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvHour.setText(new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);

//        //getting the toolbar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        //setting the title
//        toolbar.setTitle("");
//
//        //placing toolbar in place of actionbar
//        setSupportActionBar(toolbar);
    }

//    private void getBanner(String event_id) {
//        AndroidNetworking.get(ConstantUtils.URL.BANNER + "{event_id}")
//                .addPathParameter("event_id", event_id)
//                .setTag("Banner")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray(ConstantUtils.BANNER.banner);
//                            lisImage = new String[jsonArray.length()];
//                            for (int a = 0; a < jsonArray.length(); a++) {
//                                JSONObject object = jsonArray.getJSONObject(a);
//                                String id = object.getString(ConstantUtils.BANNER.event_id);
//                                String img = object.getString(ConstantUtils.BANNER.banner_image);
//                                String event = object.getString(ConstantUtils.BANNER.event_id);
//                                String url = object.getString(ConstantUtils.BANNER.banner_url);
//                                lisImage[a] = img;
//                            }
//
//                            ImageListener imageListener = new ImageListener() {
//                                @Override
//                                public void setImageForPosition(int position, ImageView imageView) {
//                                    Picasso.with(getApplicationContext())
//                                            .load(lisImage[position])
//                                            .error(R.drawable.icon_avatars)
//                                            .fit()
//                                            .into(imageView);
//                                }
//                            };
//
//                            carouselView.setImageListener(imageListener);
//                            carouselView.setPageCount(lisImage.length);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//
//                    }
//                });
//    }


    private void showBanner() {
        final String REQUEST_TAG = "get request banner";

        StringRequest request = new StringRequest(Request.Method.GET, ConstantUtil.URL.GET_BANNER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.BANNER.TAG_TITLE);
                            lisImage = new String[jsonArray.length()];
                            modelBanner = new ModelBanner();
                            listBanner = new ArrayList<ModelBanner>();
                            listGambar = new ArrayList<String>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String id = obj.getString(ConstantUtil.BANNER.TAG_ID);
                                String image = obj.getString(ConstantUtil.BANNER.TAG_NAME);
                                modelBanner = new ModelBanner(id, image);
                                listBanner.add(modelBanner);
                                gambar = image;
                                listGambar.add(image);
                                lisImage[i] = image;

                            }

                            ImageListener imageListener = new ImageListener() {
                                @Override
                                public void setImageForPosition(int position, ImageView imageView) {
                                    Picasso.get()
                                            .load(lisImage[position])
                                            .error(R.drawable.placeholder)
                                            .fit()
                                            .into(imageView);
                                }
                            };
                            carouselView.setImageListener(imageListener);
                            carouselView.setPageCount(lisImage.length);

//                            if (listGambar.size()>0) {
//                                //Log.d("panjang banner", listGambar.size() + "");
//                                for (int i = 0; i < listGambar.size(); i++) {
//                                    setFlipperImage(listGambar);
//                                }
//                            }

//                            if (!viewFlipper.isFlipping()) {
//                                if (listGambar.size() > 1) {
//                                    if (viewFlipper.getDisplayedChild() == 0) {
//                                        viewFlipper.setInAnimation(ControllerHome.this, R.anim.slide_in_from_right);
//                                        viewFlipper.setAutoStart(true);
//                                        viewFlipper.setFlipInterval(5000);
//                                        viewFlipper.showNext();
//                                        viewFlipper.startFlipping();
//                                    }
//                                }
//                            }

                        } catch (JSONException e) {
                            //e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ControllerHome.this.getApplicationContext(), "Periksa Koneksi Internet Anda", Toast.LENGTH_LONG).show();
                    }
                });
        // Adding JsonObject request to request queue
        AppSingleton.getInstance(ControllerHome.this.getApplicationContext()).addToRequestQueue(request, REQUEST_TAG);
    }

//    private void setFlipperImage(ArrayList<String> listGambar) {
//        for (int i = 0; i < listGambar.size(); i++) {
//            ImageView image = new ImageView(ControllerHome.this.getApplicationContext());
//            Picasso.get()
//                    .load(listGambar.get(i).toString())
//                    .error(R.drawable.no_image)
//                    .fit()
//                    .into(image);
//            viewFlipper.addView(image);
//        }
//    }

//    ImageListener imageListener = new ImageListener() {
//        @Override
//        public void setImageForPosition(int position, ImageView imageView) {
//            imageView.setImageResource(sampleImages[position]);
//        }
//    };

//    private void showBanner() {
//        final String REQUEST_TAG = "get request banner";
//
//        StringRequest request = new StringRequest(Request.Method.GET, ConstantUtil.URL.GET_BANNER,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObj = new JSONObject(response);
//                            JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.BANNER.TAG_TITLE);
//                            modelBanner = new ModelBanner();
//                            listBanner = new ArrayList<ModelBanner>();
//                            listGambar = new ArrayList<String>();
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject obj = jsonArray.getJSONObject(i);
//
//                                String id = obj.getString(ConstantUtil.BANNER.TAG_ID);
//                                String image = obj.getString(ConstantUtil.BANNER.TAG_NAME);
//                                modelBanner = new ModelBanner(id, image);
//                                listBanner.add(modelBanner);
//                                gambar = image;
//                                listGambar.add(image);
//                            }
//
//                            if (listGambar.size()>0) {
//                                //Log.d("panjang banner", listGambar.size() + "");
//                                for (int i = 0; i < listGambar.size(); i++) {
//                                    setFlipperImage(listGambar);
//                                }
//                            }
//
//                            if (!viewFlipper.isFlipping()) {
//                                if (listGambar.size() > 1) {
//                                    if (viewFlipper.getDisplayedChild() == 0) {
//                                        viewFlipper.setInAnimation(ControllerHome.this, R.anim.slide_in_from_right);
//                                        viewFlipper.setAutoStart(true);
//                                        viewFlipper.setFlipInterval(5000);
//                                        viewFlipper.showNext();
//                                        viewFlipper.startFlipping();
//                                    }
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            //e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ControllerHome.this.getApplicationContext(), "Periksa Koneksi Internet Anda", Toast.LENGTH_LONG).show();
//                    }
//                });
//        // Adding JsonObject request to request queue
//        AppSingleton.getInstance(ControllerHome.this.getApplicationContext()).addToRequestQueue(request, REQUEST_TAG);
//    }

//    private void setFlipperImage(ArrayList<String> listGambar) {
//        for (int i = 0; i < listGambar.size(); i++) {
//            ImageView image = new ImageView(ControllerHome.this.getApplicationContext());
//            Picasso.get()
//                    .load(listGambar.get(i).toString())
//                    .error(R.drawable.no_image)
//                    .fit()
//                    .into(image);
//            viewFlipper.addView(image);
//        }
//    }

//    ImageListener imageListener = new ImageListener() {
//        @Override
//        public void setImageForPosition(int position, ImageView imageView) {
//            imageView.setImageResource(sampleImages[position]);
//        }
//    };

    private void qrCode() {
        Intent intent = new Intent(ControllerHome.this, ControllerQRCode.class);
        startActivity(intent);
    }

    private void newsIntent() {
        Intent intent = new Intent(ControllerHome.this, ControllerNews.class);
        startActivity(intent);
    }

    private void rundownIntent() {
        Intent intent = new Intent(ControllerHome.this, ControllerAgendaRundown.class);
        startActivity(intent);
    }

    private void venueIntent() {
        Intent intent = new Intent(ControllerHome.this, ControllerVenue.class);
        startActivity(intent);
    }

    private void contentIntent() {
        Intent intent = new Intent(ControllerHome.this, ControllerAgendaContent.class);
        startActivity(intent);
    }

    private void selfieIntent() {
        Intent intent = new Intent(ControllerHome.this, ControllerSelfie.class);
        startActivity(intent);
    }

    private void galleryIntent() {
        Intent intent = new Intent(ControllerHome.this, ControllerGallery.class);
        startActivity(intent);
    }

    private void feedbackIntent() {
        Intent intent = new Intent(ControllerHome.this, ControllerFeedback.class);
        startActivity(intent);
    }

    private void settingIntent() {
        Intent intent = new Intent(ControllerHome.this, ControllerSetting.class);
        startActivity(intent);
    }

    private void launchNewActivity(Context context, String packageName) {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        }
        if (intent == null) {
            try {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                context.startActivity(intent);
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    private void aboutIntent() {
        Intent intent = new Intent(ControllerHome.this, ControllerAbout.class);
        startActivity(intent);
    }

    private void gamesIntent() {
        Intent i = new Intent(ControllerHome.this, ControllerGame2019.class);
        // i.putExtra("data",data);
//        i.putExtra("url","https://kahoot.it/");
//        i.putExtra("title","Games");
        startActivity(i);
    }

    private void panelIntent() {
        Intent i = new Intent(ControllerHome.this, ControllerPanel.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.menuLogout:

                new android.app.AlertDialog.Builder(ControllerHome.this).setMessage("Are you sure you want to exit?").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                session.logoutUser();
//                                session.createLogoutSession();
                            }
                        }).setNegativeButton("No", null).show();
                return true;

            case R.id.changePassword:

                Intent intent = new Intent(this,ControllerChangePassword.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click again to return to your home screen", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private class countNews extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ControllerHome.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            connection = new dbConnection();
            model = new ModelNews();
            model.setUser_id(user_id);
            Log.d("id user home", model.getUser_id());
            String response = connection.sendGetRequest(connection.urlCountNews + "/" + model.getUser_id());
            Log.d("URL", response);
            try {
                jumList = new ArrayList<ModelNews>();
                JSONObject jsonObj = new JSONObject(response);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.NEWS.TAG_TITLE_COUNT);

                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String id = obj.getString(ConstantUtil.NEWS.TAG_USER_ID);
                    String jml = obj.getString(ConstantUtil.NEWS.TAG_COUNT);
                    Log.d("isi count", id);
                    Log.d("isi count", jml);
                    model = new ModelNews(id, jml);
                    jumlah = jml;
                    jumList.add(model);
                }
                Log.d("json home", jsonArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (!jumList.isEmpty()) {
                if (!jumlah.equals("0")) {
                    badgeView.setText(jumlah);
                    badgeView.show();
                } else {
                    badgeView.hide();
                }
            } else {
                badgeView.hide();
            }
        }
    }
}
