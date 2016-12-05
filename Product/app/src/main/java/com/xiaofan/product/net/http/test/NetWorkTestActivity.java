package com.xiaofan.product.net.http.test;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xiaofan.product.R;
import com.xiaofan.product.activity.AbstractBaseActivity;
import com.xiaofan.product.net.http.OkHttpUtil;
import com.xiaofan.product.util.GsonUtil;
import com.xiaofan.product.util.LoaderImageUtil;
import com.xiaofan.product.util.LogUtil;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
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
        Map<String,String> params = new HashMap<>();
        params.put("userLoginId", "18210836561");
        params.put("oldPassword", "111111");
        params.put("newPassword", "222222");

        OkHttpUtil.get(mContext,"http://192.168.1.24:9090/sysCommon/control/restful/ajaxUpdatePassword", params, new Callback<TestGetAndPostBean>() {

            @Override
            public void onBefore(Request request, int id) {
                pb_loading.setVisibility(View.VISIBLE);
                tv_text.setVisibility(View.VISIBLE);
                iv_img.setVisibility(View.GONE);
            }

            @Override
            public TestGetAndPostBean parseNetworkResponse(Response response, int id) throws Exception {
                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                TestGetAndPostBean testBean = GsonUtil.json2Object(body,new TypeToken<TestGetAndPostBean>(){});
                return testBean;
            }

            @Override
            public void onResponse(TestGetAndPostBean testBean, int id) {
                // 对实体Bean进行操作之前，要进行判空处理
                if (testBean != null) {
                    LogUtil.e("TestBean:" + testBean.toString());
                    tv_text.setText("GET请求:" + testBean.toString());
                }

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                tv_text.setText("GET请求:" + "异常-->" + e.toString());
            }

            @Override
            public void onAfter(int id) {
                pb_loading.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 测试 Post
     * @param view
     */
    public void onPost(View view) {
        Map<String,String> params = new HashMap<>();
        params.put("userLoginId", "18210836561");
        params.put("oldPassword", "222222");
        params.put("newPassword", "111111");

        OkHttpUtil.post(mContext,"http://192.168.1.24:9090/sysCommon/control/restful/ajaxUpdatePassword", params, new Callback<TestGetAndPostBean>() {

            @Override
            public void onBefore(Request request, int id) {
                pb_loading.setVisibility(View.VISIBLE);
                tv_text.setVisibility(View.VISIBLE);
                iv_img.setVisibility(View.GONE);
            }

            @Override
            public TestGetAndPostBean parseNetworkResponse(Response response, int id) throws Exception {
                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                TestGetAndPostBean testBean = GsonUtil.json2Object(body,new TypeToken<TestGetAndPostBean>(){});
                return testBean;
            }

            @Override
            public void onResponse(TestGetAndPostBean testBean, int id) {

                // 对实体Bean进行操作之前，要进行判空处理
                if (testBean != null) {
                    LogUtil.e("TestBean:" + testBean.toString());
                    tv_text.setText("POST请求:" + testBean.toString());
                }

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                tv_text.setText("POST请求:" + "异常-->" + e.toString());
            }

            @Override
            public void onAfter(int id) {
                pb_loading.setVisibility(View.GONE);
            }
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
        Map<String,String> params = new HashMap<>();
        params.put("from","OSSFileLocation_03");

        Map<String,File> files = new HashMap<>();
        files.put("small_1mmexport1477283052348_1.jpg",new File("/storage/emulated/0/PicturesPictic/small_1mmexport1477283052348_1.jpg"));

        OkHttpUtil.postFiles(mContext,"http://192.168.1.24:9090/sysCommon/control/restful/ajaxImportPicUrl",params,"uploadedFileList",files,new Callback<TestUploadBean>() {

            @Override
            public void onBefore(Request request, int id) {
                pb_loading.setVisibility(View.VISIBLE);
                tv_text.setVisibility(View.GONE);
                iv_img.setVisibility(View.VISIBLE);
            }

            @Override
            public TestUploadBean parseNetworkResponse(Response response, int id) throws Exception {
                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                TestUploadBean uploadBean = GsonUtil.json2Object(body,new TypeToken<TestUploadBean>(){});
                return uploadBean;
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                LogUtil.e("========== inProgress ===========");
                LogUtil.e("progress:" + progress + ",total:" + total);
                LogUtil.e("========== inProgress ===========");
            }

            @Override
            public void onResponse(TestUploadBean uploadBean, int id) {
                if (uploadBean != null) {
                    if ("1".equals(uploadBean.getStatus())) {
                        LoaderImageUtil.displayFromNet(uploadBean.getOutputList().get(0),R.drawable.test,iv_img);
                    }
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                tv_text.setVisibility(View.VISIBLE);
                iv_img.setVisibility(View.GONE);
                tv_text.setText("上传文件:" + "异常-->" + e.toString());

            }

            @Override
            public void onAfter(int id) {
                pb_loading.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 测试 下载文件
     * @param view
     */
    public void onDownload(View view) {
        OkHttpUtil.downloadFile(mContext, "http://yxck.img-cn-beijing.aliyuncs.com/headportrait/peron/c42923ae-1f1f-4eb6-8263-8d3657da4565.jpg", new FileCallBack("/storage/emulated/0/我的下载/浏览器图片/","xiaofan.jpg") {
            @Override
            public void onBefore(Request request, int id) {
                pb_loading.setVisibility(View.VISIBLE);
                tv_text.setVisibility(View.GONE);
                iv_img.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResponse(File file, int id) {
                if (file != null) {
                    LoaderImageUtil.displayFromSDCard(file.getAbsolutePath(),iv_img);
                }
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                LogUtil.e("========== onDownload inProgress ===========");
                LogUtil.e("progress:" + progress + ",total:" + total);
                LogUtil.e("========== onDownload inProgress ===========");
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtil.e("========== onError ===========");
                LogUtil.e("Exception:" + e.toString());
                LogUtil.e("========== onError ===========");
            }

            @Override
            public void onAfter(int id) {
                pb_loading.setVisibility(View.GONE);
            }
        });
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

    @Override
    public boolean backCode() {
        return false;
    }
}
