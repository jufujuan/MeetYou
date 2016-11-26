package com.zrj.dllo.meetyou.personal;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.Utils.BitmapBlurUtils;
import com.zrj.dllo.meetyou.Utils.LogUtils;
import com.zrj.dllo.meetyou.Utils.ToastUtils;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.login.EventBusBean;
import com.zrj.dllo.meetyou.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by ${ZhaoXuancheng} on 16/11/21.
 */

public class PersonalFragment extends AbsBaseFragment implements View.OnClickListener {
    private ImageView pull_img;
    private ImageView mNightImage;
    private ImageView mImageViewLogin;
    private Bitmap mBmp;
    private Resources mRes;
    private TextView mTextViewUsername;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EventBus.getDefault().register(this);

        mRes = getResources();
        mBmp = BitmapFactory.decodeResource(mRes, R.mipmap.sidebar_pic);


        //TODO Handler目前这种写法 可能会导致短期的内存泄露
        //后期需要修改
        BitmapBlurUtils.addTask(mBmp, new Handler() {
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
        mTextViewUsername = bindView(R.id.personal_username_tv);

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
                SharedPreferences sharedPreferences = context.getSharedPreferences("night", 0);
                sharedPreferences.edit().putBoolean("isFragment", false).commit();
                Intent intent1 = new Intent("night");
                context.sendBroadcast(intent1);
                break;
            case R.id.personal_login_image:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Subscribe
    public void getLoginEvent(EventBusBean eventBusBean) {
        LogUtils.d("几级");


        mBmp = BitmapFactory.decodeResource(mRes, R.mipmap.default_head);
        //TODO Handler目前这种写法 可能会导致短期的内存泄露
        //后期需要修改
        BitmapBlurUtils.addTask(mBmp, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Drawable drawable = (Drawable) msg.obj;
                pull_img.setImageDrawable(drawable);
            }
        });
        mImageViewLogin.setImageBitmap(mBmp);
        mTextViewUsername.setText(eventBusBean.getUsername());
    }

}
