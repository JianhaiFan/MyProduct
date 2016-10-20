package com.xiaofan.product.util.oss;

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

    /**
     * 阿里云对象存储成功的本地回调
     * @param request
     * @param result
     */
    public abstract void success(PutObjectRequest request, PutObjectResult result);

    /**
     * 阿里云对象存储失败的本地回调
     * @param request
     */
    public abstract void error(PutObjectRequest request);

    /**
     * 阿里云对象存储过程中的本地回调
     * @param request
     * @param currentSize 存储过程中，当前文件已经存储的大小
     * @param totalSize  存储文件的大小
     */
    public void process(PutObjectRequest request, long currentSize, long totalSize) ;
}
