package com.xiaofan.product.activity;

import android.content.Context;
import android.os.Bundle;

import com.xiaofan.product.R;
import com.xiaofan.product.domain.DateUtilBean;
import com.xiaofan.product.util.DateUtil;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.ResourceUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        String str = DateUtil.getNewFormatDateString("", DateUtil.FORMAT_YYYY_SLASH_MM_SLASH_DD_HH_MM,DateUtil.FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH);
        LogUtil.e("时间：" + str);




    }





}
