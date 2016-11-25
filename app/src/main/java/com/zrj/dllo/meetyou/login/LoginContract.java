package com.zrj.dllo.meetyou.login;

/**
 * Created by ${ZhaoXuancheng} on 16/11/24.
 */

public interface LoginContract {

    interface View {

        void setPresenter(Presenter presenter);

        void showEmptyMsg();

        void showLoading();

        void loginSuccess();

        void loginError(String msg);

    }


    interface Presenter {

        void login(String userName, String psw);

        boolean checkEmpty(String userName, String psw);

        void loginSuccess();

        void loginError(Exception e);

    }

    interface Model {

        void login(String userName,String psw);

        void setPresenter(Presenter presenter);
    }
}
