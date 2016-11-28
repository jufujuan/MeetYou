package com.zrj.dllo.meetyou.find.listfind;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.Utils.LogUtils;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.find.ttfind.ListTTActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/26.
 */

public class ListFindFragment extends AbsBaseFragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private List<ListFindBean> datas;
    private ListFindRecyclerAdapter mRecyclerAdapter;
    private List<String> imgUrls;

    @Override
    protected int getLayout() {
        return R.layout.fra_list_find;
    }

    @Override
    protected void initView() {
        mRecyclerView=bindView(R.id.fra_list_find_recyclerview);

    }

    @Override
    protected void initDatas() {
        imgUrls=new ArrayList<>();
        for (int i = 0; i <6 ; i++) {
            imgUrls.add("http://g.hiphotos.baidu.com/image/pic/item/8ad4b31c8701a18b91badb619c2f07082838fe4f.jpg");
            imgUrls.add("http://www.feizl.com/upload2007/2012_01/1201010230427610.png");
            imgUrls.add("http://img.bitscn.com/upimg/allimg/c160120/1453262X2T3P-4094E.jpg");
            imgUrls.add("http://img3.3lian.com/2013/gif/201308/16-080332_937.jpg");
            imgUrls.add("http://wenwen.soso.com/p/20120424/20120424171320-1212289240.jpg");
            imgUrls.add("http://img4.duitang.com/uploads/item/201508/22/20150822235943_jrBtV.thumb.224_0.jpeg");
        }
        datas=new ArrayList<>();
        for (int i = 0; i <36; i++) {
            datas.add(new ListFindBean(imgUrls.get(i),"张三","女"));
        }
        mRecyclerAdapter=new ListFindRecyclerAdapter(context);
        mRecyclerAdapter.setDatas(datas);
        mRecyclerAdapter.setOnClickListener(this);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
     //   mRecyclerView.setItemAnimator();
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.item_list_find_dislike:
                Toast.makeText(context, "不喜欢", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_list_find_like:
                Toast.makeText(context, "喜欢", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_list_find_img:
                Toast.makeText(context, "点击了图片", Toast.LENGTH_SHORT).show();
                goTo(context, ListTTActivity.class);
                break;
        }
    }

}
