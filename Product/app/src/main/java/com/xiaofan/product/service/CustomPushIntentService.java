package com.xiaofan.product.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.umeng.message.UmengMessageService;
import com.xiaofan.product.domain.UmengMessageBean;
import com.xiaofan.product.util.GsonUtil;
import com.xiaofan.product.util.LogUtil;

import org.android.agoo.common.AgooConstants;

/**
 * @author: 范建海
 * @createTime: 2016/10/24 10:14
 * @className:  MyPushIntentService
 * @description: 友盟完全自定义消息处理
 * @changed by:
 */
public class CustomPushIntentService extends UmengMessageService {

    @Override
    public void onMessage(Context context, Intent intent) {
        // 接收到的消息体
        String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);

        if (!TextUtils.isEmpty(message)) {
            UmengMessageBean umb = GsonUtil.json2Object(message,new TypeToken<UmengMessageBean>(){});

            if (umb != null) {
                sendNotification(context,umb);
            }
        }


    }

    /**
     * 发送通知
     * @param context
     * @param umb
     */
    public void sendNotification(Context context,UmengMessageBean umb) {
        // TODO 通知栏提示，以及后台开进程同步数据
        LogUtil.e("sendNotification: ==============" );

    }
}
