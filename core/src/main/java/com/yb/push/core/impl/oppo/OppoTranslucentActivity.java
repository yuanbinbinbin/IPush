package com.yb.push.core.impl.oppo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yb.push.core.PushManager;
import com.yb.push.core.common.PushMessageBean;
import com.yb.push.core.common.PushPlatform;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * desc:<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/12/22 16:02
 */
public class OppoTranslucentActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Uri uri = getIntent().getData();
            Map<String, Object> map = new HashMap<String, Object>();
            if (uri != null) {
                Set<String> keys = uri.getQueryParameterNames();
                if (keys != null && !keys.isEmpty()) {
                    for (String key : keys) {
                        map.put(key, uri.getQueryParameter(key));
                    }
                }
            }
            if (!map.isEmpty()) {
                JSONObject jsonObject = new JSONObject(map);
                PushMessageBean bean = new PushMessageBean();
                bean.setPlatform(PushPlatform.OPPO);
                bean.setData(jsonObject.toString());
                PushManager.onNotificationMessageClicked(getApplicationContext(), bean);
            }
        } catch (Throwable t) {
        }
        finish();
    }
}
