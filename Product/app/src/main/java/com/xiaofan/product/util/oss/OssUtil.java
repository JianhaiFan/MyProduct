package com.xiaofan.product.util.oss;

import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.google.gson.reflect.TypeToken;
import com.xiaofan.product.net.http.OkHttpUtil;
import com.xiaofan.product.util.GsonUtil;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.URLConstant;
import com.zhy.http.okhttp.callback.Callback;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author: 范建海
 * @createTime: 2016/10/20 10:19
 * @className:  OssUtil
 * @description: 阿里云对象存储工具类
 * @changed by:
 */
public class OssUtil {
    // 阿里云对象存储实体
    private static OSS oss;

    /**
     * 初始化Oss客户端
     * @param context 上下文
     * @param ossInitBean Oss实体Bean
     */
    public static void initOSS(Context context, OssInitBean ossInitBean) {

        ClientConfiguration conf = new ClientConfiguration();
        // 连接超时，默认15秒
        conf.setConnectionTimeout(15 * 1000);
        // socket超时，默认15秒
        conf.setSocketTimeout(15 * 1000);
        // 最大并发请求数目，默认5个
        conf.setMaxConcurrentRequest(5);
        // 失败后最大重试次数，默认2次
        conf.setMaxErrorRetry(2);

        //初始化客户端
        oss = new OSSClient(context, ossInitBean.getEndPoint(), new OSSStsTokenCredentialProvider(ossInitBean.getAccessKeyId(), ossInitBean.getAccessKeySecret(), ossInitBean.getSecurityToken()), conf);
    }

