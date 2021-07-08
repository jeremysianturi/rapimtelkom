package sigma.telkomgroup.controller;

import androidx.appcompat.app.*;
import androidx.appcompat.widget.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import sigma.telkomgroup.model.ModelFeedback;
import sigma.telkomgroup.parser.PostJSON;
import sigma.telkomgroup.utils.SessionManager;

/**
 * Created by biting on 14/04/16.
 */
public class ControllerFeedback extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button;
    //feedback1
    private TextView textView1;
    private RadioGroup radioGroup1;
    private EditText editText1;
    //feedback2
    private TextView textView2;
    private RadioGroup radioGroup2;
    private EditText editText2;
    //feedback3
    private TextView textView3;
    private RadioGroup radioGroup3;
    private EditText editText3;
    //feedback4
    private TextView textView4;
    private RadioGroup radioGroup4;
    private EditText editText4;

    //check user
    private HashMap<String, String> user;
    private String user_id, username;

    private List<ModelFeedback> feedbackList;
    private List<String> questionList;
    private List<String> idQList;

    private SessionManager session;
    private ModelFeedback model;
    private PostJSON postJSON;
    private dbConnection connection;

    private String temaLokal;
    private String temaJson;

    private Integer pos, val1, val2, val3, val4;

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
        setContentView(R.layout.activity_feedback);
        temaLokal = "";
        if (isOnline(ControllerFeedback.this)) {
            new showQuisioner().execute();
        } else {
            Toast.makeText(ControllerFeedback.this, "No Internet connection", Toast.LENGTH_LONG).show();
        }

        session = new SessionManager(ControllerFeedback.this);
        user = session.getUserDetails();
        user_id = user.get(ConstantUtil.LOGIN.TAG_ID);
        Log.d("ini log di feedback", user_id);
        username = user.get(ConstantUtil.LOGIN.TAG_USER_NAME);
        Log.d("ini log di feedback", username);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        button = (Button) findViewById(R.id.buttonSubmitFb);
        //feedback1
        textView1 = (TextView) findViewById(R.id.textFeedback1);
        editText1 = (EditText) findViewById(R.id.editTextFeedback1);
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        //feedback2
        textView2 = (TextView) findViewById(R.id.textFeedback2);
        editText2 = (EditText) findViewById(R.id.editTextFeedback2);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        //feedback3
        textView3 = (TextView) findViewById(R.id.textFeedback3);
        editText3 = (EditText) findViewById(R.id.editTextFeedback3);
        radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
        //feedback4
        textView4 = (TextView) findViewById(R.id.textFeedback4);
        editText4 = (EditText) findViewById(R.id.editTextFeedback4);
        radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);

        String title = getString(R.string.nav_item_feedback);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.getTema().get("tema_feedback");
                if(session.getTema().get("tema_feedback")==null) {
                    sendData();
                    model.setTema_name(temaJson);
                    session.createFeedbackSession(temaJson);
                }

                if (!session.getTema().get("tema_feedback").equals(temaJson)) {
                    sendData();
                    model.setTema_name(temaJson);
                    session.createFeedbackSession(temaJson);
                } else {
                    Toast toast = Toast.makeText(ControllerFeedback.this, "You already submitted feedback", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pos = radioGroup1.indexOfChild(findViewById(checkedId));

                switch (pos) {
                    case 0:
                        val1 = 1;
                        break;
                    case 1:
                        val1 = 2;
                        break;
                    case 2:
                        val1 = 3;
                        break;
                    case 3:
                        val1 = 4;
                        break;
                    default:
                        val1 = 1;
                        break;
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pos = radioGroup2.indexOfChild(findViewById(checkedId));

                switch (pos) {
                    case 0:
                        val2 = 1;
                        break;
                    case 1:
                        val2 = 2;
                        break;
                    case 2:
                        val2 = 3;
                        break;
                    case 3:
                        val2 = 4;
                        break;
                    default:
                        val2 = 1;
                        break;
                }
            }
        });

        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pos = radioGroup3.indexOfChild(findViewById(checkedId));

                switch (pos) {
                    case 0:
                        val3 = 1;
                        break;
                    case 1:
                        val3 = 2;
                        break;
                    case 2:
                        val3 = 3;
                        break;
                    case 3:
                        val3 = 4;
                        break;
                    default:
                        val3 = 1;
                        break;
                }
            }
        });

        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pos = radioGroup4.indexOfChild(findViewById(checkedId));

                switch (pos) {
                    case 0:
                        val4 = 1;
                        break;
                    case 1:
                        val4 = 2;
                        break;
                    case 2:
                        val4 = 3;
                        break;
                    case 3:
                        val4 = 4;
                        break;
                    default:
                        val4 = 1;
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void reset(){
        editText1.setText("");
        editText2.setText("");
        editText3.setText("");
        editText4.setText("");
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        radioGroup3.clearCheck();
        radioGroup4.clearCheck();
    }

    private void sendData() {

        String text1 = editText1.getText().toString();
        String text2 = editText2.getText().toString();
        String text3 = editText3.getText().toString();
        String text4 = editText4.getText().toString();

        JSONObject jsontitle = new JSONObject();
        JSONObject jsontitle2nd = new JSONObject();
        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();
        JSONObject json3 = new JSONObject();
        JSONObject json4 = new JSONObject();

        try {
            json1.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ADVICE, text1);
            json1.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_VALUE, val1);
            json1.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ID_QUIS, idQList.get(0));
            json1.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ID_USER, user_id);

            json2.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ADVICE, text2);
            json2.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_VALUE, val2);
            json2.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ID_QUIS, idQList.get(1));
            json2.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ID_USER, user_id);

            json3.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ADVICE, text3);
            json3.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_VALUE, val3);
            json3.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ID_QUIS, idQList.get(2));
            json3.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ID_USER, user_id);

            json4.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ADVICE, text4);
            json4.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_VALUE, val4);
            json4.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ID_QUIS, idQList.get(3));
            json4.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_ID_USER, user_id);

            jsontitle2nd.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_TITLE_1, json1);
            jsontitle2nd.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_TITLE_2, json2);
            jsontitle2nd.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_TITLE_3, json3);
            jsontitle2nd.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_TITLE_4, json4);

            jsontitle.put(ConstantUtil.SUBMIT_FEEDBACK.TAG_TITLE, jsontitle2nd);
        } catch (JSONException e) {
            Log.d("hasil exception", e.toString());
        }

        String title_1st = jsontitle.toString();
        postJSON = (PostJSON) new PostJSON(title_1st, dbConnection.serverUrl + dbConnection.urlSubmitQuisioner).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        reset();
        Toast toast = Toast.makeText(ControllerFeedback.this, "Success Submit Data..", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private class showQuisioner extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ControllerFeedback.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            connection = new dbConnection();
            String response = connection.sendGetRequest(dbConnection.urlFeedback);

            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.QUISIONER.TAG_TITLE);
                feedbackList = new ArrayList<ModelFeedback>();
                questionList = new ArrayList<String>();
                idQList = new ArrayList<String>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String quis_id = obj.getString(ConstantUtil.QUISIONER.TAG_QUIS_ID);
                    String quis = obj.getString(ConstantUtil.QUISIONER.TAG_QUESTION);
                    String temaJ = obj.getString(ConstantUtil.QUISIONER.TAG_TEMA_NAME);
                    String start = obj.getString(ConstantUtil.QUISIONER.TAG_START_DATE);
                    String end = obj.getString(ConstantUtil.QUISIONER.TAG_END_DATE);
                    model = new ModelFeedback(quis_id, quis, start, end);
                    feedbackList.add(model);
                    questionList.add(quis);
                    idQList.add(quis_id);
                    temaJson = temaJ;
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

            int size = questionList.size();
            List<String> spinList = new ArrayList<String>();
            spinList.add("Sangat Kurang");
            spinList.add("Kurang");
            spinList.add("Baik");
            spinList.add("Sangat Baik");
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ControllerFeedback.this, R.layout.item_spinner, spinList);

            Log.d("hasil size nya", String.valueOf(size));
            Log.d("hasil id usernya", username);

            if (size == 1) {
                textView1.setText(questionList.get(0));
                textView1.setVisibility(View.VISIBLE);
                radioGroup1.setVisibility(View.VISIBLE);
                editText1.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                //dispose other
                textView2.setVisibility(View.GONE);
                radioGroup2.setVisibility(View.GONE);
                editText2.setVisibility(View.GONE);
                textView3.setVisibility(View.GONE);
                radioGroup3.setVisibility(View.GONE);
                textView4.setVisibility(View.GONE);
                radioGroup4.setVisibility(View.GONE);
                editText4.setVisibility(View.GONE);
            } else if (size == 2) {
                //show option
                textView1.setText(questionList.get(0));
                textView1.setVisibility(View.VISIBLE);
                radioGroup1.setVisibility(View.VISIBLE);
                editText1.setVisibility(View.VISIBLE);

                textView2.setText(questionList.get(1));
                textView2.setVisibility(View.VISIBLE);
                radioGroup2.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                //dispose other
                textView3.setVisibility(View.GONE);
                radioGroup3.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
                textView4.setVisibility(View.GONE);
                radioGroup4.setVisibility(View.GONE);
                editText4.setVisibility(View.GONE);
            } else if (size == 3) {
                //show option
                textView1.setText(questionList.get(0));
                textView1.setVisibility(View.VISIBLE);
                radioGroup1.setVisibility(View.VISIBLE);
                editText1.setVisibility(View.VISIBLE);

                textView2.setText(questionList.get(1));
                textView2.setVisibility(View.VISIBLE);
                radioGroup2.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.VISIBLE);

                textView3.setText(questionList.get(2));
                textView3.setVisibility(View.VISIBLE);
                radioGroup3.setVisibility(View.VISIBLE);
                editText3.setVisibility(View.VISIBLE);

                button.setVisibility(View.VISIBLE);
                //dispose other
                textView4.setVisibility(View.GONE);
                radioGroup4.setVisibility(View.GONE);
                editText4.setVisibility(View.GONE);
            } else if (size == 4) {
                //show option
                textView1.setText(questionList.get(0));
                textView1.setVisibility(View.VISIBLE);
                radioGroup1.setVisibility(View.VISIBLE);
                editText1.setVisibility(View.VISIBLE);

                textView2.setText(questionList.get(1));
                textView2.setVisibility(View.VISIBLE);
                radioGroup2.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.VISIBLE);

                textView3.setText(questionList.get(2));
                textView3.setVisibility(View.VISIBLE);
                radioGroup3.setVisibility(View.VISIBLE);
                editText3.setVisibility(View.VISIBLE);

                textView4.setText(questionList.get(3));
                textView4.setVisibility(View.VISIBLE);
                radioGroup4.setVisibility(View.VISIBLE);
                editText4.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
            }
        }
    }
}
