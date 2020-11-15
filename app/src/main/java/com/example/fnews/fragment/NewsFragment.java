package com.example.fnews.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fnews.R;
import com.example.fnews.activity.NewsActivity;
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
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author fengzhaohao
 * @date 2020/11/2
 */
public class NewsFragment extends Fragment {

    private String channel;

    private RecyclerView mNewsRv;

    private boolean mIsVisible;
    private boolean mIsViewCreated;
    private boolean mIsLoadData;

    /**
     * 返回碎片实例
     */
    public static NewsFragment newInstance(String channel) {
        Log.i("fzh", "callbackTest, newInstance, channel = " + channel);
        NewsFragment fragment = new NewsFragment();
        //动态加载fragment，接受activity传入的值
        Bundle bundle = new Bundle();
        bundle.putString("channel", channel);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            channel = getArguments().getString("channel");
        }
    }

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                                 @Nullable Bundle savedInstanceState) {
        Log.i("fzh", "callbackTest, onCreateView, channel = " + channel);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news, null);
        initView(view);
        return view;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("fzh", "callbackTest, onCreate, channel = " + channel);
        super.onCreate(savedInstanceState);
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("fzh", "callbackTest, onActivityCreated, channel = " + channel);
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View root) {
        mNewsRv = root.findViewById(R.id.rv_news_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mNewsRv.setLayoutManager(linearLayoutManager);
        mIsViewCreated = true;

        if (mIsVisible && !mIsLoadData) {
            requestNews();
        }
    }

    private void requestNews() {
        Log.i("fzh", "NewsFragment, requestNews, channel = " + channel);

        String url = UrlObtainer.getNews(channel, 40, 0);
        OkhttpBuilder builder = new OkhttpBuilder.Builder()
                .setUrl(url)
                .setOkhttpCall(new OkhttpCall() {
                    @Override
                    public void onResponse(String json) {   // 得到 json 数据
                        Gson gson = new Gson();
                        NewsBean bean = gson.fromJson(json, NewsBean.class);
                        NewsBean.ResultBean result = bean.getResult();
                        List<NewsBean.ResultBean.ListBean> listBeans = result.getList();
                        List<NewsData> dataList = new ArrayList<>();
                        for (NewsBean.ResultBean.ListBean listBean : listBeans) {
                            dataList.add(new NewsData(listBean.getTitle(), listBean.getSrc(),
                                    listBean.getTime(), listBean.getPic(), listBean.getUrl()));
                        }


                        NewsAdapter adapter = new NewsAdapter(getContext(), dataList, new NewsAdapter.NewsListener() {
                            @Override public void onClickItem(String url) {
                                Intent intent = new Intent(getActivity(), NewsActivity.class);
                                intent.putExtra(NewsActivity.KEY_URL, url);
                                startActivity(intent);
                            }
                        });

                        // 滑到第二个 tab 时，mNewsRv 引用指向第一个 tab
                        Log.i("fzh", "recyclerTest, fragment = " + this
                                + ", recyclerview = " + mNewsRv + ", adapter = " + adapter);
                        mNewsRv.setAdapter(adapter);

                        mIsLoadData = true;
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Log.i("fzh", "beanTest, failed!");
                    }
                })
                .build();
        OkhttpUtil.getRequest(builder);
    }

    @Override public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Log.i("fzh", "callbackTest, setUserVisibleHint, channel = " + channel +
                ", setUserVisibleHint, isVisibleToUser = " + isVisibleToUser);

        mIsVisible = isVisibleToUser;
        if (isVisibleToUser && mIsViewCreated && !mIsLoadData) {
            requestNews();
        }
    }

    @Override public void onDestroyView() {
        Log.i("fzh", "callbackTest, onDestroyView, channel = " + channel);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("fzh", "callbackTest, onDestroy, channel = " + channel);
        super.onDestroy();
        mIsLoadData = false;
        mIsViewCreated = false;
    }

    @Override
    public void onDetach() {
        Log.i("fzh", "callbackTest, onDetach, channel = " + channel);
        super.onDetach();
    }
}