    /**
     * 阿里云对象存储(上传到云端)
     * @param context 上下文
     * @param putObjectInfos 待上传对象的分类和对象本地路径
     * @param ossCallback 对象上传回调接口
     */
    public static void asyncPutObjectFromLocalFile(final Context context, final Map<String,String> putObjectInfos, final OssCallback ossCallback) {

        /**
         * 获的sts鉴权模式权限，初始化Oss客户端的数据
         */
        OkHttpUtil.post(context, URLConstant.URL_OSS_STS_AUTHORITY, null, new Callback<OssInitBean>() {

            @Override
            public OssInitBean parseNetworkResponse(Response response, int id) throws Exception {
                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                OssInitBean ossInitBean = GsonUtil.json2Object(body,new TypeToken<OssInitBean>(){});
                return ossInitBean;
            }

            @Override
            public void onResponse(OssInitBean ossInitBean, int id) {
                try {
                    // 获得临时授权数据，成功后初始化OSS服务，采用临时访问方式
                    initOSS(context,ossInitBean);
                } catch (Exception e) {
                    LogUtil.e("Oss 客户端初始化失败: " + e.toString());
                }

                for (String key : putObjectInfos.keySet()) {

                    final String localFilePath = putObjectInfos.get(key);
                    // 生成ObjectKey
                    final String objectKey = key + SystemClock.currentThreadTimeMillis() + localFilePath.substring(localFilePath.lastIndexOf("/") + 1);
                    // 构造上传请求
                    PutObjectRequest put = new PutObjectRequest(ossInitBean.getBucketName(), objectKey, localFilePath);

                    // 异步上传时可以设置进度回调
                    put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                        @Override
                        public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                            ossCallback.process(currentSize,totalSize);
                        }
                    });

                    OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                        @Override
                        public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                            ossCallback.success(request.getObjectKey(),localFilePath);
                        }

                        @Override
                        public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                            ossCallback.error();
                        }
                    });

                    task.waitUntilFinished();
                }

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtil.e("Oss 客户端初始化失败: " + e.toString());
            }
        });

    }

    /**
     * 阿里云对象存储(上传到云端) 断点续传
     * @param context 上下文
     * @param putObjectInfos 待上传对象的分类和对象本地路径
     * @param ossCallback 对象上传回调接口
     */
    public static void asyncResumableUploadWithRecordPathSetting(final Context context, final Map<String,String> putObjectInfos, final OssCallback ossCallback) {

        /**
         * 获的sts鉴权模式权限，初始化Oss客户端的数据
         */
        OkHttpUtil.post(context, URLConstant.URL_OSS_STS_AUTHORITY, null, new Callback<OssInitBean>() {

            @Override
            public OssInitBean parseNetworkResponse(Response response, int id) throws Exception {
                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                OssInitBean ossInitBean = GsonUtil.json2Object(body,new TypeToken<OssInitBean>(){});
                return ossInitBean;
            }

            @Override
            public void onResponse(OssInitBean ossInitBean, int id) {
                try {
                    // 获得临时授权数据，成功后初始化OSS服务，采用临时访问方式
                    initOSS(context,ossInitBean);
                } catch (Exception e) {
                    LogUtil.e("Oss 客户端初始化失败: " + e.toString());
                }

                String recordDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/oss_record/";

                File recordDir = new File(recordDirectory);

                // 要保证目录存在，如果不存在则主动创建
                if (!recordDir.exists()) {
                    recordDir.mkdirs();
                }

                for (String key : putObjectInfos.keySet()) {

                    final String localFilePath = putObjectInfos.get(key);
                    // 生成ObjectKey
                    final String objectKey = key + SystemClock.currentThreadTimeMillis() + localFilePath.substring(localFilePath.lastIndexOf("/") + 1);

                    // 创建断点上传请求，参数中给出断点记录文件的保存位置，需是一个文件夹的绝对路径
                    ResumableUploadRequest request = new ResumableUploadRequest(ossInitBean.getBucketName(), objectKey, localFilePath, recordDirectory);
                    // 设置上传过程回调
                    request.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() {
                        @Override
                        public void onProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
                            ossCallback.process(currentSize,totalSize);
                        }
                    });


                    OSSAsyncTask resumableTask = oss.asyncResumableUpload(request, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() {
                        @Override
                        public void onSuccess(ResumableUploadRequest request, ResumableUploadResult result) {
                            ossCallback.success(request.getObjectKey(),localFilePath);
                        }

                        @Override
                        public void onFailure(ResumableUploadRequest request, ClientException clientExcepion, ServiceException serviceException) {
                            ossCallback.error();
                        }
                    });

                    resumableTask.waitUntilFinished();
                }


            }

            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtil.e("Oss 客户端初始化失败: " + e.toString());
            }
        });
    }

    /**
     * 阿里云对象存储异步下载文件
     * @param context 上下文
     * @param objectKey 存储对象的唯一标志
     * @param ossCallback 下载文件
     */
    public static void asyncGetObjectRequest(final Context context, final String objectKey, final OssCallback ossCallback) {
        /**
         * 获的sts鉴权模式权限，初始化Oss客户端的数据
         */
        OkHttpUtil.post(context, URLConstant.URL_OSS_STS_AUTHORITY, null, new Callback<OssInitBean>() {

            @Override
            public OssInitBean parseNetworkResponse(Response response, int id) throws Exception {
                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                OssInitBean ossInitBean = GsonUtil.json2Object(body,new TypeToken<OssInitBean>(){});
                return ossInitBean;
            }

            @Override
            public void onResponse(OssInitBean ossInitBean, int id) {
                try {
                    // 获得临时授权数据，成功后初始化OSS服务，采用临时访问方式
                    initOSS(context,ossInitBean);
                } catch (Exception e) {
                    LogUtil.e("Oss 客户端初始化失败: " + e.toString());
                }

                GetObjectRequest get = new GetObjectRequest(ossInitBean.getBucketName(), objectKey);

                OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
                    @Override
                    public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                        // 请求成功
                        InputStream inputStream = result.getObjectContent();
                        BufferedOutputStream bos = null;

                        byte[] buffer = new byte[2048];
                        int len = -1;

                        String downloadDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/oss_download/";

                        File downloadDirectoryFile = new File(downloadDirectory);
                        if (!downloadDirectoryFile.exists()) {
                            downloadDirectoryFile.mkdirs();
                        }

                        try {
                            bos = new BufferedOutputStream(new FileOutputStream(new File(downloadDirectory,objectKey)));

                            while ((len = inputStream.read(buffer)) != -1) {
                                // 处理下载的数据
                                bos.write(buffer);
                            }
                            bos.flush();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                bos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                        ossCallback.error();
                    }
                });

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtil.e("Oss 客户端初始化失败: " + e.toString());
            }
        });
    }

}
