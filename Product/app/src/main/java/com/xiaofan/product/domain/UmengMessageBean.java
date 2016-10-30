package com.xiaofan.product.domain;

import java.io.Serializable;

/**
 * @author: 范建海
 * @createTime: 2016/10/24 11:27
 * @className:  UmengMessageBean
 * @description: 友盟消息实体Bean
 * @changed by:
 */
public class UmengMessageBean implements Serializable{
    // 推送的别名
    private String alias;
    // 消息实体
    private UmengMessageBodyBean body;
    // 附加消息
    private UmengMessageExtraBean extra;
}
