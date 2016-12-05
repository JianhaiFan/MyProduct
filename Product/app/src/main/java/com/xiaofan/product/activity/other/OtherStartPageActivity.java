package com.xiaofan.product.activity.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaofan.product.R;
import com.xiaofan.product.activity.AbstractBaseActivity;

/**
 * @author: 范建海
 * @createTime: 2016/11/20 15:26 
 * @className:  OtherStartPageActivity
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

    @Override
    public boolean backCode() {
        return false;
    }


    public void onSwitchLanguage(View view) {
        startActivity(new Intent(mContext,OtherLanguageSwitchActivity.class));
    }

    public void onLogin(View view) {
        startActivity(new Intent(mContext,OtherLoginActivity.class));
    }

    public void onRegister(View view) {
        startActivity(new Intent(mContext,MainActivity.class));
    }
}
