package sigma.telkomgroup.controller;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.utils.SessionManager;

public class ControllerPanel extends AppCompatActivity {

    private SessionManager session;

    private Toolbar toolbar;
//    private ProgressBar progressBar;

    Typeface font,fontbold;
    private HashMap<String, String> user;

    private String id;
    private String name;
    private String position;
    private String cfu_fu;

    private String [] direktorat = {"CEO", "CEO CONSUMER BUSINESS", "CEO ENTERPRISE BUSINESS", "CEO WHOLESALE & INTERNATIONAL BUSINESS", "CEO MOBILE", "CHIEF DIGITAL OFFICER", "CHIEF STRATEGIC OFFICER", "CHIEF TECHNOLOGY OFFICER", "CHIEF HUMAN CAPITAL OFFICER", "CHIEF FINANCIAL OFFICER"};
    private List<String> selectedDirektorat = new ArrayList<String>();
    private Boolean [] selectedStatus = {false,false,false,false,false,false,false,false,false,false};

    private TextView tvName;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    private EditText etQuestion;
    private Button submitButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
//        progressBar = findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.GONE);
        initView();

        session = new SessionManager(ControllerPanel.this);
        user = session.getUserDetails();

        id = user.get(ConstantUtil.LOGIN.TAG_USER_NAME);
        name = user.get(ConstantUtil.LOGIN.TAG_NAME);
        position = user.get(ConstantUtil.LOGIN.TAG_POSITION);
        cfu_fu = user.get(ConstantUtil.LOGIN.TAG_CFU);

