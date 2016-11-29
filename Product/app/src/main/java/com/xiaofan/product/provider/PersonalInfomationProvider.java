package com.xiaofan.product.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.xiaofan.product.db.PersonalInfomationHelper;
import com.xiaofan.product.domain.constant.Constant;
import com.xiaofan.product.domain.constant.SqlConstant;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.SharedPreferencesUtil;

/**
 * @author: 范建海
 * @createTime: 2016/11/28 15:09
 * @className:  PersonalInfomationProvider
 * @description:
 * @changed by:
 */
public class PersonalInfomationProvider extends ContentProvider {
    //该ContentProvider所返回的数据类型的定义，取多条数据
    public static final String CONTENT_TYPE = "vnd.android.crusor.dir/vnd.firstprovider.user";
    //该ContentProvider所返回的数据类型的定义，取一条数据
    public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd.firstprovider.user";

//    public static final int INCOMING_USER_COLLECTION = 1;
//    public static final int INCOMING_USER_SINGLE = 2;
/*
    static {
        //常量UriMatcher.NO_MATCH表示不匹配任何路径的返回码
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FirstProviderMetaData.AUTHORIY, "/users", INCOMING_USER_COLLECTION);
        uriMatcher.addURI(FirstProviderMetaData.AUTHORIY, "/users/#", INCOMING_USER_SINGLE);#号为通配符switch
    }

    switch(uriMatcher.match(uri)){
        case INCOMING_USER_COLLECTION:
            return UserTableMetaData.CONTENT_TYPE;
        case INCOMING_USER_SINGLE:
            return UserTableMetaData.CONTENT_TYPE_ITEM;
        default:
            throw new IllegalArgumentException("Unknown URI" + uri);
    }

    //生成后的Uri为：content://com.example.testcp.FirstContentProvider/users/1
    Uri uri = Uri.parse("content://com.example.testcp.FirstContentProvider/users")
    Uri insertedUserUri = ContentUris.withAppendedId(uri, 1);

    //获取的结果为:1
    Uri insertedUserUri = Uri.parse("content://com.example.testcp.FirstContentProvider/users/1")
    long userId = ContentUris.parseId(insertedUserUri);

    */



    // 数据库路径匹配
    private static UriMatcher uriMatcher;

    private SQLiteDatabase mSqliteDatabase;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(SqlConstant.PERSONAL_INFO_AUTHORITY,SqlConstant.TB_BASIC_PERSONAL_INFO_NAME,SqlConstant.URI_PERSONAL_INFO_CODE);
        uriMatcher.addURI(SqlConstant.PERSONAL_INFO_AUTHORITY,SqlConstant.TB_LANGUAGE_NAME,SqlConstant.URI_LANGUAGE_CODE);
    }

    /**
     * 获取数据库实例
     */
    private void getWritableDatabase() {
        if(mSqliteDatabase == null || !mSqliteDatabase.isOpen()) {
            mSqliteDatabase = SQLiteDatabase.openDatabase(Constant.DB_PATH + SqlConstant.DB_PERSONAL_INFO_NAME_PREFIX + Constant.DEFAULT_USER_ID + SqlConstant.DB_EXTENSION_NAME, null, SQLiteDatabase.OPEN_READWRITE);

            if (mSqliteDatabase != null) {
                LogUtil.e("getWritableDatabase: " + mSqliteDatabase.isOpen());
            }

        }
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
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
}
