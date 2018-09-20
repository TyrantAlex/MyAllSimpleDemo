package com.fileupload.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author : hongshen
 * @Date: 2018/7/4 0004
 */
public class UploadVo implements Serializable {
    private String platform;

    private String packageName;

    private String version;

    private String apkName;

    private List<LogVo> log;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public List<LogVo> getLog() {
        return log;
    }

    public void setLog(List<LogVo> log) {
        this.log = log;
    }
}
