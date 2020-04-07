package com.yb.push.core.impl.vivo;

import android.content.Context;
import android.text.TextUtils;

import com.yb.push.core.PushManager;
import com.yb.push.core.common.BasePushProvider;
import com.yb.push.core.common.IPushListener;
import com.yb.push.core.common.PushMessageBean;
import com.yb.push.core.common.PushPlatform;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;

/**
 * desc:<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/12/14 19:38
 */
public class VivoPushProvider extends BasePushProvider {

    @Override
    public void register(final Context context) {
        try {
            PushManager.getInstance().log("VIVO start register ");
            String appId = getAppMetaData(context, "com.vivo.push.app_id");
            String appKey = getAppMetaData(context, "com.vivo.push.api_key");
            if (PushManager.getInstance().isOpenLog()) {
                PushManager.getInstance().log("VIVO start register appId: " + appId + " appKey: " + appKey);
            }
            if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(appKey)) {
                PushManager.getInstance().log("VIVO register error  need app_id or app_key");
                return;
            }
            PushClient.getInstance(context.getApplicationContext()).initialize();
            PushClient.getInstance(context.getApplicationContext()).turnOnPush(new IPushActionListener() {
                @Override
                public void onStateChanged(int status) {
                    if (PushManager.getInstance().isOpenLog()) {
                        PushManager.getInstance().log("VIVO open status: " + convertMessage(status) + " onReceiveClientId: " + getToken(context));
                    }
                    if (status == 0) {
                        PushMessageBean bean = new PushMessageBean();
                        bean.setPlatform(PushPlatform.VIVO);
                        bean.setData(getToken(context));
                        PushManager.onReceiveToken(context, bean);
                    }
                }
            });
        } catch (Throwable t) {
            if (PushManager.getInstance().isOpenLog()) {
                t.printStackTrace();
            }
        }
    }

    @Override
    public void unRegister(Context context) {
        PushClient.getInstance(context.getApplicationContext()).turnOffPush(new IPushActionListener() {
            @Override
            public void onStateChanged(int status) {
                PushManager.getInstance().log("VIVO close status: " + convertMessage(status));
            }
        });
    }

    @Override
    public void setAlias(Context context, String alias) {
        PushClient.getInstance(context.getApplicationContext()).bindAlias(alias, new IPushActionListener() {
            @Override
            public void onStateChanged(int status) {
                PushManager.getInstance().log("VIVO setAlias status: " + convertMessage(status));
            }
        });
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        PushClient.getInstance(context.getApplicationContext()).unBindAlias(alias, new IPushActionListener() {
            @Override
            public void onStateChanged(int status) {
                PushManager.getInstance().log("VIVO setAlias status: " + convertMessage(status));
            }
        });
    }

    @Override
    public void setTags(Context context, String... tags) {

    }

    @Override
    public void unsetTags(Context context, String... tags) {

    }

    @Override
    public String getToken(Context context) {
        return PushClient.getInstance(context.getApplicationContext()).getRegId();
    }

    @Override
    public PushPlatform getPlatform() {
        return PushPlatform.VIVO;
    }

    @Override
    public String getVersion(Context context) {
        return "2.3.1";
    }

    private String convertMessage(int status) {
        if (!PushManager.getInstance().isOpenLog()) {
            return "" + status;
        }
        String msg = "";
        if (0 == status) {
            msg = "操作成功。";
        } else if (1 == status) {
            msg = "操作成功，此动作在未操作前已经设置成功。";
        } else if (101 == status) {
            msg = "系统不支持。";
        } else if (102 == status) {
            msg = "PUSH 初始化异常，请重现初始化 PUSH。";
        } else if (1001 == status) {
            msg = "一天内调用次数超标。";
        } else if (1002 == status) {
            msg = "操作频率过快。";
        } else if (1003 == status) {
            msg = "操作超时。";
        } else if (1004 == status) {
            msg = "应用处于黑名单。";
        } else if (1005 == status) {
            msg = "当前 push 服务不可用。";
        } else if (10000 == status) {
            msg = "未知异常。";
        } else if (30001 == status) {
            msg = "设置别名失败：请打开 push 开关。";
        } else if (30002 == status) {
            msg = "设置别名失败：订阅别名为空。";
        } else if (30003 == status) {
            msg = "设置别名失败：别名设置超长，字符长度超过 70。";
        } else if (20001 == status) {
            msg = "设置主题失败：请打开 push 开关";
        } else if (20002 == status) {
            msg = "设置主题失败：订阅主题为空";
        } else if (20003 == status) {
            msg = "设置主题失败：别名设置超长，字符长度超过 70。";
        } else if (20004 == status) {
            msg = "设置主题失败：订阅次数太频繁或已订阅数过多。";
        }
        return msg + status;
    }

    public static boolean isSupport(Context context) {
        return hasAppID(context) && isPlatformSupport(context);
    }

    private static boolean isPlatformSupport(Context context) {
        try {
            return PushClient.getInstance(context).isSupport();
        } catch (Throwable t) {
        }
        return false;
    }

    private static boolean hasAppID(Context context) {
        try {
            PushManager.getInstance().log("VIVO check isSupport ");
            String appId = getAppMetaData(context, "com.vivo.push.app_id");
            String appKey = getAppMetaData(context, "com.vivo.push.api_key");
            if (PushManager.getInstance().isOpenLog()) {
                PushManager.getInstance().log("VIVO check isSupport appId: " + appId + " appKey: " + appKey);
            }
            if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(appKey)) {
                return true;
            }
        } catch (Throwable t) {

        }
        return false;
    }
}
