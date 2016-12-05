package com.xiaofan.product.activity.other;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xiaofan.product.R;
import com.xiaofan.product.activity.AbstractBaseActivity;
import com.xiaofan.product.util.ToastUtil;
import com.xiaofan.product.widget.dialog.LoadingDialog;

/**
 * @author: 范建海
 * @createTime: 2016/11/21 9:38
 * @className:  OtherLoginActivity
 * @description: 登录页面
 * @changed by:
 */
public class OtherLoginActivity extends AbstractBaseActivity {

    private Context mContext;
    private LinearLayout ll_input_phone_clear;
    private EditText et_phone;
    private LinearLayout ll_input_psd_clear;
    private EditText et_password;
    private View view_phone_divider;
    private View view_psd_divider;
    private Button bt_login;

    private LoadingDialog dialog;
    private Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        };
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_login);
        mContext = OtherLoginActivity.this;

        ll_input_phone_clear = (LinearLayout) findViewById(R.id.ll_input_phone_clear);
        et_phone = (EditText) findViewById(R.id.et_phone);
        ll_input_psd_clear = (LinearLayout) findViewById(R.id.ll_input_psd_clear);
        et_password = (EditText) findViewById(R.id.et_password);
        view_phone_divider = findViewById(R.id.view_phone_divider);
        view_psd_divider = findViewById(R.id.view_psd_divider);
        bt_login = (Button) findViewById(R.id.bt_login);

        initListener();

    }

    @Override
    public boolean backCode() {
        return false;
    }

    public void initListener() {

        setEditTextFocusChangeListener(et_phone,view_phone_divider);
        setEditTextFocusChangeListener(et_password,view_psd_divider);

        // 手机号EditText监听
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                eliminateText(et_phone,ll_input_phone_clear);
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    bt_login.setEnabled(true);
                }else {
                    bt_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        // 密码EditText监听
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                eliminateText(et_password,ll_input_psd_clear);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


    }



    public void onLogin(View view) {
        dialog = new LoadingDialog(this, R.layout.dialog_tips_loading,R.string.loading);
        dialog.show();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    public void onBack(View view) {
        finish();
    }

}
