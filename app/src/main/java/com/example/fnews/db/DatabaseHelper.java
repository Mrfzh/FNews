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
            + DbConstant.TABLE_HISTORY_URL + " text)";

//    // 创建书架书籍信息表
//    private static final String CREATE_TABLE_BOOKSHELF_NOVEL = "create table " + Constant.TABLE_BOOKSHELF_NOVEL
//            + " (" + Constant.TABLE_BOOKSHELF_NOVEL_NOVEL_URL + " text primary key, "
//            + Constant.TABLE_BOOKSHELF_NOVEL_NAME + " text, "
//            + Constant.TABLE_BOOKSHELF_NOVEL_COVER + " text, "
//            + Constant.TABLE_BOOKSHELF_NOVEL_CHAPTER_INDEX + " int, "
//            + Constant.TABLE_BOOKSHELF_NOVEL_TYPE + " int, "
//            + Constant.TABLE_BOOKSHELF_NOVEL_POSITION + " int, "
//            + Constant.TABLE_BOOKSHELF_NOVEL_SECOND_POSITION + " int)";

    DatabaseHelper(Context context, String name,
                   SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HISTORY);
//        db.execSQL(CREATE_TABLE_BOOKSHELF_NOVEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
