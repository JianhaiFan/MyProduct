package com.xiaofan.product.domain;

/**
 * @author: 范建海
 * @createTime: 2016/9/27 19:17
 * @className:  DateUtilBean
 * @description: 日期工具类基类实体Bean，让按照日期排序的实体Bean继承
 * @changed by:
 */
public abstract class AbstractDateUtilBean  {
    // 后台返回的时间字段字符串
    protected String createdStamp;

    public String getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(String createdStamp) {
        this.createdStamp = createdStamp;
    }
}
