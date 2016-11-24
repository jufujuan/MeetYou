package com.zrj.dllo.meetyou.find;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.util.Measure;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.Utils.DensityUtil;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/23.
 * 觅友界面
 */

public class FindFragment extends AbsBaseFragment {

    private SweepImageView mSweepImageView;
    private CircleImageView mCircleImageView;
    private LinearLayout mFrameLayout;

    public static FindFragment newInstance() {

        Bundle args = new Bundle();

        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fra_find;
    }

    @Override
    protected void initView() {
        mSweepImageView = bindView(R.id.fra_find_sweepImageView);
        mCircleImageView = bindView(R.id.fra_find_circleImageView);
        mFrameLayout=bindView(R.id.fra_find_framelayout);
    }

    @Override
    protected void initDatas() {
        Bitmap bgBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.find_bg_img);
        Bitmap userBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.find_user_img2);
        mSweepImageView.setImageBitmap(bgBitmap);
        mCircleImageView.setImageBitmap(userBitmap);
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击头像扫描渲染效果
                mSweepImageView.addSweepRestartAnim();
                //点击头像的动画效果
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.2f, 0.8f, 1.2f,
                        DensityUtil.px2dip(context,mSweepImageView.getLeft()+mSweepImageView.getWidth()/2),
                        DensityUtil.px2dip(context,mSweepImageView.getTop()+mSweepImageView.getHeight()/2));
                scaleAnimation.setDuration(500);
                scaleAnimation.setInterpolator(new AccelerateInterpolator());
                mCircleImageView.startAnimation(scaleAnimation);
            }
        });
    }
}
