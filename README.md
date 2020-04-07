# IPush
融合多推送平台,提高推送到达率
* 支持[个推](https://www.getui.com/),SDK_version_2.14.0.0
* 支持[小米](https://dev.mi.com/),SDK_version_3.7.6
* 支持[华为](https://developer.huawei.com/consumer/cn/hms/huawei-pushkit/),SDK_version_2.6.1.301
* 支持[oppo](https://open.oppomobile.com/service/openservice#id=1),SDK_version_1.5.0
* 支持[vivo](https://dev.vivo.com.cn/),SDK_version_2.3.1

# Last-Version
1.0.0

#项目引入
添加Maven仓库:
```groovy
allprojects {
    repositories {
        //个推
        maven {
            url "http://mvn.gt.igexin.com/nexus/content/repositories/releases/"
        }
        //华为
        maven { url 'http://developer.huawei.com/repo/' }
    }
}
```
<img src="https://github.com/yuanbinbinbin/IPush/blob/master/ext/img/maven.png?raw=true" alt="maven.png" />

添加Gradle引用:
```groovy
  compile 'com.github.binbinrd:push:{lastVersion}'
```

添加各个平台的appkey:
```groovy
manifestPlaceholders = [
        PUSH_SCHEME:"yb",
        GETUI_APP_ID : "",
        GETUI_APP_KEY : "",
        GETUI_APP_SECRET : "",
        XIAOMI_APP_ID : "",
        XIAOMI_APP_KEY : "",
        VIVO_APP_ID : "",
        VIVO_APP_KEY : "",
        HUAWEI_APP_ID : "",
        OPPO_APP_KEY : "",
        OPPO_APP_SECRET : ""
]
```

<img src="https://github.com/yuanbinbinbin/IPush/blob/master/ext/img/appkey.png?raw=true" alt="appkey.png" />

自定义Service 用来接收数据:
```java
public class PushHandleService extends BasePushIntentService {
    @Override
    public void onReceiveToken(Context context, PushMessageBean token) {
        Log.e("IPush", token.getPlatform() + " token:" + token.getData());
        Log.e("IPush", " pid:" + android.os.Process.myPid());
    }

    @Override
    public void onReceivePassThroughMessage(Context context, PushMessageBean message) {
        Log.e("IPush", "onReceivePassThroughMessage:" + message);
        Log.e("IPush", " pid:" + android.os.Process.myPid());
    }

    @Override
    public void onNotificationMessageClicked(Context context, PushMessageBean message) {
        Log.e("IPush", "onNotificationMessageClicked:" + message);
        Log.e("IPush", " pid:" + android.os.Process.myPid());
    }

    @Override
    public void onNotificationMessageArrived(Context context, PushMessageBean message) {
        Log.e("IPush", "onNotificationMessageArrived:" + message);
        Log.e("IPush", " pid:" + android.os.Process.myPid());
    }
}
```
```
<service android:name=".PushHandleService">
     <intent-filter>
           <action android:name="ipush_action_receive_token" />
           <action android:name="ipush_action_receive_pass_through_message" />
           <action android:name="ipush_action_receive_notification_message_clicked" />
           <action android:name="ipush_action_receive_notification_message_arrived" />
     </intent-filter>
</service>
```

在application中启动push
```
PushManager.getInstance()
           .register(this);
```

#使用规则
个推
---
<img src="https://github.com/yuanbinbinbin/IPush/blob/master/ext/img/getui_icon.png?raw=true" alt="GeTuiIcon.png" />
<img src="https://github.com/yuanbinbinbin/IPush/blob/master/ext/img/getui.png?raw=true" alt="GeTui.png" />

小米
---
<img src="https://github.com/yuanbinbinbin/IPush/blob/master/ext/img/xiaomi.png?raw=true" alt="XiaoMi.png" />

华为
---
```
${PUSH_SCHEME}://${packageName}/push/huawei?Parm1=3&Parm2=2000
```
<img src="https://github.com/yuanbinbinbin/IPush/blob/master/ext/img/huawei.png?raw=true" alt="HuaWei.png" />

Oppo
---
```
${PUSH_SCHEME}://${packageName}/push/oppo?Parm1=3&Parm2=2000
```
<img src="https://github.com/yuanbinbinbin/IPush/blob/master/ext/img/oppo.png?raw=true" alt="Oppo.png" />

Vivo
---
<img src="https://github.com/yuanbinbinbin/IPush/blob/master/ext/img/vivo.png?raw=true" alt="Vivo.png" />