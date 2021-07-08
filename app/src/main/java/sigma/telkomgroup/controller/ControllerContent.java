package sigma.telkomgroup.controller;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sigma.telkomgroup.R;
import sigma.telkomgroup.adapter.AdapterContent;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.model.ModelContent;

/**
 * Created by biting on 10/03/16.
 */
public class ControllerContent extends AppCompatActivity {


    Typeface font,fontbold;
    private TextView title;
    private TextView dates;
    private ListView listContent;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;

    private String namaFile;

    private List<ModelContent> contentList;

    private dbConnection connection;
    private ConnectivityManager connectivity;
    private ModelContent model;
    private AdapterContent adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);
        connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        initView();
        new showContent().execute();
    }

    private void initView() {

        font = Typeface.createFromAsset(ControllerContent.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerContent.this.getAssets(), "fonts/Montserrat-SemiBold.otf");
        title = (TextView) findViewById(R.id.tvTitle);
        title.setTypeface(fontbold);
        dates = (TextView) findViewById(R.id.tvAgenda);
        dates.setTypeface(font);
        listContent = (ListView) findViewById(R.id.listDetailContent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        String name = intent.getStringExtra(ConstantUtil.MEETING.TAG_AGENDA_NAME);
        String tema = intent.getStringExtra(ConstantUtil.MEETING.TAG_TEMA_NAME);
        String tgl = intent.getStringExtra(ConstantUtil.MEETING.TAG_START_DATE);

        Calendar cal = Calendar.getInstance();
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(tgl);
            cal.setTime(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fdate = new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime());

        title.setText(tema);
        dates.setText(name);

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Presentation");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    // toolbar back menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    protected String getUrl() {
        Intent intent = getIntent();
        String id = intent.getStringExtra(ConstantUtil.MEETING.TAG_ID);

        StringBuilder sb = null;
        sb = new StringBuilder(dbConnection.urlContent);
        sb.append("/" + id);

        return sb.toString();
    }

    public void showFile(String name) {

        Environment.getExternalStorageState();
        String storage = Environment.getExternalStorageDirectory().toString() + "/Download";
        //File pdfFile = new File(storage + "/Rapim/" + name);
        File pdfFile = new File(storage + "/" +name);
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pdfIntent);

    }

    private class showContent extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ControllerContent.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            connection = new dbConnection();
            String response = connection.sendGetRequest(getUrl());
            System.out.println("response : " + response);
            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.CONTENT.TAG_TITLE);
                contentList = new ArrayList<ModelContent>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String fileTitle = obj.getString(ConstantUtil.CONTENT.TAG_NAME);
                    String fileName = obj.getString(ConstantUtil.CONTENT.TAG_PATH);
                    model = new ModelContent(fileTitle, fileName);
                    contentList.add(model);
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
            System.out.println("content download : " + contentList.toString());
            adapter = new AdapterContent(ControllerContent.this, contentList);
            listContent.setAdapter(adapter);
            listContent.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String fileSaved = contentList.get(+position).getContent_title().trim() + ".pdf";
                            System.out.println("isi file saved : " + fileSaved);

                            String urlDownload = contentList.get(position).getContent_path();
                            System.out.println("url download : " + urlDownload);

                            // ini download pdf langsung
//                            Environment.getExternalStorageState();
//                            String storage = Environment.getExternalStorageDirectory().toString() + "/Download";
//                            File pdfFile = new File(storage, fileSaved);
//
//                                if (pdfFile.exists()) {
//                                    showFile(fileSaved);
//                                } else {
//                                    new DownloadFile().execute(contentList.get(+position).getContent_path(), fileSaved);
//                                    Log.d("baca else direktori", "satu");
//                                }
                            // ini download pdf langsung


                            // lempar ke chrome
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlDownload));
                            startActivity(browserIntent);
                            // lempar ke chrome


                        }
                    }
            );
        }
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create progress dialog
            progressDialog = new ProgressDialog(ControllerContent.this);
            // Set your progress dialog Title
            progressDialog.setTitle("Download Materi");
            // Set your progress dialog Message
            progressDialog.setMessage("Please Wait!");
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // Show progress dialog
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... Url) {
            try {
                URL url = new URL(Url[0]);
                namaFile = Url[1];
                URLConnection connection = url.openConnection();
                connection.connect();

                // Detect the file lenghth
                int fileLength = connection.getContentLength();

                // Locate storage location
                String filepath = Environment.getExternalStorageDirectory().toString();
                Log.d("isi file path nya", filepath);

                // Download the file
                InputStream input = new BufferedInputStream(url.openStream());

                // Save the downloaded file
                OutputStream output = new FileOutputStream(filepath +"/Download/"+ namaFile);
                //OutputStream output = new FileOutputStream(filepath + "/Rapim/" + namaFile);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish the progress
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);

                }
                // Close connection
                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                // Error Log
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // Update the progress dialog
            progressDialog.setProgress(progress[0]);
            // Dismiss the progress dialog
            if (progressDialog.getProgress() == 100) {
                progressDialog.dismiss();
                showFile(namaFile);
            }
        }
    }
}
