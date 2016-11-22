package com.xiaofan.product.provider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.xiaofan.product.db.CustomSqliteOpenHelper;
import com.xiaofan.product.domain.constant.Constant;
import com.xiaofan.product.domain.constant.SqlConstant;
import com.xiaofan.product.util.LogUtil;

import java.io.File;

/**
 * @author: 范建海
 * @createTime: 2016/11/14 9:51
 * @className:  TestContentProvider
 * @description: 联系人内容提供者
 * @changed by:
 */
public class ContactContentProvider extends AbstractBaseProvider {
    // 数据库路径匹配
    private static UriMatcher uriMatcher = null;

    private CustomSqliteOpenHelper csoHelper;

    static {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(SqlConstant.AUTHORITY,SqlConstant.TB_TEST_NAME,SqlConstant.URI_TB_TEST_CODE);

    }

    @Override
    public boolean onCreate() {
        File file = new File(Constant.DB_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }

        csoHelper = CustomSqliteOpenHelper.getInstance(getContext(),Constant.DB_PATH + SqlConstant.DB_TEST_NAME,SqlConstant.DB_TEST_VERSION);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs, String orderBy) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case SqlConstant.URI_TB_TEST_CODE:
                SQLiteDatabase db = csoHelper.getWritableDatabase();
                cursor = db.query(SqlConstant.TB_TEST_NAME,columns,selection,selectionArgs,null,null,orderBy);
                break;
        }
        return cursor;

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return super.getType(uri);
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (uriMatcher.match(uri)) {
            case SqlConstant.URI_TB_TEST_CODE:
                SQLiteDatabase db = csoHelper.getWritableDatabase();
                long id = db.insert(SqlConstant.TB_TEST_NAME,null,contentValues);
                if (id != -1) {
                    LogUtil.e("insert success!");
                }else {
                    LogUtil.e("insert failure!");
                }
                db.close();
                break;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return super.delete(uri, s, strings);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return super.update(uri, contentValues, s, strings);
    }

}
