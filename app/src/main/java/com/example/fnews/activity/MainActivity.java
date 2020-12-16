package com.example.fnews.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fnews.R;
import com.example.fnews.adapter.NewsPagerAdapter;
import com.example.fnews.db.DatabaseManager;
import com.example.fnews.entity.ChannelBean;
import com.example.fnews.fragment.NewsFragment;
import com.example.fnews.http.OkhttpBuilder;
import com.example.fnews.http.OkhttpCall;
import com.example.fnews.http.OkhttpUtil;
import com.example.fnews.http.UrlObtainer;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mTestBtn;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragmentList;   //碎片集合
    private List<String> mChannelList;    //tab的标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTestBtn = findViewById(R.id.btn_main_test);
//        mTestBtn.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View view) {
//                DatabaseManager.getInstance().deleteRecommend(2);
//            }
//        });

        requestChannel();
    }

    private void doInit() {
        initViewPagerData();
        initView();
    }

    private void requestChannel() {
        String url = UrlObtainer.getChannels();
        OkhttpBuilder builder = new OkhttpBuilder.Builder()
                .setUrl(url)
                .setOkhttpCall(new OkhttpCall() {
                    @Override
                    public void onResponse(String json) {   // 得到 json 数据
                        Gson gson = new Gson();
                        ChannelBean bean = gson.fromJson(json, ChannelBean.class);
                        mChannelList = bean.getResult();
                        if (mChannelList.isEmpty()) {
                            mChannelList = Arrays.asList("头条","新闻","国内","国际","政治","财经",
            "体育","娱乐","军事","教育","科技","NBA","股票","星座","女性","健康","育儿");
                        }

                        doInit();
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Log.i("fzh", "beanTest, failed!");
                        if (mChannelList.isEmpty()) {
                            mChannelList = Arrays.asList("头条","新闻","国内","国际","政治","财经",
                                    "体育","娱乐","军事","教育","科技","NBA","股票","星座","女性","健康","育儿");
                        }

                        doInit();
                    }
                })
                .build();
        OkhttpUtil.getRequest(builder);
    }

    private void initViewPagerData() {
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < mChannelList.size(); i++) {
            mFragmentList.add(NewsFragment.newInstance(mChannelList.get(i)));
        }
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp_main_news_pager);
        mViewPager.setAdapter(new NewsPagerAdapter(getSupportFragmentManager(), mFragmentList, mChannelList));

        mTabLayout = findViewById(R.id.tl_main_news_tab);
        mTabLayout.setupWithViewPager(mViewPager);  //将TabLayout与ViewPager关联
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.history:
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                return true;
            case R.id.recommend:
                Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
                String str = String.join("-", mChannelList);
                intent.putExtra(RecommendActivity.KEY_RECOMMEND_CHANNELS, str);
                startActivity(intent);
                return true;
            case R.id.search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                return true;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }
}
