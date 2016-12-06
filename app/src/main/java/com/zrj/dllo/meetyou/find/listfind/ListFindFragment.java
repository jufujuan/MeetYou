package com.zrj.dllo.meetyou.find.listfind;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

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
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        //mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
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

