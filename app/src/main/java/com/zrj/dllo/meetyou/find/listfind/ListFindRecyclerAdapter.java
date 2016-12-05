package com.zrj.dllo.meetyou.find.listfind;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.BannerConfig;
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

public class ListFindRecyclerAdapter extends RecyclerView.Adapter<CommonViewHolder>{
    private List<Person> datas;
    private Context mContext;
    private List<Integer> heights;
    private RecyclerViewItemLikeClickListener mLikeClickListener;
    private RecyclerViewItemDislikeClickListener mDislikeClickListener;
    private RecyclerViewItemImgClickListener mImgClickListener;
    private final static int TYPE_HEADER=1;
    private final static int TYPE_CONTENT=2;

    public ListFindRecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_HEADER;
        }else{
            return TYPE_CONTENT;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
        StaggeredGridLayoutManager staggeredGridLayoutManager= (StaggeredGridLayoutManager) layoutManager;
        final int span=staggeredGridLayoutManager.getSpanCount();
       // staggeredGridLayoutManager
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
        switch (viewType){
            case TYPE_HEADER:
                return CommonViewHolder.getViewHolder(parent, R.layout.item_list_find_rv);
            case TYPE_CONTENT:
                return CommonViewHolder.getViewHolder(parent, R.layout.item_list_find_header);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        if (position==0) {
            ViewGroup.LayoutParams params = holder.getItemView().getLayoutParams();//得到item的LayoutParams布局参数
            params.height = heights.get(position);//把随机的高度赋予itemView布局
            holder.getItemView().setLayoutParams(params);//把params设置给itemView布局
            holder.setImage(R.id.item_list_find_img, datas.get(position).getUserImgUrl(), mContext, mImgClickListener, position, datas.get(position));
            holder.setImage(R.id.item_list_find_dislike, mDislikeClickListener, position, datas.get(position));
            holder.setImage(R.id.item_list_find_like, mLikeClickListener, position, datas.get(position));
        }else{
            List<String> imgUrls=new ArrayList<>();
            imgUrls.add("http://images2.china.com/game/zh_cn/picnews/11128819/20140314/18394327_20140314115011898593008.jpg");
            imgUrls.add("http://img.nongshanghang.cn/allimg/160927/09450XJ4_0.jpg");
            imgUrls.add("http://image92.360doc.com/DownloadImg/2016/01/0121/63840877_39.jpg");
            holder.setBanner(R.id.item_list_find_banner, BannerConfig.CIRCLE_INDICATOR_TITLE,imgUrls);
        }
    }

    @Override
    public int getItemCount() {
        return datas!=null&&datas.size()!=0?datas.size():0;
    }

}
