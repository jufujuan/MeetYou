package com.zrj.dllo.meetyou.find.listfind;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.find.ttfind.ListTTActivity;

import java.util.List;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/24.
 */

public class ListFindPresenter implements ListFindContract.Presenter {
    private ListFindFragment mView;
    private ListFindModel mModel;

    public ListFindPresenter(ListFindFragment view, ListFindModel model) {
        mView = view;
        mModel = model;
    }

    /**
     * 得到数据库中所有的数据(去掉自己)
     */
    @Override
    public void getAllDatas(Context context,int distance) {
        mModel.selectAllDatas(mModel.searchCurrentL(context),context,distance);
    }

    /**
     * 将数据集发送给recyclerview
     *
     * @param mPersons 得到的数据集
     */
    @Override
    public void sendDatasToView(List<Person> mPersons, final Context context) {
        if (mPersons.size()>0){
            mView.mRecyclerAdapter.setDatas(mPersons);
            mView.mRecyclerAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.item_list_find_dislike:
                            Toast.makeText(context, "不喜欢", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.item_list_find_like:
                            Toast.makeText(context, "喜欢", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.item_list_find_img:
                            Toast.makeText(context, "点击了图片", Toast.LENGTH_SHORT).show();
                           // goTo(context, ListTTActivity.class);
                            context.startActivity(new Intent(context,ListTTActivity.class));
                            break;
                    }
                }
            });
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mView.mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
            mView.mRecyclerView.setAdapter(mView.mRecyclerAdapter);
        }
    }

}
