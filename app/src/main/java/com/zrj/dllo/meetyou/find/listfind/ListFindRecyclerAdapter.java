package com.zrj.dllo.meetyou.find.listfind;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.CommonViewHolder;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemDislikeClickListener;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemImgClickListener;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemLikeClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/26.
 */

public class ListFindRecyclerAdapter extends RecyclerView.Adapter<CommonViewHolder> implements InnerAdapterAddHead{
    private List<Person> datas;
    private Context mContext;
    private List<Integer> heights;
    private RecyclerViewItemLikeClickListener mLikeClickListener;
    private RecyclerViewItemDislikeClickListener mDislikeClickListener;
    private RecyclerViewItemImgClickListener mImgClickListener;
    private int headCount;

    public void setHeadCount(int headCount) {
        this.headCount = headCount;
    }

    public ListFindRecyclerAdapter(Context context) {
        mContext = context;
    }

    public ListFindRecyclerAdapter() {
    }

    public void insetData(List<Person> data){
        datas.addAll(data);
        for (int i = 0; i < data.size(); i++) {
            heights.add((int)(500+Math.random()*300));
        }
    }

    public void deleteFromPos(int pos){
        datas.remove(pos);
        heights.remove(pos);
    }

    public void setLikeClickListener(RecyclerViewItemLikeClickListener likeClickListener) {
        mLikeClickListener = likeClickListener;
    }

    public void setDislikeClickListener(RecyclerViewItemDislikeClickListener dislikeClickListener) {
        mDislikeClickListener = dislikeClickListener;
    }

    public void setImgClickListener(RecyclerViewItemImgClickListener imgClickListener) {
        mImgClickListener = imgClickListener;
    }

    public void setDatas(List<Person> datas) {
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
            ViewGroup.LayoutParams params = holder.getItemView().getLayoutParams();//得到item的LayoutParams布局参数
            params.height = heights.get(position);//把随机的高度赋予itemView布局
            holder.getItemView().setLayoutParams(params);//把params设置给itemView布局
            holder.setImage(R.id.item_list_find_img, datas.get(position).getUserImgUrl(),  mImgClickListener, position, datas.get(position));
            holder.setImage(R.id.item_list_find_dislike, mDislikeClickListener, headCount, datas.get(position));
            holder.setImage(R.id.item_list_find_like, mLikeClickListener, headCount, datas.get(position));
            holder.setText(R.id.item_list_find_name,datas.get(position).getuName());
    }

    @Override
    public int getItemCount() {
        return datas!=null&&datas.size()!=0?datas.size():0;
    }

}
