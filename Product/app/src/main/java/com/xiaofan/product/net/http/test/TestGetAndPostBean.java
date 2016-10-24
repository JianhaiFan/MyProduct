package com.xiaofan.product.net.http.test;

import java.io.Serializable;

/**
 * @author: 范建海
 * @createTime: 2016/10/20 16:06
 * @className:  TestBean
 * @description: 测试实体Bean
 * @changed by:
 */
public class TestGetAndPostBean implements Serializable {

    private String status;

    private String msg;

    public TestGetAndPostBean(){}

    public TestGetAndPostBean(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TestGetAndPostBean{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
