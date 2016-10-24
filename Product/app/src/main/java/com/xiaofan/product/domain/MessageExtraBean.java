package com.xiaofan.product.domain;

import java.io.Serializable;

/**
 * @author: 范建海
 * @createTime: 2016/10/24 11:31
 * @className:  MessageExtraBean
 * @description: 友盟消息扩展消息
 * @changed by:
 */
public class MessageExtraBean implements Serializable {
    // 消息类型
    private String messageType;
    // 附加内容
    private String map;
    // 是否要通知栏提示
    private String isSilent;

    public MessageExtraBean() {}

    public MessageExtraBean(String messageType, String map, String isSilent) {
        this.messageType = messageType;
        this.map = map;
        this.isSilent = isSilent;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getIsSilent() {
        return isSilent;
    }

    public void setIsSilent(String isSilent) {
        this.isSilent = isSilent;
    }

    @Override
    public String toString() {
        return "MessageExtraBean{" +
                "messageType='" + messageType + '\'' +
                ", map='" + map + '\'' +
                ", isSilent='" + isSilent + '\'' +
                '}';
    }
}
