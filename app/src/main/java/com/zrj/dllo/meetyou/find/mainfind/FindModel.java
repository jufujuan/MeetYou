package com.zrj.dllo.meetyou.find.mainfind;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zrj.dllo.meetyou.MainActivity;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.tools.LogUtils;
import com.zrj.dllo.meetyou.tools.StaticValues;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/23.
 */

public class FindModel implements FindContract.Model {
    private FindContract.Presenter mPresenter;
    private FindHandler mFindHandler = new FindHandler();

    private class FindHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /**
     * 把Presenter层放到Model层
     *
     * @param presenter
     */
    @Override
    public void setPresenter(FindContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 设置百度位置的初步设置
     *
     * @param locationClient
     */
    @Override
    public void initLocation(LocationClient locationClient) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        locationClient.setLocOption(option);
    }

    /**
     * 延时跳转到主界面
     *
     * @param context 上下文
     * @param flags   设置跳转的状态
     * @param time    延迟跳转的时间
     */
    @Override
    public void goToMainAcLater(final Context context, final int flags, final int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(flags);
                context.startActivity(intent);
            }
        }).start();
    }

    /**
     * 查询是否成功得到sp中的当前用户名
     *
     * @return 得到sp中的用户名返回true, 否则返回false
     */
    @Override
    public String getSpNmae(Context context) {
        SharedPreferences sp = context.getSharedPreferences(StaticValues.SP_USEING_TABLE_NAME, Context.MODE_PRIVATE);
        String uname = sp.getString(StaticValues.SP_USEING_NAME_COLUMN, "");
        return uname;
    }

    /**
     * 利用当前用户名查询索引
     */
    @Override
    public void searchIdByName(Context context,BDLocation location) {
        if (!TextUtils.isEmpty(getSpNmae(context))) {
            // 查询id,利用id更新数据库
            BmobQuery<Person> query = new BmobQuery<>("Person");
            query.addWhereEqualTo("uName", getSpNmae(context));
            QueryFindListener queryFindListener=new QueryFindListener(location,context);
            query.findObjects(queryFindListener);
        } else {
            LogUtils.d("查询失败");
        }
    }

    /**
     * 查询数据库的监听
     * 如果查询索引成功,更新数据
     */
    private class QueryFindListener extends FindListener<Person> {
        private BDLocation mLocation;
        private Context mContext;

        public QueryFindListener(BDLocation location,Context context) {
            mLocation = location;
            mContext=context;
        }

        @Override
        public void done(List<Person> list, BmobException e) {
            String nameId = list.get(0).getObjectId();
            //将查询到的信息存储到sp中
            SharedPreferences sp = mContext.getSharedPreferences(StaticValues.SP_USEING_TABLE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(StaticValues.SP_USEING_ID, list.get(0).getObjectId());
            editor.putString(StaticValues.SP_USEING_ADRESS_COLUMN, list.get(0).getAdress());
            editor.putString(StaticValues.SP_USEING_LONTITUDE_COLUMN, list.get(0).getLontitude());
            editor.putString(StaticValues.SP_USEING_LATITUDE_COLUMN, list.get(0).getLatitude());
            editor.commit();
            LogUtils.d("bbb索引是多少:" + nameId);
            if (!nameId.isEmpty()) {
                Person person = new Person();
                //利用id更新数据
                person.setAdress(mLocation.getAddrStr());
                person.setLatitude(String.valueOf(mLocation.getLatitude()));
                person.setLontitude(String.valueOf(mLocation.getLongitude()));
                person.setLikeCount(String.valueOf(0));
                person.setLocationDate(mLocation.getTime());
                person.setRadius(String.valueOf(mLocation.getRadius()));
                person.update(nameId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            LogUtils.d("bbb更新成功");
                        } else {
                            LogUtils.d("bbb更新失败,这个问题就麻烦了,可能是Bmob的原因");
                        }
                    }
                });
            } else {
                LogUtils.d("没有查询到该用户!请确定数据库中有该用户");
            }
        }
    }
}
