package com.yb.push;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yb.push.core.PushManager;
import com.yb.push.core.common.PushPlatform;


/**
 * desc:主activity<br>
 * author : yuanbin<br>
 * email : binbinrd@foxmail.com<br>
 * date : 2018/9/7 16:26
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tuisong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGeTui(v);
            }
        });
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushManager.getInstance().unregister(MainActivity.this);
            }
        });
    }

    public void startGeTui(View view) {
        PushManager.getInstance()
                .openLog(null)
                .register(this);
    }
}
