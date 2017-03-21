package com.xiaofan.product.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

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
public abstract class AbstractBaseActivity extends FragmentActivity {

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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 事件被消费的标志
        boolean isEventConsumed = false;

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            isEventConsumed = judgeBackCode();
        }

        return isEventConsumed == true ? isEventConsumed : super.onKeyDown(keyCode,event);
    }

    /**
     * 子类在左上角回退按钮调用的方法
     * @return
     */
    protected boolean judgeBackCode() {
        boolean isEventConsumed = backCode();

        if (!isEventConsumed) {
            finish();
        }

        return isEventConsumed;

    }

    /**
     * 点击物理返回键或者点击界面上返回键回调的方法
     * (只需要实现相应的回退逻辑即可,不调用这个方法)
     * @return
     */
    public abstract boolean backCode();

}
