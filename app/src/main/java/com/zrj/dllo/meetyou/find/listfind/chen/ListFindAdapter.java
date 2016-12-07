package com.zrj.dllo.meetyou.find.listfind.chen;

import android.support.v7.widget.RecyclerView;

import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.find.listfind.ListFindBean;
import com.zrj.dllo.meetyou.find.listfind.ListFindRecyclerAdapter;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemDislikeClickListener;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemImgClickListener;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemLikeClickListener;

import java.util.List;

/**
 * If there is no bug, then it is created by ChenFengYao on 2016/12/6,
 * otherwise, I do not know who create it either.
 * ListFind的Adapter
 */

public class ListFindAdapter extends MyHeaderAndFooterWrapper {
    public ListFindAdapter() {
        super(new ListFindRecyclerAdapter());
    }

    public void setData(List<Person> data) {
        ListFindRecyclerAdapter innerAdapter = (ListFindRecyclerAdapter) getInnerAdapter();
        innerAdapter.setDatas(data);
        notifyDataSetChanged();
    }

    public void setLikeClickListener(RecyclerViewItemLikeClickListener likeClickListener) {
        ListFindRecyclerAdapter innerAdapter = (ListFindRecyclerAdapter) getInnerAdapter();
        innerAdapter.setLikeClickListener(likeClickListener);
    }

    public void setDislikeClickListener(RecyclerViewItemDislikeClickListener dislikeClickListener) {
        ListFindRecyclerAdapter innerAdapter = (ListFindRecyclerAdapter) getInnerAdapter();
        innerAdapter.setDislikeClickListener(dislikeClickListener);
    }

    public void setImgClickListener(RecyclerViewItemImgClickListener imgClickListener) {
        ListFindRecyclerAdapter innerAdapter = (ListFindRecyclerAdapter) getInnerAdapter();
        innerAdapter.setImgClickListener(imgClickListener);
    }

    //删除数据的回调
    public void delectFromPos(int pos) {
        ListFindRecyclerAdapter innerAdapter = (ListFindRecyclerAdapter) getInnerAdapter();
        innerAdapter.deleteFromPos(pos);
        notifyItemRemoved(pos + getHeadersCount());
        //notifyDataSetChanged();
    }
}
