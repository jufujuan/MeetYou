package com.zrj.dllo.meetyou.app;

import android.app.Application;
import android.content.Context;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/22.
 */

public class MeetYouApp extends Application{
    /**
     * 除了网络和数据库最好别的地方不要用
     * 负荷太大
     */
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext=this;
    }

    /**
     * 静态方法获取上下文
     * @return 上下文
     */
    public static Context getContext(){
        return sContext;
    }
}
