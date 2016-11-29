package com.zrj.dllo.meetyou.login;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zrj.dllo.meetyou.MainActivity;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.SweepActivity;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;

import com.zrj.dllo.meetyou.eventbus.EventBusBean;

import com.zrj.dllo.meetyou.tools.BitmapBlurUtils;


import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
public class LoginActivity extends AbsBaseActivity implements View.OnClickListener {

    private ImageView mImageViewBackground;
    private FragmentManager mFragmentManager;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private EventBusBean mEventBusBean;
    private String mUserName;
    private String mPassword;


    //注册
    private SaveListener<BmobUser> registerListener = new SaveListener<BmobUser>() {
        @Override
        public void done(BmobUser bmobUser, BmobException e) {
            if (e == null) {
                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                loginOnClick(mUserName, mPassword);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            } else {
                Log.d("444", e.getMessage());
                Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    //登录
    private SaveListener<LoginUserBean> loginListener = new SaveListener<LoginUserBean>() {
        @Override
        public void done(LoginUserBean loginUserBean, BmobException e) {
            if (e == null) {


                EventBus.getDefault().post(mEventBusBean);
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("userMessage", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("userName", mEventBusBean.getUsername());
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, SweepActivity.class);
                startActivity(intent);
                Log.d("MainActivity", "登录成功");
//                finish();
            } else {
                Toast.makeText(LoginActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", e.getMessage());
                Log.d("MainActivity", "登录失败");
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
        TextView textViewEsc = bindView(R.id.login_esc_tv);
        textViewEsc.setOnClickListener(this);
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

    //登录跳转至注册
    public void onRegisterIntent() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.login_fl, registerFragment);
        transaction.commit();
    }

    //注册跳转至登录
    public void onLoginIntent() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.login_fl, loginFragment);
        transaction.commit();
    }

    //注册操作
    public void registerOnClick(String userName, String passWord) {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(userName);
        bmobUser.setPassword(passWord);
        bmobUser.signUp(registerListener);

        Person person = new Person();
        person.setuName(userName);
        person.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                
            }
        });

        mUserName = userName;
        mPassword = passWord;

    }

    //登录操作
    public void loginOnClick(String userName, String passWord) {

        LoginUserBean loginUserBean = new LoginUserBean();
        loginUserBean.setUsername(userName);
        loginUserBean.setPassword(passWord);
        loginUserBean.login(loginListener);

        mEventBusBean = new EventBusBean();
        mEventBusBean.setUsername(userName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_esc_tv:
                finish();
                break;
        }
    }
}