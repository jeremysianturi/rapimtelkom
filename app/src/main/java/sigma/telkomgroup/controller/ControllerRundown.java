package sigma.telkomgroup.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.adapter.AdapterRundown;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.model.ModelRundown;

/**
 * Created by biting on 10/03/16.
 */
public class ControllerRundown extends AppCompatActivity {

    Typeface font,fontbold;
    private TextView title;
    private TextView dates;
    private ListView listRundown;
    private TextView content;
    private TextView times, tvTema, tvAgenda;
    private Toolbar toolbar;

    private dbConnection connection;
    private ModelRundown model;
    private List<ModelRundown> rundownList;
    private AdapterRundown adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rundown);
        initView();
        new showRundown().execute();



    }

    private void initView(){

        font = Typeface.createFromAsset(ControllerRundown.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerRundown.this.getAssets(), "fonts/Montserrat-SemiBold.otf");

        tvAgenda = (TextView) findViewById(R.id.tvAgendaName);
        tvTema = (TextView) findViewById(R.id.tvTemaName);
        tvAgenda.setTypeface(fontbold);
        tvTema.setTypeface(fontbold);
        listRundown = (ListView) findViewById(R.id.listDetailRundown);
        content = (TextView) findViewById(R.id.textRundownContent);
        times = (TextView) findViewById(R.id.textRundownTime);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        String agenda = intent.getStringExtra(ConstantUtil.MEETING.TAG_AGENDA_NAME);
        String tema = intent.getStringExtra(ConstantUtil.MEETING.TAG_TEMA_NAME);
        tvAgenda.setText(agenda);
        tvTema.setText(tema);
//        Calendar cal = Calendar.getInstance();
//        try {
//            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
//            cal.setTime(d);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        String fdate = new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime());
//        String loca = intent.getStringExtra(ConstantUtil.MEETING.TAG_LOCATION);
//
//        title.setText(loca);
//        dates.setText(fdate);

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Rundown");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    //toolbar back-menu action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    protected String getUrl(){
        Intent intent = getIntent();
        String id = intent.getStringExtra(ConstantUtil.RUNDOWN.TAG_ID);

        StringBuilder sb = null;
        sb = new StringBuilder(dbConnection.urlRundown);
        sb.append("/" + id);
        return sb.toString();
    }

    private class showRundown extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ControllerRundown.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            connection = new dbConnection();
            String response = connection.sendGetRequest(getUrl());

            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.RUNDOWN.TAG_TITLE);
                rundownList = new ArrayList<ModelRundown>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String num = String.valueOf(i + 1);
                    String ids = obj.getString(ConstantUtil.RUNDOWN.TAG_MEET_ID);
                    String name = obj.getString(ConstantUtil.RUNDOWN.TAG_NAME);
                    String time = obj.getString(ConstantUtil.RUNDOWN.TAG_TIME);
                    String desc = obj.getString(ConstantUtil.RUNDOWN.TAG_DESC);
                    model = new ModelRundown(num, name, time, desc);
                    rundownList.add(model);
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
                adapter = new AdapterRundown(ControllerRundown.this, rundownList);
                listRundown.setAdapter(adapter);
                listRundown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent a = new Intent(ControllerRundown.this, ControllereDetailRundown.class);
                        a.putExtra(ConstantUtil.RUNDOWN.TAG_NAME,rundownList.get(position).getRundown_name());
                        a.putExtra(ConstantUtil.RUNDOWN.TAG_DESC,rundownList.get(position).getRundown_desc());
                        a.putExtra(ConstantUtil.RUNDOWN.TAG_TIME,rundownList.get(position).getRundown_time());
                        startActivity(a);
                    }
                });
        }
    }

}
