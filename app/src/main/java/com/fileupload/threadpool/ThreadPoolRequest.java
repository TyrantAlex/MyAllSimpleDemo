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

    public ThreadPoolRequest(Builder builder) {
        this.url = builder.url;
        this.file = builder.file;
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

    public static class Builder{
        public String url;

        public File file;

        public Builder() {
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
