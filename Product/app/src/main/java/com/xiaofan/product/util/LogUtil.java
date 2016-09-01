package com.xiaofan.product.util;

import android.util.Log;

/**
 * @author: 范建海
 * @createTime: 2016/8/24 20:42
 * @className:  LogUtil
 * @Description: 日志工具类
 */
public class LogUtil {

    public static void d(String desc) {
        if (ConstantUtil.DEBUG)
            Log.d(ConstantUtil.TAG, desc);
    }

    public static void d(String desc, Throwable tr) {
        if (ConstantUtil.DEBUG)
            Log.d(ConstantUtil.TAG, desc, tr);
    }

    public static void v(String desc) {
        if (ConstantUtil.DEBUG)
            Log.v(ConstantUtil.TAG, desc);
    }
    public static void v(String desc, Throwable tr) {
        if (ConstantUtil.DEBUG)
            Log.v(ConstantUtil.TAG, desc);
    }

    public static void w(String desc) {
        if (ConstantUtil.DEBUG)
            Log.w(ConstantUtil.TAG, desc);
    }

    public static void w(Throwable ioe) {
        if (ConstantUtil.DEBUG)
            Log.w(ConstantUtil.TAG, ioe);
    }

    public static void w(String desc, Throwable e) {
        if (ConstantUtil.DEBUG)
            Log.w(ConstantUtil.TAG, desc, e);
    }

    public static void i(String desc) {
        if (ConstantUtil.DEBUG)
            Log.i(ConstantUtil.TAG,desc);
    }

    public static void i(String desc, Throwable tr) {
        if (ConstantUtil.DEBUG)
            Log.i(ConstantUtil.TAG, desc, tr);
    }

    public static void e(String desc) {
        if (ConstantUtil.DEBUG)
            Log.e(ConstantUtil.TAG, desc);
    }

    public static void e(String desc, Throwable tr) {
        if (ConstantUtil.DEBUG)
            Log.e(ConstantUtil.TAG, desc, tr);
    }
}
