package com.xiaofan.product.activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;

import com.xiaofan.product.R;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.ResourceUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        String line = getFromAssets("text.txt");
        LogUtil.e("line:" + line);




    }


    public String getFromAssets(String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader( ResourceUtil.open(mContext,fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                LogUtil.e("line:line:" + line);
                Result += line;
        } catch (Exception e) {
            LogUtil.e("exception:" + e.toString());
        } finally {
            return Result;
        }
    }

}
