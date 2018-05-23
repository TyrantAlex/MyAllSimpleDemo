package com.fileupload;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.alex.dagger.R;
import com.fileupload.threadpool.AsyncCall;
import com.fileupload.threadpool.ThreadPoolDispatcher;
import com.fileupload.threadpool.ThreadPoolRequest;
import com.fileupload.utils.UploadUtils;

import java.io.File;

/**
 * 文件上传测试类
 * @author : hongshen
 * @Date: 2018/5/22 0022
 */
public class FileUploadTestActivity extends Activity{

    private String path = "";

    private static final String url = "http://10.1.87.29:8080/download/TomcatTest/FileUpload";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload_test);
        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "2dfire.txt";
                final File file = new File(path);
                if (file.exists()) {
//                    for (int i = 0; i < 15; i++) {
//                        trashDownload(file);
//                    }
                    hahaDonwload(file);
                }
            }
        });
    }

    private void trashDownload(final File file){
        new Thread(){
            @Override
            public void run() {
                //多线程测试打印
                Log.d("sqs", "Current Thread:" + Thread.currentThread().getName());
                UploadUtils.uploadFile(file, url);
            }
        }.start();
    }

    private void hahaDonwload(File file){
        ThreadPoolRequest request = new ThreadPoolRequest.Builder()
                .url(url)
                .file(file)
                .build();
        ThreadPoolDispatcher threadPoolDispatcher = new ThreadPoolDispatcher();
        AsyncCall asyncCall = new AsyncCall(request);
        try{
            for (int i = 0; i < 15; i++) {
                threadPoolDispatcher.enqueue(asyncCall);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPoolDispatcher.finished(asyncCall);
        }
    }
}
