package sigma.telkomgroup.controller;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.InputStream;
import java.util.HashMap;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.connection.dbConnection;
import sigma.telkomgroup.utils.Contents;
import sigma.telkomgroup.utils.QRCodeEncoder;
import sigma.telkomgroup.utils.SessionManager;

/**
 * Created by biting on 10/10/16.
 */
public class ControllerQRCode extends AppCompatActivity {


    Typeface font,fontbold;
    private Toolbar toolbar;
    private TextView textName,textwelcome,text;
    private ImageView imageCode;
    private SessionManager session;
    private dbConnection connection;
    private HashMap<String, String> userDetail;
    private final int requestCode = 1;


    private String username, password, nama, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);


//        TextView
        font = Typeface.createFromAsset(ControllerQRCode.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerQRCode.this.getAssets(), "fonts/Montserrat-SemiBold.otf");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        textName = (TextView) findViewById(R.id.textWelcomePresensi);
        textName.setTypeface(fontbold);
//        textwelcome= (TextView) findViewById(R.id.textWelcome);
//        textwelcome.setTypeface(fontbold);
//        textwelcome.setVisibility(View.GONE);
        text= (TextView) findViewById(R.id.textView5);
        text.setTypeface(fontbold);
        imageCode = (ImageView) findViewById(R.id.imageQRCode);



        session = new SessionManager(ControllerQRCode.this);
        userDetail = session.getUserDetails();
        nama = userDetail.get(ConstantUtil.LOGIN.TAG_NAME);
        username = userDetail.get(ConstantUtil.LOGIN.TAG_USER_NAME);
        password = userDetail.get(ConstantUtil.LOGIN.TAG_PASS);
        code = userDetail.get(ConstantUtil.LOGIN.TAG_QRCODE);

        Log.d("session login", nama);
        Log.d("session login", username);
        Log.d("session login", password);
//        Log.d("session login", code);

        String title = getString(R.string.nav_item_qrcode);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Presence");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textName.setText(nama);

        System.out.println("check value tagID : " + ConstantUtil.LOGIN.TAG_ID);
        String link = ConstantUtil.URL.SERVER + "presensi?id=" + userDetail.get(ConstantUtil.LOGIN.TAG_ID);
        System.out.println("check value : \n link : " + link + "\n image code : " + imageCode);
        generateQRCode(link,imageCode);
        //new DownloadImageTask(imageCode).execute(code);

        Button capturedImageButton = (Button)findViewById(R.id.button2);

        capturedImageButton.setTypeface(fontbold);

        capturedImageButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (ActivityCompat.checkSelfPermission(ControllerQRCode.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ControllerQRCode.this,
                            new String[]{Manifest.permission.CAMERA},
                            1);
                    return;
                }else{
                    new IntentIntegrator(ControllerQRCode.this).setCaptureActivity(ControllerQRScanner.class).initiateScan();

                }


            }

        });

}

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    new IntentIntegrator(ControllerQRCode.this).setCaptureActivity(ControllerQRScanner.class).initiateScan();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ControllerQRCode.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                //Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                System.out.println("QR == "+result.getContents());

                String res = result.getContents();
                if(res.equals("http://rapim.digitalevent.id/presensi?id=") || res.equals("http://180.250.242.69:8017/presensi?id=")
                        || res.equals("RapimTelkomgroup")){

                    new AlertDialog.Builder(this)
                            .setMessage("Scan Complete")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent d = new Intent(getApplicationContext(), ControllerWebview.class);

                                    d.putExtra("url",ConstantUtil.URL.SERVER+"presensi?id="+session.getUserDetails().get(ConstantUtil.LOGIN.TAG_ID));
                                    d.putExtra("title","QR Code");
                                    startActivity(d);
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();


                } else if (res.equals("http://rapimcorsec.digitalevent.id/presensi?id=")){

                    new AlertDialog.Builder(this)
                            .setMessage("Scan Complete")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent d = new Intent(getApplicationContext(), ControllerWebview.class);


                                    d.putExtra("url",ConstantUtil.URL.SERVER+"presensi?id="+session.getUserDetails().get(ConstantUtil.LOGIN.TAG_ID));
                                    d.putExtra("title","QR Code");
                                    startActivity(d);
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();

                } else{
                    Toast.makeText(ControllerQRCode.this,"QR Code is not valid",Toast.LENGTH_SHORT).show();
                }

            }

        }else{
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void generateQRCode(String qrInputText, ImageView myImage) {
        //Find screen size
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            //ImageView myImage = (ImageView) findViewById(R.id.imageCode);
            myImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
