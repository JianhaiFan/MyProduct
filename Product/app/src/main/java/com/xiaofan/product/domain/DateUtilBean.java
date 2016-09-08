package com.xiaofan.product.domain;

import java.io.Serializable;

/**
 * @author: 范建海
 * @createTime: 2016/9/8 11:54
 * @className:  DateUtilBean
 * @Description: 日期工具类测试实体Bean
 */
public class DateUtilBean implements Serializable {
    /** 时间 **/
    private String createdStamp;
    /** 其他 **/
    private String otherInfo;

    public DateUtilBean(){}

    public DateUtilBean(String createdStamp, String otherInfo) {
        this.createdStamp = createdStamp;
        this.otherInfo = otherInfo;
    }

    public String getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(String createdStamp) {
        this.createdStamp = createdStamp;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    @Override
    public String toString() {
        return "DateUtilBean{" +
                "createdStamp='" + createdStamp + '\'' +
                ", otherInfo='" + otherInfo + '\'' +
                '}';
    }
}
