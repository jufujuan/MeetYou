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
                try {
                    Thread.sleep(3000);
                    if ("111111".equals(userName) && "2222222".equals(psw)) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.loginSuccess();
                            }
                        });
                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Exception exception = new Exception("用户名或密码错误");
                                mPresenter.loginError(exception);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
