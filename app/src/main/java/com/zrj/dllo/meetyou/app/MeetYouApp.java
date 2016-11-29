package com.zrj.dllo.meetyou.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/22.
 */

public class MeetYouApp extends Application {
    /**
     * 除了网络和数据库最好别的地方不要用
     * 负荷太大
     */
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        SharedPreferences sharedPreferences = getSharedPreferences("night", 0);
        sharedPreferences.edit().putBoolean("isFragment", true).commit();
        Bmob.initialize(this, "aa000b85c4f21c464fdb127df4e5744b");

        // 初始化环信
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(sContext.getPackageName())) {
            Log.e("meetyou", "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        //初始化
        EMClient.getInstance().init(sContext, options);

    }

    /**
     * 静态方法获取上下文
     *
     * @return 上下文
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * 获取app的名字
     * @param pID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {

            }
        }
        return processName;
    }
}
