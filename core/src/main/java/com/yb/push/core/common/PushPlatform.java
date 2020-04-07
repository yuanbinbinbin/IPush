package com.yb.push.core.common;

/**
 * desc:平台<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/9/7 20:40
 */
public enum PushPlatform {
    /**
     * 个推推送
     */
    GE_TUI("GE_TUI", 1),
    XIAO_MI("XIAO_MI", 2),
    VIVO("VIVO", 3),
    HUA_WEI("HUA_WEI", 4),
    OPPO("OPPO", 5);

    PushPlatform(String s, int i) {
        name = s;
        code = i;
    }

    public String name;
    public int code;
}
