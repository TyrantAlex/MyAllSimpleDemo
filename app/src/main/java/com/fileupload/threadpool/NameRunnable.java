package com.fileupload.threadpool;

import android.util.Log;

/**
 * @author : hongshen
 * @Date: 2018/5/23 0023
 */
public abstract class NameRunnable implements Runnable{
    @Override
    public void run() {
        //多线程测试打印
        Log.d("sqs", "Current HAHA Thread:" + Thread.currentThread().getName());
        execute();
    }

    protected abstract void execute();
}
