package com.xiaofan.product.application;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.xiaofan.product.service.CustomPushIntentService;
import com.xiaofan.product.util.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * @author: 范建海
 * @createTime: 2016/8/24 20:12
 * @className:  CustomApplication
 * @Description: 自定义应用类
 */
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化网络
        OkHttpClient okHttpclient = new OkHttpClient.Builder()
                                                    .addInterceptor(new LoggerInterceptor("TAG"))
                                                    .connectTimeout(15000L, TimeUnit.MILLISECONDS)
                                                    .readTimeout(15000L,TimeUnit.MILLISECONDS)
                                                    .build();
        OkHttpUtils.initClient(okHttpclient);

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

//        HttpsUtils.getSslSocketFactory(
//                证书的inputstream,
//                本地证书的inputstream,
//                本地证书的密码);

        // 初始化友盟相关信息
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.e(deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

        // 完全自定义消息处理
        mPushAgent.setPushIntentServiceClass(CustomPushIntentService.class);
        // 获取ImageLoader下载图片的路径
        String path = StorageUtils.getCacheDirectory(this).getAbsolutePath();
        LogUtil.e("ImageLoader文件下载路径:" + path);
        // 初始化ImageLoader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 采用软引用
                .memoryCache(new WeakMemoryCache())
                // 输出Log日志
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);

    }
}
