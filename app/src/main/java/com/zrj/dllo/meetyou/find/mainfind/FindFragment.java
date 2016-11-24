package com.zrj.dllo.meetyou.find.mainfind;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.Utils.DensityUtil;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/23.
 * 觅友界面
 */

public class FindFragment extends AbsBaseFragment implements FindContract.View, View.OnClickListener {

    private SweepImageView mSweepImageView;
    private CircleImageView mCircleImageView;
    private FindContract.Presenter mPresenter;

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
    }

    @Override
    protected void initDatas() {
        showSweepView();
        mCircleImageView.setOnClickListener(this);
    }

    /**
     * 将Presenter层放到View层
     *
     * @param presenter
     */
    @Override
    public void setPersenter(FindContract.Presenter presenter) {
        mPresenter=presenter;
    }

    /**
     * 显示扫描的图片
     */
    @Override
    public void showSweepView() {
        Bitmap bgBitmap=BitmapFactory.decodeResource(context.getResources(), R.mipmap.find_bg_img);
        Bitmap userBimap=BitmapFactory.decodeResource(context.getResources(), R.mipmap.find_user_img2);
        mSweepImageView.setImageBitmap(bgBitmap);
        mCircleImageView.setImageBitmap(userBimap);
    }

    /**
     * 显示点击动画
     */
    @Override
    public void showClickAnim() {
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

    @Override
    public void onClick(View view) {
        showClickAnim();
    }
}
