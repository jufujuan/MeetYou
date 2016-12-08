package com.zrj.dllo.meetyou.find.listfind.chen;

import android.content.Context;

import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.find.listfind.ListFindBean;

import java.util.List;

/**
 * If there is no bug, then it is created by ChenFengYao on 2016/12/6,
 * otherwise, I do not know who create it either.
 */

public class FindPresenter implements Contract.Presenter {
    private Contract.View mView;
    private Contract.Model mModel;

    public FindPresenter(Contract.View view) {
        mView = view;
        mModel = new ListFindModel(this);
    }

    //请求网络数据
    @Override
    public void getListFindData(Context context,int distance) {
        mModel.selectAllDatas(mModel.searchCurrentL(context),context,distance);
    }

    @Override
    public void onGetDataSuccess(List<Person> persons) {
        mView.setDataFromNet(persons);
    }

    @Override
    public void onGetDataError(Throwable throwable) {

    }

    @Override
    public void setLikePersonInLocalP(Person personvb) {
        mModel.setLikePersonInLocal(personvb);
    }

    @Override
    public void sendGoodFriendsRequestP(Person person) {
        mModel.sendGoodFriendsRequest(person);
    }

    @Override
    public List<Person> loadMoreData(Context context, int distance) {
        return mModel.loadMoreData(context,distance);
    }


}
