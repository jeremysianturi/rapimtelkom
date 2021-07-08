package sigma.telkomgroup.controller;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.adapter.AdapterNews;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.model.ModelNews;
import sigma.telkomgroup.parser.PostJSON;
import sigma.telkomgroup.utils.SessionManager;

//http://indragni.com/blog/tutorials/android/

/**
 * Created by biting on 29/03/16.
 */
public class ControllerNews extends AppCompatActivity {

    private ListView listNews;
    private ImageView image;
    private ImageView icon;
    private dbConnection connection;
    private ModelNews model;
    private List<ModelNews> newsList;
    private AdapterNews adapter;
    private SessionManager session;
    private Toolbar toolbar;
    private HashMap<String, String> user;
    private String user_id, idMessage;
    private PostJSON postJSON;
    TextView tvDate;

    public static boolean isOnline(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        session = new SessionManager(ControllerNews.this);
        session.checkLogin();
        user = session.getUserDetails();
        user_id = user.get(ConstantUtil.LOGIN.TAG_ID);
        tvDate = (TextView) findViewById(R.id.tvDate);

//        String tgl = model.getNews_date();
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = df.format(c);

//        Calendar cal = Calendar.getInstance();
//        try {
//            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//            cal.setTime(d);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        String fdate = new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime());
        tvDate.setText(formattedDate);
        String title = getString(R.string.nav_item_news);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listNews = (ListView) findViewById(R.id.listNews);
//        image = (ImageView) findViewById(R.id.imageNews);
//        icon = (ImageView) findViewById(R.id.imageNewLogo);
        if (isOnline(ControllerNews.this)) {
            new showNews().execute();
        } else {
            Toast.makeText(ControllerNews.this, "No Internet connection", Toast.LENGTH_LONG).show();
        }

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

    private class showNews extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ControllerNews.this);
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
            System.out.println("check url get news : " + connection.urlNews + "/" + model.getUser_id());
            String response = connection.sendGetRequest( connection.urlNews + "/" + model.getUser_id());
            Log.d("URL", response);
            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.NEWS.TAG_TITLE);
                newsList = new ArrayList<ModelNews>();

                int length = jsonArray.length();
                for (int i=0; i<length; i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String id = obj.getString(ConstantUtil.NEWS.TAG_ID);
                    String usid = obj.getString(ConstantUtil.NEWS.TAG_USER_NEWS_ID);
                    String titl = obj.getString(ConstantUtil.NEWS.TAG_TITLE_CONTENT);
                    String cont = obj.getString(ConstantUtil.NEWS.TAG_CONTENT);
                    String dat = obj.getString(ConstantUtil.NEWS.TAG_DATE);
                    String stat = obj.getString(ConstantUtil.NEWS.TAG_STATUS);

                    model = new ModelNews(id, usid, titl, cont, dat, stat);
                    newsList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            adapter = new AdapterNews(ControllerNews.this, newsList);
            listNews.setAdapter(adapter);
            listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ControllerNews.this, ControllerDetailNews.class);
                    intent.putExtra(ConstantUtil.NEWS.TAG_ID, newsList.get(+position).getNews_id());
                    intent.putExtra(ConstantUtil.NEWS.TAG_USER_NEWS_ID, newsList.get(+position).getUser_news_id());
                    intent.putExtra(ConstantUtil.NEWS.TAG_TITLE_CONTENT, newsList.get(+position).getNews_title());
                    intent.putExtra(ConstantUtil.NEWS.TAG_CONTENT, newsList.get(+position).getNews_content());
                    intent.putExtra(ConstantUtil.NEWS.TAG_DATE, newsList.get(+position).getNews_date());
                    startActivity(intent);
                    idMessage = newsList.get(+position).getUser_news_id();
                    changeStatus();
                }
            });
        }
    }

    private void changeStatus() {

        try {
            JSONObject json = new JSONObject();
            json.put(ConstantUtil.NEWS.TAG_USER_NEWS_ID, idMessage);

            String title_1st = json.toString();
            postJSON = (PostJSON) new PostJSON(title_1st, dbConnection.serverUrl + dbConnection.urlUpdateNews).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        } catch (JSONException e) {
        }

    }

    /*
    private class showNews extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ControllerNews.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            connection = new dbConnection();
            String response = connection.sendGetRequest(dbConnection.urlNews);

                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.NEWS.TAG_TITLE);
                    newsList = new ArrayList<ModelNews>();

                    int length = jsonArray.length();
                    for (int i = length - 1; i > -1; i--) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String id = obj.getString(ConstantUtil.NEWS.TAG_ID);
                        String titl = obj.getString(ConstantUtil.NEWS.TAG_TITLE_CONTENT);
                        String cont = obj.getString(ConstantUtil.NEWS.TAG_CONTENT);
                        String dat = obj.getString(ConstantUtil.NEWS.TAG_DATE);

                        model = new ModelNews(id, titl, cont, dat);
                        newsList.add(model);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
                adapter = new AdapterNews(ControllerNews.this, newsList);
                listNews.setAdapter(adapter);
                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ControllerNews.this, ControllerDetailNews.class);
                        intent.putExtra(ConstantUtil.NEWS.TAG_ID, newsList.get(+position).getNews_id());
                        intent.putExtra(ConstantUtil.NEWS.TAG_TITLE_CONTENT, newsList.get(+position).getNews_title());
                        intent.putExtra(ConstantUtil.NEWS.TAG_CONTENT, newsList.get(+position).getNews_content());
                        intent.putExtra(ConstantUtil.NEWS.TAG_DATE, newsList.get(+position).getNews_date());
                        startActivity(intent);
                    }
                });
        }
    }
    */
}
