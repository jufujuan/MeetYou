package com.zrj.dllo.meetyou.login;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by ${ZhaoXuancheng} on 16/11/24.
 */

public class LoginModel implements LoginContract.Model {

    private LoginContract.Presenter mPresenter;
    private Handler mHandler;

    public LoginModel() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void login(final String userName, final String psw) {
        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }).start();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
