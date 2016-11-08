package com.xiaofan.product.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.umeng.message.PushAgent;
import com.xiaofan.product.net.http.OkHttpUtil;
import com.xiaofan.product.receiver.ExitAppReceiver;
import com.xiaofan.product.util.ManifestUtil;

/**
 * @author: 范建海
 * @createTime: 2016/8/24 20:45 
 * @className:  AbstractBaseActivity
 * @Description: Activity页面的基类
 */
public abstract class AbstractBaseActivity extends Activity {

    protected ExitAppReceiver mExitAppReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 统计友盟消息打开的次数
        PushAgent.getInstance(this).onAppStart();
        // 注册退出应用的广播接收者
        registerExitAppReceiver();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消网络响应
        OkHttpUtil.cancel(AbstractBaseActivity.this);

        if (mExitAppReceiver != null) {
            unregisterReceiver(mExitAppReceiver);
        }
    }

    /**
     * 注册退出应用的广播
     */
    public void registerExitAppReceiver() {
        // 发送广播
        // Intent intent = new Intent(ManifestUtil.getPackageName(this)+"." + "ExitAppReceiver");
        // sendBroadcast(intent);

        mExitAppReceiver = new ExitAppReceiver();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(ManifestUtil.getPackageName(this) + "." + "ExitAppReceiver");
        registerReceiver(mExitAppReceiver, intentfilter);
    }
}
