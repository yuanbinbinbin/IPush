package com.yb.push.core.impl.vivo;

import android.content.Context;

import com.yb.push.core.PushManager;
import com.yb.push.core.common.PushMessageBean;
import com.yb.push.core.common.PushPlatform;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

/**
 * desc:vivo 消息接收者<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/12/14 19:34
 */
public class VivoPushMessageReceiver extends OpenClientPushMessageReceiver {

    /**
     * 通知被点击后的结果返回。当 push 发出的通知被点击后便会触发onNotificationClicked 通知应用。
     *
     * @param context
     * @param msg
     */
    //通知被点击后的结果返回。当 push 发出的通知被点击后便会触发onNotificationClicked 通知应用。
    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage msg) {
        //vivo onNotificationMessageClicked is called. UPSNotificationMessage{mTargetType=0, mTragetContent='', mTitle='我是title', mContent='我是内容', mNotifyType=2, mPurePicUrl='', mIconUrl='', mCoverUrl='', mSkipContent='{"a":"a","b":"b"}', mSkipType=4, mShowTime=false, mMsgId=-587757232, mParams={d=d, c=c}}
        try {
            PushManager.getInstance().log("VIVO onNotificationMessageClicked is called. " + msg.toString());
            PushMessageBean bean = createPushMessageBean();
            bean.setTitle(msg.getTitle());
            bean.setContent(msg.getContent());
            bean.setData(msg.getSkipContent());
            PushManager.onNotificationMessageClicked(context, bean);
        } catch (Throwable t) {
        }
    }

    /**
     * RegId 结果返回。当开发者首次调用 turnOnPush 成功或 regId 发生改变时会回调该方法。
     *
     * @param context
     * @param regId
     */
    //RegId 结果返回。当开发者首次调用 turnOnPush 成功或 regId 发生改变时会回调该方法。
    @Override
    public void onReceiveRegId(Context context, String regId) {
        PushManager.getInstance().log("VIVO Push onReceiveClientId: " + regId);
    }

    private PushMessageBean createPushMessageBean() {
        PushMessageBean bean = new PushMessageBean();
        bean.setPlatform(PushPlatform.VIVO);
        return bean;
    }

}
