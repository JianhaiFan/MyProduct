package com.xiaofan.product.activity.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaofan.product.R;
import com.xiaofan.product.activity.AbstractBaseActivity;
import com.xiaofan.product.domain.constant.SqlConstant;
import com.xiaofan.product.util.LogUtil;

/**
 * @author: 范建海
 * @createTime: 2016/11/14 10:17
 * @className:  ProviderTestActivity
 * @description: 内容提供者测试类
 * @changed by:
 */
public class ProviderTestActivity extends AbstractBaseActivity {
    // 展示搜索出来的内容
    private TextView tv_content;
    // 内容处理器
    private ContentResolver mContentResolver;

    Uri uri = Uri.parse(SqlConstant.SCHEME + SqlConstant.PERSONAL_INFO_AUTHORITY + "/" + SqlConstant.TB_TEST_NAME);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_provider);

        tv_content = (TextView) findViewById(R.id.tv_content);

        mContentResolver = getContentResolver();
    }



    public void onInsert(View view) {
        LogUtil.e("onInsert...");
        ContentValues values = new ContentValues();
        values.put("name","王二小");
        values.put("gender","boy");
        values.put("age",18);
        mContentResolver.insert(uri,values);
    }

    public void onDelete(View view) {
        LogUtil.e("onDelete...");
    }

    public void onUpdate(View view) {
        LogUtil.e("onUpdate...");
    }

    public void onQuery(View view) {
        LogUtil.e("onQuery...");
        Cursor cursor = mContentResolver.query(uri,null,null,null,null);
        if (cursor != null) {
            while(cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String gender = cursor.getString(cursor.getColumnIndex("gender"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));

                tv_content.setText("姓名: " + name + "\n" + "性别: " + gender + "\n" + "年龄: " + age + "\n");
            }
        }

    }


}
