package com.yb.push.core.impl.huawei;

import android.content.Context;
import android.os.Bundle;

import com.huawei.hms.support.api.push.PushReceiver;
import com.yb.push.core.PushManager;
import com.yb.push.core.common.PushMessageBean;
import com.yb.push.core.common.PushPlatform;

/**
 * desc:华为消息接收者<br>
 * 应用需要创建一个子类继承com.huawei.hms.support.api.push.PushReceiver，
 * 实现onToken，onPushState ，onPushMsg，onEvent，这几个抽象方法，用来接收token返回，push连接状态，透传消息和通知栏点击事件处理。
 * onToken 调用getToken方法后，获取服务端返回的token结果，返回token以及belongId
 * onPushState 调用getPushState方法后，获取push连接状态的查询结果
 * onPushMsg 推送消息下来时会自动回调onPushMsg方法实现应用透传消息处理。本接口必须被实现。 在开发者网站上发送push消息分为通知和透传消息
 * 通知为直接在通知栏收到通知，通过点击可以打开网页，应用 或者富媒体，不会收到onPushMsg消息
 * 透传消息不会展示在通知栏，应用会收到onPushMsg
 * onEvent 该方法会在设置标签、点击打开通知栏消息、点击通知栏上的按钮之后被调用。由业务决定是否调用该函数。
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/12/15 14:43
 */
public class HuaWeiPushMessageReceiver extends PushReceiver {
    static String token;

    //目前支持的回调事件有通知栏消息点击事件回调、通知栏扩展消息按钮点击事件回调
    @Override
    public void onEvent(Context context, Event event, Bundle extras) {
        super.onEvent(context, event, extras);
//        try {
//            PushMessageBean messageBean = createPushMessageBean();
//            messageBean.setData(extras.getString("pushMsg"));
//            PushManager.onNotificationMessageClicked(context, messageBean);
//        } catch (Throwable t) {
//
//        }
//        if (PushManager.getInstance().isOpenLog()) {
//            PushManager.getInstance().log("HUA WEI receiver event: " + event + " extras: " + extras);
//        }
    }

    //接收透传消息
    @Override
    public boolean onPushMsg(Context context, byte[] msgBytes, Bundle extras) {
        try {
            PushMessageBean messageBean = createPushMessageBean();
            messageBean.setData(extras.getString("pushMsg"));
            PushManager.onReceivePassThroughMessage(context, messageBean);
        } catch (Throwable t) {

        }
        if (PushManager.getInstance().isOpenLog()) {
            PushManager.getInstance().log("HUA WEI receiver msg: " + new String(msgBytes) + " extras: " + extras);
        }
        return super.onPushMsg(context, msgBytes, extras);
    }

    //用来接收push连接状态
    @Override
    public void onPushState(Context context, boolean pushState) {
        super.onPushState(context, pushState);
        PushManager.getInstance().log("HUA WEI receiver state: " + pushState);
    }

    //接受token
    @Override
    public void onToken(Context context, String t, Bundle extras) {
        super.onToken(context, t, extras);
        token = t;
        PushMessageBean messageBean = createPushMessageBean();
        messageBean.setData(token);
        PushManager.onReceiveToken(context, messageBean);
        PushManager.getInstance().log("HUA WEI receiver token: " + token);
    }

    private PushMessageBean createPushMessageBean() {
        PushMessageBean bean = new PushMessageBean();
        bean.setPlatform(PushPlatform.HUA_WEI);
        return bean;
    }
}
