package com.fileupload.threadpool;

import com.fileupload.utils.UploadUtils;

/**
 * @author : hongshen
 * @Date: 2018/5/23 0023
 */
public class AsyncCall extends NameRunnable{

    private ThreadPoolRequest request;

    public AsyncCall(ThreadPoolRequest request) {
        this.request = request;
    }

    String host() {
        return request.host();
    }

    @Override
    protected void execute() {
        UploadUtils.uploadFile(request.file(), request.url());
    }
}
