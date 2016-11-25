package com.zrj.dllo.meetyou.login;

import android.text.TextUtils;

import com.zrj.dllo.meetyou.Utils.LogUtils;

/**
 * Created by ${ZhaoXuancheng} on 16/11/24.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private LoginContract.Model mModel;

    public LoginPresenter(LoginContract.View view, LoginContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void login(String userName, String psw) {

        LogUtils.d(userName);
        if (checkEmpty(userName, psw)) {
            mView.showEmptyMsg();
        } else {
            mModel.login(userName, psw);
            mView.showLoading();
        }
    }

    @Override
    public boolean checkEmpty(String userName, String psw) {
        return TextUtils.isEmpty(userName)||TextUtils.isEmpty(psw);
    }

    @Override
    public void loginSuccess() {
        mView.loginSuccess();
    }

    @Override
    public void loginError(Exception e) {
        if (e == null) {
            mView.loginError("登录失败");
        }else {
            mView.loginError(e.getMessage());
        }
    }
}
