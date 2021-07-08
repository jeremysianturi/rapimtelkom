package sigma.telkomgroup.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.adapter.AdapterMyAgenda;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.model.ModelMeeting;
import sigma.telkomgroup.utils.SessionManager;

//http://indragni.com/blog/tutorials/android/

/**
 * Created by biting on 29/03/16.
 */
public class ControllerAgendaRundown extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listAgenda;
    private dbConnection connection;
    private ModelMeeting meeting;
    private List<ModelMeeting> meetingList;
    private AdapterMyAgenda adapter;
    private SessionManager session;
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
        setContentView(R.layout.activity_agenda_rundown);
        session = new SessionManager(ControllerAgendaRundown.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listAgenda = (ListView) findViewById(R.id.listAgenda);
        tvDate = (TextView) findViewById(R.id.tvDate);
        String title = getString(R.string.nav_item_rundown);
        if (isOnline(ControllerAgendaRundown.this)) {
            new showAgenda().execute();
        } else {
            Toast.makeText(ControllerAgendaRundown.this, "No Internet connection", Toast.LENGTH_LONG).show();
        }


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = df.format(c);
        tvDate.setText(formattedDate);

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

    private class showAgenda extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ControllerAgendaRundown.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            connection = new dbConnection();
            String response = connection.sendGetRequest(dbConnection.urlMetting);

            if (response != null) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.MEETING.TAG_TITLE);
                    meetingList = new ArrayList<ModelMeeting>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String id = obj.getString(ConstantUtil.MEETING.TAG_ID);
                        String tema = obj.getString(ConstantUtil.MEETING.TAG_TEMA_NAME);
                        String agenda = obj.getString(ConstantUtil.MEETING.TAG_AGENDA_NAME);
                        String start = obj.getString(ConstantUtil.MEETING.TAG_START_DATE);
                        String end = obj.getString(ConstantUtil.MEETING.TAG_END_DATE);
                        String agDate = obj.getString(ConstantUtil.MEETING.TAG_AGENDA_DATE);
                        String loca = obj.getString(ConstantUtil.MEETING.TAG_LOCATION);
                        meeting = new ModelMeeting(id, agenda, tema, agDate, start, end, loca);
                        meetingList.add(meeting);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Log.e("masuk else background", "hasil response == null");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            adapter = new AdapterMyAgenda(ControllerAgendaRundown.this, meetingList);
            listAgenda.setAdapter(adapter);
            listAgenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ControllerAgendaRundown.this, ControllerRundown.class);
                    intent.putExtra(ConstantUtil.MEETING.TAG_ID, meetingList.get(+position).getMeeting_id());
                    intent.putExtra(ConstantUtil.MEETING.TAG_AGENDA_NAME, meetingList.get(+position).getAgenda_name());
                    intent.putExtra(ConstantUtil.MEETING.TAG_TEMA_NAME, meetingList.get(+position).getTema_name());
                    intent.putExtra(ConstantUtil.MEETING.TAG_AGENDA_DATE, meetingList.get(+position).getAgenda_date());
                    intent.putExtra(ConstantUtil.MEETING.TAG_START_DATE, meetingList.get(+position).getStart_date());
                    intent.putExtra(ConstantUtil.MEETING.TAG_END_DATE, meetingList.get(+position).getEnd_date());
                    intent.putExtra(ConstantUtil.MEETING.TAG_LOCATION, meetingList.get(+position).getMeeting_location());
                    startActivity(intent);
                }
            });
        }
    }


}
