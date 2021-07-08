package sigma.telkomgroup.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.adapter.AdapterPollingChoice;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.model.ModelChoice;

/**
 * Created by biting on 19/04/16.
 */
public class ControllerPollingChoice extends Activity {
    private ListView listView;

    private dbConnection connection;
    private List<ModelChoice> modelList;
    private ModelChoice model;
    private AdapterPollingChoice adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_polling);
        listView = (ListView) findViewById(R.id.listChoicePolling);
        new showQuestion().execute();
    }

    private class showQuestion extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ControllerPollingChoice.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            connection = new dbConnection();
            String response = connection.sendGetRequest(dbConnection.urlChoice);

            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.CHOICE.TAG_TITLE);
                modelList = new ArrayList<ModelChoice>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String id = obj.getString(ConstantUtil.CHOICE.TAG_ID);
                    String quest = obj.getString(ConstantUtil.CHOICE.TAG_OPTION_NAME);
                    model = new ModelChoice(id, quest);
                    modelList.add(model);
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
            adapter = new AdapterPollingChoice(ControllerPollingChoice.this, modelList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(ControllerPollingChoice.this, modelList.get(+position).getChoice_id(), Toast.LENGTH_SHORT).show();
                    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                }
            });

        }
    }

}
