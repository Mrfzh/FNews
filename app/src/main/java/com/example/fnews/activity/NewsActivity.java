package com.example.fnews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

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
    }

    private void loadUrl() {
        mNewsWv.loadUrl(mUrl);
    }
}
