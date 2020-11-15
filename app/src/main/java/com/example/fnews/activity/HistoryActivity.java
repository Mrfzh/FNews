package com.example.fnews.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.fnews.R;
import com.example.fnews.adapter.NewsAdapter;
import com.example.fnews.db.DatabaseManager;
import com.example.fnews.entity.NewsData;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView mListRv;

    private List<NewsData> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("历史记录");
        setContentView(R.layout.activity_history);

        initData();
        initView();
    }

    @Override protected void onResume() {
        super.onResume();

        mDataList.clear();
        mDataList = DatabaseManager.getInstance().queryAllHistory();
        updateAdapter();
    }

    private void initData() {
        mDataList = DatabaseManager.getInstance().queryAllHistory();
    }

    private void initView() {
        mListRv = findViewById(R.id.rv_history_list);
        mListRv.setLayoutManager(new LinearLayoutManager(this));
        updateAdapter();
    }

    private void updateAdapter() {
        NewsAdapter adapter = new NewsAdapter(this, mDataList, new NewsAdapter.NewsListener() {
            @Override public void onClickItem(String url) {
                Intent intent = new Intent(HistoryActivity.this, NewsActivity.class);
                intent.putExtra(NewsActivity.KEY_URL, url);
                startActivity(intent);
            }
        });
        mListRv.setAdapter(adapter);
    }
}
