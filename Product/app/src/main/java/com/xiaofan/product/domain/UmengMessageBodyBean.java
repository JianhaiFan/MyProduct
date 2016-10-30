package com.xiaofan.product.domain;

import java.io.Serializable;

/**
 * @author: 范建海
 * @createTime: 2016/10/24 11:29
 * @className:  MessageBodyBean
 * @description: 友盟消息体
 * @changed by:
 */
public class UmengMessageBodyBean implements Serializable {
    // 消息标题
    private String title;
    // 自定义消息
    private String custom;
    // 消息内容
    private String text;

    public UmengMessageBodyBean() {}

    public UmengMessageBodyBean(String title, String custom, String text) {
        this.title = title;
        this.custom = custom;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "UmengMessageBodyBean{" +
                "title='" + title + '\'' +
                ", custom='" + custom + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
