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

    /**
     * 返回碎片实例
     */
    public static NewsFragment newInstance(String channel) {
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

        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news, null);
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        requestNews();
    }

    private void initView() {
        mNewsRv = getActivity().findViewById(R.id.rv_news_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mNewsRv.setLayoutManager(linearLayoutManager);
    }

    private void requestNews() {
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
                                    listBean.getTime(), listBean.getPic(), listBean.getUrl(),
                                    listBean.getWeburl()));
                        }

                        NewsAdapter adapter = new NewsAdapter(getContext(), dataList, new NewsAdapter.NewsListener() {
                            @Override public void onClickItem(String url) {
                                Intent intent = new Intent(getActivity(), NewsActivity.class);
                                intent.putExtra(NewsActivity.KEY_URL, url);
                                startActivity(intent);
                            }
                        });
                        mNewsRv.setAdapter(adapter);
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
