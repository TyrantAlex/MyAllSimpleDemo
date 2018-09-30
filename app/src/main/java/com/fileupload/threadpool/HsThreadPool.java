package com.fileupload.threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HsThreadPool {

    private ThreadPoolExecutor mExecutor;

    private int corePoolSize;

    private int maximumPoolSize;

    private long keepAliveTime;

    public HsThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    public void execute(Runnable runnable) {
        if (runnable == null)
            return;
        if (mExecutor == null) {
            mExecutor = new ThreadPoolExecutor(corePoolSize
                    , maximumPoolSize
                    , keepAliveTime, TimeUnit.SECONDS
                    , new LinkedBlockingDeque<Runnable>());
        }

        mExecutor.execute(runnable);
    }

    public void remove(Runnable runnable) {
        if (mExecutor != null) {
            mExecutor.getQueue().remove(runnable);
        }
    }
}
