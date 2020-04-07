package com.yb.push.core.impl.getui;

import android.content.Context;
import android.text.TextUtils;

import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.yb.push.core.common.BasePushProvider;
import com.yb.push.core.common.IPushListener;
import com.yb.push.core.common.PushPlatform;

/**
 * desc:个推推送管理类<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/9/7 20:43
 */
public class GeTuiPushProvider extends BasePushProvider {

    @Override
    public void register(Context context) {
        try {
            com.yb.push.core.PushManager.getInstance().log("GE TUI start register ");
            String appId = getAppMetaData(context, "GETUI_APP_ID");
            String appKey = getAppMetaData(context, "GETUI_APP_KEY");
            String appSecret = getAppMetaData(context, "GETUI_APP_SECRET");
            if (com.yb.push.core.PushManager.getInstance().isOpenLog()) {
                com.yb.push.core.PushManager.getInstance().log("GE TUI start register appId: " + appId + " appKey: " + appKey + " appSecret: " + appSecret);
            }
            if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(appKey) || TextUtils.isEmpty(appSecret)) {
                com.yb.push.core.PushManager.getInstance().log("GE TUI register error   need app_id or app_key or appSecret");
                return;
            }
            PushManager.getInstance().initialize(context, null);
            PushManager.getInstance().registerPushIntentService(context, GeTuiIntentService.class);
            PushManager.getInstance().turnOnPush(context);
        } catch (Throwable t) {
            if (com.yb.push.core.PushManager.getInstance().isOpenLog()) {
                t.printStackTrace();
            }
        }
    }

    @Override
    public void unRegister(Context context) {
        PushManager.getInstance().turnOffPush(context);
    }

    @Override
    public void setAlias(Context context, String alias) {
        PushManager.getInstance().bindAlias(context, alias);
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        PushManager.getInstance().unBindAlias(context, alias, false);
    }

    @Override
    public void setTags(Context context, String... tags) {
        Tag[] temps = new Tag[tags.length];
        for (int i = 0; i < tags.length; i++) {
            Tag tag = new Tag();
            tag.setName(tags[i]);
            temps[i] = tag;
        }
        PushManager.getInstance().setTag(context, temps, null);
    }

    @Override
    public void unsetTags(Context context, String... tags) {
        PushManager.getInstance().setTag(context, new Tag[0], null);
    }

    @Override
    public String getToken(Context context) {
        return PushManager.getInstance().getClientid(context);
    }

    @Override
    public PushPlatform getPlatform() {
        return PushPlatform.GE_TUI;
    }

    @Override
    public String getVersion(Context context) {
        return PushManager.getInstance().getVersion(context);
    }

}
