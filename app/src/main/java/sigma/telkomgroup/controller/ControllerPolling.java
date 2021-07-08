package sigma.telkomgroup.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import sigma.telkomgroup.model.ModelChoice;
import sigma.telkomgroup.model.ModelPolling;
import sigma.telkomgroup.parser.PostJSON;
import sigma.telkomgroup.utils.SessionManager;

/**
 * Created by biting on 11/03/16.
 */


public class ControllerPolling extends Fragment {

    //Polling 1
    private TextView textView1, qc1a, qc1b;
    private Spinner spinnerA1;
    private Spinner spinnerA2;
    //Polling 2
    private TextView textView2, qc2a, qc2b;
    private Spinner spinnerB1;
    private Spinner spinnerB2;
    //Polling 3
    private TextView textView3, qc3a, qc3b;
    private Spinner spinnerC1;
    private Spinner spinnerC2;
    //button
    private Button button;

    private dbConnection connection;
    private List<ModelPolling> modelPList;
    private List<String> questionList;
    private List<ModelChoice> modelCList;
    private ArrayList<String> spinnerList;
    private ModelPolling modelP;
    private ModelChoice modelC;
    private SessionManager session;
    private PostJSON postJSON;
    //check user
    private HashMap<String, String> user;
    private String id_user, username;

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
        if (isOnline(getActivity().getApplicationContext())) {
            new showQuestion().execute();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No Internet connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_polling, null);

        session = new SessionManager(getActivity().getApplicationContext());
        user = session.getUserDetails();
        id_user = user.get(ConstantUtil.LOGIN.TAG_ID);
        username = user.get(ConstantUtil.LOGIN.TAG_USER_NAME);

        textView1 = (TextView) view.findViewById(R.id.textPolling1);
        spinnerA1 = (Spinner) view.findViewById(R.id.spinnerPollingA1);
        spinnerA2 = (Spinner) view.findViewById(R.id.spinnerPollingA2);
        qc1a = (TextView) view.findViewById(R.id.textView1);
        qc1b = (TextView) view.findViewById(R.id.textView2);

        textView2 = (TextView) view.findViewById(R.id.textPolling2);
        spinnerB1 = (Spinner) view.findViewById(R.id.spinnerPollingB1);
        spinnerB2 = (Spinner) view.findViewById(R.id.spinnerPollingB2);
        qc2a = (TextView) view.findViewById(R.id.textView3);
        qc2b = (TextView) view.findViewById(R.id.textView4);

        textView3 = (TextView) view.findViewById(R.id.textPolling3);
        spinnerC1 = (Spinner) view.findViewById(R.id.spinnerPollingC1);
        spinnerC2 = (Spinner) view.findViewById(R.id.spinnerPollingC2);
        qc3a = (TextView) view.findViewById(R.id.textView5);
        qc3b = (TextView) view.findViewById(R.id.textView6);

