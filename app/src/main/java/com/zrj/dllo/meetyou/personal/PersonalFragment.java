package com.zrj.dllo.meetyou.personal;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.Utils.LogUtils;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;

public class PersonalFragment extends AbsBaseFragment implements View.OnClickListener {
    private ImageView pull_img;
    private ImageView mNightImage;
    private ImageView mImageViewLogin;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.sidebar_pic);

        //TODO Handler目前这种写法 可能会导致短期的内存泄露
        //后期需要修改
        BitmapBlurUtil.addTask(bmp, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Drawable drawable = (Drawable) msg.obj;
                pull_img.setImageDrawable(drawable);
            }
        });

    }

    /**
     * 绑定布局
     *
     * @return 将布局返回
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_personal;
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initView() {
        pull_img = bindView(R.id.pull_img);
        mNightImage = bindView(R.id.personal_night_iv);
        mNightImage.setOnClickListener(this);
        mImageViewLogin = bindView(R.id.personal_login_image);
        mImageViewLogin.setOnClickListener(this);

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initDatas() {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_night_iv:
                EventBusBean busBean = new EventBusBean();
                EventBus.getDefault().postSticky(busBean);
                break;
            case R.id.personal_login_image:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }




    }
}
