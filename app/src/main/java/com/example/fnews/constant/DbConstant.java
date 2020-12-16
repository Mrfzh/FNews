package com.example.fnews.constant;

/**
 * @author fengzhaohao
 * @date 2020/11/15
 */
public class DbConstant {
    // 数据库名
    public static final String DB_NAME = "FNews.db";

    // 历史记录表
    public static final String TABLE_HISTORY = "TABLE_HISTORY";
    // 历史记录表的记录
    public static final String TABLE_HISTORY_ID = "TABLE_HISTORY_ID";       // 自增 id（主键）
    public static final String TABLE_HISTORY_TITLE = "TABLE_HISTORY_TITLE";
    public static final String TABLE_HISTORY_SRC = "TABLE_HISTORY_SRC";
    public static final String TABLE_HISTORY_TIME = "TABLE_HISTORY_TIME";
    public static final String TABLE_HISTORY_PIC = "TABLE_HISTORY_PIC";
    public static final String TABLE_HISTORY_URL = "TABLE_HISTORY_URL";
    public static final String TABLE_HISTORY_CHANNEL = "TABLE_HISTORY_CHANNEL";

    // 推荐记录表
    public static final String TABLE_RECOMMEND = "TABLE_RECOMMEND";
    // 推荐记录表的记录
    public static final String TABLE_RECOMMEND_ID = "TABLE_RECOMMEND_ID";
    public static final String TABLE_RECOMMEND_CHANNEL = "TABLE_RECOMMEND_CHANNEL";

    // 本地新闻表
    public static final String TABLE_LOCAL = "TABLE_LOCAL";
    // 本地新闻表的记录
    public static final String TABLE_LOCAL_ID = "TABLE_LOCAL_ID";       // 自增 id（主键）
    public static final String TABLE_LOCAL_TITLE = "TABLE_LOCAL_TITLE";
    public static final String TABLE_LOCAL_SRC = "TABLE_LOCAL_SRC";
    public static final String TABLE_LOCAL_TIME = "TABLE_LOCAL_TIME";
    public static final String TABLE_LOCAL_PIC = "TABLE_LOCAL_PIC";
    public static final String TABLE_LOCAL_URL = "TABLE_LOCAL_URL";
    public static final String TABLE_LOCAL_CHANNEL = "TABLE_LOCAL_CHANNEL";
}
