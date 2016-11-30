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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zrj.dllo.meetyou.MainActivity;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.tools.BitmapBlurUtils;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.editor.EditorActivity;
import com.zrj.dllo.meetyou.eventbus.EventBusBean;
import com.zrj.dllo.meetyou.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.bmob.v3.BmobUser;

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
    private RelativeLayout mRelativeLayoutEscLogin;
    private TextView mTextViewEdtor;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences preferences = context.getSharedPreferences("userMessage", Activity.MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        EventBus.getDefault().register(this);

        mRes = getResources();

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
        mRelativeLayoutEscLogin = bindView(R.id.personal_esc_login_rl);
        mRelativeLayoutEscLogin.setOnClickListener(this);
        mTextViewEdtor = bindView(R.id.personal_editor_tv);
        mTextViewEdtor.setOnClickListener(this);
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
            case R.id.personal_esc_login_rl:
                logOut();
                SharedPreferences preferences = context.getSharedPreferences("userMessage", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                Intent intent3 = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent3);
                editor.commit();
                BmobUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了

                getActivity().finish();

                Toast.makeText(context, "已退出登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.personal_editor_tv:

                Intent intent2 = new Intent(getActivity(), EditorActivity.class);
                startActivity(intent2);
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
    }

    /**
     * 登出
     * 异步
     */
    private void logOut(){
        EMClient.getInstance().logout(false, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.d("LoginActivity", "退出登录成功");
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub

            }
        });
    }

}
