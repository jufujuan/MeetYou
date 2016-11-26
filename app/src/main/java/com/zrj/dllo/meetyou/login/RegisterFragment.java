package com.zrj.dllo.meetyou.login;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

/**
 * Created by ${ZhaoXuancheng} on 16/11/24.
 */
public class RegisterFragment extends AbsBaseFragment implements View.OnClickListener {
    private FragmentManager mFragmentManager;
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;

    /**
     * 绑定布局
     *
     * @return 布局的资源id
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_register;
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initView() {
        TextView textViewReturn = bindView(R.id.login_register_return);
        textViewReturn.setOnClickListener(this);
        mEditTextUserName = bindView(R.id.login_register_username_et);
        mEditTextPassword = bindView(R.id.login_register_password_et);
        TextView textViewRegister = bindView(R.id.login_register_register);
        textViewRegister.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initDatas() {

    }

    private LoginActivity mLoginActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mLoginActivity = (LoginActivity) activity;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_register_return:
                mLoginActivity.onLoginIntent();
                break;
            case R.id.login_register_register:
                mLoginActivity.registerOnClick(mEditTextUserName.getText().toString(), mEditTextPassword.getText().toString());
                break;

        }
    }
}
