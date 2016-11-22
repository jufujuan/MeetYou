package com.zrj.dllo.meetyou.Utils;

import android.util.Log;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/22.
 */

public class LogUtils {
    public static boolean isDebug=true;
    private static final String TAG="test";

    private LogUtils() {
    }
    //下面四个是默认的tag的函数
    public static void i(String msg){
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }
    public static void d(String msg){
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }
    public static void e(String msg){
        if (isDebug){
            Log.e(TAG,msg);
        }
    }
    public static void v(String msg){
        if (isDebug){
            Log.v(TAG,msg);
        }
    }


    //下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }


}
