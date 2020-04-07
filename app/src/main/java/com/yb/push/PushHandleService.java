package com.yb.push;

import android.content.Context;
import android.util.Log;

import com.yb.push.core.common.PushMessageBean;
import com.yb.push.core.BasePushIntentService;

/**
 * desc:接收service<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/9/10 11:29
 */
public class PushHandleService extends BasePushIntentService {
    @Override
    public void onReceiveToken(Context context, PushMessageBean token) {
        Log.e("IPush", token.getPlatform() + " token:" + token.getData());
        Log.e("IPush", " pid:" + android.os.Process.myPid());
    }

    @Override
    public void onReceivePassThroughMessage(Context context, PushMessageBean message) {
        Log.e("IPush", "onReceivePassThroughMessage:" + message);
        Log.e("IPush", " pid:" + android.os.Process.myPid());
    }

    @Override
    public void onNotificationMessageClicked(Context context, PushMessageBean message) {
        Log.e("IPush", "onNotificationMessageClicked:" + message);
        Log.e("IPush", " pid:" + android.os.Process.myPid());
    }

    @Override
    public void onNotificationMessageArrived(Context context, PushMessageBean message) {
        Log.e("IPush", "onNotificationMessageArrived:" + message);
        Log.e("IPush", " pid:" + android.os.Process.myPid());
    }
}
