package com.xiaofan.product.activity;

import android.content.Context;
import android.os.Bundle;

import com.xiaofan.product.R;
import com.xiaofan.product.util.DateUtil;
import com.xiaofan.product.util.LogUtil;

import java.util.Date;


public class MainActivity extends BaseActivity {

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        Date d = new Date(0);
        LogUtil.e("timestamp ==> " + d.getTime());
        LogUtil.e("data: ==> " + DateUtil.formatDate2String(d,DateUtil.FORMAT_YYYY_LINE_MM_LINE_DD_HH_MM));




    }





}
