package com.zrj.dllo.meetyou.find;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.Utils.DensityUtil;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.find.mainfind.CircleImageView;
import com.zrj.dllo.meetyou.find.mainfind.SweepImageView;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/23.
 * 觅友界面
 */

public class FindFragment extends AbsBaseFragment{

    private SweepImageView mSweepImageView;
    private CircleImageView mCircleImageView;

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
        mSweepImageView=bindView(R.id.fra_find_sweepImageView);
        mCircleImageView=bindView(R.id.fra_find_circleImageView);
    }

    @Override
    protected void initDatas() {
        Bitmap bgBitmap= BitmapFactory.decodeResource(context.getResources(),R.mipmap.find_bg_img);
        Bitmap userBitmap=BitmapFactory.decodeResource(context.getResources(),R.mipmap.find_user_img2);

        mSweepImageView.setImageBitmap(bgBitmap);
        mCircleImageView.setImageBitmap(userBitmap);
    }
}
