package com.example.fnews.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fnews.R;
import com.example.fnews.adapter.NewsAdapter;
import com.example.fnews.db.DatabaseManager;
import com.example.fnews.entity.NewsBean;
import com.example.fnews.entity.NewsData;
import com.example.fnews.http.OkhttpBuilder;
import com.example.fnews.http.OkhttpCall;
import com.example.fnews.http.OkhttpUtil;
import com.example.fnews.http.UrlObtainer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RecommendActivity extends AppCompatActivity {

    public static final String KEY_RECOMMEND_CHANNELS = "key_recommend_channels";
    private static final int ALL_COUNT = 40;
    private static final int OTHER_CHANNEL = 3;
    private static final int ALL_CHANNEL = 5;

    private RecyclerView mListRv;

    private List<NewsData> mDataList = new ArrayList<>();
    private AtomicInteger mCount = new AtomicInteger(ALL_CHANNEL);

    private Map<String, Integer> mCountMap = new HashMap<>();
    private List<String> mChannelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("新闻推荐");
        setContentView(R.layout.activity_recommend);

        initData();
        initView();
    }

    private void initView() {
        mListRv = findViewById(R.id.rv_recommend_list);
        mListRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        String listStr = getIntent().getStringExtra(KEY_RECOMMEND_CHANNELS);
        if (listStr != null) {
            for (String s : listStr.split("-")) {
                mChannelList.add(s);
            }
        }

        if (mChannelList.isEmpty()) {
            Toast.makeText(RecommendActivity.this, "获取 channels 失败", Toast.LENGTH_SHORT).show();
            return;
        }

        requestDb();
        ensureCount();
    }

    private void requestDb() {
        long count = DatabaseManager.getInstance().getRecommendCount();
        if (count > 100) {
            int dec = (int) (count - 100);
            DatabaseManager.getInstance().deleteRecommend(dec);
        }

        List<String> dbChannels = DatabaseManager.getInstance().queryAllRecommend();
        for (String channel : dbChannels) {
            if (mCountMap.containsKey(channel)) {
                int newCount = mCountMap.get(channel) + 1;
                mCountMap.put(channel, newCount);
            } else {
                mCountMap.put(channel, 1);
            }
        }
    }

    private void ensureCount() {
        Collections.sort(mChannelList, new Comparator<String>() {
            @Override public int compare(String s, String t1) {
                int c1 = mCountMap.containsKey(s)? mCountMap.get(s) : 0;
                int c2 = mCountMap.containsKey(t1)? mCountMap.get(t1) : 0;
                return c1 - c2;
            }
        });

        float allCount = ALL_COUNT;
        int recommendCount = (int) (allCount * 0.85);
        int otherCount = (int) (allCount * 0.05);

        String channel1 = mChannelList.get(0);
        int count1 = mCountMap.containsKey(channel1) ? mCountMap.get(channel1) : 1;
        String channel2 = mChannelList.get(1);
        int count2 = mCountMap.containsKey(channel2) ? mCountMap.get(channel2) : 1;

        int realCount1 = (int) (((float) count1 / (count1 + count2)) * recommendCount);
        int realCount2 = (int) (((float) count2 / (count1 + count2)) * recommendCount);
        List<String> otherChannels = getOtherChannel(OTHER_CHANNEL);

        mCount.set(2 + otherChannels.size());
        requestNews(channel1, realCount1);
        requestNews(channel2, realCount2);
        for (int i = 0; i < otherChannels.size(); i++) {
            requestNews(otherChannels.get(i), otherCount);
        }
    }

    private List<String> getOtherChannel(int count) {
        HashSet<String> res = new HashSet<>();
        while (res.size() <= count) {
            Random random = new Random();
            int i = random.nextInt(mChannelList.size() - 2) + 2;
            String channel = mChannelList.get(i);
            res.add(channel);
        }

        return new ArrayList<>(res);
    }

    private void requestNews(final String channel, int count) {
        String url = UrlObtainer.getNews(channel, count, 0);
        OkhttpBuilder builder = new OkhttpBuilder.Builder()
                .setUrl(url)
                .setOkhttpCall(new OkhttpCall() {
                    @Override
                    public void onResponse(String json) {   // 得到 json 数据
                        Gson gson = new Gson();
                        NewsBean bean = gson.fromJson(json, NewsBean.class);
                        NewsBean.ResultBean result = bean.getResult();
                        List<NewsBean.ResultBean.ListBean> listBeans = result.getList();
                        for (NewsBean.ResultBean.ListBean listBean : listBeans) {
                            int index = new Random().nextInt(mDataList.size() + 1) - 1;
                            if (index < 0) {
                                index = 0;
                            }
                            mDataList.add(index, new NewsData(listBean.getTitle(), listBean.getSrc(),
                                    listBean.getTime(), listBean.getPic(), listBean.getUrl(), channel));
                        }

                        endRequest();
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        endRequest();
                    }
                })
                .build();
        OkhttpUtil.getRequest(builder);
    }

    private void endRequest() {
        mCount.decrementAndGet();
        if (mCount.get() == 0) {
            // 请求完毕，展示数据
            show();
        }
    }

    private void show() {
        NewsAdapter adapter = new NewsAdapter(this, mDataList, new NewsAdapter.NewsListener() {
            @Override public void onClickItem(String url) {
                Intent intent = new Intent(RecommendActivity.this, NewsActivity.class);
                intent.putExtra(NewsActivity.KEY_URL, url);
                startActivity(intent);
            }
        });
        mListRv.setAdapter(adapter);
    }
}
