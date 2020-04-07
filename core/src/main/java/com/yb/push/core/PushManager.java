package com.yb.push.core;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.yb.push.core.common.IPushProvider;
import com.yb.push.core.common.PushMessageBean;
import com.yb.push.core.common.PushPlatform;
import com.yb.push.core.impl.getui.GeTuiPushProvider;
import com.yb.push.core.impl.huawei.HuaWeiPushProvider;
import com.yb.push.core.impl.oppo.OppoProvider;
import com.yb.push.core.impl.vivo.VivoPushProvider;
import com.yb.push.core.impl.xiaomi.XiaoMiPushProvider;
import com.vivo.push.PushClient;

/**
 * desc:推送管理器<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/9/10 10:21
 */
public class PushManager {
    private static PushManager instance;
    private IPushProvider pushProvider;
    private PushPlatform selectPlatform;
    private Selector selector;

    private PushManager() {
    }

    public static synchronized PushManager getInstance() {
        if (instance == null) {
            instance = new PushManager();
        }
        return instance;
    }

    public PushManager setSelector(Selector selector) {
        if (selector == null) {
            return this;
        }
        this.selector = selector;
        return this;
    }

    public void register(Context context) {
        if (context == null) {
            return;
        }
        context = context.getApplicationContext();
        if (selector == null) {
            selector = new Selector();
        }
        if (pushProvider != null) {
            pushProvider.unRegister(context);
        }
        selectPlatform = selector.select(context, Build.BRAND);
        if (selectPlatform != null) {
            pushProvider = createProviders(selectPlatform);
            if (isOpenLog()) {
                log("select Push Platform  【" + selectPlatform.name + "】 version: 【" + getVersion(context) + "】");
            }
            pushProvider.register(context);
        }
    }

    public void unregister(Context context) {
        if (pushProvider != null && context != null) {
            pushProvider.unRegister(context.getApplicationContext());
        }
    }

    public void setAlias(Context context, String alias) {
        if (pushProvider != null) {
            pushProvider.setAlias(context, alias);
        }
    }

    public void unsetAlias(Context context, String alias) {
        if (pushProvider != null) {
            pushProvider.unsetAlias(context, alias);
        }
    }

    public void setTags(Context context, String... tags) {
        if (pushProvider != null) {
            pushProvider.setTags(context, tags);
        }
    }

    public void unsetTags(Context context, String... tags) {
        if (pushProvider != null) {
            pushProvider.unsetTags(context, tags);
        }
    }

    /**
     * 推送平台分配的id
     *
     * @param context
     * @return
     */
    public String getToken(Context context) {
        if (pushProvider != null) {
            return pushProvider.getToken(context);
        }
        return "";
    }

    /**
     * 获取推送平台
     *
     * @return
     */
    public PushPlatform getPlatform() {
        return selectPlatform;
    }

    /**
     * 获取推送平台int值
     *
     * @return
     */
    public int getPlatformCode() {
        PushPlatform platform = getPlatform();
        if (platform == null) {
            return -1;
        }
        return platform.code;
    }

    //获取版本号
    public String getVersion(Context context) {
        if (pushProvider != null) {
            return pushProvider.getVersion(context);
        }
        return "";
    }

    public static class Selector {
        public PushPlatform select(Context context, String brand) {
            if (TextUtils.isEmpty(brand)) {
                return PushPlatform.GE_TUI;
            }
            brand = brand.toLowerCase();
            if (VivoPushProvider.isSupport(context)) {
                return PushPlatform.VIVO;
            } else if (HuaWeiPushProvider.isSupport(context)) {
                return PushPlatform.HUA_WEI;
            } else if (OppoProvider.isSupport(context)) {
                return PushPlatform.OPPO;
            } else if (brand.contains("xiaomi") && XiaoMiPushProvider.isSupport(context)) {
                return PushPlatform.XIAO_MI;
            } else
                return PushPlatform.GE_TUI;
        }
    }

    private IPushProvider createProviders(PushPlatform selectPlatform) {
        switch (selectPlatform) {
            case GE_TUI:
                return new GeTuiPushProvider();
            case XIAO_MI:
                return new XiaoMiPushProvider();
            case VIVO:
                return new VivoPushProvider();
            case HUA_WEI:
                return new HuaWeiPushProvider();
            case OPPO:
                return new OppoProvider();
            default:
                return new GeTuiPushProvider();
        }
    }

    public static void onReceiveToken(Context context, PushMessageBean token) {
        Intent intent = new Intent(BasePushIntentService.PushAction.ACTION_RECEIVE_TOKEN);
        intent.putExtra(BasePushIntentService.PushAction.MESSAGE, token);
        intent.setPackage(context.getPackageName());
        context.startService(intent);
    }

    public static void onReceivePassThroughMessage(Context context, PushMessageBean message) {
        Intent intent = new Intent(BasePushIntentService.PushAction.ACTION_RECEIVE_PASS_THROUGH_MESSAGE);
        intent.putExtra(BasePushIntentService.PushAction.MESSAGE, message);
        intent.setPackage(context.getPackageName());
        context.startService(intent);
    }

    public static void onNotificationMessageClicked(Context context, PushMessageBean message) {
        Intent intent = new Intent(BasePushIntentService.PushAction.ACTION_RECEIVE_NOTIFICATION_MESSAGE_CLICKED);
        intent.putExtra(BasePushIntentService.PushAction.MESSAGE, message);
        intent.setPackage(context.getPackageName());
        context.startService(intent);
    }

    public static void onNotificationMessageArrived(Context context, PushMessageBean message) {
        Intent intent = new Intent(BasePushIntentService.PushAction.ACTION_RECEIVE_NOTIFICATION_MESSAGE_ARRIVED);
        intent.putExtra(BasePushIntentService.PushAction.MESSAGE, message);
        intent.setPackage(context.getPackageName());
        context.startService(intent);
    }

    //region logger
    private boolean isOpen = false;
    private String logTAG = "IPush";

    /**
     * @param TAG 可传空 , 默认为IPush
     */
    public PushManager openLog(String TAG) {
        isOpen = true;
        if (!TextUtils.isEmpty(TAG)) {
            logTAG = TAG;
        }
        return this;
    }

    public void log(String msg) {
        if (isOpen) {
            Log.e(logTAG, msg);
        }
    }

    public boolean isOpenLog() {
        return isOpen;
    }
    //endregion
}
