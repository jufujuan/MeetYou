package com.zrj.dllo.meetyou.find.ttfind;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/26.
 */

public class ListTTActivity extends AbsBaseActivity{

    private FragmentManager mFragmentManager;

    @Override
    protected int getLayout() {
        return R.layout.ac_list_tt;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        mFragmentManager=getSupportFragmentManager();
        FragmentTransaction mTransaction=mFragmentManager.beginTransaction();
        mTransaction.replace(R.id.item_list_find_img,ListTTFragment.newInstance());
        mTransaction.commit();

    }
}
