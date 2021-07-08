package sigma.telkomgroup.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import sigma.telkomgroup.R;

public class ControllerGame2019 extends AppCompatActivity {

    private TextView title;
    private TextView dates;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_game2019);

        toolbar = (Toolbar) findViewById(R.id.toolbar);


        WebView myWebView = (WebView) findViewById(R.id.webViewGames);
        myWebView.loadUrl("https://kahoot.it/");
//        myWebView.setBackgroundResource(R.drawable.lbg);
//        myWebView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);

//        WebSettings settings = webView.getSettings();
//        settings.setDomStorageEnabled(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });


        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Games");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
