package com.xiaofan.product.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaofan.product.R;
import com.xiaofan.product.net.http.test.NetWorkTestActivity;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.ResourceUtil;


public class MainActivity extends AbstractBaseActivity {


    private ImageView iv_title_bar_plus;
    private ImageView iv_title_bar_search;
    private LinearLayout pll_title_bar_root;
    private TextView tv_title_bar_title;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        initData();


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

        pll_title_bar_root = (LinearLayout) findViewById(R.id.pll_title_bar_root);

        LogUtil.e("====================菜单===================\n");
        LogUtil.e("屏幕密度等级:" + ResourceUtil.getDensity(mContext));
        LogUtil.e("====================菜单===================\n");
        LogUtil.e("逻辑像素密度:" + ResourceUtil.getDensityDpi(mContext));
        LogUtil.e("====================菜单===================\n");
        LogUtil.e("标题栏的高度:" + pll_title_bar_root.getMeasuredHeight());
        LogUtil.e("====================菜单===================\n");
        LogUtil.e("图片的边距差:" + iv_title_bar_plus.getMeasuredHeight());

        tv_title_bar_title = (TextView) findViewById(R.id.tv_title_bar_title);

        LogUtil.e("标题大小：" + tv_title_bar_title.getTextSize());
    }

    public void onLogin(View view) {
        startActivity(new Intent(MainActivity.this, NetWorkTestActivity.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
