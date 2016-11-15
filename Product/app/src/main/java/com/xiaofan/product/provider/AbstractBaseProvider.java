package com.xiaofan.product.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * @author: 范建海
 * @createTime: 2016/11/14 9:47
 * @className:  CustomContentProvider
 * @description: 基类内容提供者
 * @changed by:
 */
public abstract class AbstractBaseProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

//   http://www.jb51.net/article/82806.htm
//   http://blog.csdn.net/feng4656/article/details/8138799
//    http://ask.csdn.net/questions/22929 关闭打开数据库

}
