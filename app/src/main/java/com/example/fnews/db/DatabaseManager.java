package com.example.fnews.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fnews.app.App;
import com.example.fnews.constant.DbConstant;
import com.example.fnews.entity.NewsData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Feng Zhaohao
 * Created on 2020/11/15
 */
public class DatabaseManager {
    private static final String TAG = "DatabaseManager";

    private static DatabaseManager mManager;
    private SQLiteDatabase mDb;

    private DatabaseManager() {
        SQLiteOpenHelper helper = new DatabaseHelper(
                App.getContext(), DbConstant.DB_NAME, null, 1);
        mDb = helper.getWritableDatabase();
    }

    public static DatabaseManager getInstance() {
        if (mManager == null) {
            mManager = new DatabaseManager();
        }
        return mManager;
    }

    /**
     * 插入一条新的历史记录
     */
    public void insertHistory(NewsData newsData) {
        ContentValues values = new ContentValues();
        values.put(DbConstant.TABLE_HISTORY_TITLE, newsData.getTitle());
        values.put(DbConstant.TABLE_HISTORY_SRC, newsData.getSrc());
        values.put(DbConstant.TABLE_HISTORY_TIME, newsData.getTime());
        values.put(DbConstant.TABLE_HISTORY_PIC, newsData.getPic());
        values.put(DbConstant.TABLE_HISTORY_CHANNEL, newsData.getChannel());
        values.put(DbConstant.TABLE_HISTORY_URL, newsData.getUrl());
        mDb.insert(DbConstant.TABLE_HISTORY, null, values);
    }

    /**
     * 删除一条历史记录
     */
    public void deleteHistory(String url) {
        mDb.delete(DbConstant.TABLE_HISTORY,
                DbConstant.TABLE_HISTORY_URL + " = ?", new String[]{url});
    }

    /**
     * 查询所有历史记录（较新的记录排前面）
     */
    public List<NewsData> queryAllHistory() {
        List<NewsData> res = new ArrayList<>();
        Cursor cursor = mDb.query(DbConstant.TABLE_HISTORY, null, null,
                null, null, null,null);
        if (cursor.moveToLast()) {
            do {
                NewsData newsData = new NewsData();
                newsData.setTitle(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_HISTORY_TITLE)));
                newsData.setSrc(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_HISTORY_SRC)));
                newsData.setTime(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_HISTORY_TIME)));
                newsData.setPic(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_HISTORY_PIC)));
                newsData.setChannel(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_HISTORY_CHANNEL)));
                newsData.setUrl(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_HISTORY_URL)));
                res.add(newsData);
            } while (cursor.moveToPrevious());
        }
        cursor.close();

        return res;
    }

    /**
     * 删除所有历史记录
     */
    public void deleteAllHistories() {
        mDb.delete(DbConstant.TABLE_HISTORY, null, null);
    }

    /**
     * 插入一条新的推荐记录
     */
    public void insertRecommend(String channel) {
        ContentValues values = new ContentValues();
        values.put(DbConstant.TABLE_RECOMMEND_CHANNEL, channel);
        mDb.insert(DbConstant.TABLE_RECOMMEND, null, values);
    }

    /**
     * 查询推荐记录条数
     */
    public long getRecommendCount() {
        String sql = "select count(*) from " + DbConstant.TABLE_RECOMMEND;
        Cursor cursor = mDb.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

    /**
     * 删除前 n 条推荐记录
     */
    public void deleteRecommend(int n) {
        String sql = "delete from " + DbConstant.TABLE_RECOMMEND +
                " where " + DbConstant.TABLE_RECOMMEND_ID + " in(" +
                "select " + DbConstant.TABLE_RECOMMEND_ID + " from " + DbConstant.TABLE_RECOMMEND +
                " order by " + DbConstant.TABLE_RECOMMEND_ID +
                " limit " + n + ")";
        mDb.execSQL(sql);
    }

    /**
     * 查询所有推荐记录（较新的记录排前面）
     */
    public List<String> queryAllRecommend() {
        List<String> res = new ArrayList<>();
        Cursor cursor = mDb.query(DbConstant.TABLE_RECOMMEND, null, null,
                null, null, null,null);
        if (cursor.moveToLast()) {
            do {
                res.add(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_RECOMMEND_CHANNEL)));
            } while (cursor.moveToPrevious());
        }
        cursor.close();

        return res;
    }

    /**
     * 插入一条新的本地新闻记录
     */
    public void insertLocal(List<NewsData> newsDataList) {
        ContentValues values = new ContentValues();
        for (NewsData newsData : newsDataList) {
            mDb.delete(DbConstant.TABLE_LOCAL,
                    DbConstant.TABLE_LOCAL_TITLE + " = ?",
                    new String[]{newsData.getTitle()});

            values.put(DbConstant.TABLE_LOCAL_TITLE, newsData.getTitle());
            values.put(DbConstant.TABLE_LOCAL_SRC, newsData.getSrc());
            values.put(DbConstant.TABLE_LOCAL_TIME, newsData.getTime());
            values.put(DbConstant.TABLE_LOCAL_PIC, newsData.getPic());
            values.put(DbConstant.TABLE_LOCAL_CHANNEL, newsData.getChannel());
            values.put(DbConstant.TABLE_LOCAL_URL, newsData.getUrl());
            mDb.insert(DbConstant.TABLE_LOCAL, null, values);
            values.clear();
        }
    }

    /**
     * 查询所有本地新闻记录（较新的记录排前面）
     */
    public List<NewsData> queryAllLocal() {
        List<NewsData> res = new ArrayList<>();
        Cursor cursor = mDb.query(DbConstant.TABLE_LOCAL, null, null,
                null, null, null,null);
        if (cursor.moveToLast()) {
            do {
                NewsData newsData = new NewsData();
                newsData.setTitle(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_LOCAL_TITLE)));
                newsData.setSrc(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_LOCAL_SRC)));
                newsData.setTime(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_LOCAL_TIME)));
                newsData.setPic(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_LOCAL_PIC)));
                newsData.setChannel(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_LOCAL_CHANNEL)));
                newsData.setUrl(cursor.getString(cursor.getColumnIndex(DbConstant.TABLE_LOCAL_URL)));
                res.add(newsData);
            } while (cursor.moveToPrevious());
        }
        cursor.close();

        return res;
    }

    /**
     * 删除前 n 条本地新闻记录
     */
    public void deleteLocal(int n) {
        String sql = "delete from " + DbConstant.TABLE_RECOMMEND +
                " where " + DbConstant.TABLE_RECOMMEND_ID + " in(" +
                "select " + DbConstant.TABLE_RECOMMEND_ID + " from " + DbConstant.TABLE_RECOMMEND +
                " order by " + DbConstant.TABLE_RECOMMEND_ID +
                " limit " + n + ")";
        mDb.execSQL(sql);
    }

    /**
     * 查询本地新闻记录条数
     */
    public long getLocalCount() {
        String sql = "select count(*) from " + DbConstant.TABLE_LOCAL;
        Cursor cursor = mDb.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }
}
