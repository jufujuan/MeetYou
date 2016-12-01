package com.zrj.dllo.meetyou.find.listfind;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.find.ttfind.ListTTActivity;
import com.zrj.dllo.meetyou.tools.DistanceUtils;
import com.zrj.dllo.meetyou.tools.LogUtils;
import com.zrj.dllo.meetyou.tools.StaticValues;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/26.
 */

public class ListFindFragment extends AbsBaseFragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private List<ListFindBean> datas;
    private ListFindRecyclerAdapter mRecyclerAdapter;
    private List<String> imgUrls;
    private final static int QUERY_LIMIT = 50;//查询人数限制
    private final static int DISTANCE = 2000;//单位米
    private List<Person> mAllPersons;
    private List<Person> mPersons;

    @Override
    protected int getLayout() {
        return R.layout.fra_list_find;
    }

    @Override
    protected void initView() {
        mRecyclerView = bindView(R.id.fra_list_find_recyclerview);

    }

    @Override
    protected void initDatas() {
        /******构造数据******/
        imgUrls = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            imgUrls.add("http://g.hiphotos.baidu.com/image/pic/item/8ad4b31c8701a18b91badb619c2f07082838fe4f.jpg");
            imgUrls.add("http://www.feizl.com/upload2007/2012_01/1201010230427610.png");
            imgUrls.add("http://img.bitscn.com/upimg/allimg/c160120/1453262X2T3P-4094E.jpg");
            imgUrls.add("http://img3.3lian.com/2013/gif/201308/16-080332_937.jpg");
            imgUrls.add("http://wenwen.soso.com/p/20120424/20120424171320-1212289240.jpg");
            imgUrls.add("http://img4.duitang.com/uploads/item/201508/22/20150822235943_jrBtV.thumb.224_0.jpeg");
        }
        datas = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            datas.add(new ListFindBean(imgUrls.get(i), "张三", "女"));
        }
        //获得数据库中的所有人的数据
        mAllPersons = new ArrayList<>();
        BmobQuery<Person> query = new BmobQuery<>();
        query.findObjects(new QueryAll());
        setPersons();


        /*****************/
        mRecyclerAdapter = new ListFindRecyclerAdapter(context);
       // mRecyclerAdapter.setDatas(mPersons);

    }

    private void setPersons() {
        //获得从数据库中的到的当前用户的信息
        SharedPreferences sp = context.getSharedPreferences(StaticValues.SP_USEING_TABLE_NAME, Context.MODE_PRIVATE);
        //获取该用户的地理位置
        //String lo = sp.getString(StaticValues.SP_USEING_LONTITUDE_COLUMN, "0");
        //String la = sp.getString(StaticValues.SP_USEING_LATITUDE_COLUMN, "0");
        String nameId=sp.getString(StaticValues.SP_USEING_ID,"0");
        BmobQuery<Person> query=new BmobQuery<>();
        query.getObject(nameId, new QueryListener<Person>() {
            @Override
            public void done(Person person, BmobException e) {
                String lo=person.getLontitude();
                String la=person.getLatitude();

                mPersons = new ArrayList<>();
                for (int i = 0; i < mAllPersons.size(); i++) {
                    Double loOne = Double.parseDouble(lo);
                    Double laOne = Double.parseDouble(la);
                    LogUtils.d(loOne+"-----------"+laOne);
                    if (lo.isEmpty() || la.isEmpty()) {
                        LogUtils.d("该用户的经纬度没找到....");
                    } else {
                        LogUtils.d(mAllPersons.get(i).getLontitude() + "  " + mAllPersons.get(i).getLatitude());

                        if (TextUtils.isEmpty(mAllPersons.get(i).getLontitude()) ||TextUtils.isEmpty(mAllPersons.get(i).getLatitude())) {
                            LogUtils.d("跳过去");
                            continue;
                        }

                        Double loTwo = Double.parseDouble(mAllPersons.get(i).getLontitude());
                        Double laTwo = Double.parseDouble(mAllPersons.get(i).getLatitude());

                        LogUtils.d("距离" + DistanceUtils.getDistance(loOne, laOne, loTwo, laTwo));
                        if (DistanceUtils.getDistance(loOne, laOne, loTwo, laTwo) < DISTANCE) {
                            mPersons.add(mAllPersons.get(i));
                        }

                    }
                }
                LogUtils.d("得到的:" + mPersons.size());
                if (mPersons.size()>0){
                    mRecyclerAdapter.setDatas(mPersons);
                    mRecyclerAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.item_list_find_dislike:
                                    Toast.makeText(context, "不喜欢", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.item_list_find_like:
                                    Toast.makeText(context, "喜欢", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.item_list_find_img:
                                    Toast.makeText(context, "点击了图片", Toast.LENGTH_SHORT).show();
                                    goTo(context, ListTTActivity.class);
                                    break;
                            }
                        }
                    });
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                    mRecyclerView.setAdapter(mRecyclerAdapter);
                }
            }
        });




    }

    private class QueryAll extends FindListener<Person> {

        @Override
        public void done(List<Person> list, BmobException e) {
            if (list != null && list.size() > 0) {
                LogUtils.d("查询到的数据集" + list.size());
                for (Person person : list) {
                    LogUtils.d(person.getuName());
                    mAllPersons.add(person);
                }
            } else {
                LogUtils.d("查询失败!服务器的问题");
            }
            //LogUtils.d(e.getMessage() + "," + e.getErrorCode());
            setPersons();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_list_find_dislike:
                Toast.makeText(context, "不喜欢", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_list_find_like:
                Toast.makeText(context, "喜欢", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_list_find_img:
                Toast.makeText(context, "点击了图片", Toast.LENGTH_SHORT).show();
                goTo(context, ListTTActivity.class);
                break;
        }
    }

}
