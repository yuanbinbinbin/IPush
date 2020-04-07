package com.yb.push.core.impl.xiaomi;

import android.content.Context;

import com.yb.push.core.PushManager;
import com.yb.push.core.common.PushMessageBean;
import com.yb.push.core.common.PushPlatform;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * desc:<br/>
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 2、需要将自定义的 DemoMessageReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 。<br/>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked ，
 * 。<br/>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived <br/>
 * 6、DemoMessageReceiver 的 onCommandResult 。<br/>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult <br/>
 * 8、以上这些方法运行在非 UI 线程中。<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/12/14 13:49
 */
public class XiaoMiPushMessageReceiver extends PushMessageReceiver {
    private String mRegId;
    private String mTopic;
    private String mAlias;
    private String mAccount;
    private String mStartTime;
    private String mEndTime;

    /**
     * 方法用来接收服务器向客户端发送的透传消息
     *
     * @param context
     * @param message
     */
    //方法用来接收服务器向客户端发送的透传消息
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        PushManager.getInstance().log("XIAO MI onReceivePassThroughMessage is called. " + message.toString());
        try {
            PushMessageBean bean = createPushMessageBean();
            bean.setContent("");
            bean.setData(message.getContent());
            PushManager.onReceivePassThroughMessage(context, bean);
        } catch (Throwable t) {
        }
    }


    /**
     * 方法用来接收服务器向客户端发送的通知消息,这个回调方法会在用户手动点击通知后触发
     *
     * @param context
     * @param message
     */
    //方法用来接收服务器向客户端发送的通知消息,这个回调方法会在用户手动点击通知后触发
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        //XIAO MI onNotificationMessageClicked is called. messageId={sdm00145544778548225Bb},passThrough={0},alias={null},topic={null},userAccount={null},content={{"jumpType":3,"moduleNo":1000,"args":""}},description={testset},title={test},isNotified={true},notifyId={0},notifyType={5}, category={null}, extra={{source=op, notify_foreground=1, __planId__=0}}
        PushManager.getInstance().log("XIAO MI onNotificationMessageClicked is called. " + message.toString());
        try {
            PushMessageBean bean = createPushMessageBean();
            bean.setContent("");
            bean.setData(message.getContent());
            PushManager.onNotificationMessageClicked(context, bean);
        } catch (Throwable t) {
        }
    }

    /**
     * 方法用来接收服务器向客户端发送的通知消息，这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。
     *
     * @param context
     * @param message
     */
    //   方法用来接收服务器向客户端发送的通知消息，这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        PushManager.getInstance().log("XIAO MI onNotificationMessageArrived is called. " + message.toString());
        try {
            PushMessageBean bean = createPushMessageBean();
            bean.setContent("");
            bean.setData(message.getContent());
            PushManager.onNotificationMessageArrived(context, bean);
        } catch (Throwable t) {
        }
    }

    /**
     * 方法用来接收客户端向服务器发送命令后的响应结果
     *
     * @param context
     * @param message
     */
    //方法用来接收客户端向服务器发送命令后的响应结果
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        PushManager.getInstance().log("XIAO MI onCommandResult is called. " + message.toString());
    }

    /**
     * 方法用来接收客户端向服务器发送注册命令后的响应结果。
     *
     * @param context
     * @param message
     */
    //方法用来接收客户端向服务器发送注册命令后的响应结果。
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        PushManager.getInstance().log("XIAO MI onReceiveRegisterResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                PushMessageBean bean = createPushMessageBean();
                bean.setData(mRegId);
                PushManager.onReceiveToken(context, bean);
                PushManager.getInstance().log("XIAO MI Push onReceiveClientId: " + mRegId);
            } else {
                PushManager.getInstance().log("XIAO MI Push onReceiveClientId: error");
            }
        }
    }

    private PushMessageBean createPushMessageBean() {
        PushMessageBean bean = new PushMessageBean();
        bean.setPlatform(PushPlatform.XIAO_MI);
        return bean;
    }
}
