package com.yb.push.core.common;

import java.io.Serializable;

/**
 * desc:推送消息实体类<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/9/7 20:36
 */
public class PushMessageBean implements Serializable {
    private String title;
    private String content;
    private String data;
    private PushPlatform platform;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public PushPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(PushPlatform platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "PushMessageBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", data='" + data + '\'' +
                ", platform=" + platform +
                '}';
    }
}
