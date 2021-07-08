package sigma.telkomgroup.controller;

import androidx.appcompat.app.*;
import androidx.appcompat.widget.*;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.model.ModelShoutbox;
import sigma.telkomgroup.parser.PostJSON;
import sigma.telkomgroup.utils.SessionManager;

/**
 * Created by biting on 22/07/16.
 */
public class ControllerShoutbox extends AppCompatActivity {

    private LinearLayout layoutUtama;
    private LinearLayout layoutCadangan;
    private Spinner spinner;
    private EditText editText;
    private Button button;
    private dbConnection connection;
    private List<String> idTemaList,idOptionList,songList;
    private PostJSON postJSON;
    private SessionManager session;
    private HashMap<String, String> user;
    private String user_id;
    private String id, status, note;
    private ModelShoutbox model;
    private Toolbar toolbar;
    private String idOption, idTema;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoutbox);
        layoutUtama = (LinearLayout) findViewById(R.id.layoutUtama);
        layoutCadangan = (LinearLayout) findViewById(R.id.layoutCadangan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinnerShout);
        editText = (EditText) findViewById(R.id.editShout);
        button = (Button) findViewById(R.id.buttonShout);

        session = new SessionManager(ControllerShoutbox.this);
        user = session.getUserDetails();
        user_id = user.get(ConstantUtil.LOGIN.TAG_ID);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        new checkWasSubmit().execute();

        String title = getString(R.string.nav_item_shoutbox);
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

    private class checkWasSubmit extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ControllerShoutbox.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            connection = new dbConnection();
            model = new ModelShoutbox();
            model.setId_user(user_id);
            //model.setId_user("12");
            String respon = connection.sendGetRequest(connection.urlCheckShoutbox + "/" + model.getId_user());
            Log.d("URL", respon);
            try {
                JSONObject jsonObj = new JSONObject(respon);
                JSONObject json = jsonObj.getJSONObject("shoutbox");
                String sta = json.getString("status");
                status = sta;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return respon;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (!result.equals("0")) {
                Log.d("masuk ke", "satu");
                Log.d("model", status);
                if (status.equals("0")) {
                    layoutUtama.setVisibility(View.VISIBLE);
                    new showOption().execute();
                } else {
                    layoutCadangan.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(ControllerShoutbox.this, "Connection Problem", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class showOption extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            connection = new dbConnection();
            String response = connection.sendGetRequest(dbConnection.urlShoutbox);
            Log.d("hasil", response);
            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.SHOUTBOX.TAG_TITLE);
                songList = new ArrayList<String>();
                idOptionList = new ArrayList<String>();
                idTemaList = new ArrayList<String>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String ido = obj.getString(ConstantUtil.SHOUTBOX.TAG_SHOUT_OPTION_ID);
                    String idt = obj.getString(ConstantUtil.SHOUTBOX.TAG_TEMA_ID);
                    String title = obj.getString(ConstantUtil.SHOUTBOX.TAG_SONG);
                    songList.add(title);
                    idOptionList.add(ido);
                    idTemaList.add(idt);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (!result.equals("0")) {
                Log.d("hasil", "satu");
                ArrayAdapter<String> spinnerArrayAdapter;
                spinnerArrayAdapter = new ArrayAdapter<String>(ControllerShoutbox.this, R.layout.item_spinner, songList);
                spinner.setAdapter(spinnerArrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idOption = idOptionList.get(position);
                        idTema = idTemaList.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                layoutUtama.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(ControllerShoutbox.this, "Connection Problem", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void sendData() {

        try {
            String value = idOption;
            String IDtema = idTema;
            String caption = editText.getText().toString();

            JSONObject jsontitle = new JSONObject();
            JSONObject json1 = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            try {
                json1.put(ConstantUtil.SUBMIT_SHOUT.TAG_USER_ID, user_id);
                json1.put(ConstantUtil.SUBMIT_SHOUT.TAG_TEMA_ID, IDtema);
                json1.put(ConstantUtil.SUBMIT_SHOUT.TAG_VALUE, value);
                json1.put(ConstantUtil.SUBMIT_SHOUT.TAG_CAPTION, caption);

                JSONArray array = jsonArray.put(json1);
                jsontitle.put(ConstantUtil.SUBMIT_SHOUT.TAG_TITLE, array);
            } catch (JSONException e) {
                Log.d("hasil exception", e.toString());
            }

            String title_1st = jsontitle.toString();
            Log.d("POST", title_1st);
            postJSON = (PostJSON) new PostJSON(title_1st, dbConnection.serverUrl + dbConnection.urlSubmitShoutbox).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            Toast.makeText(ControllerShoutbox.this, "Success ..", Toast.LENGTH_LONG).show();
            layoutUtama.setVisibility(View.GONE);
            layoutCadangan.setVisibility(View.VISIBLE);
        } catch (Exception ee) {
            Toast.makeText(ControllerShoutbox.this, "Failed ..", Toast.LENGTH_LONG).show();
        }
    }
}
