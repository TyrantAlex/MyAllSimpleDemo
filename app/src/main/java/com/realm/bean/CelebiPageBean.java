package com.realm.bean;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * @author : hongshen
 * @Date: 2018/3/28 0028
 */

public class CelebiPageBean extends RealmObject{

    private String packageVersion;

    private RealmList<CelebiPage> pages;

    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    public RealmList<CelebiPage> getPages() {
        return pages;
    }

    public void setPages(RealmList<CelebiPage> pages) {
        this.pages = pages;
    }
}
