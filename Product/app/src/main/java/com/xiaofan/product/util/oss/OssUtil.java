package com.xiaofan.product.util.oss;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;

import java.util.List;

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
    // 阿里云对象存储初始化实体Bean
    private static OssInitBean ossInitBean;

    /**
     * 初始化OSS客户端
     * @param ossInitBean
     */
    public static void initOSS(OssInitBean ossInitBean) {

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
        oss = new OSSClient(null, ossInitBean.getEndPoint(), new OSSStsTokenCredentialProvider(ossInitBean.getAccessKeyId(), ossInitBean.getAccessKeySecret(), ossInitBean.getSecurityToken()), conf);
    }

    /**
     * 阿里云对象存储(上传到云端)
     * @param putObjectLocalPaths 本地对象路径集合
     * @param ossCallback 对象上传回调接口
     */
    public static void asyncPutObjectFromLocalFile(List<String> putObjectLocalPaths,OssCallback ossCallback) {

    }

}
