package com.fileupload;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.alex.dagger.R;
import com.fileupload.threadpool.AsyncCall;
import com.fileupload.threadpool.HsThreadPool;
import com.fileupload.threadpool.HsThreadPoolManager;
import com.fileupload.threadpool.ThreadPoolDispatcher;
import com.fileupload.threadpool.ThreadPoolRequest;
import com.fileupload.utils.MD5Util;
import com.fileupload.utils.UploadUtils;
import com.fileupload.vo.LogVo;
import com.fileupload.vo.UploadVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传测试类
 * @author : hongshen
 * @Date: 2018/5/22 0022
 */
public class FileUploadTestActivity extends Activity{

    private String path = "";

    /**
     * http://10.1.87.29:8080/download/TomcatTest/FileUpload
     * http://10.1.87.29:8080/download/boss-ci/v1/upload_memory_leak
     *
     * http://10.1.86.54:8080/boss-ci/v1/upload_memory_leak
     */
    private static final String url = "http://10.1.86.54:8080/boss-ci/v1/upload_memory_leak";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload_test);
        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //文件上传
//                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "2dfire.txt";
//                final File file = new File(path);
//                if (file.exists()) {
//                    for (int i = 0; i < 15; i++) {
//                        trashDownload(file);
//                    }

//                    hahaDonwload(file);
//                }

                //post
                postJson(null);
            }
        });

    }

    /**
     * post上传
     */
    private void postJson(UploadVo uploadVo) {
        //test
        UploadVo dataVo = new UploadVo();
        dataVo.setApkName("二维火掌柜");
        dataVo.setPlatform("Android");
        dataVo.setVersion("5.6.68");
        dataVo.setPackageName("zmsoft.rest.dev");
        LogVo logVo = new LogVo();
        logVo.setContent("这是一段内存分析日志1");
        logVo.setUniqueId(MD5Util.encode("这是一段内存分析日志1"));
        LogVo logVo2 = new LogVo();
        logVo2.setContent("这是一段内存分析日志2");
        logVo2.setUniqueId(MD5Util.encode("这是一段内存分析日志2"));
        List<LogVo> logs = new ArrayList<>();
        logs.add(logVo);
        logs.add(logVo2);
        dataVo.setLog(logs);

        //parse obj to json
        String jsonStr = null;
        try {
            JSONObject originJsonObj = new JSONObject();
            originJsonObj.put("platform", dataVo.getPlatform());
            originJsonObj.put("packageName", dataVo.getPackageName());
            originJsonObj.put("version", dataVo.getVersion());
            originJsonObj.put("apkName", dataVo.getApkName());
            JSONArray jsonArray = new JSONArray();
            for (LogVo vo : logs) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uniqueId", vo.getUniqueId());
                jsonObject.put("content", vo.getContent());
                jsonArray.put(jsonObject);
            }
            originJsonObj.put("log", jsonArray);
//            JSONObject log = new JSONObject();
//            log.put("log", originJsonObj.toString());
            jsonStr = "log=" + originJsonObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ThreadPoolRequest request = new ThreadPoolRequest.Builder()
                .url(url)
                .param(jsonStr)
                .build();
        ThreadPoolDispatcher threadPoolDispatcher = new ThreadPoolDispatcher();
        AsyncCall asyncCall = new AsyncCall(request);
        try{
            threadPoolDispatcher.enqueue(asyncCall);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPoolDispatcher.finished(asyncCall);
        }
    }


    private void trashDownload(final File file){
        new Thread(){
            @Override
            public void run() {
                //多线程测试打印
                Log.d("sqs", "Current Thread:" + Thread.currentThread().getName());
                UploadUtils.uploadFile(file, url, null);
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
            threadPoolDispatcher.enqueue(asyncCall);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPoolDispatcher.finished(asyncCall);
        }
    }

    private void hsThreadPoolTest() {
        ThreadPoolRequest request = new ThreadPoolRequest.Builder()
                .url(url)
                .param("1232131")
                .build();
        AsyncCall asyncCall = new AsyncCall(request);
        HsThreadPool threadPool = HsThreadPoolManager.newInstance();
        threadPool.execute(asyncCall);
    }
}
