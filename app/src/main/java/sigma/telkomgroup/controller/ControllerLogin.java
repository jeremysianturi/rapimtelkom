package sigma.telkomgroup.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.model.ModelUser;
import sigma.telkomgroup.utils.SessionManager;


/**
 * Created by biting on 08/03/16.
 */
public class ControllerLogin extends Activity {

    Typeface font,fontbold;
    String apiKey;
    private EditText editUser;
    private EditText editPassword;
    private Button btnLogin;
    private CheckBox checkBox;
    private ProgressDialog progressDialog;
    private ModelUser modelUser;
    private dbConnection connection;
    private SessionManager session;


    public static boolean isOnline(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        connection = new dbConnection();
        setContentView(R.layout.activity_login);
        if (isOnline(ControllerLogin.this)) {
            login();
        } else {
            Toast.makeText(ControllerLogin.this, "No Connection", Toast.LENGTH_SHORT).show();
        }
//        displayFirebaseRegId();
    }

//    private void displayFirebaseRegId() {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        String regId = pref.getString("regId", null);
//
//        Log.e(TAG, "Firebase reg id: " + regId);
//
//        if (!TextUtils.isEmpty(regId))
//            Toast.makeText(this, "Firebase Reg Id: " + regId, Toast.LENGTH_SHORT).show();
////            tgl.setText("Firebase Reg Id: " + regId);
//        else
//            Toast.makeText(this, "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT).show();
////        tgl.setText("Firebase Reg Id is not received yet!");
//    }

    public void reset() {
        editUser.setText("");
        editPassword.setText("");
        editUser.setFocusable(true);
    }

    public void login() {

        //        TextView
        font = Typeface.createFromAsset(ControllerLogin.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerLogin.this.getAssets(), "fonts/Montserrat-SemiBold.otf");
        editUser = (EditText) findViewById(R.id.EditUser);
        editUser.setTypeface(font);
        editPassword = (EditText) findViewById(R.id.EditPassword);
        editPassword.setTypeface(font);
        btnLogin = (Button) findViewById(R.id.BtnLogin);
        btnLogin.setTypeface(font);
//        checkBox = (CheckBox) findViewById(R.id.CheckShow);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editUser.getText().toString();
                String pass = editPassword.getText().toString();

                if (!user.trim().isEmpty() && !pass.trim().isEmpty()) {
                    modelUser = new ModelUser();
                    modelUser.setUsername(user);
                    new LoginProcess().execute();
                } else {
                    reset();
                    Toast.makeText(ControllerLogin.this, "Please complete required field", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // checkbox status is changed from uncheck to checked.
//                if (!isChecked) {
//                    // show password
//                    editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                } else {
//                    // hide password
//                    editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                }
//            }
//        });
    }

    public void onBackPressed() {

        new AlertDialog.Builder(this).setMessage("Are you sure you want to exit?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }).setNegativeButton("No", null).show();
    }

    private class LoginProcess extends AsyncTask<String, String, String> {
        String user = editUser.getText().toString();
        String pass = editPassword.getText().toString();

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ControllerLogin.this);
            progressDialog.setMessage("Verification..");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            modelUser = new ModelUser();
            modelUser.setIdDevice("android");
            modelUser.setVersion_apk(getString(R.string.version_device));
            modelUser.setUsername(user);
            System.out.println("check username at login : " + user);
            modelUser.setPassword(pass);
            System.out.println("check password at login : " + pass);

            String respon = connection.sendPostRequestLogin(modelUser, connection.urlLogin);
            System.out.println("response login" + respon);
            Log.d("response login", respon);
            try {

                connection = new dbConnection();
                JSONObject jsonObj = new JSONObject(respon);

                for (int i = 0; i < jsonObj.length(); i++) {
                    String status = jsonObj.getString(ConstantUtil.LOGIN.TAG_STATUS);
                    modelUser.setSuccess(status);

                    String id = jsonObj.getString(ConstantUtil.LOGIN.TAG_ID);
                    String username = jsonObj.getString(ConstantUtil.LOGIN.TAG_USER_NAME);
                    String name = jsonObj.getString(ConstantUtil.LOGIN.TAG_NAME);
                    String password = modelUser.getPassword();
                    String cfu = jsonObj.getString(ConstantUtil.LOGIN.TAG_CFU);
                    String pos = jsonObj.getString(ConstantUtil.LOGIN.TAG_POSITION);
                    String qrcode = jsonObj.getString(ConstantUtil.LOGIN.TAG_QRCODE);

                    Log.d("controller login Log", id);
                    Log.d("controller login Log", username);
                    Log.d("controller login Log", name);
                    Log.d("controller login Log", password);
                    Log.d("controller login Log", cfu);
                    Log.d("controller login Log", pos);

                    System.out.println("Check id at login : " + id);
                    System.out.println("Check username at login : " + username);
                    System.out.println("Check name at login : " + name);
                    System.out.println("Check password at login : " + password);
                    System.out.println("Check cfu at login : " + cfu);
                    System.out.println("Check pos at login : " + pos);

                    session.createLoginSession(id, username, password, name, cfu, pos, qrcode, apiKey);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return respon;

        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (!result.equals("0")) {
                if (modelUser.getSuccess().equals("1")) {
                    Intent intent = new Intent(ControllerLogin.this, ControllerHome.class);
                    intent.putExtra("username", user);
                    intent.putExtra("password",pass);
                    startActivityForResult(intent, 1);
                    finish();
                } else {
                    Toast.makeText(ControllerLogin.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ControllerLogin.this, "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
