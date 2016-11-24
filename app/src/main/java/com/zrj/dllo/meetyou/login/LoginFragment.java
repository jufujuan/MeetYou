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

public class LoginFragment extends AbsBaseFragment implements View.OnClickListener {


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
        switch (v.getId()){
            case R.id.login_register:
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.login_fl,RegisterFragment.newInstance());
                transaction.commit();
                break;
        }
    }
}
