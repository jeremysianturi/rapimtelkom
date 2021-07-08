package sigma.telkomgroup.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sigma.telkomgroup.R;
import sigma.telkomgroup.adapter.AdapterVenue;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.model.ModelVenue;

/**
 * Created by biting on 14/04/16.
 */
public class ControllerVenue extends AppCompatActivity implements OnMapReadyCallback {

    private RecyclerView gridView;
    private Toolbar toolbar;

    private GoogleMap mMap;

    Typeface font,fontbold;
    private dbConnection connection;
    private ArrayList<ModelVenue> venueList;
    private ModelVenue model;
    private AdapterVenue adapter;
    ImageView ivMaps;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static boolean isOnline(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    WebView wb;
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);
        gridView = (RecyclerView) findViewById(R.id.gridview);

        // ini sebelumnya di comment

//        wb=(WebView)findViewById(R.id.webView1);
//        wb.getSettings().setJavaScriptEnabled(true);
//        wb.getSettings().setLoadWithOverviewMode(true);
//        wb.getSettings().setUseWideViewPort(true);
//        wb.getSettings().setBuiltInZoomControls(true);
//        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
////        wb.getSettings().setPluginsEnabled(true);
//        wb.setWebViewClient(new HelloWebViewClient());
//        wb.loadUrl("https://www.google.com/maps/place/Telkom+Landmark+Tower,+The+Telkom+Hub/@-6.2304538,106.8157385,17z/data=!3m1!4b1!4m5!3m4!1s0x2e69f158843078e3:0xce64d3c98a332ab0!8m2!3d-6.2304591!4d106.8179272");

//        ivMaps = (ImageView) findViewById(R.id.ivMaps);

        // ini sebelumnya di comment


        if (isOnline(ControllerVenue.this)) {
            initView();
            new showGalery().execute();
        } else {
            Toast.makeText(ControllerVenue.this, "No Internet connection", Toast.LENGTH_LONG).show();
        }

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

//        String url = "http://maps.google.com/maps/api/staticmap?center=-6.230363,106.817916&zoom=15&size=200x200&sensor=false&key=AIzaSyBpAlTrHul0Zp4R0f4gu7TrNtRiONdfJPw";
        String url = "http://maps.google.com/maps/api/staticmap?center=-6.230363,106.817916&zoom=15&size=200x200&sensor=false&key=AIzaSyBEdl3bME51CowbGDXEG2BsQcUyQ8Vw0e4";
//

//        Picasso.get().load(url).into(ivMaps);


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

    private void initView() {

        font = Typeface.createFromAsset(ControllerVenue.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerVenue.this.getAssets(), "fonts/Montserrat-SemiBold.otf");


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Venue");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    protected String getUrl() {
        Intent intent = getIntent();
        String id = intent.getStringExtra(ConstantUtil.MEETING.TAG_ID);

        StringBuilder sb = null;
        sb = new StringBuilder(dbConnection.urlVenue);
        sb.append("/" + id);

        return sb.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-6.230363, 106.817916);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Telkom Landmark Tower"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.230363, 106.817916), 14.0f));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class showGalery extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ControllerVenue.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            connection = new dbConnection();
            String response = connection.sendGetRequest(getUrl());

            try {
                JSONObject jsonObj = new JSONObject(response);
                System.out.println("kwjehr"+jsonObj);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.VENUE.TAG_TITLE);
                venueList = new ArrayList<ModelVenue>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String id = obj.getString(ConstantUtil.VENUE.TAG_ID);
                    String title = obj.getString(ConstantUtil.VENUE.TAG_VENUE_TITLE);
                    String ima = obj.getString(ConstantUtil.VENUE.TAG_IMAGE);
                    String caption = obj.getString(ConstantUtil.VENUE.TAG_VENUE_CAPTION);
                    model = new ModelVenue(id, title, ima, caption);
                    venueList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            adapter=new AdapterVenue(venueList);
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(ControllerVenue.this, LinearLayoutManager.HORIZONTAL, false);
//                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcView.getContext(),
//                                    horizontalLayoutManagaer.getOrientation());
//                            rcView.addItemDecoration(dividerItemDecoration);
            gridView.setLayoutManager(horizontalLayoutManagaer);
            gridView.setAdapter(adapter);
//
//                adapter = new AdapterVenue(ControllerVenue.this, venueList);
//                gridView.setAdapter(adapter);
//
//                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent(ControllerVenue.this, ControllerVenueZoom.class);
//                        intent.putExtra(ConstantUtil.MEETING.TAG_ID, venueList.get(+position).getVenue_id());
//                        intent.putExtra(ConstantUtil.MEETING.TAG_IMAGE, venueList.get(+position).getVenue_image());
//                        startActivity(intent);
//                    }
//                });
        }
    }
}
