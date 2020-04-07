package com.yb.push.core;

import android.app.IntentService;
import android.content.Intent;

import com.yb.push.core.common.IPushListener;
import com.yb.push.core.common.PushMessageBean;


/**
 * 用于接收推送消息的基类
 */
public abstract class BasePushIntentService extends IntentService implements IPushListener {


    public BasePushIntentService() {
        super("BasePushIntentService");
    }

    @Override
    public final void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            try {
                PushMessageBean message = (PushMessageBean) intent.getSerializableExtra(PushAction.MESSAGE);
                if (PushAction.ACTION_RECEIVE_TOKEN.equals(action)) {
                    onReceiveToken(this, message);
                } else if (PushAction.ACTION_RECEIVE_NOTIFICATION_MESSAGE_CLICKED.equals(action)) {
                    onNotificationMessageClicked(this, message);
                } else if (PushAction.ACTION_RECEIVE_NOTIFICATION_MESSAGE_ARRIVED.equals(action)) {
                    onNotificationMessageArrived(this, message);
                } else if (PushAction.ACTION_RECEIVE_PASS_THROUGH_MESSAGE.equals(action)) {
                    onReceivePassThroughMessage(this, message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static final class PushAction {
        public static final String MESSAGE = "message";
        public static final String ACTION_RECEIVE_TOKEN = "ipush_action_receive_token";
        public static final String ACTION_RECEIVE_PASS_THROUGH_MESSAGE = "ipush_action_receive_pass_through_message";
        public static final String ACTION_RECEIVE_NOTIFICATION_MESSAGE_CLICKED = "ipush_action_receive_notification_message_clicked";
        public static final String ACTION_RECEIVE_NOTIFICATION_MESSAGE_ARRIVED = "ipush_action_receive_notification_message_arrived";
    }
}