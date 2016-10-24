package com.xiaofan.product.net.http;

import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
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
     * GET请求
     * @param ctx 调用该方法时所在的上下文，取消之前发起的请求用
     * @param url 请求链接
     * @param params 请求参数
     * @param callback 请求回调
     * @param <T> 泛型实体Bean
     */
    public static <T> void get(Context ctx, String url, Map<String,String> params, Callback<T> callback) {

        OkHttpUtils.get()
                .url(url)
                .tag(ctx)
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
                .tag(ctx)
                .params(params)
                .build()
                .execute(callback);
    }

    /**
     * 模拟form表单上传文件
     * @param ctx 调用该方法时所在的上下文，取消之前发起的请求用
     * @param url 请求链接
     * @param params 请求的其他参数
     * @param key 待上传文件的key,即类别表单中<input type="file" name="mFile"/>的name属性。
     * @param files 待上传文件的路径和文件实体
     * @param callback 请求回调
     * @param <T> 泛型实体
     */
    public static <T> void postFiles(Context ctx, String url, Map<String,String> params,String key, Map<String, File> files,Callback<T> callback) {
        OkHttpUtils.post()
                .files(key,files)
                .addHeader("USERNAME","18210836561")
                .addHeader("PASSWORD","111111")
                .url(url)
                .params(params)
                .tag(ctx)
                .build()
                .execute(callback);
    }

    /**
     * 下载文件
     * @param ctx 调用该方法时所在的上下文，取消之前发起的请求用
     * @param url 请求链接
     * @param fileCallBack 下载文件时的回调
     * @param <T> 泛型实体
     */
    public static <T> void downloadFile(Context ctx, String url,FileCallBack fileCallBack) {
        OkHttpUtils.get()
                .addHeader("USERNAME","18210836561")
                .addHeader("PASSWORD","111111")
                .url(url)
                .tag(ctx)
                .build()
                .execute(fileCallBack);
    }

    /**
     * 根据tag取消请求WE
     * @param ctx tag标记(在这里我们把请求所在的上下文作为tag)
     */
    public static void cancel(Context ctx) {
        OkHttpUtils.getInstance().cancelTag(ctx);
    }




    






}
