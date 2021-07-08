package sigma.telkomgroup.controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.utils.SessionManager;

/**
 * Created by biting on 10/10/16.
 */
public class ControllerProfil extends AppCompatActivity {

    Typeface font,fontbold;
    private Toolbar toolbar;
    private TextView textName,textfeel,info;
    private TextView textCfu,fu;
    private TextView textPosition,position;
    private Button btnCode;
    private SessionManager session;
    private HashMap<String, String> userDetail;
    private String nama, cfu, posisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //        TextView
        font = Typeface.createFromAsset(ControllerProfil.this.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(ControllerProfil.this.getAssets(), "fonts/Montserrat-SemiBold.otf");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textName = (TextView) findViewById(R.id.textProfilName);
        textCfu = (TextView) findViewById(R.id.textProfilCFU);
        textPosition = (TextView) findViewById(R.id.textProfilPosition);
        btnCode = (Button) findViewById(R.id.buttonCode);
        btnCode.setTypeface(fontbold);
        textfeel = (TextView) findViewById(R.id.feel5);
        textfeel.setTypeface(fontbold);
        info = (TextView) findViewById(R.id.info);
        info.setTypeface(fontbold);
        fu = (TextView) findViewById(R.id.cfu);
        fu.setTypeface(fontbold);
        position = (TextView) findViewById(R.id.position);
        position.setTypeface(fontbold);


        session = new SessionManager(ControllerProfil.this);
        userDetail = session.getUserDetails();
        nama = userDetail.get(ConstantUtil.LOGIN.TAG_NAME);
        cfu = userDetail.get(ConstantUtil.LOGIN.TAG_CFU);
        posisi = userDetail.get(ConstantUtil.LOGIN.TAG_POSITION);

        textName.setText(nama);
        textName.setTypeface(fontbold);
        textCfu.setText(cfu);
        textCfu.setTypeface(fontbold);
        textPosition.setText(posisi);
        textPosition.setTypeface(fontbold);

        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerProfil.this, ControllerQRCode.class);
                startActivity(intent);
            }
        });
        String title = getString(R.string.nav_item_profile);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Presence");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
