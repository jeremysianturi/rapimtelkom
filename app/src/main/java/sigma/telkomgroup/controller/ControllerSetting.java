package sigma.telkomgroup.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.*;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import sigma.telkomgroup.R;
import sigma.telkomgroup.adapter.AdapterSetting;
import sigma.telkomgroup.utils.SessionManager;

/**
 * Created by biting on 25/07/16.
 */
public class ControllerSetting extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private SessionManager session;
    private String[] listMenu = {
            "About",
            "Log Out"
    };
    private Integer[] imageId = {
            R.drawable.about,
            R.drawable.logout
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.listViewSetting);
        String title = getString(R.string.nav_item_setting);

        session = new SessionManager(getApplicationContext());

        AdapterSetting adapters = new AdapterSetting(ControllerSetting.this, listMenu, imageId);
        listView.setAdapter(adapters);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listMenu[+position].equals("About")) {
                    Intent intent = new Intent(ControllerSetting.this, ControllerAbout.class);
                    startActivity(intent);
                } else {
                    new android.app.AlertDialog.Builder(ControllerSetting.this).setMessage("Are you sure you want to exit?").setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    session.logoutUser();
                                }
                            }).setNegativeButton("No", null).show();
                }

            }
        });

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle(title);

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
