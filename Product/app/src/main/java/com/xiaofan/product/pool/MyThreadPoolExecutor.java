package com.xiaofan.product.pool;

import com.xiaofan.product.util.LogUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: 范建海
 * @createTime: 2017/1/10 16:54
 * @className:  MyThreadPoolExcutor
 * @description: 自定义线程池
 * @changed by:
 */

public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        String threadName = t.getName();
        LogUtil.e("线程：" + threadName + "准备执行任务！");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        String threadName = Thread.currentThread().getName();
        LogUtil.e("线程：" + threadName + "任务执行结束！");
    }

    @Override
    protected void terminated() {
        super.terminated();
        LogUtil.e("线程池结束");
    }
}

