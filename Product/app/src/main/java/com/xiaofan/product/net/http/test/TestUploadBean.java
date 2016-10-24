package com.xiaofan.product.net.http.test;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 范建海
 * @createTime: 2016/10/24 14:49
 * @className:  TestUpLoadBean
 * @description: 测试上传文件实体Bean
 * @changed by:
 */
public class TestUploadBean implements Serializable {

    private String status;

    private String msg;

    private List<String> outputList;

    public TestUploadBean() {
    }

    public TestUploadBean(String status, String msg, List<String> outputList) {
        this.status = status;
        this.msg = msg;
        this.outputList = outputList;
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

    public List<String> getOutputList() {
        return outputList;
    }

    public void setOutputList(List<String> outputList) {
        this.outputList = outputList;
    }

    @Override
    public String toString() {
        return "TestUploadBean{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", outputList=" + outputList +
                '}';
    }
}
