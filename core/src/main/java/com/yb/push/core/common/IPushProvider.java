package com.yb.push.core.common;

import android.content.Context;

/**
 * desc:推送提供者<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/9/7 20:27
 */
public interface IPushProvider {

    /**
     * 启动推送
     *
     * @param context
     */
    void register(Context context);

    /**
     * 关闭推送
     *
     * @param context
     */
    void unRegister(Context context);

    /**
     * 个推：设置别名
     *
     * @param context
     * @param alias
     */
    void setAlias(Context context, String alias);

    /**
     * 取消
     *
     * @param context
     * @param alias
     */
    void unsetAlias(Context context, String alias);

    /**
     * 个推：设置标签
     *
     * @param context
     * @param tags
     */
    void setTags(Context context, String... tags);

    void unsetTags(Context context, String... tags);

    /**
     * 推送平台分配的id
     *
     * @param context
     * @return
     */
    String getToken(Context context);

    /**
     * 获取推送平台
     *
     * @return
     */
    PushPlatform getPlatform();

    //获取版本号
    String getVersion(Context context);

}
