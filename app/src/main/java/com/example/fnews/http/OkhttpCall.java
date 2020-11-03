package com.example.fnews.http;

/**
 * @author Feng Zhaohao
 * Created on 2020/11/2
 */
public interface OkhttpCall {
    void onResponse(String json);
    void onFailure(String errorMsg);
}
