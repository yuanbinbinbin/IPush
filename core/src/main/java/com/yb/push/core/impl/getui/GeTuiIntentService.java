package com.yb.push.core.impl.getui;

import android.content.Context;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.yb.push.core.PushManager;
import com.yb.push.core.common.PushMessageBean;
import com.yb.push.core.common.PushPlatform;


import org.json.JSONObject;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class GeTuiIntentService extends GTIntentService {
    private static PushMessageBean notificationMessage = null;

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    /**
     * 透传消息
     *
     * @param context
     * @param msg
     */
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        if (msg == null || msg.getPayload() == null) {
            PushManager.getInstance().log("GE TUI onReceiveMessageData -> " + "payload=null");
            return;
        }
        String data = new String(msg.getPayload());
        PushManager.getInstance().log("GE TUI onReceiveMessageData -> " + "payload = " + data);
        try {
            //是否显示通知栏
            if (notificationMessage != null) {
                notificationMessage.setData(data);
                PushManager.onNotificationMessageClicked(context, notificationMessage);
                notificationMessage = null;
            } else {
                PushMessageBean message = new PushMessageBean();
                message.setData(data);
                message.setPlatform(PushPlatform.GE_TUI);
                PushManager.onReceivePassThroughMessage(context, message);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveClientId(Context context, String clientId) {
        PushManager.getInstance().log("GE TUI onReceiveClientId -> " + "clientId = " + clientId);
        PushMessageBean message = new PushMessageBean();
        message.setPlatform(PushPlatform.GE_TUI);
        message.setData(clientId);
        PushManager.onReceiveToken(context, message);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
        if (msg == null) {
            PushManager.getInstance().log("GE TUI onNotificationMessageArrived -> " + "payload=null");
            return;
        }
        PushMessageBean message = new PushMessageBean();
        message.setTitle(msg.getTitle());
        message.setContent(msg.getContent());
        message.setPlatform(PushPlatform.GE_TUI);
        PushManager.onNotificationMessageArrived(context, message);
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
        if (msg == null) {
            PushManager.getInstance().log("GE TUI onNotificationMessageClicked -> " + "payload=null");
            return;
        }
        notificationMessage = new PushMessageBean();
        notificationMessage.setTitle(msg.getTitle());
        notificationMessage.setContent(msg.getContent());
        notificationMessage.setPlatform(PushPlatform.GE_TUI);
    }
}