        tvName = findViewById(R.id.tvName);
        tvName.setText(name);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);

        button1.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
        button2.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
        button3.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
        button4.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
        button5.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
        button6.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
        button7.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
        button8.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
        button9.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
        button10.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));

        etQuestion = findViewById(R.id.editTextPanel);
        etQuestion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }});
        submitButton = findViewById(R.id.submitButton);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStatus[0] == false){
                    button1.setTextColor(getResources().getColor(R.color.colorWhite));
                    button1.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC));
                    selectedStatus[0] = true;
                }
                else{
                    button1.setTextColor(getResources().getColor(R.color.colorBlack));
                    button1.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
                    selectedStatus[0] = false;
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStatus[1] == false){
                    button2.setTextColor(getResources().getColor(R.color.colorWhite));
                    button2.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC));
                    selectedStatus[1] = true;
                }
                else{
                    button2.setTextColor(getResources().getColor(R.color.colorBlack));
                    button2.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
                    selectedStatus[1] = false;
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStatus[2] == false){
                    button3.setTextColor(getResources().getColor(R.color.colorWhite));
                    button3.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC));
                    selectedStatus[2] = true;
                }
                else{
                    button3.setTextColor(getResources().getColor(R.color.colorBlack));
                    button3.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
                    selectedStatus[2] = false;
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStatus[3] == false){
                    button4.setTextColor(getResources().getColor(R.color.colorWhite));
                    button4.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC));
                    selectedStatus[3] = true;
                }
                else{
                    button4.setTextColor(getResources().getColor(R.color.colorBlack));
                    button4.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
                    selectedStatus[3] = false;
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStatus[4] == false){
                    button5.setTextColor(getResources().getColor(R.color.colorWhite));
                    button5.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC));
                    selectedStatus[4] = true;
                }
                else{
                    button5.setTextColor(getResources().getColor(R.color.colorBlack));
                    button5.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
                    selectedStatus[4] = false;
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStatus[5] == false){
                    button6.setTextColor(getResources().getColor(R.color.colorWhite));
                    button6.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC));
                    selectedStatus[5] = true;
                }
                else{
                    button6.setTextColor(getResources().getColor(R.color.colorBlack));
                    button6.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
                    selectedStatus[5] = false;
                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStatus[6] == false){
                    button7.setTextColor(getResources().getColor(R.color.colorWhite));
                    button7.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC));
                    selectedStatus[6] = true;
                }
                else{
                    button7.setTextColor(getResources().getColor(R.color.colorBlack));
                    button7.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
                    selectedStatus[6] = false;
                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStatus[7] == false){
                    button8.setTextColor(getResources().getColor(R.color.colorWhite));
                    button8.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC));
                    selectedStatus[7] = true;
                }
                else{
                    button8.setTextColor(getResources().getColor(R.color.colorBlack));
                    button8.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
                    selectedStatus[7] = false;
                }
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStatus[8] == false){
                    button9.setTextColor(getResources().getColor(R.color.colorWhite));
                    button9.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC));
                    selectedStatus[8] = true;
                }
                else{
                    button9.setTextColor(getResources().getColor(R.color.colorBlack));
                    button9.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
                    selectedStatus[8] = false;
                }
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStatus[9] == false){
                    button10.setTextColor(getResources().getColor(R.color.colorWhite));
                    button10.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC));
                    selectedStatus[9] = true;
                }
                else{
                    button10.setTextColor(getResources().getColor(R.color.colorBlack));
                    button10.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorLightGray), PorterDuff.Mode.SRC));
                    selectedStatus[9] = false;
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isSelectedEmpty = true;
                for (int i=0;i<selectedStatus.length;i++){
                    if (selectedStatus[i] == true){
                        selectedDirektorat.add(direktorat[i]);
                        isSelectedEmpty = false;
                    }
                }

                if (isSelectedEmpty == true){
                    Toast.makeText(getApplicationContext(), "Silahkan pilih Panelis terlebih dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (etQuestion.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Tuliskan pertanyaan anda terlebih dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }

                String messageDirektorat = "";

                for (int i=0;i<selectedDirektorat.size();i++){
                    if (selectedDirektorat.size() == 1){
                        messageDirektorat = selectedDirektorat.get(i);
                        break;
                    }

                    if (messageDirektorat != ""){
                        messageDirektorat = messageDirektorat + " " + selectedDirektorat.get(i);
                    }
                    else{
                        messageDirektorat = selectedDirektorat.get(i);
                    }

                    if (i == selectedDirektorat.size()-2){
                        messageDirektorat = messageDirektorat + " dan";
                    }
                    else{
                        if (i != selectedDirektorat.size()-1){
                            messageDirektorat = messageDirektorat + ",";
                        }
                    }
                }

                new android.app.AlertDialog.Builder(ControllerPanel.this).setMessage("Ajukan pertanyaan pada Panelis " + messageDirektorat + "?").setCancelable(true)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                submit();
                            }
                        }).setNegativeButton("Batal", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {
            Intent intent = new Intent(ControllerPanel.this, ControllerQuestionHistory.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        font = Typeface.createFromAsset(ControllerPanel.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerPanel.this.getAssets(), "fonts/Montserrat-SemiBold.otf");


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Ask the BoE");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void submit(){
        String question = etQuestion.getText().toString();

        for (int i=0;i<selectedDirektorat.size();i++){
            String panelis = selectedDirektorat.get(i);
            sendAsking(id,name,question,panelis,position,cfu_fu);
        }
    }

    private void sendAsking(String userID, String username, String question, String panelis, String position, String cfu_fu) {
//        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(ConstantUtil.URL.SERVER+"api_data/submitPanel")
                .addBodyParameter("user_id", userID)
                .addBodyParameter("user_name", username)
                .addBodyParameter("panel_question", question)
                .addBodyParameter("panelis", panelis)
                .addBodyParameter("position", position)
                .addBodyParameter("cfu_fu", cfu_fu)
                .setTag("Panel")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("1")) {
                                Toast.makeText(getApplicationContext(), "Pertanyaan berhasil disubmit!", Toast.LENGTH_SHORT).show();
                                etQuestion.setText("");
                                onBackPressed();

//                                Intent intent = new Intent(getApplicationContext(), MateriAskingActivity.class);
//                                startActivity(intent);
//                                progressBar.setVisibility(View.GONE);
                            } else {
//                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}
