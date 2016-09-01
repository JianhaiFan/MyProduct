package com.xiaofan.product.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaofan.product.R;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.ResourceUtil;


public class MainActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        Boolean isBool = ResourceUtil.getBoolean(mContext,R.bool.no);
        if (isBool) {
            LogUtil.e("hehe...");
        }else {
            LogUtil.e("sb....");
        }


    }
}
