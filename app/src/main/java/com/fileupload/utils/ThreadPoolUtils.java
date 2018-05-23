package com.fileupload.utils;

import java.util.concurrent.ThreadFactory;

/**
 * @author : hongshen
 * @Date: 2018/5/23 0023
 */
public class ThreadPoolUtils {

    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }

}
