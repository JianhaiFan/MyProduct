package com.xiaofan.product.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.umeng.message.PushAgent;
import com.xiaofan.product.net.http.OkHttpUtil;
import com.xiaofan.product.receiver.ExitAppReceiver;
import com.xiaofan.product.util.ManifestUtil;
import com.xiaofan.product.util.ResourceUtil;

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



    /**
     * 消除指定文本框的文本
     * @param editText 指定文本框
     * @param closeParent 关闭按钮父控件
     */
    public void eliminateText(final EditText editText, LinearLayout closeParent) {
        if (editText != null && closeParent != null) {
            String tempStr = editText.getText().toString().trim();

            if (TextUtils.isEmpty(tempStr) || tempStr.length() <= 0) {
                closeParent.setVisibility(View.GONE);
                closeParent.setOnClickListener(null);
            }else {
                closeParent.setVisibility(View.VISIBLE);
                closeParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.setText("");
                    }
                });
            }
        }
    }

    /**
     * 监听EditText焦点变化
     * @param editText
     * @param view EditText下面的横线
     */
    public void setEditTextFocusChangeListener(EditText editText, final View view) {
        if (editText != null) {
            editText.setOnFocusChangeListener(new android.view.View.
                    OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (view != null) {
                        if (hasFocus) {
                            // 此处为得到焦点时的处理内容
                            view.setBackgroundColor(Color.parseColor("#47C11D"));

                        } else {
                            // 此处为失去焦点时的处理内容
                            view.setBackgroundColor(Color.parseColor("#DADADA"));
                        }
                    }

                }
            });
        }

    }



}
