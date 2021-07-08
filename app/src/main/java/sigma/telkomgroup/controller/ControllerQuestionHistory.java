package sigma.telkomgroup.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.ListView;

import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import sigma.telkomgroup.R;
import sigma.telkomgroup.adapter.AdapterHistory;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.model.ModelHistory;
import sigma.telkomgroup.utils.SessionManager;

public class ControllerQuestionHistory extends AppCompatActivity {

    private SessionManager session;
    private dbConnection connection;
    private HashMap<String, String> user;

    private Toolbar toolbar;
    Typeface font,fontbold;

    private ArrayList<ModelHistory> historyList;
    private ModelHistory model;
    private AdapterHistory adapter;

    private ListView listHistory;

    public static boolean isOnline(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_history);

        session = new SessionManager(ControllerQuestionHistory.this);
        user = session.getUserDetails();

        initView();

//        if (isOnline(ControllerQuestionHistory.this)) {
//            initView();
//            new ControllerQuestionHistory.getQuestionHistory().execute();
//        } else {
//            Toast.makeText(ControllerQuestionHistory.this, "No Internet connection", Toast.LENGTH_LONG).show();
//        }

        new getQuestionHistory().execute();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void initView() {
        listHistory = (ListView) findViewById(R.id.listHistory);

        font = Typeface.createFromAsset(ControllerQuestionHistory.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerQuestionHistory.this.getAssets(), "fonts/Montserrat-SemiBold.otf");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Question History");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private class getQuestionHistory extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ControllerQuestionHistory.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            connection = new dbConnection();
            String id = user.get(ConstantUtil.LOGIN.TAG_USER_NAME);
            String response = connection.sendGetRequest(dbConnection.urlQuestionHistory + "/" + id);

            try {
                JSONObject jsonObj = new JSONObject(response);
                System.out.println("kwjehr"+jsonObj);
                JSONArray jsonArray = jsonObj.getJSONArray("data");
                historyList = new ArrayList<ModelHistory>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String date = obj.getString("panel_date");
                    String question = obj.getString("question");
                    String direktorat = obj.getString("panelist");

                    model = new ModelHistory(date, question, direktorat);
                    historyList.add(model);
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
            adapter = new AdapterHistory(ControllerQuestionHistory.this, historyList);
            listHistory.setAdapter(adapter);
        }
    }
}
