package com.zrj.dllo.meetyou.login;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

/**
 * Created by ${ZhaoXuancheng} on 16/11/24.
 */
public class RegisterFragment extends AbsBaseFragment implements View.OnClickListener {
    private FragmentManager mFragmentManager;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
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
        return R.layout.fragment_register;
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initView() {
        TextView textViewReturn = bindView(R.id.login_register_return);
        textViewReturn.setOnClickListener(this);


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
            case R.id.login_register_return:
                mFragmentManager = getFragmentManager();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.login_fl, LoginFragment.newInstance());
                transaction.commit();
                break;
        }
    }
}
