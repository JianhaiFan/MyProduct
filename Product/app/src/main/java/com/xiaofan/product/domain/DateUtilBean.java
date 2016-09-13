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

    public DateUtilBean(){}

    public DateUtilBean(String createdStamp) {
        this.createdStamp = createdStamp;
    }

    public String getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(String createdStamp) {
        this.createdStamp = createdStamp;
    }

    @Override
    public String toString() {
        return "DateUtilBean{" +
                "createdStamp='" + createdStamp + '\'' +
                '}';
    }
}
