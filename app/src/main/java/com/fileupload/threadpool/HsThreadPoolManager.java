package com.fileupload.threadpool;

public class HsThreadPoolManager {

    public static HsThreadPool mThreadPool;

    public static HsThreadPool newInstance(){
        if (mThreadPool == null) {
            synchronized (HsThreadPoolManager.class) {
                if (mThreadPool == null) {
                    mThreadPool = new HsThreadPool(0, Integer.MAX_VALUE, 60L);
                }
            }
        }
        return mThreadPool;
    }
}
