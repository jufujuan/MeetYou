package com.zrj.dllo.meetyou.find.listfind;

import android.content.Context;

import com.zrj.dllo.meetyou.Person;

import java.util.List;

/**
 * If there is no bug, then it is created by ChenFengYao on 2016/12/6,
 * otherwise, I do not know who create it either.
 */

public interface Contract {
    interface View{
        /**
         * 把p层放到层中
         * @param presenter
         */
        void setPresenter(Presenter presenter);

        /**
         * 向Adapter中这设置网络数据
         * @param listFindBean
         */
        void setDataFromNet(List<Person> listFindBean);
    }

    interface Presenter{
        /**
         * 请求网络数据
         */
        void getListFindData(Context context,int distance);

        /**
         * 请求数据成功
         * @param listFindBean
         */
        void onGetDataSuccess(List<Person> listFindBean);

        /**
         * 请求数据失败
         * @param throwable
         */
        void onGetDataError(Throwable throwable);

        /**
         * 将喜欢的人数据存储在数据库中
         */
        void setLikePersonInLocalP(Person personvb);
        /**
         * 发送好友请求
         */
        void sendGoodFriendsRequestP(Person person);

        /**
         * 加载更多
         */
        List<Person> loadMoreData(Context context,int distance);

    }

    interface Model{
        /**
         * 查询bmob数据库的所有数据
         * (这是一个耗时操作)
         */
        void selectAllDatas(String name, Context context, int distance);
        /**
         * 查询sp或者bmob数据库中当前用户的索引
         */
        String searchCurrentL(Context context);
        /**
         * 经过判断,在全部的数据库中查询到符合条件的数据集合
         */
        void getFillDatas(List<Person> allDatas, Context context, int distance);
        /**
         * 将喜欢的人数据存储在数据库中
         */
        void setLikePersonInLocal(Person personvb);
        /**
         * 发送好友请求
         */
        void sendGoodFriendsRequest(Person person);
        /**
         * 加载更多
         */
        List<Person> loadMoreData(Context context,int distance);

    }
}
