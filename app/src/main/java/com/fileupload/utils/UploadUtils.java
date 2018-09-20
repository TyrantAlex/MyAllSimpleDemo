package com.fileupload.utils;

import android.text.TextUtils;
import android.util.Log;

import com.fileupload.threadpool.HttpCallBack;
import com.fileupload.vo.LogVo;
import com.fileupload.vo.UploadVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * @author : hongshen
 * @Date: 2018/5/22 0022
 */
public class UploadUtils {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 10000000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";

    /**
     * 上传json数据
     * @param urlPath
     * @param json
     * @return
     */
    public static String uploadJson(String urlPath, String json, HttpCallBack httpCallBack) {
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
//            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
//            conn.setRequestProperty("accept","application/json");
            // 往服务器里面发送数据
            if (json != null && !TextUtils.isEmpty(json)) {
                byte[] writebytes = json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                conn.setRequestProperty("log", json);
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("sqs", "post responseCode:"+conn.getResponseCode());
            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String str, wholeStr = "";
                while((str = reader.readLine()) != null){
                    wholeStr += str;
                }
                Log.d("sqs", "post responseRsult:"+ wholeStr);
                if (httpCallBack != null) {
                    httpCallBack.onSuccess(wholeStr);
                }
            } else {
                if (httpCallBack != null) {
                    httpCallBack.onFail();
                }
            }
        } catch (Exception e) {
            if (httpCallBack != null) {
                httpCallBack.onFail();
            }
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    if (httpCallBack != null) {
                        httpCallBack.onFail();
                    }
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * android上传文件到服务器
     *
     * @param file       需要上传的文件
     * @param RequestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(File file, String RequestURL, HttpCallBack httpCallBack) {
        String BOUNDARY = UUID.randomUUID().toString();  //边界标识   随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";   //内容类型

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Charset", CHARSET);  //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                OutputStream outputSteam = conn.getOutputStream();

                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的   比如:abc.png
                 */

                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.e(TAG, "response code:" + res);
                if (res == 200) {
                    if (httpCallBack != null) {
                        httpCallBack.onSuccess("");
                    }
                    return SUCCESS;
                } else {
                    if (httpCallBack != null) {
                        httpCallBack.onFail();
                    }
                }
            }
        } catch (MalformedURLException e) {
            if (httpCallBack != null) {
                httpCallBack.onFail();
            }
            e.printStackTrace();
        } catch (IOException e) {
            if (httpCallBack != null) {
                httpCallBack.onFail();
            }
            e.printStackTrace();
        }
        return FAILURE;
    }

    public static String getPostJsonString(UploadVo uploadVo) {
        if (uploadVo == null) {
            return "";
        }
        String jsonStr = "";
        try {
            JSONObject originJsonObj = new JSONObject();
            originJsonObj.put("platform", uploadVo.getPlatform());
            originJsonObj.put("packageName", uploadVo.getPackageName());
            originJsonObj.put("version", uploadVo.getVersion());
            originJsonObj.put("apkName", uploadVo.getApkName());
            JSONArray jsonArray = new JSONArray();
            for (LogVo vo : uploadVo.getLog()) {
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
        return jsonStr;
    }
}
