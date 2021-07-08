package sigma.telkomgroup.controller;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;

/**
 * Created by Biting on 10/19/2017.
 */

public class ControllerGalleryDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);

        Intent getIntent = getIntent();
        String id_user = getIntent.getStringExtra(ConstantUtil.LOGIN.TAG_ID);
        String id_selfie = getIntent.getStringExtra(ConstantUtil.GALLERY.TAG_ID);

        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("http://telkomgroup.digitalevent.id/selfiediscuss?selfie_id=" + id_selfie + "&user_id=" + id_user);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Do something

            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        String title = getString(R.string.nav_item_gallery);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ControllerGalleryDetail.this, ControllerHome.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
