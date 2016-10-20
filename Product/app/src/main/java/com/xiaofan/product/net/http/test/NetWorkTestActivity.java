package com.xiaofan.product.net.http.test;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiaofan.product.R;
import com.xiaofan.product.activity.AbstractBaseActivity;
import com.xiaofan.product.net.http.OkHttpUtil;
import com.xiaofan.product.util.LogUtil;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: 范建海
 * @createTime: 2016/10/20 20:04
 * @className:  NetWorkTestActivity
 * @description: 网络测试页面
 * @changed by:
 */
public class NetWorkTestActivity extends AbstractBaseActivity {
    // 上下文
    private Context mContext;
    // 展示文字
    private TextView tv_text;
    // 展示图片
    private ImageView iv_img;
    // 加载数据进度条
    private ProgressBar pb_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_network);

        mContext = NetWorkTestActivity.this;

        tv_text = (TextView) findViewById(R.id.tv_text);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);

    }

    /**
     * 测试 Get
     * @param view
     */
    public void onGet(View view) {

    }

    /**
     * 测试 Post
     * @param view
     */
    public void onPost(View view) {
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

    /**
     * 测试 上传Json
     * @param view
     */
    public void onJson(View view) {

    }

    /**
     * 测试 上传文件
     * @param view
     */
    public void onUpload(View view) {

    }

    /**
     * 测试 下载文件
     * @param view
     */
    public void onDownload(View view) {

    }

    /**
     * 测试 Cookie/Session
     * @param view
     */
    public void onOther(View view) {

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消请求
        OkHttpUtil.cancel(mContext);
    }
}
