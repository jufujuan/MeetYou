package com.zrj.dllo.meetyou.find.listfind;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/26.
 */

public class ListFindRecyclerAdapter extends RecyclerView.Adapter<CommonViewHolder>{
    private List<ListFindBean> datas;
    private Context mContext;
    private List<Integer> heights;

    public ListFindRecyclerAdapter(Context context) {
        mContext = context;

    }

    public ListFindRecyclerAdapter() {
    }

    public void setDatas(List<ListFindBean> datas) {
        this.datas = datas;
        heights = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            heights.add((int)(500+Math.random()*300));
        }
        notifyDataSetChanged();
    }


    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.getViewHolder(parent, R.layout.item_list_find_rv);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        ViewGroup.LayoutParams params =  holder.getItemView().getLayoutParams();//得到item的LayoutParams布局参数
        params.height = heights.get(position);//把随机的高度赋予itemView布局
        holder.getItemView().setLayoutParams(params);//把params设置给itemView布局
        holder.setImage(R.id.item_list_find_img,datas.get(position).getAvatar(),mContext);

    }

    @Override
    public int getItemCount() {
        return datas!=null&&datas.size()!=0?datas.size():0;
    }

}
