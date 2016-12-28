package bw.com.yunifangstore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import bw.com.yunifangstore.application.MyApplication;

public class CommonUtils {

    private static SharedPreferences sharedPreferences;

    public static Context getContext() {
        return MyApplication.getContext();
    }

    public static Handler getHandler() {
        return MyApplication.getHandler();
    }

    public static Thread getMainThread() {
        return MyApplication.getMainThread();
    }

    public static int getMainThreadId() {
        return MyApplication.getMainThreadId();
    }



    /**
     * 获取资源文件夹操作
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * string文件中的字符串
     */
    public static String getString(int stringId) {
        return getResources().getString(stringId);
    }

    /**
     * 返回drawable操作
     */
    public static Drawable getDrawable(int drawableId) {
        return getResources().getDrawable(drawableId);
    }

    /**
     * string-array
     */
    public static String[] getStringArray(int arrayId) {
        return getResources().getStringArray(arrayId);
    }

    /**
     * dip转px, 1dp = 2px,定义一个控件的宽高 layoutParams(w,h)
     */
    public static int dip2px(int dip) {
        // 获取dp和px的转换关系的变量
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    /**
     * px转dp
     */
    public static int px2dip(int px) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }
    //存入sharedPreferences对象中
    public static void saveSp(String key, String values) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences("disPlayQQ", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, values);
        edit.commit();
    }

    //取sharedPreferences中的值
    public static String getSp(String key) {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences("disPlayQQ", getContext().MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, "");
    }
    public static void removeSp(String flag){
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences("disPlayQQ", getContext().MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(flag);
        edit.commit();
    }
    /**
     * 判断当前线程是否在主线程中
     */
    public static boolean isRunInMainThread() {
        //先获取主线程的线程号然后和当前线程号比较
        return getMainThreadId() == android.os.Process.myTid();
    }

    /**
     * 区分是否在 主线程中,做UI处理
     */
    public static void runOnMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            // 如果当前任务就在主线程中,直接运行
            runnable.run();
        } else {
            // 如果当任务在子线程中,则将其传递到主线程中去执行
            getHandler().post(runnable);
        }
    }


    /**
     * 填充布局
     */
    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    /**
     * 将dimens中的dp转换成像素值
     */
    public static int getDimens(int dimensId) {
        return getResources().getDimensionPixelSize(dimensId);
    }

    public static void postDelayed(Runnable runnable, int delayed) {
        // 延时做任务方法
        getHandler().postDelayed(runnable, delayed);
    }

    /**
     * 移除指定任务的操作
     */
    public static void removeCallBack(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    /**
     * 根据id获取颜色选择器xml
     */
    public static ColorStateList getColorStateList(int mTabTextColorResId) {
        return getResources().getColorStateList(mTabTextColorResId);
    }

}
