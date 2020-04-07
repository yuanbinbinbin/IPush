package com.yb.push.core.impl.oppo;

import android.content.Context;
import android.text.TextUtils;

import com.coloros.mcssdk.PushManager;
import com.coloros.mcssdk.callback.PushAdapter;
import com.yb.push.core.common.BasePushProvider;
import com.yb.push.core.common.PushMessageBean;
import com.yb.push.core.common.PushPlatform;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2019/3/28 19:53
 */
public class OppoProvider extends BasePushProvider {
    @Override
    public void register(Context context) {
        try {
            context = context.getApplicationContext();
            com.yb.push.core.PushManager.getInstance().log("OPPO start register ");
            String appKey = getAppMetaData(context, "OPPO_APP_KEY");
            String appSecret = getAppMetaData(context, "OPPO_APP_SECRET");
            com.yb.push.core.PushManager.getInstance().log("OPPO : APP_KEY:" + appKey + " appSecret:" + appSecret);
            if (TextUtils.isEmpty(appSecret) || TextUtils.isEmpty(appKey)) {
                com.yb.push.core.PushManager.getInstance().log("OPPO register error  need app_id or app_key");
                return;
            }
            final Context finalContext = context;
            PushManager.getInstance().register(context.getApplicationContext(), appKey, appSecret, new PushAdapter() {
                //注册的结果,如果注册成功,registerID就是客户端的唯一身份标识
                @Override
                public void onRegister(int responseCode, String registerID) {
                    if (responseCode == 0) {
                        com.yb.push.core.PushManager.getInstance().log("OPPO onReceiveClientId -> " + "clientId = " + registerID + " version: 【" + getVersion(finalContext) + "】");
                        PushMessageBean message = new PushMessageBean();
                        message.setPlatform(PushPlatform.OPPO);
                        message.setData(registerID);
                        com.yb.push.core.PushManager.onReceiveToken(finalContext, message);
                    }
                }

            });
        } catch (Throwable t) {
        }
    }

    @Override
    public void unRegister(Context context) {
        try {
            PushManager.getInstance().unRegister();
        } catch (Throwable e) {
        }
    }

    @Override
    public void setAlias(Context context, String alias) {
        try {
            List<String> aliases = new ArrayList<>();
            aliases.add(alias);
            PushManager.getInstance().setAliases(aliases);
        } catch (Throwable e) {
        }
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        try {
            PushManager.getInstance().unsetAlias(alias);
        } catch (Throwable e) {
        }
    }

    @Override
    public void setTags(Context context, String... tags) {
        try {
            if (tags != null) {
                List<String> tagList = new ArrayList<String>();
                for (int i = 0; i < tags.length; i++) {
                    tagList.add(tags[i]);
                }
                PushManager.getInstance().setTags(tagList);
            }
        } catch (Throwable e) {
        }
    }

    @Override
    public void unsetTags(Context context, String... tags) {
        try {
            if (tags != null) {
                List<String> tagList = new ArrayList<String>();
                for (int i = 0; i < tags.length; i++) {
                    tagList.add(tags[i]);
                }
                PushManager.getInstance().unsetTags(tagList);
            }
        } catch (Throwable e) {
        }
    }

    @Override
    public String getToken(Context context) {
        try {
            return PushManager.getInstance().getRegisterID();
        } catch (Throwable e) {
        }
        return "";
    }

    @Override
    public PushPlatform getPlatform() {
        return PushPlatform.OPPO;
    }

    @Override
    public String getVersion(Context context) {
        try {
            return PushManager.getInstance().getPushVersionName();
        } catch (Throwable e) {
        }
        return "";
    }

    public static boolean isSupport(Context context) {
        return hasAppID(context) && isPlatformSupport(context);
    }

    private static boolean isPlatformSupport(Context context) {
        try {
            return PushManager.isSupportPush(context);
        } catch (Throwable t) {
        }
        return false;
    }

    private static boolean hasAppID(Context context) {
        try {
            com.yb.push.core.PushManager.getInstance().log("oppo check isSupport ");
            String appKey = getAppMetaData(context, "OPPO_APP_KEY");
            String appSecret = getAppMetaData(context, "OPPO_APP_SECRET");
            com.yb.push.core.PushManager.getInstance().log("OPPO : APP_KEY:" + appKey + " appSecret:" + appSecret);
            if (!TextUtils.isEmpty(appKey) && !TextUtils.isEmpty(appSecret)) {
                return true;
            }
        } catch (Throwable t) {
        }
        return false;
    }
}
