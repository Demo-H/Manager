package com.tupperware.mgt.base;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;

import com.dhunter.common.GlobalAppComponent;
import com.dhunter.common.utils.FileUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tupperware.mgt.R;
import com.tupperware.mgt.wxapi.WxKey;

/**
 * Created by dhunter on 2018/6/25.
 */

public class BaseApplication extends Application {

    private static BaseApplication mInstance;
    public static IWXAPI mWxApi;


    @Override
    public void onCreate() {
        super.onCreate();
        if (mInstance == null) {
            mInstance = this;
        }
        init();
    }

    private void init() {
        /**
         * 初始化SDK卡目录
         */
        FileUtils.initSdcardDirs(this);
        /**内存泄漏初始化**/
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Volley 初始化
//        RequestManager.init(this);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
//        createImageCache();

        Fresco.initialize(this);
        GlobalAppComponent.init(getApplicationContext());
        //向微信注册
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, WxKey.APP_ID, false);
        mWxApi.registerApp(WxKey.APP_ID); // 将该app注册到微信

    }

    /**
     * 突破64K问题，MultiDex构建
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}
