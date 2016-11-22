package com.zrj.dllo.meetyou.Utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.zrj.dllo.meetyou.app.MeetYouApp;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/22.
 * Toast的工具类
 * 将Toast进行封装
 */

public final class ToastUtils {

    private static Boolean isDebug = true;
    private static Toast mToast = new Toast(MeetYouApp.getContext());
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    //无参数的构造方法,私有证明该对象不能被创建
    private ToastUtils() {
    }

    /**
     * 短时间显示Toast
     *
     * @param msg 字符类型的参数
     */
    public static void showShortToast(final CharSequence msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示Toast(重载)
     *
     * @param msg int类型的参数
     */
    public static void showShortToast(final int msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     *
     * @param msg 字符类型的Toast显示的内容
     */
    public static void showLongToast(CharSequence msg) {
        show(msg, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast(重载)
     *
     * @param msg int类型的Toast显示的内容
     */
    public static void showLongToast(int msg) {
        show(msg, Toast.LENGTH_LONG);
    }

    /**
     * 自定义时间显示Toast
     *
     * @param msg      字符类型的toast显示的信息
     * @param duration toast显示的时间
     */
    public static void show(final CharSequence msg, int duration) {
        if (isDebug) {
            mToast.setDuration(duration);
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    mToast.setText(msg);
                    mToast.show();
                }
            });
        }
    }

    /**
     * 自定义时间显示Toast
     *
     * @param msg      字符类型的toast显示的信息
     * @param duration toast显示的时间
     */
    public static void show(final int msg, int duration) {
        if (isDebug) {
            mToast.setDuration(duration);
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    mToast.setText(msg);
                    mToast.show();
                }
            });
        }
    }

}
