package com.example.fnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fnews.adapter.NewsPagerAdapter;
import com.example.fnews.entity.NewsBean;
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
    private List<String> mPageTitleList;    //tab的标题

    private static final int TAB_NUM = 3;   //标签数
//    private List<String> mStrs = Arrays.asList("头条","新闻","国内","国际","政治","财经",
//            "体育","娱乐","军事","教育","科技","NBA","股票","星座","女性","健康","育儿");

    private List<String> mStrs = Arrays.asList("头条","新闻","国内");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTestBtn = findViewById(R.id.btn_main_test);
//        mTestBtn.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View view) {
//                requestTest();
//            }
//        });

        initVariable();

        initView();
    }

    private void initVariable() {
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < TAB_NUM; i++) {
            mFragmentList.add(NewsFragment.newInstance(mStrs.get(i)));
        }

        mPageTitleList = new ArrayList<>();
        for (int i = 0; i < TAB_NUM; i++) {
            mPageTitleList.add(mStrs.get(i));
        }
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp_main_news_pager);
        mViewPager.setAdapter(new NewsPagerAdapter(getSupportFragmentManager(), mFragmentList, mPageTitleList));

        mTabLayout = findViewById(R.id.tl_main_news_tab);
        mTabLayout.setupWithViewPager(mViewPager);  //将TabLayout与ViewPager关联
    }

    private void requestTest() {
        String url = UrlObtainer.getNews("头条", 10, 0);
        OkhttpBuilder builder = new OkhttpBuilder.Builder()
                .setUrl(url)
                .setOkhttpCall(new OkhttpCall() {
                    @Override
                    public void onResponse(String json) {   // 得到 json 数据
                        Gson gson = new Gson();
                        NewsBean bean = gson.fromJson(json, NewsBean.class);
                        Log.i("fzh", "beanTest, bean = " + bean);
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Log.i("fzh", "beanTest, failed!");
                    }
                })
                .build();
        OkhttpUtil.getRequest(builder);
    }
}
