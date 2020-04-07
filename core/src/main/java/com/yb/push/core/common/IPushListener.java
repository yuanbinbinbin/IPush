package com.yb.push.core.common;

import android.content.Context;

/**
 * desc:推送监听<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/9/7 20:48
 */
public interface IPushListener {

    /**
     * 获取到token
     *
     * @param context
     * @param token
     */
    public void onReceiveToken(Context context, PushMessageBean token);

    /**
     * 透传
     *
     * @param context
     * @param message
     */
    public void onReceivePassThroughMessage(Context context, PushMessageBean message);

    /**
     * 通知栏消息点击
     *
     * @param context
     * @param message
     */
    public void onNotificationMessageClicked(Context context, PushMessageBean message);

    /**
     * 通知栏消息到达
     *
     * @param context
     * @param message
     */
    public void onNotificationMessageArrived(Context context, PushMessageBean message);
}
