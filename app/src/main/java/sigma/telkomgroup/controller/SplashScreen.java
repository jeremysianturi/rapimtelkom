package sigma.telkomgroup.controller;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;

import sigma.telkomgroup.BuildConfig;
import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.utils.SessionManager;

/**
 * Created by biting on 19/04/16.
 */
public class SplashScreen extends Activity {

    private static final int REQUEST_PERMISSIONS = 1;
    private static final int SPLASH_TIME_OUT = 3000;
    private String TAG = "permission";
    private dbConnection connection;
    private String versionDevice;
    private String versionServer;
    private int deviceVersionCode;
    private String dbVersionCode;
    private String namaFile, namaURL;
    private ProgressDialog progressDialog;
    checkVersion.DownloadFile downloadFile;
    private SessionManager session;
    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        dialog = new ProgressDialog(SplashScreen.this);
        progressDialog = new ProgressDialog(SplashScreen.this);
        versionDevice = BuildConfig.VERSION_NAME;
        session = new SessionManager(SplashScreen.this);

//        versionServer = "";
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        new checkVersion().execute();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isStoragePermissionEnabled()) {
                    new checkVersion().execute();
                } else {
                    requestStoragePermission();
                }
            }
        }, SPLASH_TIME_OUT);


        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadFile.cancel(true);
            }
        });

        // get version code
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
//            deviceVersionCode = pInfo.versionName;
            deviceVersionCode = BuildConfig.VERSION_CODE;

            System.out.println("device version code : " + deviceVersionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }


    private void showDialogPerm(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public boolean isStoragePermissionEnabled() {
        return ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestStoragePermission() {
        showDialogPerm("The Permissions are required for this application. Please allow storage permission.",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
                        }
                    }
                });
        return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        if (requestCode == REQUEST_PERMISSIONS) {
            Log.d(TAG, "code    ---    " + requestCode);
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
                    new checkVersion().execute();
                    //else any one or both the permissions are not granted
                } else {
                    Log.d(TAG, "Some permissions are not granted ask again ");
                    //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                    // shouldShowRequestPermissionRationale will return true
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
                        new checkVersion().execute();
                        Log.d("yes", "masuk");
                    }
                }
            }
        } else {
            Log.d("hasil code", String.valueOf(requestCode));
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("SETTINGS", okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    private class checkVersion extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            dialog.setMessage("check version...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            connection = new dbConnection();
            String response = connection.sendGetRequest(dbConnection.urlVersion);
            System.out.println("response get version : " + response);
            System.out.println("version device: " + versionDevice);
            System.out.println(response + "testt");

            try {

                JSONObject jsonObj = new JSONObject(response);
                System.out.println(jsonObj + "UPDATEVERSI");
                JSONArray jsonArray = jsonObj.getJSONArray(ConstantUtil.CHECK_VERSION.TAG_TITLE);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    // DB
                    String versionName = obj.getString(ConstantUtil.CHECK_VERSION.TAG_NUMBER);
                    String versionCode = obj.getString(ConstantUtil.CHECK_VERSION.TAG_NOTE);
                    System.out.println("DB = check version name: " + versionName + "dan version codenya :" + versionCode);
                    String url = obj.getString(ConstantUtil.CHECK_VERSION.TAG_URL);

                    versionServer = versionName;
                    dbVersionCode = versionCode;
                    namaURL = url;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            System.out.println(versionDevice + "-hgwkrhbke-" + versionServer);
            if (versionServer.equalsIgnoreCase(versionDevice)) {       // yang lama pake equal

                // convert String to Int
                int dbVCodeInteger = Integer.parseInt(dbVersionCode);
                System.out.println("check integer : \n device : " + deviceVersionCode + "\n db : " + dbVCodeInteger);

                if (deviceVersionCode <= dbVCodeInteger) {       // sini
                    System.out.println("masuk ke sudah up" +
                            " to date");
                    if (session.isLoggedIn()) {
                        Intent intent = new Intent(SplashScreen.this, ControllerHome.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashScreen.this, ControllerLogin.class);
                        startActivity(intent);
                    }
                } else {
                    System.out.println("masuk ke belom up to date");
                    if (session.isLoggedIn()) {
                        session.logoutUpdate();
                    }
                    new AlertDialog.Builder(SplashScreen.this)
                            .setMessage("New Release is Available! You must download the latest APK")
                            .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                        Uri webpage = Uri.parse(ConstantUtil.CHECK_VERSION.TAG_URL);
                            Uri webpage = Uri.parse("https://play.google.com/store/apps/details?id=sigma.telkomgroup");
                            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                            startActivity(myIntent);

//                        new DownloadFile().execute(namaURL, "rapimTelkom.apk");
                        }
                    }).show();
                }
            }
        }

        class DownloadFile extends AsyncTask<String, Integer, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Create progress dialog
                progressDialog = new ProgressDialog(SplashScreen.this);
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
                    OutputStream output = new FileOutputStream(filepath + "/Download/" + namaFile);

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
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setProgress(progress[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                progressDialog.dismiss();
                if (result != null) {
                    Toast.makeText(SplashScreen.this, "Download error: " + result, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(SplashScreen.this, "File downloaded", Toast.LENGTH_SHORT).show();

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
}
