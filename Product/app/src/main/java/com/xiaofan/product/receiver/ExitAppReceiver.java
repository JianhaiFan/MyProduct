package com.xiaofan.product.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xiaofan.product.activity.AbstractBaseActivity;

/**
 * @author: 范建海
 * @createTime: 2016/11/3 17:43
 * @className:  ExitAppReceiver
 * @description: 退出应用的广播
 * @changed by:
 */
public class ExitAppReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ((AbstractBaseActivity)context).finish();
    }
}
