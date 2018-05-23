package com.fileupload.threadpool;

import com.fileupload.utils.ThreadPoolUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : hongshen
 * @Date: 2018/5/23 0023
 */
public class ThreadPoolDispatcher {

    /**
     * 最大请求数量
     */
    private int maxRequests = 64;

    /**
     * 同一主机最大请求数量
     */
    private int maxRequestsPerHost = 5;

    /**
     * 超过最大请求数量或者同一主机最大请求数量时, 请求放在此集合中
     */
    private final Deque<AsyncCall> readyAsyncCalls = new ArrayDeque<>();

    /**
     * 所有请求集合
     */
    private final Deque<AsyncCall> runningAsyncCalls = new ArrayDeque<>();

    /**
     *
     */
    private final Deque<AsyncCall> runningSyncCalls = new ArrayDeque<>();

    private ExecutorService executorService;

    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), ThreadPoolUtils.threadFactory("HaHa Dispatcher", false));
        }
        return executorService;
    }

    public synchronized void enqueue(AsyncCall call){
        //所有请求小于最大请求数, 且来自同一主机的请求数小于最大同一主机请求数
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(call) < maxRequestsPerHost) {
            runningAsyncCalls.add(call);
            executorService().execute(call);
        } else {
            //超过最大请求数量或者同一主机最大请求数量时, 请求放在此集合中
            readyAsyncCalls.add(call);
        }
    }

    /**
     * 来自同一主机请求数量
     * @param call
     * @return
     */
    private int runningCallsForHost(AsyncCall call) {
        int result = 0;
        for (AsyncCall c : runningAsyncCalls) {
            if (c.host().equals(call.host())) result++;
        }
        return result;
    }

    /**
     * finish
     * @param call
     */
    public synchronized void finished(AsyncCall call){
        if (!runningAsyncCalls.remove(call)) throw new AssertionError("Call wasn't running!");
        promoteCalls();
    }

    private void promoteCalls() {
        //已经运行最大容量
        if (runningAsyncCalls.size() >= maxRequests) return;
        //ready任务池为空
        if (readyAsyncCalls.isEmpty()) return;

        for (Iterator<AsyncCall> i = readyAsyncCalls.iterator(); i.hasNext();) {
            AsyncCall call = i.next();

            if (runningCallsForHost(call) < maxRequestsPerHost) {
                i.remove();
                runningAsyncCalls.add(call);
                executorService().execute(call);
            }
            //达到最大容量
            if(runningAsyncCalls.size() >= maxRequests) return;
        }
    }
}