        button = (Button) view.findViewById(R.id.buttonSubmitPolling);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        return view;
    }

    private void sendData() {

        try {
            int val1A = spinnerA1.getSelectedItemPosition() + 1;
            int val1B = spinnerA2.getSelectedItemPosition() + 1;
            int val2A = spinnerB1.getSelectedItemPosition() + 1;
            int val2B = spinnerB2.getSelectedItemPosition() + 1;
            int val3A = spinnerC1.getSelectedItemPosition() + 1;
            int val3B = spinnerC2.getSelectedItemPosition() + 1;

            JSONObject jsontitle = new JSONObject();
            JSONObject jsontitle2nd = new JSONObject();
            JSONObject json1 = new JSONObject();
            JSONObject json2 = new JSONObject();
            JSONObject json3 = new JSONObject();

            try {
                json1.put(ConstantUtil.SUBMIT_POLLING.TAG_USER_ID, id_user);
                json1.put(ConstantUtil.SUBMIT_POLLING.TAG_POLL_ID, "1");
                json1.put(ConstantUtil.SUBMIT_POLLING.TAG_DATE, "2016-04-18 21:00:00");
                json1.put(ConstantUtil.SUBMIT_POLLING.TAG_VALUE_1, val1A);
                json1.put(ConstantUtil.SUBMIT_POLLING.TAG_VALUE_2, val1B);

                json2.put(ConstantUtil.SUBMIT_POLLING.TAG_USER_ID, id_user);
                json2.put(ConstantUtil.SUBMIT_POLLING.TAG_POLL_ID, "2");
                json2.put(ConstantUtil.SUBMIT_POLLING.TAG_DATE, "2016-04-18 21:00:00");
                json2.put(ConstantUtil.SUBMIT_POLLING.TAG_VALUE_1, val2A);
                json2.put(ConstantUtil.SUBMIT_POLLING.TAG_VALUE_2, val2B);

                json3.put(ConstantUtil.SUBMIT_POLLING.TAG_USER_ID, id_user);
                json3.put(ConstantUtil.SUBMIT_POLLING.TAG_POLL_ID, "3");
                json3.put(ConstantUtil.SUBMIT_POLLING.TAG_DATE, "2016-04-18 21:00:00");
                json3.put(ConstantUtil.SUBMIT_POLLING.TAG_VALUE_1, val3A);
                json3.put(ConstantUtil.SUBMIT_POLLING.TAG_VALUE_2, val3B);

                jsontitle2nd.put(ConstantUtil.SUBMIT_POLLING.TAG_TITLE_1, json1);
                jsontitle2nd.put(ConstantUtil.SUBMIT_POLLING.TAG_TITLE_2, json2);
                jsontitle2nd.put(ConstantUtil.SUBMIT_POLLING.TAG_TITLE_3, json3);

                jsontitle.put(ConstantUtil.SUBMIT_POLLING.TAG_TITLE, jsontitle2nd);
            } catch (JSONException e) {
                Log.d("hasil exception", e.toString());
            }

            String title_1st = jsontitle.toString();
            postJSON = (PostJSON) new PostJSON(title_1st, dbConnection.serverUrl + dbConnection.urlSubmitPolling).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            Toast.makeText(getActivity().getApplicationContext(), "Success ..", Toast.LENGTH_LONG).show();
            button.setEnabled(false);
        } catch (Exception ee) {
            Toast.makeText(getActivity().getApplicationContext(), "Failed ..", Toast.LENGTH_LONG).show();
        }
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
            String respon = connection.sendGetRequest(dbConnection.urlChoice);

            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.POLLING.TAG_TITLE);
                modelPList = new ArrayList<ModelPolling>();
                questionList = new ArrayList<String>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String id = obj.getString(ConstantUtil.POLLING.TAG_POLL_ID);
                    String quest = obj.getString(ConstantUtil.POLLING.TAG_QUESTION);
                    String date = obj.getString(ConstantUtil.POLLING.TAG_START_DATE);
                    String time = obj.getString(ConstantUtil.POLLING.TAG_END_DATE);
                    modelP = new ModelPolling(id, quest, date, time);
                    modelPList.add(modelP);
                    questionList.add(quest);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObj = new JSONObject(respon);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.CHOICE.TAG_TITLE);
                modelCList = new ArrayList<ModelChoice>();
                spinnerList = new ArrayList<String>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String id = obj.getString(ConstantUtil.CHOICE.TAG_ID);
                    String name = obj.getString(ConstantUtil.CHOICE.TAG_OPTION_NAME);
                    modelC = new ModelChoice(id, name);
                    modelCList.add(modelC);
                    spinnerList.add(name);
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
            ArrayAdapter<String> spinnerArrayAdapter;
            spinnerArrayAdapter = new ArrayAdapter<String>(getContext().getApplicationContext(), R.layout.item_spinner, spinnerList);

            if (questionList.size() == 1) {
                textView1.setText(questionList.get(0));
                textView2.setText("");
                textView3.setText("");
                //show option 1
                spinnerA1.setAdapter(spinnerArrayAdapter);
                spinnerA2.setAdapter(spinnerArrayAdapter);
                //hidden option 2
                qc2a.setText("");
                qc2b.setText("");
                spinnerB1.setVisibility(View.GONE);
                spinnerB2.setVisibility(View.GONE);
                //hidden option 3
                qc3a.setText("");
                qc3b.setText("");
                spinnerC1.setVisibility(View.GONE);
                spinnerC2.setVisibility(View.GONE);
            } else if (questionList.size() == 2) {
                textView1.setText(questionList.get(0));
                textView2.setText(questionList.get(1));
                //show option 1
                spinnerA1.setAdapter(spinnerArrayAdapter);
                spinnerA2.setAdapter(spinnerArrayAdapter);
                //show option 2
                spinnerB1.setAdapter(spinnerArrayAdapter);
                spinnerB2.setAdapter(spinnerArrayAdapter);
                //hidden option 3
                textView3.setText("");
                qc3a.setText("");
                qc3b.setText("");
                spinnerC1.setVisibility(View.GONE);
                spinnerC2.setVisibility(View.GONE);
            } else if (questionList.size() == 3) {
                textView1.setText(questionList.get(0));
                textView2.setText(questionList.get(1));
                textView3.setText(questionList.get(2));
                //show option 1
                spinnerA1.setAdapter(spinnerArrayAdapter);
                spinnerA2.setAdapter(spinnerArrayAdapter);
                //show option 2
                spinnerB1.setAdapter(spinnerArrayAdapter);
                spinnerB2.setAdapter(spinnerArrayAdapter);
                //show option 3
                spinnerC1.setAdapter(spinnerArrayAdapter);
                spinnerC2.setAdapter(spinnerArrayAdapter);
            } else {

            }
        }
    }

}