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
    // 数据库帮助帮助类实例
    private static CustomSqliteOpenHelper instance;



    /**
     * 构造数据库方法
     * @param context 上下文
     * @param dbName 数据库名
     * @param dbVersion 当前版本
     */
    private CustomSqliteOpenHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlConstant.TB_TEST_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * 获取数据库帮助类的单例方法
     * @param context 上下文
     * @param dbName 数据库名称
     * @param dbVersion 数据库版本号
     * @return
     */
    public static CustomSqliteOpenHelper getInstance(Context context,String dbName, int dbVersion) {
        if(instance == null)
            synchronized (CustomSqliteOpenHelper.class) {
                if(instance == null)
                    instance = new CustomSqliteOpenHelper(context,dbName,dbVersion);
            }
        return instance;

    }
}
