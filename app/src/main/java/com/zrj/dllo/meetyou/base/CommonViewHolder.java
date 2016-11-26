package com.zrj.dllo.meetyou.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/11/26.
 * 通用的ViewHolder
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {


    private SparseArray<View> views;
    private View itemView;


    public CommonViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        views = new SparseArray<>();
    }

    public <T extends View> T getView(int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

    // listView
    public static CommonViewHolder getViewHolder(View itemView, ViewGroup parent, int itemId) {
        CommonViewHolder commonViewHolder;
        if ((itemView == null)) {
            Context context = parent.getContext();
            itemView = LayoutInflater.from(context).inflate(itemId, parent, false);
            commonViewHolder = new CommonViewHolder(itemView);
            itemView.setTag(commonViewHolder);
        } else {
            commonViewHolder = (CommonViewHolder) itemView.getTag();
        }
        return commonViewHolder;
    }


    /**
     * 用于recyclerView
     *
     * @param parent
     * @param itemId
     * @return
     */
    public static CommonViewHolder getViewHolder(ViewGroup parent, int itemId) {
        return getViewHolder(null, parent, itemId);
    }

    // 给头布局使用的方法
    public static CommonViewHolder getHeadViewHolder(View view) {
        return new CommonViewHolder(view);
    }

    /**
     * 返回行布局
     *
     * @return
     */
    public View getItemView() {
        return itemView;
    }


    /******设置数据******/

    /**
     * 设置文字
     *
     * @param id   view的id
     * @param text 要设置的文字
     * @return this
     */
    public CommonViewHolder setText(int id, String text) {
        TextView textView = getView(id);
        textView.setText(text);
        return this;
    }

    /**
     * 设置本地图片
     *
     * @param id    view的id
     * @param imgId 图片的id
     * @return this
     */
    public CommonViewHolder setImage(int id, int imgId) {
        ImageView imageView = getView(id);
        imageView.setImageResource(imgId);
        return this;
    }

    /**
     * 设置网络图片
     *
     * @param id     ImageView的资源id
     * @param imgUrl 图片的网址
     * @return this
     */
    public CommonViewHolder setImage(int id, String imgUrl,Context context) {
        ImageView imageView = getView(id);
        Glide.with(context).load(imgUrl).into(imageView);
        return this;
    }

    /**
     * item的点击事件
     *
     * @param listener 点击监听
     * @return this
     */
    public CommonViewHolder setItemClick(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置view的点击事件
     *
     * @param id       view的id
     * @param listener 点击监听
     * @return this
     */
    public CommonViewHolder setViewClick(int id, View.OnClickListener listener) {
        getView(id).setOnClickListener(listener);
        return this;
    }
}
