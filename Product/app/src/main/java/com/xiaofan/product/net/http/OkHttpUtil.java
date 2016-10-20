package com.xiaofan.product.net.http;

import android.content.Context;
import android.widget.PopupWindow;

import com.xiaofan.product.fragment.AbstractBaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * @author: 范建海
 * @createTime: 2016/10/20 14:18
 * @className:  OkHttpUtil
 * @description: 基于OkHttp的网络请求工具类
 * @changed by:
 */
public class OkHttpUtil {

    /**
     *  GET请求
     * @param url 请求链接
     * @param params 请求参数
     * @param callback 请求回调
     * @param <T> 泛型实体Bean
     */
    public static <T> void get(String url, Map<String,String> params, Callback<T> callback) {

        OkHttpUtils.get()
                .url(url)
                .params(params)
                .build()
                .execute(callback);
    }

    /**
     * POST请求
     * @param ctx 调用该方法时所在的上下文，取消之前发起的请求用
     * @param url 请求链接
     * @param params 请求参数
     * @param callback 请求回调
     * @param <T> 泛型实体
     */
    public static <T> void post(Context ctx, String url, Map<String,String> params, Callback<T> callback) {
        OkHttpUtils.post()
                .url(url)
                .tag(ctx)   // 设置tag
                .params(params)
                .build()
                .execute(callback);
    }

    /**
     * 根据tag取消请求
     * @param ctx tag标记(在这里我们把请求所在的上下文作为tag)
     */
    public static void cancel(Context ctx) {
        OkHttpUtils.getInstance().cancelTag(ctx);
    }




    






}
