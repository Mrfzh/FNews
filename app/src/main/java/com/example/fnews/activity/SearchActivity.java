package com.example.fnews.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fnews.R;
import com.example.fnews.adapter.NewsAdapter;
import com.example.fnews.db.DatabaseHelper;
import com.example.fnews.db.DatabaseManager;
import com.example.fnews.entity.NewsData;
import com.example.fnews.utils.EditTextUtil;
import com.example.fnews.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mBackIv;
    private EditText mSearchBarEt;
    private TextView mSearchTv;
    private ImageView mDeleteSearchTextIv;
    private RecyclerView mListRv;

    private List<NewsData> mNewsDataList = new ArrayList<>();
    private boolean mInitNews = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);   //隐藏标题栏

        setContentView(R.layout.activity_search);

        // 更改状态栏颜色
        StatusBarUtil.setLightColorStatusBar(this);
        getWindow().setStatusBarColor(getResources().getColor(R.color.search_bg));

        initView();
    }

    private void initView() {

        mBackIv = findViewById(R.id.iv_search_back);
        mBackIv.setOnClickListener(this);

        mSearchBarEt = findViewById(R.id.et_search_search_bar);
        // 监听内容变化
        mSearchBarEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    // 隐藏删除 icon
                    mDeleteSearchTextIv.setVisibility(View.GONE);
                    // 显示软键盘
                    EditTextUtil.focusAndShowSoftKeyboard(SearchActivity.this, mSearchBarEt);
                } else {
                    // 显示删除 icon
                    mDeleteSearchTextIv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 监听软键盘
        mSearchBarEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 点击“完成”或者“下一项”
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT) {
                    // 进行搜索操作
                    doSearch();
                }
                return false;
            }
        });

        mSearchTv = findViewById(R.id.tv_search_search_text);
        mSearchTv.setOnClickListener(this);

        mDeleteSearchTextIv = findViewById(R.id.iv_search_delete_search_text);
        mDeleteSearchTextIv.setOnClickListener(this);

        mListRv = findViewById(R.id.rv_search_news);
        mListRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_back:
                finish();
                break;
            case R.id.tv_search_search_text:
                doSearch();
                break;
            case R.id.iv_search_delete_search_text:
                // 删除 EditText 内容
                mSearchBarEt.setText("");
            default:
                break;
        }
    }

    private void doSearch() {
        new Thread(new Runnable() {
            @Override public void run() {
                doSearchInSubThread();
            }
        }).start();
    }

    private void doSearchInSubThread() {
        String searchText = mSearchBarEt.getText().toString();

        if (TextUtils.isEmpty(searchText)) {
            return;
        }

        if (!mInitNews) {
            // 查询最新 200 条数据（第一次搜索的时候进行）
            long localCount = DatabaseManager.getInstance().getLocalCount();
            if (localCount > 200) {
                DatabaseManager.getInstance().deleteLocal((int) (localCount - 200));
            }
            mNewsDataList = DatabaseManager.getInstance().queryAllLocal();
            mInitNews = true;
        }

        final List<NewsData> searchResList = new ArrayList<>();
        for (NewsData newsData : mNewsDataList) {
            String title = newsData.getTitle();
            // 判断标题是否含有关键字
            if (title.contains(searchText)) {
                searchResList.add(newsData);
            }
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {
                showSearchRes(searchResList);
            }
        });
    }

    private void showSearchRes(List<NewsData> newsData) {
        NewsAdapter adapter = new NewsAdapter(this, newsData, new NewsAdapter.NewsListener() {
            @Override public void onClickItem(String url) {
                Intent intent = new Intent(SearchActivity.this, NewsActivity.class);
                intent.putExtra(NewsActivity.KEY_URL, url);
                startActivity(intent);
            }
        });
        mListRv.setAdapter(adapter);
    }
}
