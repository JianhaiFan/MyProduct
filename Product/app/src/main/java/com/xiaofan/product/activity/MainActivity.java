package com.xiaofan.product.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaofan.product.R;
import com.xiaofan.product.activity.home.RecycleViewActivity;
import com.xiaofan.product.util.LogUtil;


public class MainActivity extends AbstractBaseActivity {

    private ImageView iv_title_bar_plus;
    private ImageView iv_title_bar_search;
    private LinearLayout pll_title_bar_root;
    private TextView tv_title_bar_title;
    private Context mContext;

    private Button bt_login;
    private Button bt_progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        initData();
        tv_title_bar_title = (TextView) findViewById(R.id.tv_title_bar_title);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_progress = (Button) findViewById(R.id.bt_progress);
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
        startActivity(new Intent(mContext, RecycleViewActivity.class));

    }

    public void onProgress(View view) {
        startActivity(new Intent(this, ProgressBarActivity.class));
    }

    public void onProvider(View view) {
        startActivity(new Intent(this,ProviderTestActivity.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}


