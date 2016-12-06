package com.zrj.dllo.meetyou.find.listfind;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.CommonViewHolder;
import com.zrj.dllo.meetyou.find.imgbgclick.FindBgClickActivity;
import com.zrj.dllo.meetyou.find.ttfind.ListTTActivity;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemDislikeClickListener;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemImgClickListener;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemLikeClickListener;
import com.zrj.dllo.meetyou.widget.GlideImageLoader;

import java.util.ArrayList;
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
    public void sendDatasToView(final List<Person> mPersons, final Context context) {
        if (mPersons.size()>0){
            mView.mRecyclerAdapter.setDatas(mPersons);
            mView.mRecyclerAdapter.setImgClickListener(new RecyclerViewItemImgClickListener() {
                @Override
                public void onItemImg(View view, int position, Person person) {
                    Toast.makeText(context, "点击了图片", Toast.LENGTH_SHORT).show();
                    // goTo(context, ListTTActivity.class);
                    context.startActivity(new Intent(context,FindBgClickActivity.class));
                }
            });
            mView.mRecyclerAdapter.setLikeClickListener(new RecyclerViewItemLikeClickListener() {
                @Override
                public void onItemLike(View view, int position, Person person) {
                    Toast.makeText(context, "喜欢", Toast.LENGTH_SHORT).show();
                    //1.向本地数据库中数据存储(存储自己的喜欢列表)
                    mModel.setLikePersonInLocal(person);
                    //2.向对方这个人发送好友申请
                    mModel.sendGoodFriendsRequest(person);

                    mPersons.remove(position);
                    mView.mRecyclerAdapter.notifyItemRemoved(position);
                    mView.mRecyclerAdapter.notifyItemRangeChanged(position-1,mPersons.size()+1-position);
                }
            });
            mView.mRecyclerAdapter.setDislikeClickListener(new RecyclerViewItemDislikeClickListener() {
                @Override
                public void onItemDislike(View view, int position, Person person) {
                    Toast.makeText(context, "不喜欢", Toast.LENGTH_SHORT).show();
                    Log.d("aaaaa", "position"+position+"persons:"+mPersons.size());

                    mPersons.remove(position);
                    mView.mRecyclerAdapter.notifyItemRemoved(position);
                    mView.mRecyclerAdapter.notifyItemRangeChanged(position-1,mPersons.size()+1-position);
                }
            });
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mView.mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

            //添加头布局
            HeaderAndFooterWrapper headerAndFooterWrapper = addHeader(context);

            mView.mRecyclerView.setAdapter(headerAndFooterWrapper);
        }
    }

    /**
     * 添加头布局
     * @param context
     * @return
     */
    @NonNull
    private HeaderAndFooterWrapper addHeader(Context context) {
        HeaderAndFooterWrapper headerAndFooterWrapper=new HeaderAndFooterWrapper(mView.mRecyclerAdapter);
        List<String> imgUrls=new ArrayList<>();
        imgUrls.add("http://images2.china.com/game/zh_cn/picnews/11128819/20140314/18394327_20140314115011898593008.jpg");
        imgUrls.add("http://4493bz.1985t.com/uploads/allimg/150127/4-15012G52133.jpg");
        imgUrls.add("http://image92.360doc.com/DownloadImg/2016/01/0121/63840877_39.jpg");

        View itemView= LayoutInflater.from(context).inflate(R.layout.item_list_find_header,null);
        Banner banner= (Banner) itemView.findViewById(R.id.item_list_find_banner);
        banner.setImages(imgUrls);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.setDelayTime(1500);
        banner.isAutoPlay(true);
        banner.setMinimumHeight(200);
        //banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
        headerAndFooterWrapper.addHeaderView(itemView);
        return headerAndFooterWrapper;
    }


}
