package com.fileupload.threadpool;

/**
 * @author huiguo
 * @date 2018/9/11
 */
public interface HttpCallBack {

    void onSuccess(String result);
    void onFail();
}
