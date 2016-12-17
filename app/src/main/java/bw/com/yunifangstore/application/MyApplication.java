package bw.com.yunifangstore.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;

import bw.com.yunifangstore.utils.ImageLoaderUtils;


/**
 * @author : 张鸿鹏
 * @date : 2016/11/28.
 */

public class MyApplication extends Application {
    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        //获取应用的上下文
        context = getApplicationContext();
        //handler线程
        handler = new Handler();
        //获取主线程的线程号
        mainThreadId = Process.myTid();
        //imageLoader初始化
        ImageLoaderUtils.initConfiguration(this);
        //xutils3初始化配置
        x.Ext.init(this);
        //设置是debug模式
        x.Ext.setDebug(true);
        //屏幕适配
        AutoLayoutConifg.getInstance().useDeviceSize();
        //qq登录
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMShareAPI.get(this);
    }

    /**
     * 获取上下文
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取Handler对象
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程号
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return Thread.currentThread();
    }
}

