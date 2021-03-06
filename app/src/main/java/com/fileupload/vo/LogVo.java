package com.fileupload.vo;

/**
 * @author : hongshen
 * @Date: 2018/7/4 0004
 */
public class LogVo {
    /**
     * 日志加密或hash后的唯一标记
     */
    private String uniqueId;

    /**
     * 日志具体内容
     */
    private String content;

    /**
     * 日志类型
     */
    private String type;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
