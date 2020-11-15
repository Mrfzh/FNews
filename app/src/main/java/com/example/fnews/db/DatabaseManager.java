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
 * Created on 2019/11/11
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
}
