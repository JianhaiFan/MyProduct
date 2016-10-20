package com.xiaofan.product.application;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * @author: 范建海
 * @createTime: 2016/8/24 20:12
 * @className:  MyApplication
 * @Description: 自定义应用类
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpclient = new OkHttpClient.Builder()
                                                    .addInterceptor(new LoggerInterceptor("TAG"))
                                                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                                                    .readTimeout(10000L,TimeUnit.MILLISECONDS)
                                                    .build();
        OkHttpUtils.initClient(okHttpclient);

    }
}
