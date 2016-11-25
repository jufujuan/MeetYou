package com.zrj.dllo.meetyou.find.mainfind;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.animation.Animation;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/23.
 */

public interface FindContract {
    interface View{
        /**
         * 将Presenter层放到View层
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
    }
    interface Presenter{


    }
    interface Model{
        /**
         * 把Presenter层放到Model层
         * @param presenter
         */
        void setPresenter(Presenter presenter);

    }
}
