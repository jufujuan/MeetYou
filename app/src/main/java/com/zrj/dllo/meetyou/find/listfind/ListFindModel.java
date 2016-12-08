package com.zrj.dllo.meetyou.find.listfind;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.find.listfind.Contract;
import com.zrj.dllo.meetyou.tools.DistanceUtils;
import com.zrj.dllo.meetyou.tools.LiteOrmInstance;
import com.zrj.dllo.meetyou.tools.LogUtils;
import com.zrj.dllo.meetyou.tools.StaticValues;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * If there is no bug, then it is created by ChenFengYao on 2016/12/6,
 * otherwise, I do not know who create it either.
 */

public class ListFindModel implements Contract.Model {
    private List<Person> allDatas = new ArrayList<>();

    private Contract.Presenter mPresenter;
    private List<Person> mPersons;
    private int positionLoadMore;

    public ListFindModel(Contract.Presenter presenter) {
        mPresenter = presenter;
    }


    /**
     * 查询bmob数据库的所有数据
     * (这是一个耗时操作)
     */
    @Override
    public void selectAllDatas(String name, Context context, int distance) {

        BmobQuery<Person> query = new BmobQuery<>();
        QueryAll queryAll = new QueryAll();
        queryAll.setName(name);
        queryAll.setContext(context);
        queryAll.setDistance(distance);
        query.findObjects(queryAll);
    }

    /**
     * 查询sp或者bmob数据库中当前用户的索引
     * return 返回当前用户的索引
     */
    @Override
    public String searchCurrentL(Context context) {
        //获得从数据库中的到的当前用户的信息
        SharedPreferences sp = context.getSharedPreferences(StaticValues.SP_USEING_TABLE_NAME, Context.MODE_PRIVATE);
        //获取该用户的地理位置
        //String lo = sp.getString(StaticValues.SP_USEING_LONTITUDE_COLUMN, "0");
        //String la = sp.getString(StaticValues.SP_USEING_LATITUDE_COLUMN, "0");
        String nameId = sp.getString(StaticValues.SP_USEING_ID, "0");
        if (TextUtils.isEmpty(nameId)){
            Toast.makeText(context, "索引为空", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "索引为:" + nameId, Toast.LENGTH_SHORT).show();
        }
        return nameId;
    }

    /**
     * 经过判断,在全部的数据库中查询到符合条件的数据集合
     *
     * @param allDatas
     */
    @Override
    public void getFillDatas(final List<Person> allDatas, final Context context, final int distance) {
        BmobQuery<Person> query = new BmobQuery<>();
        if (!TextUtils.isEmpty(searchCurrentL(context))) {
            query.getObject(searchCurrentL(context), new QueryListener<Person>() {
                @Override
                public void done(Person person, BmobException e) {
                    if (e==null) {
                        String lo = person.getLontitude();
                        String la = person.getLatitude();

                        mPersons = new ArrayList<>();
                        for (int i = 0; i < allDatas.size(); i++) {
                            Double loOne = Double.parseDouble(lo);
                            Double laOne = Double.parseDouble(la);
                            LogUtils.d(loOne + "-----------" + laOne);
                            if (lo.isEmpty() || la.isEmpty()) {
                                LogUtils.d("该用户的经纬度没找到....");
                            } else {
                                LogUtils.d(allDatas.get(i).getLontitude() + "  " + allDatas.get(i).getLatitude());

                                if (TextUtils.isEmpty(allDatas.get(i).getLontitude()) || TextUtils.isEmpty(allDatas.get(i).getLatitude())) {
                                    LogUtils.d("跳过去");
                                    continue;
                                }

                                Double loTwo = Double.parseDouble(allDatas.get(i).getLontitude());
                                Double laTwo = Double.parseDouble(allDatas.get(i).getLatitude());

                                LogUtils.d("距离" + DistanceUtils.getDistance(loOne, laOne, loTwo, laTwo));
                                if (DistanceUtils.getDistance(loOne, laOne, loTwo, laTwo) < distance) {
                                    if (mPersons.size()>=10){
                                        positionLoadMore=i;
                                        break;
                                    }else {
                                        mPersons.add(allDatas.get(i));
                                    }
                                }

                            }
                        }
                        LogUtils.d("得到的:" + mPersons.size());

                        mPresenter.onGetDataSuccess(mPersons);
                    }else{
                        Toast.makeText(context, "bmob数据库查询失败"+e.getErrorCode()+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(context, "用户的索引没有获取到", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 将喜欢的人数据存储在数据库中
     */
    @Override
    public void setLikePersonInLocal(Person person) {
        InsertLiteOrm insertRunnable = new InsertLiteOrm(person);
        new Thread(insertRunnable).start();

    }

    /**
     * 发送好友请求
     *
     * @param person
     */
    @Override
    public void sendGoodFriendsRequest(Person person) {
        try {
            EMClient.getInstance().contactManager().addContact(person.getuName(), null);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Person> loadMoreData(Context context,int distance) {
        List<Person> loadMoreDatas=new ArrayList<>();
        SharedPreferences sp=context.getSharedPreferences(StaticValues.SP_USEING_TABLE_NAME,Context.MODE_PRIVATE);
        Double mLa=Double.parseDouble(sp.getString(StaticValues.SP_USEING_LATITUDE_COLUMN,"0"));
        Double mLo=Double.parseDouble(sp.getString(StaticValues.SP_USEING_LONTITUDE_COLUMN,"0"));
        LogUtils.d("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        LogUtils.d("加载更多中当前用户经纬度"+mLa);
        for (int i = positionLoadMore+1; i < allDatas.size(); i++) {
            Double la=Double.parseDouble(allDatas.get(i).getLatitude());
            Double lo=Double.parseDouble(allDatas.get(i).getLontitude());
            if (DistanceUtils.getDistance(mLo, mLa, lo, la) <= distance){
                if (loadMoreDatas.size()>=10){
                    break;
                }else {
                    LogUtils.d("加载一条......");
                    loadMoreDatas.add(allDatas.get(i));
                }
            }
            positionLoadMore=i;
        }
        return loadMoreDatas;
    }

    /**
     * 将喜欢的人数据存储在数据库中的线程
     */
    private class InsertLiteOrm implements Runnable {
        private Person mPerson;

        public InsertLiteOrm(Person person) {
            mPerson = person;
        }

        @Override
        public void run() {
            LiteOrmInstance liteOrmInstance = LiteOrmInstance.getInstance();
            liteOrmInstance.insert(mPerson);
        }
    }

    /**
     * 得到所有的数据集合
     */
    private class QueryAll extends FindListener<Person> {
        private String name;
        private Context mContext;
        private int distance;

        public void setContext(Context context) {
            mContext = context;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        @Override
        public void done(List<Person> list, BmobException e) {
            if (list != null && list.size() > 0) {
                LogUtils.d("查询到的数据集" + list.size());
                for (Person person : list) {
                    LogUtils.d(person.getuName());
                    if (person.getObjectId().equals(name)) {
                        continue;
                    }
                    allDatas.add(person);
                }
            } else {
                LogUtils.d("查询失败!服务器的问题");
            }
            /*************在这里得到了所有的数据**************/
            /**
             *  得到的数据进行各种操作放在这里
             */
            getFillDatas(allDatas, mContext, distance);
            /***********************************************/
        }
    }
}
