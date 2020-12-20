package com.example.fnews.http;


/**
 * 获取相应的 url
 *
 * @author Feng Zhaohao
 * Created on 2020/11/2
 */
public class UrlObtainer {

    /**
     * 获取新闻
     *
     * @param num 数量 默认10，最大40
     * @param start 起始位置，默认0 最大400
     */
    public static String getNews(String channel, int num, int start) {
        return "https://api.jisuapi.com/news/get?channel=" + channel +
                "&start=" + start +
                "&num=" + num +
                "&appkey=def11146a1a6dc9e";
    }

    /**
     * 获取新闻频道
     */
    public static String getChannels() {
        return "https://api.jisuapi.com/news/channel?appkey=def11146a1a6dc9e";
    }

}
