package com.zrj.dllo.meetyou.login;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;
import com.zrj.dllo.meetyou.Utils.BitmapBlurUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AbsBaseActivity {

    private ImageView mImageViewBackground;
    private FragmentManager mFragmentManager;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    private SaveListener<BmobUser> loginListener = new SaveListener<BmobUser>() {
        @Override
        public void done(BmobUser bmobUser, BmobException e) {
            if (e == null) {
                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.d("444", e.getMessage());
                Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

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
        registerFragment = new RegisterFragment();
        mImageViewBackground = bindView(R.id.login_background_img1);

        mFragmentManager = getSupportFragmentManager();
        loginFragment = new LoginFragment();

        LoginModel loginModel = new LoginModel();
        LoginPresenter loginPresenter = new LoginPresenter(loginFragment, loginModel);
        Log.d("LoginActivity", "loginPresenter:" + loginPresenter);
        loginFragment.setPresenter(loginPresenter);

        loginModel.setPresenter(loginPresenter);
        mFragmentManager.beginTransaction().replace(R.id.login_fl, loginFragment).commit();
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

    public void onLoginClick() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.login_fl, registerFragment);
        transaction.commit();
    }

    public void onRegisterClick() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.login_fl, loginFragment);
        transaction.commit();
    }

    public void regist(String userName,String pass){
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(userName);
        bmobUser.setPassword(pass);
        bmobUser.signUp(loginListener);
    }
}
