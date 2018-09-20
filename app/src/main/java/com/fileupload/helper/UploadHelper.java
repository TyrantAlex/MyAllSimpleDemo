package com.fileupload.helper;

import com.fileupload.constants.UploadUrlConstants;
import com.fileupload.threadpool.AsyncCall;
import com.fileupload.threadpool.ThreadPoolDispatcher;
import com.fileupload.threadpool.ThreadPoolRequest;
import com.fileupload.vo.LogVo;
import com.fileupload.vo.UploadVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 上传
 * @author : hongshen
 * @Date: 2018/7/10 0010
 */
public class UploadHelper {

    private static UploadHelper instance;

    public static UploadHelper getInstance() {
        if (instance == null) {
            instance = new UploadHelper();
        }
        return instance;
    }

    private UploadHelper() {

    }

    /**
     * 开始上传
     * @param uploadVo 日志对象
     */
    public void startUpload(UploadVo uploadVo) {
        start(uploadVo);
    }

    /**
     * 开始上传
     * @param content 日志内容
     */
    public void startUpload(String content) {
        UploadVo uploadVo = conversion(content);
        start(uploadVo);
    }

    private void start(UploadVo uploadVo) {
        if (uploadVo == null) {
            return;
        }
        List<LogVo> logs = uploadVo.getLog();
        //parse obj to json
        String jsonStr = null;
        try {
            JSONObject originJsonObj = new JSONObject();
            originJsonObj.put("platform", uploadVo.getPlatform());
            originJsonObj.put("packageName", uploadVo.getPackageName());
            originJsonObj.put("version", uploadVo.getVersion());
            originJsonObj.put("apkName", uploadVo.getApkName());
            JSONArray jsonArray = new JSONArray();
            for (LogVo vo : logs) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uniqueId", vo.getUniqueId());
                jsonObject.put("content", vo.getContent());
                jsonArray.put(jsonObject);
            }
            originJsonObj.put("log", jsonArray);
            jsonStr = "log=" + originJsonObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ThreadPoolRequest request = new ThreadPoolRequest.Builder()
                .url(UploadUrlConstants.MEMORY_UPLOAD_URL)
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

    private UploadVo conversion(String content) {
        return null;
    }
}
