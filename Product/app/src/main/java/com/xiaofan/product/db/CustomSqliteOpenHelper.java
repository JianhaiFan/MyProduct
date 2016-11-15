package com.xiaofan.product.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xiaofan.product.domain.constant.SqlConstant;

/**
 * @author: 范建海
 * @createTime: 2016/11/13 17:49
 * @className:  CustomSqliteOpenHelper
 * @description: 数据库帮助类
 * @changed by:
 */
public class CustomSqliteOpenHelper extends SQLiteOpenHelper{

    /**
     * 构造数据库方法
     * @param context 上下文
     * @param name 数据库名
     * @param version 当前版本
     */
    public CustomSqliteOpenHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlConstant.TB_TEST_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
