package com.zrj.dllo.meetyou.find.mainfind;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.zrj.dllo.meetyou.MainActivity;
import com.zrj.dllo.meetyou.R;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/23.
 */

public class FindPresenter implements FindContract.Presenter {
    private FindContract.Model mModel;
    private FindContract.View mView;

    public FindPresenter(FindContract.Model model, FindContract.View view) {
        mModel = model;
        mView = view;
    }


    /**
     * 扫描界面跳转到主界面
     *
     * @param context
     */
    @Override
    public void goToMainAc(Context context ,int flags,int time,BDLocation bdLocation) {
        //先存储数据,再跳转
        mModel.searchIdByName(context,bdLocation);
        mModel.goToMainAcLater(context,flags,time);
    }

    /**
     * 开始搜索定位
     */
    @Override
    public void startSearch(LocationClient locationClient) {
        mModel.initLocation(locationClient);
    }


}
