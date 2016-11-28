package com.zrj.dllo.meetyou.personal;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.tools.BitmapBlurUtils;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.login.EventBusBean;
import com.zrj.dllo.meetyou.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by ${ZhaoXuancheng} on 16/11/21.
 */

public class PersonalFragment extends AbsBaseFragment implements View.OnClickListener {
    private ImageView pullImg;
    private ImageView mNightImage;
    private ImageView mImageViewLogin;
    private Bitmap mBmp;
    private Resources mRes;
    private TextView mTextViewUsername;
    private LinearLayout mLinearLayoutUnLogin;
    private RelativeLayout mRelativeLayoutEscLogin;
    private Bitmap mBmp1;
    private Resources mRes1;
    private ImageView mPullImgUnLogin;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences preferences = context.getSharedPreferences("userMessage", Activity.MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
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
                pullImg.setImageDrawable(drawable);
            }
        });

        mRes1 = getResources();

        mBmp1 = BitmapFactory.decodeResource(mRes1, R.mipmap.sidebar_pic);
        //TODO Handler目前这种写法 可能会导致短期的内存泄露
        //后期需要修改
        BitmapBlurUtils.addTask(mBmp1, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Drawable drawable = (Drawable) msg.obj;
                mPullImgUnLogin.setImageDrawable(drawable);
            }
        });

        //已登录状态下重新赋值
        if (!userName.equals("")) {
            mLinearLayoutUnLogin.setVisibility(View.GONE);
            mBmp = BitmapFactory.decodeResource(mRes, R.mipmap.default_head);
            //TODO Handler目前这种写法 可能会导致短期的内存泄露
            //后期需要修改
            BitmapBlurUtils.addTask(mBmp, new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Drawable drawable = (Drawable) msg.obj;
                    pullImg.setImageDrawable(drawable);
                }
            });
            mImageViewLogin.setImageBitmap(mBmp);
            mTextViewUsername.setText(userName);
        }
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
        pullImg = bindView(R.id.pull_img);
        mNightImage = bindView(R.id.personal_night_iv);
        mNightImage.setOnClickListener(this);
        mImageViewLogin = bindView(R.id.personal_login_image);
        mImageViewLogin.setOnClickListener(this);
        mTextViewUsername = bindView(R.id.personal_username_tv);
        mLinearLayoutUnLogin = bindView(R.id.ll_un_login);
        mLinearLayoutUnLogin.setOnClickListener(this);
        mRelativeLayoutEscLogin = bindView(R.id.personal_esc_login_rl);
        mRelativeLayoutEscLogin.setOnClickListener(this);
        mPullImgUnLogin = bindView(R.id.pull_img_un_login);
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

                break;
            case R.id.ll_un_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.personal_esc_login_rl:
                mLinearLayoutUnLogin.setVisibility(View.VISIBLE);
                SharedPreferences preferences = context.getSharedPreferences("userMessage", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
//                editor.putString("userName",);
                editor.commit();
                Toast.makeText(context, "已退出登录", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //登录后执行的方法
    @Subscribe
    public void getLoginEvent(EventBusBean eventBusBean) {

        mBmp = BitmapFactory.decodeResource(mRes, R.mipmap.default_head);
        //TODO Handler目前这种写法 可能会导致短期的内存泄露
        //后期需要修改
        BitmapBlurUtils.addTask(mBmp, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Drawable drawable = (Drawable) msg.obj;
                pullImg.setImageDrawable(drawable);
            }
        });
        mImageViewLogin.setImageBitmap(mBmp);
        mTextViewUsername.setText(eventBusBean.getUsername());
        mLinearLayoutUnLogin.setVisibility(View.GONE);

    }
}
