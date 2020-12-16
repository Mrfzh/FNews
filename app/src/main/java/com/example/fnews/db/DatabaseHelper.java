package com.example.fnews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fnews.constant.DbConstant;

/**
 * @author Feng Zhaohao
 * Created on 2020/11/15
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "fzh";

    // 创建历史记录表
    private static final String CREATE_TABLE_HISTORY = "create table " + DbConstant.TABLE_HISTORY
            + " (" + DbConstant.TABLE_HISTORY_ID + " integer primary key autoincrement, "
            + DbConstant.TABLE_HISTORY_TITLE + " text, "
            + DbConstant.TABLE_HISTORY_SRC + " text, "
            + DbConstant.TABLE_HISTORY_TIME + " text, "
            + DbConstant.TABLE_HISTORY_PIC + " text, "
            + DbConstant.TABLE_HISTORY_CHANNEL + " text, "
            + DbConstant.TABLE_HISTORY_URL + " text)";

    // 创建推荐记录表
    private static final String CREATE_TABLE_RECOMMEND = "create table " + DbConstant.TABLE_RECOMMEND
            + " (" + DbConstant.TABLE_RECOMMEND_ID + " integer primary key autoincrement, "
            + DbConstant.TABLE_RECOMMEND_CHANNEL + " text)";

    // 创建本地新闻表
    private static final String CREATE_TABLE_LOCAL = "create table " + DbConstant.TABLE_LOCAL
            + " (" + DbConstant.TABLE_LOCAL_ID + " integer primary key autoincrement, "
            + DbConstant.TABLE_LOCAL_TITLE + " text, "
            + DbConstant.TABLE_LOCAL_SRC + " text, "
            + DbConstant.TABLE_LOCAL_TIME + " text, "
            + DbConstant.TABLE_LOCAL_PIC + " text, "
            + DbConstant.TABLE_LOCAL_CHANNEL + " text, "
            + DbConstant.TABLE_LOCAL_URL + " text)";


    DatabaseHelper(Context context, String name,
                   SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HISTORY);
        db.execSQL(CREATE_TABLE_RECOMMEND);
        db.execSQL(CREATE_TABLE_LOCAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
