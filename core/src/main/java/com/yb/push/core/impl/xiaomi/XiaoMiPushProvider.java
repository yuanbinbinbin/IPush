package com.yb.push.core.impl.xiaomi;

import android.content.Context;
import android.text.TextUtils;

import com.yb.push.core.PushManager;
import com.yb.push.core.common.BasePushProvider;
import com.yb.push.core.common.IPushListener;
import com.yb.push.core.common.PushPlatform;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * desc:<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/12/14 14:09
 */
public class XiaoMiPushProvider extends BasePushProvider {

    @Override
    public void register(Context context) {
        try {
            PushManager.getInstance().log("XIAO MI start register ");
            String appID = getAppMetaData(context, "XIAOMI_APP_ID");
            String appKey = getAppMetaData(context, "XIAOMI_APP_KEY");
            PushManager.getInstance().log("XIAO MI : APP_ID:" + appID + " appKEY:" + appKey);
            appID = appID.substring("XIAOMI_APP_ID".length());
            appKey = appKey.substring("XIAOMI_APP_KEY".length());
            if (TextUtils.isEmpty(appID) || TextUtils.isEmpty(appKey)) {
                PushManager.getInstance().log("XIAO MI register error  need app_id or app_key");
                return;
            }
            MiPushClient.registerPush(context, appID, appKey);
        } catch (Throwable t) {
            if (PushManager.getInstance().isOpenLog()) {
                t.printStackTrace();
            }
        }
    }

    @Override
    public void unRegister(Context context) {
        MiPushClient.unregisterPush(context);
    }

    @Override
    public void setAlias(Context context, String alias) {
        if (!TextUtils.isEmpty(getToken(context))) {
            MiPushClient.setAlias(context.getApplicationContext(), alias, null);
        }
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        if (!TextUtils.isEmpty(getToken(context))) {
            MiPushClient.unsetAlias(context.getApplicationContext(), alias, null);
        }
    }

    @Override
    public void setTags(Context context, String... tags) {

    }

    @Override
    public void unsetTags(Context context, String... tags) {

    }

    @Override
    public String getToken(Context context) {
        return MiPushClient.getRegId(context);
    }

    @Override
    public PushPlatform getPlatform() {
        return PushPlatform.XIAO_MI;
    }

    @Override
    public String getVersion(Context context) {
        return "3.7.6";
    }

    public static boolean isSupport(Context context) {
        return hasAppID(context) && isPlatformSupport(context);
    }

    private static boolean isPlatformSupport(Context context) {
        return true;
    }

    private static boolean hasAppID(Context context) {
        try {
            PushManager.getInstance().log("xiao mi check isSupport ");
            String appID = getAppMetaData(context, "XIAOMI_APP_ID");
            String appKey = getAppMetaData(context, "XIAOMI_APP_KEY");
            PushManager.getInstance().log("isSupport XIAO MI : APP_ID:" + appID + " appKEY:" + appKey);
            appID = appID.substring("XIAOMI_APP_ID".length());
            appKey = appKey.substring("XIAOMI_APP_KEY".length());
            if (!TextUtils.isEmpty(appID) && !TextUtils.isEmpty(appKey)) {
                return true;
            }
        } catch (Throwable t) {
        }
        return false;
    }
}
