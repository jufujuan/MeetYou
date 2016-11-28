package com.zrj.dllo.meetyou;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zrj.dllo.meetyou.base.AbsBaseActivity;
import com.zrj.dllo.meetyou.find.listfind.ListFindFragment;
import com.zrj.dllo.meetyou.find.mainfind.FindFragment;
import com.zrj.dllo.meetyou.find.mainfind.FindModel;
import com.zrj.dllo.meetyou.find.mainfind.FindPresenter;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/26.
 */

public class SweepActivity extends AbsBaseActivity{
    private FragmentManager mManager;
    @Override
    protected int getLayout() {
        return R.layout.ac_sweep;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

        mManager = getSupportFragmentManager();

        FindFragment findFragment = FindFragment.newInstance();
        FindPresenter findPresenter = new FindPresenter();
        FindModel findModel = new FindModel();

        findFragment.setPersenter(findPresenter);
        findModel.setPresenter(findPresenter);

        FragmentTransaction mTransacyion = mManager.beginTransaction();
        mTransacyion.replace(R.id.ac_sweep_framelayout, FindFragment.newInstance());
        mTransacyion.commit();

    }


}
