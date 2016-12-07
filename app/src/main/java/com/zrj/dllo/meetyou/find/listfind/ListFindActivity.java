package com.zrj.dllo.meetyou.find.listfind;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;
import com.zrj.dllo.meetyou.find.listfind.chen.*;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/26.
 * 觅友列表界面
 */

public class ListFindActivity extends AbsBaseActivity{
    private FragmentManager mFragmentManager;
    private Toolbar mToolbar;

//    private ListFindFragment mView;
//    private ListFindPresenter mPresenter;
//    private ListFindModel mModel;
    private com.zrj.dllo.meetyou.find.listfind.chen.ListFindFragment
    mView;

    @Override
    protected int getLayout() {
        return R.layout.ac_list_find;
    }

    @Override
    protected void initView() {
        mToolbar=bindView(R.id.ac_list_find_toolbar);
    }

    @Override
    protected void initDatas() {

        mView=com.zrj.dllo.meetyou.find.listfind.chen.ListFindFragment.newInstance();
//        mModel=new ListFindModel();
//        mPresenter=new ListFindPresenter(mView,mModel);
        FindPresenter findPresenter = new FindPresenter(mView);
        mView.setPresenter(findPresenter);
//        mModel.setPresenter(mPresenter);

        mFragmentManager=getSupportFragmentManager();
        FragmentTransaction mTransaction=mFragmentManager.beginTransaction();
        mTransaction.replace(R.id.ac_list_find_framelayout,mView);
        mTransaction.commit();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//使能app bar的导航功能
    }

    /**
     * 你不需要对导航按钮的点击事件做出响应。
     * 因为点击导航按钮的响应事件，
     * 父类已经已经实现好了，
     * 你只需要调用父类方法即可
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
}
