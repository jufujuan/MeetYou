package com.zrj.dllo.meetyou.find.listfind;

import android.content.Context;

import com.zrj.dllo.meetyou.Person;

import java.util.List;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/12/1.
 */

public interface ListFindContract {
    interface MyView{
        /**
         * 把p层放到层中
         * @param presenter
         */
        void setPresenter(ListFindPresenter presenter);
    }
    interface Presenter{

        /**
         * 得到数据库中所有的数据(去掉自己)
         */
        void getAllDatas(Context context,int distance);
        /**
         * 将数据集发送给recyclerview
         */
        void sendDatasToView(List<Person> datas,Context context);

    }
    interface Model{
        /**
         * 把p层放到M层中
         * @param presenter
         */
        void setPresenter(ListFindPresenter presenter);
        /**
         * 查询bmob数据库的所有数据
         * (这是一个耗时操作)
         */
        void selectAllDatas(String name,Context context,int distance);
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
    }

}
