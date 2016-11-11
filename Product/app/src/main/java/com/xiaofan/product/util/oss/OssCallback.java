package com.xiaofan.product.util.oss;

import android.app.ProgressDialog;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

/**
 * @author: 范建海
 * @createTime: 2016/10/20 10:19 
 * @className:  OssCallBack
 * @description: 阿里云对象存储回调接口
 * @changed by:
 */
public interface OssCallback {
    // 视频，音频路径的前缀
    public static final String OSS_ENDPOINT = "http://yxck.oss-cn-beijing.aliyuncs.com/";
    // 图片的路径的前缀
    public static final String OSS_ENDPOINT_IMG = "http://yxck.img-cn-beijing.aliyuncs.com/";

    /**
     * 阿里云对象存储成功的本地回调
     * @param progressDialog 进度条
     * @param objectKey 上传对象的ObjectKey（唯一标志）
     * @param filePath  上传对象的本地路径
     */
    public abstract void success(ProgressDialog progressDialog , String objectKey, String filePath);

    /**
     * 阿里云对象存储失败的本地回调
     * @param progressDialog 进度条
     */
    public abstract void error(ProgressDialog progressDialog);

    /**
     * 阿里云对象存储过程中的本地回调
     * @param progressDialog 进度条
     * @param currentSize 存储过程中，当前文件已经存储的大小
     * @param totalSize  存储文件的大小
     */
    public abstract void process(ProgressDialog progressDialog,long currentSize, long totalSize) ;
}
