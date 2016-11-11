package com.xiaofan.product.util.oss;

import java.util.Map;

/**
 * @author: 范建海
 * @createTime: 2016/11/7 15:39
 * @className:  OssMapCallBack
 * @description: 上传阿里云对象回调接口
 * @changed by:
 */
public interface OssMapCallBack {
    /**
     * 门诊成功上传回调
     * @param map
     */
    public abstract void success(Map<String, String> map);

    /**
     * 上传失败回调
     * @param msg 提示信息
     */
    public abstract void error(String msg);

}
