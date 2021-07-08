package sigma.telkomgroup.controller;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.drawer.FragmentDrawer;
import sigma.telkomgroup.model.ModelUser;
import sigma.telkomgroup.model.ModelVersion;
import sigma.telkomgroup.utils.SessionManager;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private SessionManager session;
    private ProgressDialog progressDialog;
    private ModelUser modelUser;
    private ModelVersion modelVersion;
    private dbConnection connection;
    //check user
    private HashMap<String, String> user;
    private String pass, username;
    String apiKey;
    private String versionDevice;
    private String versionServer;
    private String namaFile, namaURL;
    private String TAG = "permission";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        versionDevice = getString(R.string.version_device);

        session = new SessionManager(getApplicationContext());
        session.isLoggedIn();
        user = session.getUserDetails();
        username = user.get(ConstantUtil.LOGIN.TAG_USER_NAME);

        pass = user.get(ConstantUtil.LOGIN.TAG_PASS);
        if(!session.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(getApplicationContext(), ControllerLogin.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            getApplicationContext().startActivity(i);
        } else {
            if (checkAndRequestPermissions()) {
            }

            mToolbar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
            drawerFragment.setDrawerListener(this);

            new LoginProcess().execute();
            displayView(0);
        }
    }


    private boolean checkAndRequestPermissions() {
        int perStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int perCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (perStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (perCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "all permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                            showDialogOK("The Permissions are required for this application",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    //checkAndRequestPermissions();
                                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                            Uri.fromParts("package", getPackageName(), null));
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            Log.d("yes","masuk");
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("SETTINGS", okListener)
                .create()
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.menuLogout :

                return true;

            case R.id.changePassword :

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (position) {
            case 0:
                //fragment = new ControllerNews();
                //title = getString(R.string.nav_item_news);
                break;
            case 1:
                //fragment = new ControllerAgendaRundown();
                //title = getString(R.string.nav_item_rundown);
                break;
            case 2:
                //fragment = new ControllerAgendaVenue();
                //title = getString(R.string.nav_item_venue);
                break;
            case 3:
                //fragment = new ControllerAgendaContent();
                //title = getString(R.string.nav_item_content);
                break;
            case 4:
                //fragment = new ControllerSelfie();
                //title = getString(R.string.nav_item_selfie);
                break;
            case 5:
                //fragment = new ControllerShoutbox();
                //title = getString(R.string.nav_item_shoutbox);
                break;
            case 6:
                //fragment = new ControllerFeedback();
                //title = getString(R.string.nav_item_feedback);
                break;
            case 7:
//                new AlertDialog.Builder(this).setMessage("Are you sure you want to exit?").setCancelable(true)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                session.logoutUser();
//                            }
//                        }).setNegativeButton("No", null).show();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
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

    @Override
    protected void onResume() {
        super.onResume();

        new checkVersion().execute();
    }

    private class LoginProcess extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {

            modelUser = new ModelUser();
            modelUser.setUsername(username);
            modelUser.setPassword(pass);
            modelUser.setIdDevice("android");
            modelUser.setVersion_apk(versionDevice);
            Log.d("ini log di main", modelUser.getUsername());
            Log.d("ini log di main", modelUser.getPassword());
            connection = new dbConnection();
            String respon = connection.sendPostRequestLogin(modelUser, connection.urlLogin);
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
                    String code = jsonObj.getString(ConstantUtil.LOGIN.TAG_QRCODE);

                    Log.d("controller login Log", id);
                    Log.d("controller login Log", username);
                    Log.d("controller login Log", name);
                    Log.d("controller login Log", password);

                    session.createLoginSession(id, username, password, name, cfu, pos, code, apiKey);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return respon;

        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("0")) {
                if (modelUser.getSuccess().equals("1")) {
                } else {
                    Intent intent = new Intent(MainActivity.this, ControllerLogin.class);
                    startActivityForResult(intent, 1);
                    finish();
                }
            } else {
                Toast.makeText(MainActivity.this, "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class checkVersion extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("check version...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            connection = new dbConnection();
            String response = connection.sendGetRequest(dbConnection.urlVersion);

            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.CHECK_VERSION.TAG_TITLE);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String id = obj.getString(ConstantUtil.CHECK_VERSION.TAG_ID);
                    String number = obj.getString(ConstantUtil.CHECK_VERSION.TAG_NUMBER);
                    String date = obj.getString(ConstantUtil.CHECK_VERSION.TAG_DATE);
                    String url = obj.getString(ConstantUtil.CHECK_VERSION.TAG_URL);
                    String note = obj.getString(ConstantUtil.CHECK_VERSION.TAG_NOTE);

                    versionServer = number;
                    namaURL = url;
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
            Log.d("check version", versionServer);
            Log.d("check version", versionDevice);
            Log.d("check version", namaURL);
            if (!versionServer.equalsIgnoreCase(versionDevice)) {
                Toast.makeText(MainActivity.this, "Check Your Version", Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("the latest version was available, you must download it")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                new DownloadFile().execute(namaURL,"rapimTelkom.apk");
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            } else {
                //Toast.makeText(MainActivity.this, "The latest updates have already been installed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) MainActivity.this.getSystemService(Context.POWER_SERVICE);
            // Create progress dialog
            progressDialog = new ProgressDialog(MainActivity.this);
            // Set your progress dialog Title
            progressDialog.setTitle("Updating Application");
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
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            if (result != null) {
                Toast.makeText(MainActivity.this, "Download error: " + result, Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(MainActivity.this, "File downloaded", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/" + "rapimTelkom.apk")),
                        "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
