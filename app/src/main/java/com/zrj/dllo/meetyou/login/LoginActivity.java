package com.zrj.dllo.meetyou.login;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;
import com.zrj.dllo.meetyou.Utils.BitmapBlurUtils;

public class LoginActivity extends AbsBaseActivity{


    private ImageView mImageViewBackground;

    /**
     * 绑定布局
     *
     * @return 将布局返回
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initView() {
        mImageViewBackground = bindView(R.id.login_background_img1);




    }

    /**
     * 初始化数据
     */
    @Override
    protected void initDatas() {

        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.aiqing1);

        //TODO Handler目前这种写法 可能会导致短期的内存泄露
        //后期需要修改
        BitmapBlurUtils.addTask(bmp, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Drawable drawable = (Drawable) msg.obj;
                mImageViewBackground.setImageDrawable(drawable);
            }
        });
    }






}
