package com.xiaofan.product.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: 范建海
 * @createTime: 2016/9/19 17:31
 * @className:  ResourceUtil
 * @Description: 获取本地资源的工具类
 */
public class ResourceUtil {
    /**
     * 获取资源对象
     * @param ctx 上下文
     * @return 资源实体
     */
    public static Resources getResources(Context ctx) {
        return ctx.getResources();
    }

    /**
     * 获取资源管理器
     * @param ctx 上下文
     * @return assets资源管理器
     */
    public static AssetManager getAssetManager(Context ctx) {
        return getResources(ctx).getAssets();
    }

    /**
     * 获取指定资源id对应的颜色
     * @param ctx 上下文
     * @param resID 颜色资源ID
     * @return (测试通过)
     */
    public static int getColor(Context ctx, int resID) {
        return getResources(ctx).getColor(resID);
    }

    /**
     * 获取指定资源id对应的图片
     * @param ctx 上下文
     * @param resID 图片资源ID
     * @return (测试通过)
     * {@link android.widget.ImageView#setImageDrawable(android.graphics.drawable.Drawable)}
     * {@link ImageView#setImageBitmap(android.graphics.Bitmap)}
     * {@link View#setBackgroundDrawable(android.graphics.drawable.Drawable)}
     */
    public static Drawable getDrawable(Context ctx, int resID) {
        return getResources(ctx).getDrawable(resID);
    }

    /**
     * 获取指定资源id对应的布尔值
     * @param ctx 上下文
     * @param resID 资源ID
     * @return (测试通过)
     */
    public static boolean getBoolean(Context ctx,int resID) {
        return getResources(ctx).getBoolean(resID);
    }

    /**
     * 获取指定资源id对应的字符串
     * @param ctx 上下文
     * @param resID  字符串资源ID
     * @return (测试通过)
     */
    public static String getString(Context ctx,int resID) {
        return getResources(ctx).getString(resID);
    }

    /**
     * 获取指定资源id对应的字符串数组
     * @param ctx 上下文
     * @param resID 字符串数组资源ID
     * @return (测试通过)
     */
    public static String[] getStringArray(Context ctx,int resID) {
        return getResources(ctx).getStringArray(resID);
    }

    /**
     * 获取指定资源id对应的整型数组
     * @param ctx 上下文
     * @param resID 整型数组资源ID
     * @return
     */
    public static int[] getIntArray(Context ctx,int resID) {
        return getResources(ctx).getIntArray(resID);
    }

    /**
     * 获取指定资源id对应的尺寸
     * @param ctx 上下文
     * @param resID 尺寸资源ID
     * @return (测试通过) 返回的单位为 px(dp * density = px)(sp * density = px)
     */
    public static float getDimension(Context ctx,int resID) {
        return getResources(ctx).getDimension(resID);
    }

    /**
     * 获取手机的像素密度
     * @param ctx 上下文
     * @return (测试通过) 返回屏幕密度等级(参考适配文件原理图)
     */
    public static float getDensity(Context ctx) {
        return getResources(ctx).getDisplayMetrics().density;
    }

    /**
     *
     * @param ctx 上下文
     * @return （测试通过）返回逻辑像素密度(参考适配文件原理图)
     */
    public static float getDensityDpi(Context ctx) {
        return getResources(ctx).getDisplayMetrics().densityDpi;
    }

    /**
     * dip换算为px
     * @param ctx 上下文
     * @param dpValue dp值
     * @return (测试通过)
     */
    public static int dip2px(Context ctx, float dpValue) {
        return (int) (dpValue * getDensity(ctx) + 0.5f);
    }

    /**
     * px换算为dip
     * @param ctx 上下文
     * @param pxValue 像素值
     * @return (测试通过)
     */
    public static float px2dip(Context ctx, float pxValue) {
        return (pxValue * 160) / getDensityDpi(ctx);
    }

    /**
     * 获取指定资源id对应的状态选择器
     * @param ctx 上下文
     * @param resID 资源ID
     * @return (测试通过) 设置文字状态选择器，对应的资源文件写到color目录下面
     */
    public static ColorStateList getColorStateList(Context ctx, int resID) {
        return getResources(ctx).getColorStateList(resID);
    }

    /**
     * 打开assets目录下的文件
     * @param ctx 上下文
     * @param filename 文件名称
     * @return (测试通过)返回文件流对象
     * @throws IOException
     */
    public static InputStream open(Context ctx, String filename) throws IOException {
        return getAssetManager(ctx).open(filename);
    }

    /**
     * 获取手机设备上的配置信息
     * @param ctx 上下文
     * @return
     */
    public Configuration getConfiguration(Context ctx) {
        return getResources(ctx).getConfiguration();
    }

}
