package com.xiaofan.product.activity;


import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiaofan.product.R;
import com.xiaofan.product.net.http.OkHttpUtil;
import com.xiaofan.product.net.http.test.TestBean;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.ResourceUtil;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AbstractBaseActivity {


    private ProgressBar pb_loading;
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
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);


//        initData();


    }

    public void onLogin(View view) {

        Map<String,String> params = new HashMap<>();
        params.put("userLoginId", "18210836561");
        params.put("oldPassword", "111111");
        params.put("newPassword", "222222");

        OkHttpUtil.post(this,"http://192.168.1.24:9090/sysCommon/control/restful/ajaxUpdatePassword", params, new Callback<TestBean>() {

            @Override
            public void onBefore(Request request, int id) {}

            @Override
            public void inProgress(float progress, long total, int id) {}

            @Override
            public TestBean parseNetworkResponse(Response response, int id) throws Exception {

                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                TestBean testBean = new Gson().fromJson(body, TestBean.class);
                return testBean;
            }

            @Override
            public void onResponse(TestBean testBean, int id) {

                // 对实体Bean进行操作之前，要进行判空处理
                if (testBean != null) {
                    LogUtil.e("TestBean:" + testBean.toString());
                }
                pb_loading.setVisibility(View.GONE);

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                pb_loading.setVisibility(View.GONE);
                call.cancel();
            }

            @Override
            public void onAfter(int id) {}
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消请求
        OkHttpUtil.cancel(mContext);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
