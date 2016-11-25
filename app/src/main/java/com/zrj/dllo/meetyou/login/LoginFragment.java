package com.zrj.dllo.meetyou.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.Utils.LogUtils;
import com.zrj.dllo.meetyou.Utils.ToastUtils;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

/**
 * Created by ${ZhaoXuancheng} on 16/11/24.
 */

public class LoginFragment extends AbsBaseFragment implements View.OnClickListener, LoginContract.View {

    private ProgressDialog mProgressDialog;
    private LoginContract.Presenter mPresenter;
    private EditText mEditTextPassword;
    private EditText mEditTextUserName;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 绑定布局
     *
     * @return 布局的资源id
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initView() {
        TextView textViewRegister = bindView(R.id.login_register);
        textViewRegister.setOnClickListener(this);
        mEditTextUserName = bindView(R.id.login_user_edittext);
        mEditTextPassword = bindView(R.id.login_password_edittext);
        TextView textViewLogin = bindView(R.id.login_login_tv);
        textViewLogin.setOnClickListener(this);

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
            case R.id.login_register:
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.login_fl, RegisterFragment.newInstance());
                transaction.commit();
                break;

            case R.id.login_login_tv:
                String userName = mEditTextUserName.getText().toString();
                String userPassword = mEditTextPassword.getText().toString();
                Log.d("LoginFragment", userName + userPassword);
                Log.d("LoginFragment", "mPresenter:" + mPresenter);
                mPresenter.login(userName, userPassword);
                break;
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void showEmptyMsg() {
//        ToastUtils.show("用户名或密码不能为空",1000);
        Toast.makeText(context, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mProgressDialog = ProgressDialog.show(getActivity(), "正在登录", "正在登录");
    }

    @Override
    public void loginSuccess() {
        mProgressDialog.dismiss();
//        ToastUtils.showShortToast("登录成功");
        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError(String msg) {
        mProgressDialog.dismiss();
//        ToastUtils.showShortToast(msg);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
