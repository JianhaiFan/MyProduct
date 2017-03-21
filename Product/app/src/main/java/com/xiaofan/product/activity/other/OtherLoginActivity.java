package com.xiaofan.product.activity.other;

import android.content.Context;
import android.content.Intent;
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
import com.xiaofan.product.net.http.test.NetWorkTestActivity;
import com.xiaofan.product.pool.MyThreadPoolExecutor;
import com.xiaofan.product.pool.PausableThreadPoolExecutor;
import com.xiaofan.product.pool.PriorityRunnable;
import com.xiaofan.product.util.LogUtil;
import com.xiaofan.product.util.ViewUtil;
import com.xiaofan.product.widget.dialog.LoadingDialog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: 范建海
 * @createTime: 2016/11/21 9:38
 * @className:  OtherLoginActivity
 * @description: 登录页面
 * @changed by:
 */
public class OtherLoginActivity extends AbstractBaseActivity {
    // 上下文
    private Context mContext;
    // 手机号清除按钮
    private LinearLayout ll_input_phone_clear;
    // 手机号    private EditText et_phone;
    // 密码清除按钮
    private LinearLayout ll_input_psd_clear;
    // 密码
    private EditText et_password;
    // 手机号分割线
    private View view_phone_divider;
    // 密码分割线
    private View view_psd_divider;
    // 登录按钮
    private Button bt_login;
    // 加载进度条
    private LoadingDialog dialog;

    private Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                startActivity(new Intent(mContext, NetWorkTestActivity.class));
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



    public void initListener() {

        ViewUtil.setEditTextFocusChangeListener(et_phone,view_phone_divider);
        ViewUtil.setEditTextFocusChangeListener(et_password,view_psd_divider);

        // 手机号EditText监听
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ViewUtil.eliminateText(et_phone,ll_input_phone_clear);
                // 控制登录按钮的可用情况
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
                ViewUtil.eliminateText(et_password,ll_input_psd_clear);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }



    public void onLogin(View view) {
        LogUtil.e("================线程池=================");
//        method1();
//        method2();
//        method3();
//        method4();
//        method4_1();
//        method5();
//        method6();
        method7();

    }

    public void method7() {

        PausableThreadPoolExecutor pausableThreadPoolExecutor = new PausableThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue());

        for (int i = 1; i <= 100; i++) {
            final int priority = i;
            pausableThreadPoolExecutor.execute(new PriorityRunnable(priority) {
                @Override
                public void doSth() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.e(priority + "  --");
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    // 具有暂停功能的线程池
    public void method6() {
        ExecutorService myThreadPool = new MyThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue());

        myThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                LogUtil.e("线程：" + Thread.currentThread().getName() + "正在执行任务!");
            }
        });

        myThreadPool.shutdown();


    }

    // 自定义优先级线程池
    public void method5() {
        ExecutorService priorityThreadPool = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue());
        for (int i = 1; i <= 10; i++) {
            final int priority = i;
            priorityThreadPool.execute(new PriorityRunnable(priority) {
                @Override
                public void doSth() {
                    String threadName = Thread.currentThread().getName();
                    LogUtil.e("线程：" + threadName + ",正在执行优先级为：" + priority + "的任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    // 创建一个可以定时或者周期性执行任务的线程池
    public void method4_1() {
        ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
        //延迟1秒后，每隔2秒执行一次该任务
        singleThreadScheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                LogUtil.e("线程：" + threadName + ",正在执行");
            }
        },1,2,TimeUnit.SECONDS);
    }

    // 创建一个可以定时或者周期性执行任务的线程池
    public void method4() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        //延迟2秒后执行该任务
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {

            }
        }, 2, TimeUnit.SECONDS);
        //延迟1秒后，每隔2秒执行一次该任务
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    // 创建一个可以根据实际情况调整线程池中线程的数量的线程池
    public void method3() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    LogUtil.e("线程："+threadName+",正在执行第" + index + "个任务");
                    try {
                        long time = index * 500;
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    //创建一个只有一个线程的线程池，每次只能执行一个线程任务，多余的任务会保存到一个任务队列中，等待线程处理完再依次处理任务队列中的任务
    public void method2() {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    LogUtil.e("线程："+threadName+",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    // 创建一个固定线程数量的线程池
    public void method1() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    LogUtil.e("线程："+threadName+",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }



    public void onBack(View view) {
        judgeBackCode();
    }

    @Override
    public boolean backCode() {
        if(dialog != null) {
//            dialog.dismiss();
        }
        return false;
    }

}
