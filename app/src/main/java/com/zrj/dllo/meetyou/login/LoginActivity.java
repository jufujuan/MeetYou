package com.zrj.dllo.meetyou.login;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zrj.dllo.meetyou.MainActivity;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.SweepActivity;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;

import com.zrj.dllo.meetyou.eventbus.EventBusBean;

import com.zrj.dllo.meetyou.tools.BitmapBlurUtils;
import com.zrj.dllo.meetyou.tools.StaticValues;

import cn.bmob.v3.BmobQuery;
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
    private Handler mHandler = new Handler(Looper.getMainLooper());
    
    //注册
    private SaveListener<BmobUser> registerListener = new SaveListener<BmobUser>() {
        @Override
        public void done(BmobUser bmobUser, BmobException e) {

            if (e == null) {
                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                loginOnClick(mUserName, mPassword);
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
                Intent intent = new Intent(LoginActivity.this, SweepActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences(StaticValues.SP_USEING_TABLE_NAME, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString(StaticValues.SP_USEING_NAME_COLUMN, mEventBusBean.getUsername());
//                editor.putString(StaticValues.SP_USEING_IMG_URL_COLUMN)
                editor.commit();

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

        SharedPreferences sharedPreferences = getSharedPreferences(StaticValues.SP_USEING_TABLE_NAME, Activity.MODE_PRIVATE);
        String usingName = sharedPreferences.getString(StaticValues.SP_USEING_NAME_COLUMN, "10086");
        if (!usingName.equals("10086")) {
            Intent intent = new Intent(LoginActivity.this, SweepActivity.class);
            startActivity(intent);
            finish();
        }

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

    /**
     * 注册方法
     */
    public void signUp(final String userName, final String passWord) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(userName, passWord);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            signIn(userName, passWord);
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

    /**
     * 登录方法
     */
    public void signIn(String userName, String passWord) {
        EMClient.getInstance().getOptions().setUseHttps(true);
        EMClient.getInstance().login(userName, passWord, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Log.d("main", "登录聊天服务器成功！");
//                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }


}