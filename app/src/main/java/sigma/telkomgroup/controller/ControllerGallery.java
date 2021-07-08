package sigma.telkomgroup.controller;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.adapter.AdapterGalleryGrid;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.model.ModelGallery;
import sigma.telkomgroup.utils.AppSingleton;
import sigma.telkomgroup.utils.SessionManager;

/**
 * Created by Biting on 7/20/2017.
 */

public class ControllerGallery extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ProgressBar pb;
    private Toolbar toolbar;
    private GridView gridView;
    private HashMap<String, String> data;
    private ModelGallery model;
    private List<ModelGallery> listGallery;
    private List<String> listGambar;
    private AdapterGalleryGrid adapter;
    private SessionManager session;
    private HashMap<String, String> userDetail;
    private String id_user;
    private boolean doubleBackToExitPressedOnce = false;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pb = (ProgressBar) findViewById(R.id.progressBar2);
        gridView = (GridView) findViewById(R.id.grid);
        swipeRefreshLayout.setOnRefreshListener(this);
        session = new SessionManager(ControllerGallery.this);
        userDetail = session.getUserDetails();
        id_user = userDetail.get(ConstantUtil.LOGIN.TAG_ID);

        showGallery();
        String title = getString(R.string.nav_item_gallery);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle(title);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        showGallery();
                                    }
                                }
        );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public void onRefresh() {
        showGallery();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ControllerGallery.this, ControllerHome.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void showGallery() {
        pb.setVisibility(View.VISIBLE);
        final String REQUEST_TAG = "get request gallery";

        System.out.println("check string request gallery : " + ConstantUtil.URL.GET_GALLERY);
        StringRequest request = new StringRequest(Request.Method.GET, ConstantUtil.URL.GET_GALLERY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.GALLERY.TAG_TITLE);
                            model = new ModelGallery();
                            listGallery = new ArrayList<ModelGallery>();
                            listGambar = new ArrayList<String>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                String id = obj.getString(ConstantUtil.GALLERY.TAG_ID);
                                String image = obj.getString(ConstantUtil.GALLERY.TAG_NAME);
                                model = new ModelGallery(id, image);
                                listGallery.add(model);
                                listGambar.add(image);
                            }
                            pb.setVisibility(View.GONE);
                            adapter = new AdapterGalleryGrid(ControllerGallery.this, listGallery);
                            gridView.setAdapter(adapter);
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent i = new Intent(ControllerGallery.this, ControllerGalleryDetail.class);
                                    i.putExtra(ConstantUtil.GALLERY.TAG_ID, listGallery.get(position).getIdGallery());
                                    i.putExtra(ConstantUtil.LOGIN.TAG_ID, id_user);
                                    startActivity(i);
                                }
                            });
                            swipeRefreshLayout.setRefreshing(false);

                        } catch (JSONException e) {
                            //e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(ControllerGallery.this, "Periksa koneksi internet anda", Toast.LENGTH_LONG).show();
                    }
                });
        // Adding JsonObject request to request queue
        AppSingleton.getInstance(ControllerGallery.this ).addToRequestQueue(request, REQUEST_TAG);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ControllerGallery.this, ControllerHome.class);
        startActivity(i);
        finish();
    }
}
