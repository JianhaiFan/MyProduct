package com.xiaofan.product.activity;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaofan.product.R;
import com.xiaofan.product.util.DensityUtil;
import com.xiaofan.product.util.DeviceUtil;
import com.xiaofan.product.util.HanziUtil;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.ResourceUtil;


public class MainActivity extends AbstractBaseActivity {

    private ImageView iv_title_bar_plus;
    private ImageView iv_title_bar_search;
    private LinearLayout pll_title_bar_root;
    private TextView tv_title_bar_title;
    private Context mContext;

    private Button bt_login;
    private Button bt_progress;

    private LinearLayout ll_root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        initData();
        tv_title_bar_title = (TextView) findViewById(R.id.tv_title_bar_title);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_progress = (Button) findViewById(R.id.bt_progress);
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
    }


    public void initData() {
        iv_title_bar_plus = (ImageView) findViewById(R.id.iv_title_bar_plus);
        iv_title_bar_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.e("iv_title_bar_plus...");

            }
        });

        iv_title_bar_search = (ImageView) findViewById(R.id.iv_title_bar_search);
        iv_title_bar_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.e("iv_title_bar_search...");
            }
        });

    }

    public void onLogin(View view) {

    }

    public void onProgress(View view) {
        startActivity(new Intent(this, ProgressBarActivity.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}


