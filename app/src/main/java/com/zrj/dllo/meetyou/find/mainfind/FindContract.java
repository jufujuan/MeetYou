package com.zrj.dllo.meetyou.find.mainfind;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.animation.Animation;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/23.
 */

public interface FindContract {
    interface View {
        /**
         * 将Presenter层放到View层
         *
         * @param presenter
         */
        void setPersenter(Presenter presenter);

        /**
         * 显示扫描的图片
         */
        void showSweepView();

        /**
         * 显示点击动画
         */
        void showClickAnim();

        /**
         * GPS定位成功,显示提示信息信息
         */
        void showGPSMsg(BDLocation location);
        /**
         * 网络定位成功,显示提示信息信息
         */
        void showNetMsg(BDLocation location);
        /**
         * 离线定位定位成功,显示提示信息信息
         */
        void showNotNetMsg(BDLocation location);
    }

    interface Presenter {
        /**
         * 扫描界面跳转到主界面
         *
         * @param context 传入的上下文
         * @param flags   设置跳转的状态
         */
        void goToMainAc(Context context, int flags,int time,BDLocation bdLocation);
        /**
         * 开始搜索定位
         */
        void startSearch(LocationClient locationClient);
    }

    interface Model {
        /**
         * 把Presenter层放到Model层
         *
         * @param presenter
         */
        void setPresenter(Presenter presenter);

        /**
         * 设置百度位置的初步设置
         *
         * @param locationClient
         */
        void initLocation(LocationClient locationClient);
        /**
         * 延时跳转到主界面
         * @param context 上下文
         * @param flags 设置跳转的状态
         * @param time 延迟跳转的时间
         */
        void goToMainAcLater(Context context, int flags,int time);

        /**
         * 查询是否成功得到sp中的当前用户名
         * @return 得到sp中的用户名返回true,否则返回false
         */
        String getSpNmae(Context context);
        /**
         * 利用当前用户名查询索引
         */
        void searchIdByName(Context context,BDLocation location);

    }
}
