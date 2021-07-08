package sigma.telkomgroup.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.adapter.AdapterPollingQuestion;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.model.ModelPolling;

/**
 * Created by biting on 19/04/16.
 */
public class ControllerPollingQuestion extends Fragment {

    private ListView listView;

    private dbConnection connection;
    private List<ModelPolling> modelList;
    private ModelPolling model;
    private AdapterPollingQuestion adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new showQuestion().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertFragment = inflater.inflate(R.layout.fragment_polli, null);

        listView = (ListView) convertFragment.findViewById(R.id.listQuestionPolling);

        return convertFragment;
    }

    private class showQuestion extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            connection = new dbConnection();
            String response = connection.sendGetRequest(dbConnection.urlPolling);

            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.POLLING.TAG_TITLE);
                modelList = new ArrayList<ModelPolling>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String id = obj.getString(ConstantUtil.POLLING.TAG_POLL_ID);
                    String quest = obj.getString(ConstantUtil.POLLING.TAG_QUESTION);
                    String date = obj.getString(ConstantUtil.POLLING.TAG_START_DATE);
                    String time = obj.getString(ConstantUtil.POLLING.TAG_END_DATE);
                    model = new ModelPolling(id, quest, date, time);
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
            adapter = new AdapterPollingQuestion(getActivity(), modelList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent sentIntent = new Intent(getActivity().getApplicationContext(), ControllerPollingChoice.class);
                    sentIntent.putExtra(ConstantUtil.POLLING.TAG_POLL_ID, modelList.get(+position).getId_polling());
                    sentIntent.putExtra(ConstantUtil.POLLING.TAG_QUESTION, modelList.get(+position).getQuestion());
                    startActivity(sentIntent);
                }
            });
        }
    }
}
