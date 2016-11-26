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
import android.view.View;
import android.widget.LinearLayout;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/26.
 */

public class ListFindFragment extends AbsBaseFragment{

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
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
     //   mRecyclerView.setItemAnimator();
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }
//    class MyItemAnimator extends RecyclerView.ItemAnimator{
//        List<RecyclerView.ViewHolder> mAnimationAddViewHolders = new ArrayList<RecyclerView.ViewHolder>();
//        List<RecyclerView.ViewHolder> mAnimationRemoveViewHolders = new ArrayList<RecyclerView.ViewHolder>();
//        @Override
//        public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
//            return mAnimationRemoveViewHolders.add(viewHolder);
//        }
//
//        @Override
//        public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
//            return mAnimationAddViewHolders.add(viewHolder);
//        }
//
//        @Override
//        public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
//            return false;
//        }
//
//        @Override
//        public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
//            return false;
//        }
//
//
//        @Override
//        public void runPendingAnimations() {
//            if (!mAnimationAddViewHolders.isEmpty()) {
//
//                AnimatorSet animator;
//                View target;
//                for (final RecyclerView.ViewHolder viewHolder : mAnimationAddViewHolders) {
//                    target = viewHolder.itemView;
//                    animator = new AnimatorSet();
//
//                    animator.playTogether(
//                            ObjectAnimator.ofFloat(target, "translationX", -target.getMeasuredWidth(), 0.0f),
//                            ObjectAnimator.ofFloat(target, "alpha", target.getAlpha(), 1.0f)
//                    );
//
//                    animator.setTarget(target);
//                    animator.setDuration(100);
//                    animator.addListener(new Animator.AnimatorListener() {
//                        @Override
//                        public void onAnimationStart(Animator animator) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            mAnimationAddViewHolders.remove(viewHolder);
//                            if (!isRunning()) {
//                                dispatchAnimationsFinished();
//                            }
//                        }
//
//                        @Override
//                        public void onAnimationCancel(Animator animator) {
//
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animator animator) {
//
//                        }
//                    });
//                    animator.start();
//                }
//            }
//            else if(!mAnimationRemoveViewHolders.isEmpty()){
//            }
//        }
//
//        @Override
//        public void endAnimation(RecyclerView.ViewHolder item) {
//
//        }
//
//        @Override
//        public void endAnimations() {
//
//        }
//
//        @Override
//        public boolean isRunning() {
//            return !(mAnimationAddViewHolders.isEmpty()&&mAnimationRemoveViewHolders.isEmpty());
//        }
//    }
}
