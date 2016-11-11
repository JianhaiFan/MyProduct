package com.xiaofan.product.util.oss;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;

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
import com.xiaofan.product.domain.constant.Constant;
import com.xiaofan.product.domain.constant.UrlConstant;
import com.xiaofan.product.net.http.OkHttpUtil;
import com.xiaofan.product.util.ExternalStorageUtil;
import com.xiaofan.product.util.GsonUtil;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.ToastUtil;
import com.zhy.http.okhttp.callback.Callback;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
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
    // 上传图片成功的数目
    private static int uploadPicCount;
    // 上传进度
    private static int uploadProgress;

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
     * @param progressDialog 进度条
     * @param context 上下文
     * @param partDirectory 图片存在阿里云上的子路径
     * @param localFilePaths 本地图片文件路径
     * @param ossCallback 对象上传回调接口
     */
    public static void asyncPutObjectFromLocalFile(final ProgressDialog progressDialog, final Context context, final String partDirectory, final List<String> localFilePaths, final OssCallback ossCallback) {
        /**
         * 获的sts鉴权模式权限，初始化Oss客户端的数据
         */
        OkHttpUtil.post(context, UrlConstant.URL_OSS_STS_AUTHORITY, null, new Callback<OssInitBean>() {

            @Override
            public OssInitBean parseNetworkResponse(Response response, int id) throws Exception {
                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                OssInitBean ossInitBean = GsonUtil.json2Object(body,new TypeToken<OssInitBean>(){});

                try {
                    // 获得临时授权数据，成功后初始化OSS服务，采用临时访问方式
                    initOSS(context,ossInitBean);

                    for(int i = 0;i < localFilePaths.size();i ++) {

                        final String localFilePath = localFilePaths.get(i);
                        // 生成ObjectKey
                        final String objectKey = partDirectory + System.currentTimeMillis() +localFilePath.substring(localFilePath.lastIndexOf("/") + 1);

                        // 构造上传请求
                        PutObjectRequest put = new PutObjectRequest(ossInitBean.getBucketName(), objectKey, localFilePath);

                        // 异步上传时可以设置进度回调
                        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                            @Override
                            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                                ossCallback.process(progressDialog,currentSize,totalSize);
                            }
                        });

                        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                            @Override
                            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                                ossCallback.success(progressDialog,request.getObjectKey(),localFilePath);
                            }

                            @Override
                            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                                ossCallback.error(progressDialog);
                            }
                        });

                        task.waitUntilFinished();
                    }

                } catch (Exception e) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    ToastUtil.show(context,"Oss 客户端初始化失败",0);
                    LogUtil.e("Oss 客户端初始化失败: " + e.toString());
                }

                return ossInitBean;
            }

            @Override
            public void onResponse(final OssInitBean ossInitBean, int id) {
                // 主线程
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                ToastUtil.show(context,"Oss 客户端初始化失败",0);
                LogUtil.e("Oss 客户端初始化失败: " + e.toString());
            }
        });
    }

    /**
     * 上传对象的加强版(患教)
     * @param progressDialog 进度条
     * @param context 上下文
     * @param uploadObjInfos 待上传对象的相关信息
     * @param ossCallback 上传回调
     */
    public static void asyncPutObjectFromLocalFile(final ProgressDialog progressDialog, final Context context, final Map<String,List<String>> uploadObjInfos, final OssCallback ossCallback) {
        /**
         * 获的sts鉴权模式权限，初始化Oss客户端的数据
         */
        OkHttpUtil.post(context, UrlConstant.URL_OSS_STS_AUTHORITY, null, new Callback<OssInitBean>() {

            @Override
            public OssInitBean parseNetworkResponse(Response response, int id) throws Exception {
                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                OssInitBean ossInitBean = GsonUtil.json2Object(body,new TypeToken<OssInitBean>(){});

                try {
                    // 获得临时授权数据，成功后初始化OSS服务，采用临时访问方式
                    initOSS(context,ossInitBean);

                    for (String key : uploadObjInfos.keySet()) {

                        List<String> values = uploadObjInfos.get(key);

                        if (values != null && values.size() > 0) {
                            for (int i = 0;i < values.size();i ++) {
                                final String localFilePath = values.get(i);
                                // 生成ObjectKey
                                final String objectKey = key + System.currentTimeMillis() +localFilePath.substring(localFilePath.lastIndexOf("/") + 1);

                                // 构造上传请求
                                PutObjectRequest put = new PutObjectRequest(ossInitBean.getBucketName(), objectKey, localFilePath);

                                // 异步上传时可以设置进度回调
                                put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                                    @Override
                                    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                                        ossCallback.process(progressDialog,currentSize,totalSize);
                                    }
                                });

                                OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                                    @Override
                                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                                        ossCallback.success(progressDialog,request.getObjectKey(),localFilePath);
                                    }

                                    @Override
                                    public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                                        ossCallback.error(progressDialog);
                                    }
                                });

                                task.waitUntilFinished();
                            }
                        }


                    }

                } catch (Exception e) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    ToastUtil.show(context,"Oss 客户端初始化失败",0);
                    LogUtil.e("Oss 客户端初始化失败: " + e.toString());
                }

                return ossInitBean;
            }

            @Override
            public void onResponse(final OssInitBean ossInitBean, int id) {
                // 主线程
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                ToastUtil.show(context,"Oss 客户端初始化失败",0);
                LogUtil.e("Oss 客户端初始化失败: " + e.toString());
            }
        });

    }

    /**
     * 上传文件对象加强版(门诊)
     * @param context 上下文
     * @param uploadID 上传id
     * @param uploadObjInfos 上传对象信息
     *                       key 唯一标识
     *                       value 本地文件路径
     * @param ossMapCallback 上传回调
     */
    public static void asyncPutObjectFromLocalFile(final Context context, final String uploadID, final Map<String,String> uploadObjInfos, final OssMapCallBack ossMapCallback) {

        uploadPicCount = 0;
        uploadProgress = 0;

        /**
         * 获的sts鉴权模式权限，初始化Oss客户端的数据
         */
        OkHttpUtil.post(context, UrlConstant.URL_OSS_STS_AUTHORITY, null, new Callback<OssInitBean>() {

            @Override
            public OssInitBean parseNetworkResponse(Response response, final int id) throws Exception {
                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                OssInitBean ossInitBean = GsonUtil.json2Object(body,new TypeToken<OssInitBean>(){});

                try {
                    // 获得临时授权数据，成功后初始化OSS服务，采用临时访问方式
                    initOSS(context,ossInitBean);
                    // 预备返回的结果集
                    final Map<String,String> returnMap = new HashMap<String, String>();

                    for (String key : uploadObjInfos.keySet()) {
                        // 用来标识文件上传到阿里云的路径
                        String partDirectory = "";

                        if(key.contains("photo")) {
                            // 视频第一帧
                            partDirectory = Constant.TYPE_VIDEO_FIRST_FRAME_OF_TREATMENT;
                        } else if(key.contains("caseValEnum_2")) {
                            // 图片
                            partDirectory = Constant.TYPE_IMG_OF_TREATMENT;
                        }else if (key.contains("caseValEnum_3")) {
                            // 音频
                            partDirectory = Constant.TYPE_AUDIO_OF_TREATMENT;
                        }else if (key.contains("caseValEnum_4")) {
                            // 视频
                            partDirectory = Constant.TYPE_VIDEO_OF_TREATMENT;
                        }

                        final String localFilePath = uploadObjInfos.get(key) ;
                        // 生成ObjectKey
                        final String objectKey = partDirectory + System.currentTimeMillis() + localFilePath.substring(localFilePath.lastIndexOf("/") + 1);

                        // 初始化待返回的集合
                        returnMap.put(key,objectKey);

                        // 构造上传请求
                        PutObjectRequest put = new PutObjectRequest(ossInitBean.getBucketName(), objectKey, localFilePath);

                        // 异步上传时可以设置进度回调
                        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                            @Override
                            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {}

                        });

                        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                            @Override
                            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                                ++ uploadPicCount;
                                uploadProgress =(int)(uploadPicCount * 100.0 / uploadObjInfos.size());
                                // TODO 设置进度

                                if (uploadPicCount >= uploadObjInfos.size()) {
                                    ossMapCallback.success(returnMap);
                                }
                            }


                            @Override
                            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                                ++ uploadPicCount;
                                returnMap.put(request.getObjectKey(),"");

                            }
                        });

                        task.waitUntilFinished();

                    }

                } catch (Exception e) {
                    ToastUtil.show(context,"Oss 客户端初始化失败",0);
                    LogUtil.e("Oss 客户端初始化失败: " + e.toString());
                    ossMapCallback.error(e.toString());
                }

                return ossInitBean;
            }

            @Override
            public void onResponse(final OssInitBean ossInitBean, int id) {
                // 主线程
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(context,"Oss 客户端初始化失败",0);
                LogUtil.e("Oss 客户端初始化失败: " + e.toString());
                ossMapCallback.error(e.toString());
            }
        });

    }


    /**
     * 阿里云对象存储(上传到云端) 断点续传
     * @param context 上下文
     * @param putObjectInfos 待上传对象的分类和对象本地路径
     * @param ossCallback 对象上传回调接口
     */
    public static void asyncResumableUploadWithRecordPathSetting(final ProgressDialog progressDialog, final Context context, final Map<String,String> putObjectInfos, final OssCallback ossCallback) {

        /**
         * 获的sts鉴权模式权限，初始化Oss客户端的数据
         */
        OkHttpUtil.post(context, UrlConstant.URL_OSS_STS_AUTHORITY, null, new Callback<OssInitBean>() {

            @Override
            public OssInitBean parseNetworkResponse(Response response, int id) throws Exception {
                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                OssInitBean ossInitBean = GsonUtil.json2Object(body,new TypeToken<OssInitBean>(){});

                try {
                    // 获得临时授权数据，成功后初始化OSS服务，采用临时访问方式
                    initOSS(context,ossInitBean);
                } catch (Exception e) {
                    LogUtil.e("Oss 客户端初始化失败: " + e.toString());
                }
                // 断点续传保存路径
                String recordDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/yxck/oss_resumable_record/";

                File recordDir = new File(recordDirectory);

                // 要保证目录存在，如果不存在则主动创建
                if (!recordDir.exists()) {
                    recordDir.mkdirs();
                }

                for (String key : putObjectInfos.keySet()) {

                    final String localFilePath = putObjectInfos.get(key);
                    // 生成ObjectKey
                    final String objectKey = key + System.currentTimeMillis() + localFilePath.substring(localFilePath.lastIndexOf("/") + 1);

                    // 创建断点上传请求，参数中给出断点记录文件的保存位置，需是一个文件夹的绝对路径
                    ResumableUploadRequest request = new ResumableUploadRequest(ossInitBean.getBucketName(), objectKey, localFilePath, recordDirectory);
                    // 设置上传过程回调
                    request.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() {
                        @Override
                        public void onProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
                            ossCallback.process(progressDialog,currentSize,totalSize);
                        }
                    });


                    OSSAsyncTask resumableTask = oss.asyncResumableUpload(request, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() {
                        @Override
                        public void onSuccess(ResumableUploadRequest request, ResumableUploadResult result) {
                            ossCallback.success(progressDialog,request.getObjectKey(),localFilePath);
                        }

                        @Override
                        public void onFailure(ResumableUploadRequest request, ClientException clientExcepion, ServiceException serviceException) {
                            ossCallback.error(progressDialog);
                        }
                    });

                    resumableTask.waitUntilFinished();
                }

                return ossInitBean;
            }

            @Override
            public void onResponse(OssInitBean ossInitBean, int id) {}

            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtil.e("Oss 客户端初始化失败: " + e.toString());
            }
        });
    }

    /**
     * 阿里云对象存储异步下载文件
     * @param progressDialog 进度条
     * @param context 上下文
     * @param partDirectory 文件上传时候的类型
     * @param objectKey 存储对象的唯一标志
     * @param ossCallback 下载文件
     */
    public static void asyncGetObjectRequest(final ProgressDialog progressDialog, final Context context, final String partDirectory, final String objectKey, final OssCallback ossCallback) {
        /**
         * 获的sts鉴权模式权限，初始化Oss客户端的数据
         */
        OkHttpUtil.post(context, UrlConstant.URL_OSS_STS_AUTHORITY, null, new Callback<OssInitBean>() {

            @Override
            public OssInitBean parseNetworkResponse(Response response, int id) throws Exception {
                // 服务器响应返回的Json串，注意，用临时变量接收一下，再做相应的操作，方法( response.body().string() )只能被调用一次，否则会返回空
                String body = response.body().string();
                OssInitBean ossInitBean = GsonUtil.json2Object(body,new TypeToken<OssInitBean>(){});
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

                        byte[] buffer = new byte[512];
                        int len = -1;

                        String downloadDirectory = ExternalStorageUtil.getExternalStoragePath() + "/yxck/oss_download/" + partDirectory;

                        File downloadDirectoryFile = new File(downloadDirectory);
                        if (!downloadDirectoryFile.exists()) {
                            downloadDirectoryFile.mkdirs();
                        }

                        try {
                            String fileName = objectKey.substring(objectKey.lastIndexOf("/")+1);
                            bos = new BufferedOutputStream(new FileOutputStream(new File(downloadDirectory,fileName)));

                            int processCount = 0;


                            while ((len = inputStream.read(buffer)) != -1) {
                                // 处理下载的数据
                                bos.write(buffer, 0, len);
                                LogUtil.i("doctorlog   length="+bos.toString().length());
                                if (++ processCount < 9) {
                                    progressDialog.setProgress(processCount * 10);
                                }
                            }
                            bos.flush();

                            progressDialog.setProgress(100);

                            ossCallback.success(progressDialog,objectKey,downloadDirectory + fileName);

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
                        ossCallback.error(progressDialog);
                    }
                });

                return ossInitBean;
            }

            @Override
            public void onResponse(OssInitBean ossInitBean, int id) {}

            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtil.e("Oss 客户端初始化失败: " + e.toString());
            }
        });
    }

}
