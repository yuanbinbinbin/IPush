package com.yb.push.core.impl.huawei;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.DeleteTokenHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.api.push.HmsPushConst;
import com.yb.push.core.PushManager;
import com.yb.push.core.common.BasePushProvider;
import com.yb.push.core.common.IPushListener;
import com.yb.push.core.common.IPushProvider;
import com.yb.push.core.common.PushPlatform;

import java.lang.reflect.Method;

/**
 * desc:<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/12/15 14:47
 */
public class HuaWeiPushProvider extends BasePushProvider {

    @Override
    public void register(Context context) {
        try {
            PushManager.getInstance().log("HUA WEI start register ");
            String appID = getAppMetaData(context, "com.huawei.hms.client.appid");
            PushManager.getInstance().log("HUA WEI : APP_ID:" + appID);
            appID = appID.substring("appid=".length());
            if (TextUtils.isEmpty(appID)) {
                PushManager.getInstance().log("HUA WEI register error  need app_id");
                return;
            }
            HMSAgent.init((Application) (context.getApplicationContext()));
            HMSAgent.connect(new ConnectHandler() {
                @Override
                public void onConnect(int rst) {
                    PushManager.getInstance().log("HUA WEI connect end:" + convertMessage(rst));
                }
            });
            HMSAgent.Push.getToken(new GetTokenHandler() {
                @Override
                public void onResult(int rst) {
                    PushManager.getInstance().log("HUA WEI get Token end:" + convertMessage(rst));
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
        HMSAgent.Push.deleteToken(getToken(context), new DeleteTokenHandler() {
            @Override
            public void onResult(int rst) {
                PushManager.getInstance().log("hua wei delete Token end:" + convertMessage(rst));
            }
        });
    }

    @Override
    public void setAlias(Context context, String alias) {

    }

    @Override
    public void unsetAlias(Context context, String alias) {

    }

    @Override
    public void setTags(Context context, String... tags) {

    }

    @Override
    public void unsetTags(Context context, String... tags) {

    }

    @Override
    public String getToken(Context context) {
        return HuaWeiPushMessageReceiver.token;
    }

    @Override
    public PushPlatform getPlatform() {
        return PushPlatform.HUA_WEI;
    }

    @Override
    public String getVersion(Context context) {
        return "2.6.1.301";
    }

    private String convertMessage(int status) {
        if (!PushManager.getInstance().isOpenLog()) {
            return "" + status;
        }
        String msg = "";
        if (HMSAgent.AgentResultCode.HMSAGENT_SUCCESS == status) {
            msg = "成功";
        } else if (HMSAgent.AgentResultCode.HMSAGENT_NO_INIT == status) {
            msg = "没有初始化";
        } else if (HMSAgent.AgentResultCode.NO_ACTIVITY_FOR_USE == status) {
            msg = "请求需要activity，但当前没有可用的activity";
        } else if (HMSAgent.AgentResultCode.RESULT_IS_NULL == status) {
            msg = "结果为空";
        } else if (HMSAgent.AgentResultCode.STATUS_IS_NULL == status) {
            msg = "状态为空";
        } else if (HMSAgent.AgentResultCode.START_ACTIVITY_ERROR == status) {
            msg = "拉起activity异常，需要检查activity有没有在manifest中配置";
        } else if (HMSAgent.AgentResultCode.ON_ACTIVITY_RESULT_ERROR == status) {
            msg = "回调结果错误";
        } else if (HMSAgent.AgentResultCode.REQUEST_REPEATED == status) {
            msg = "重复请求";
        } else if (HMSAgent.AgentResultCode.APICLIENT_TIMEOUT == status) {
            msg = "连接client 超时";
        } else if (HMSAgent.AgentResultCode.CALL_EXCEPTION == status) {
            msg = "调用接口异常";
        } else if (HMSAgent.AgentResultCode.EMPTY_PARAM == status) {
            msg = "接口参数为空";
        } else if (HmsPushConst.ErrorCode.REPORT_TAG_SUCCESS == status) {
            msg = "上报tag成功";
        } else if (HmsPushConst.ErrorCode.REPORT_SYSTEM_ERROR == status) {
            msg = "HMS 系统错误";
        } else if (HmsPushConst.ErrorCode.REPORT_PARAM_INVALID == status) {
            msg = "上报tag时参数错误";
        } else if (HmsPushConst.ErrorCode.PLUGIN_TOKEN_INVALID == status) {
            msg = "上报tag时，token无效";
        } else if (HmsPushConst.ErrorCode.NETWORK_INVALID == status) {
            msg = "无网络";
        } else if (HmsPushConst.ErrorCode.HAS_NOT_AGREE_PUSH_TERMS == status) {
            msg = "不同意push条款";
        } else if (HmsPushConst.ErrorCode.AGREE_PUSH_TERMS == status) {
            msg = "同意push条款";
        } else if (HmsPushConst.ErrorCode.DISAGREE_PUSH_TERMS == status) {
            msg = "不同意push条款";
        } else if (CommonCode.ErrorCode.ARGUMENTS_INVALID == status) {
            msg = "传入的参数错误";
        } else if (CommonCode.ErrorCode.INTERNAL_ERROR == status) {
            msg = "内部错误";
        } else if (CommonCode.ErrorCode.NAMING_INVALID == status) {
            msg = "服务不存在";
        } else if (CommonCode.ErrorCode.CLIENT_API_INVALID == status) {
            msg = "apicliente对象无效";
        } else if (CommonCode.ErrorCode.EXECUTE_TIMEOUT == status) {
            msg = "调用AIDL超时";
        } else if (CommonCode.ErrorCode.NOT_IN_SERVICE == status) {
            msg = "当前区域不支持此业务";
        } else if (CommonCode.ErrorCode.SESSION_INVALID == status) {
            msg = "AIDL连接session无效";
        } else if (CommonCode.ErrorCode.HMS_VERSION_CONFIGER_INVALID == status) {
            msg = "开发者配置的HMS APK的版本号不满足当前接口要求的HMS APK的最低版本号";
        } else if (CommonCode.StatusCode.API_UNAVAILABLE == status) {
            msg = "请求的API module不存在";
        } else if (CommonCode.StatusCode.API_CLIENT_EXPIRED == status) {
            msg = "API_CLIENT会话过期";
        } else if (ConnectionResult.SERVICE_MISSING == status) {
            msg = "在设备上没有发现华为移动服务";
        } else if (ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED == status) {
            msg = "已经安装的华为移动服务过时了";
        } else if (ConnectionResult.SERVICE_DISABLED == status) {
            msg = "此设备上的华为移动服务已经不可用";
        } else if (ConnectionResult.DEVELOPER_ERROR == status) {
            msg = "应用配置错误。该错误为不可恢复,将被视为致命。开发者应该看看日志后这个决定更有效的信息";
        } else if (ConnectionResult.SERVICE_UNSUPPORTED == status) {
            msg = "设备因太老而不能被支持";
        }
        return msg + status;
    }

    public static boolean isSupport(Context context) {
        return hasAppID(context) && isPlatformSupport(context);
    }

    private static boolean isPlatformSupport(Context context) {
        int emuiApiLevel = 0;
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            Method method = cls.getDeclaredMethod("get", new Class[]{String.class});
            emuiApiLevel = Integer.parseInt((String) method.invoke(cls, new Object[]{"ro.build.hw_emui_api_level"}));
        } catch (Exception e) {
        }
        // >= emui 4.0, 获取要求的最低版本是16
        return emuiApiLevel >= 8 && Build.VERSION.SDK_INT >= 16;
    }

    private static boolean hasAppID(Context context) {
        try {
            PushManager.getInstance().log("HUA WEI check isSupport ");
            String appID = getAppMetaData(context, "com.huawei.hms.client.appid");
            PushManager.getInstance().log("HUA WEI : APP_ID:" + appID);
            appID = appID.substring("appid=".length());
            if (!TextUtils.isEmpty(appID)) {
                return true;
            }
        } catch (Throwable t) {
            if (PushManager.getInstance().isOpenLog()) {
                t.printStackTrace();
            }
        }
        return false;
    }
}
