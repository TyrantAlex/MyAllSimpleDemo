package com.fileupload.threadpool;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author : hongshen
 * @Date: 2018/5/23 0023
 */
public class ThreadPoolRequest {

    private String url;

    private String host;

    private File file;

    private String param;

    public ThreadPoolRequest(Builder builder) {
        this.url = builder.url;
        this.file = builder.file;
        this.param = builder.param;
    }

    public String host() {
        try {
            if (url != null) {
                host = new URL(url).getHost();
            } else {
                throw new NullPointerException("url can not be null!");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return host;
    }

    public String url(){
        return url;
    }

    public File file(){
        return file;
    }

    public String params(){
        return param;
    }

    public static class Builder{
        public String url;

        public File file;

        public String param;

        public Builder() {
        }

        public Builder param(String param){
            this.param = param;
            return this;
        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder file(File file){
            this.file = file;
            return this;
        }

        public ThreadPoolRequest build(){
            return new ThreadPoolRequest(this);
        }
    }
}
