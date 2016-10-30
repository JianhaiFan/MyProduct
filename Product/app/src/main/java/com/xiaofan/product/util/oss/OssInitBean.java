package com.xiaofan.product.util.oss;

import android.content.Context;

import java.io.Serializable;

/**
 * @author: 范建海
 * @createTime: 2016/10/20 13:53
 * @className:  OssInitBean
 * @description: 阿里云对象存储初始化实体Bean
 * @changed by:
 */
public class OssInitBean implements Serializable {
    // 访问地址
    private String endPoint;
    // 公钥
    private String accessKeyId;
    // 私钥
    private String accessKeySecret;
    // 临时令牌
    private String securityToken;
    // 储存空间
    private String bucketName;

    public OssInitBean() {}

    public OssInitBean(String endPoint, String accessKeyId, String accessKeySecret, String securityToken, String bucketName) {
        this.endPoint = endPoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.securityToken = securityToken;
        this.bucketName = bucketName;

    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String toString() {
        return "OssInitBean{" +
                "endPoint='" + endPoint + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", securityToken='" + securityToken + '\'' +
                ", bucketName='" + bucketName + '\'' +
                '}';
    }
}
