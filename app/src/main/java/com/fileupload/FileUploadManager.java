package com.fileupload;

import android.os.Environment;

import com.fileupload.threadpool.AsyncCall;
import com.fileupload.threadpool.ThreadPoolDispatcher;
import com.fileupload.threadpool.ThreadPoolRequest;

import java.io.File;

/**
 * 文件上传管理类
 * @author : hongshen
 * @Date: 2018/5/25 0025
 */
public class FileUploadManager {
    /**
     * 文件存放路径
     */
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/2dfire_manager/leakcanary/";

    /**
     * 上传文件的方法
     * @param url
     * @param file
     */
    public void hahaDonwload(String url, File file) {
        ThreadPoolRequest request = new ThreadPoolRequest.Builder()
                .url(url)
                .file(file)
                .build();
        ThreadPoolDispatcher threadPoolDispatcher = new ThreadPoolDispatcher();
        AsyncCall asyncCall = new AsyncCall(request);
        try {
            threadPoolDispatcher.enqueue(asyncCall);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPoolDispatcher.finished(asyncCall);
        }
    }

    /**
     * 扫描整个文件夹
     */
    public void scanFolder(){

    }
}
