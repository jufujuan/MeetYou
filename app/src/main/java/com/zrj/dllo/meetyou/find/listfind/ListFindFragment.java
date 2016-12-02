package com.zrj.dllo.meetyou.find.listfind;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.find.ttfind.ListTTActivity;
import com.zrj.dllo.meetyou.tools.DistanceUtils;
import com.zrj.dllo.meetyou.tools.LogUtils;
import com.zrj.dllo.meetyou.tools.StaticValues;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/26.
 */

public class ListFindFragment extends AbsBaseFragment implements ListFindContract.MyView {

    protected RecyclerView mRecyclerView;
    protected ListFindRecyclerAdapter mRecyclerAdapter;
    private final static int DISTANCE = 2000;//单位米
    private ListFindPresenter mPresenter;

    public static ListFindFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ListFindFragment fragment = new ListFindFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getLayout() {
        return R.layout.fra_list_find;
    }

    @Override
    protected void initView() {
        mRecyclerView = bindView(R.id.fra_list_find_recyclerview);
        mRecyclerAdapter = new ListFindRecyclerAdapter(context);

    }

    @Override
    protected void initDatas() {
        mPresenter.getAllDatas(context,DISTANCE);
    }

    /**
     * 把p层放到层中
     *
     * @param presenter
     */
    @Override
    public void setPresenter(ListFindPresenter presenter) {
        mPresenter=presenter;
    }


}
