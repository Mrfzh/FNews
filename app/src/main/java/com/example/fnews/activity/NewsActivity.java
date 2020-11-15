package com.example.fnews.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.fnews.R;

public class NewsActivity extends AppCompatActivity {

    public static final String KEY_URL = "key_url";

    private WebView mNewsWv;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initData();
        initView();
        loadUrl();
    }

    private void initData() {
        mUrl = getIntent().getStringExtra(KEY_URL);
    }

    private void initView() {
        mNewsWv = findViewById(R.id.wv_news_web_view);
        //启用支持javascript
        WebSettings settings = mNewsWv.getSettings();
        settings.setJavaScriptEnabled(true);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mNewsWv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                    return true;
                } catch (Exception e){
                    return false;
                }
            }
        });
    }

    private void loadUrl() {
        mNewsWv.loadUrl(mUrl);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.open:
                // 浏览器打开
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(mUrl);
                intent.setData(content_url);
                startActivity(intent);
                return true;
            case R.id.reload:
                mNewsWv.reload();
                return true;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }


}
