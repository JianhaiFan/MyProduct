package com.xiaofan.product.activity.other;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.xiaofan.product.R;
import com.xiaofan.product.activity.AbstractBaseActivity;
import com.xiaofan.product.util.ResourceUtil;
import com.xiaofan.product.util.ToastUtil;

/**
 * @author: 范建海
 * @createTime: 2016/11/20 15:26 
 * @className:  StartPageActivity
 * @description: 
 * @changed by:
 */
public class OtherStartPageActivity extends AbstractBaseActivity{

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_start_page);
        mContext = OtherStartPageActivity.this;
    }


    public void onSwitchLanguage(View view) {
        ToastUtil.show(mContext, ResourceUtil.getString(mContext,R.string.language),0);
    }

    public void onLogin(View view) {
        ToastUtil.show(mContext, ResourceUtil.getString(mContext,R.string.loginin),0);
    }

    public void onRegister(View view) {
        ToastUtil.show(mContext, ResourceUtil.getString(mContext,R.string.register),0);
    }
}
