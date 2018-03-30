package com.realm.bean;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * @author : hongshen
 * @Date: 2018/3/28 0028
 */

public class CelebiPage extends RealmObject{

    private String android;

    private String id;

    private String ios;

    private String url;

    private RealmList<String> version;

    //是否已经有本地json缓存
    private boolean cacehed;

    public boolean isCacehed() {
        return cacehed;
    }

    public void setCacehed(boolean cacehed) {
        this.cacehed = cacehed;
    }

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RealmList<String> getVersion() {
        return version;
    }

    public void setVersion(RealmList<String> version) {
        this.version = version;
    }
}
