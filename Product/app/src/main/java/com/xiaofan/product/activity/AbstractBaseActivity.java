package com.xiaofan.product.activity;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.message.PushAgent;

/**
 * @author: 范建海
 * @createTime: 2016/8/24 20:45 
 * @className:  AbstractBaseActivity
 * @Description: Activity页面的基类
 */
public abstract class AbstractBaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 友盟统计
        PushAgent.getInstance(this).onAppStart();
    }
}
