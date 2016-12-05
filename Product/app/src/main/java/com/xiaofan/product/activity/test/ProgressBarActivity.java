package com.xiaofan.product.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import com.xiaofan.product.R;
import com.xiaofan.product.activity.AbstractBaseActivity;
import com.xiaofan.product.db.PersonalInfomationHelper;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.ManifestUtil;
import com.xiaofan.product.widget.RoundProgressBar;

/**
 * @author: 范建海
 * @createTime: 2016/11/1 17:11
 * @className:  ProgressBarActivity
 * @description: 进度条页面
 * @changed by:
 */
public class ProgressBarActivity extends AbstractBaseActivity {

    private RoundProgressBar mRoundProgressBar1, mRoundProgressBar2 ,mRoundProgressBar3, mRoundProgressBar4, mRoundProgressBar5;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_progress);

        mRoundProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar1);
        mRoundProgressBar2 = (RoundProgressBar) findViewById(R.id.roundProgressBar2);
        mRoundProgressBar3 = (RoundProgressBar) findViewById(R.id.roundProgressBar3);
        mRoundProgressBar4 = (RoundProgressBar) findViewById(R.id.roundProgressBar4);
        mRoundProgressBar5 = (RoundProgressBar) findViewById(R.id.roundProgressBar5);

        ((Button)findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while(progress <= 100){
                            progress += 3;

                            System.out.println(progress);

                            mRoundProgressBar1.setProgress(progress);
                            mRoundProgressBar2.setProgress(progress);
                            mRoundProgressBar3.setProgress(progress);
                            mRoundProgressBar4.setProgress(progress);
                            mRoundProgressBar5.setProgress(progress);

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).start();
            }
        });

    }

    @Override
    public boolean backCode() {
        return false;
    }


    public void exit(View view) {

        Intent intent = new Intent(ManifestUtil.getPackageName(this)+"." + "ExitAppReceiver");
        sendBroadcast(intent);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);

                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        t.start();

    }
}
