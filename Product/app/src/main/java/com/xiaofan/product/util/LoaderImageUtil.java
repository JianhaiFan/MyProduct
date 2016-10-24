package com.xiaofan.product.util;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * @author: 范建海
 * @createTime: 2016/10/8 11:29
 * @className:  LoadImageUtil
 * @description: 加载图片的工具类
 * @changed by:
 */
public class LoaderImageUtil {
    // 头像类缩略图类型
    public static final String TYPE_IMG_100PX_SIZE = "@!100px_db";
    // 期刊缩略图类型
    public static final String TYPE_IMG_160PX_SIZE = "@!160px_db";
    // 其他缩略(除了头像类型和期刊封面)图类型
    public static final String TYPE_IMG_250PX_SIZE = "@!250px_db";
    // 开启预览界面标志
    public static final String TAG_START_PREVIEW = "picture_info";
    // 默认资源图片ID,默认图片资源ID为-1，表示默认不加载本地图片
    private static int resID = -1;
    // 本地图片路径
    private static String localFilePath;

    /**
     * 展示网络图片
     * @param url
     * @param iv
     * @param imgType
     * @param listener
     */
    public static void displayFromNet(String url, ImageView iv, String imgType, ImageLoadingListener listener) {

    }

    /**
     * 加载SDcard中本地图片
     * @param uri
     * @param iv
     */
    public static void displayFromSDCard(String uri, ImageView iv) {
        innerDisplay("file://" + uri, iv,null);
    }

    /**
     * 从assets文件夹中异步加载图片
     * @param imageName 图片名称，带后缀的，例如：1.png
     * @param iv 图片控件
     */
    public static void dispalyFromAssets(String imageName, ImageView iv) {
        innerDisplay("assets://" + imageName,iv,null);
    }

    /**
     * 从drawable中已不加载本地图片， (only images, non-9patch)
     * @param resId
     * @param iv
     */
    public static void displayFromDrawable(int resId, ImageView iv) {
        innerDisplay("drawable://" + resId,iv,null);
    }

    /**
     * 从内容提提供者中抓取图片
     * @param uri  例如：content://media/external/audio/albumart/13;
     * @param iv 图片控件
     */
    public static void displayFromContent(String uri, ImageView iv) {
        innerDisplay("content://" + uri,iv,null);
    }

    // 展示图片逻辑处理
    public static void innerDisplay(String url, ImageView iv,ImageLoadingListener listener) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();

        if (resID != -1) {
            builder.showImageForEmptyUri(resID);
            builder.showImageOnFail(resID);
        }
        builder.cacheInMemory(true);
        builder.cacheOnDisk(true);
        builder.considerExifParams(true);
        builder.imageScaleType(ImageScaleType.EXACTLY);
        builder.bitmapConfig(Bitmap.Config.RGB_565);
        DisplayImageOptions	options = builder.build();
        ImageLoader.getInstance().displayImage(url,iv,options,listener);
    }


}






























