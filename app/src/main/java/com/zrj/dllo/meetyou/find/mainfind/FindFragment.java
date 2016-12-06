package com.zrj.dllo.meetyou.find.mainfind;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zrj.dllo.meetyou.MainActivity;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.app.MeetYouApp;
import com.zrj.dllo.meetyou.find.listfind.ListFindActivity;
import com.zrj.dllo.meetyou.tools.DensityUtil;

import com.zrj.dllo.meetyou.base.AbsBaseFragment;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zrj.dllo.meetyou.tools.LogUtils;
import com.zrj.dllo.meetyou.tools.StaticValues;
import com.zrj.dllo.meetyou.widget.CircleImageView;
import com.zrj.dllo.meetyou.widget.SweepImageView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/23.
 * 觅友界面
 */

public class FindFragment extends AbsBaseFragment implements FindContract.View, View.OnClickListener {

    private SweepImageView mSweepImageView;
    private CircleImageView mCircleImageView;
    private FindContract.Presenter mPresenter;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener;
    private TextView loadingTv;
    private String nameId = null;
    private Button ignoreBtn;

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
        loadingTv = bindView(R.id.fra_find_seek_tv);
        myListener = new MyLocationListener();
        ignoreBtn = bindView(R.id.fra_find_sweep_ignore);
    }

    @Override
    protected void initDatas() {
        ignoreBtn.setVisibility(View.GONE);
        showSweepView();
        mCircleImageView.setOnClickListener(this);
        ignoreBtn.setOnClickListener(this);

        mLocationClient = new LocationClient(MeetYouApp.getContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
    }

    /**
     * 将Presenter层放到View层
     *
     * @param presenter
     */
    @Override
    public void setPersenter(FindContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 显示扫描的图片
     */
    @Override
    public void showSweepView() {
        Bitmap bgBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.find_bg_img);
        Bitmap userBimap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.find_user_img2);
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
                DensityUtil.px2dip(context, mSweepImageView.getLeft() + mSweepImageView.getWidth() / 2),
                DensityUtil.px2dip(context, mSweepImageView.getTop() + mSweepImageView.getHeight() / 2));
        scaleAnimation.setDuration(500);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        mCircleImageView.startAnimation(scaleAnimation);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fra_find_circleImageView:
                mPresenter.startSearch(mLocationClient);
                showClickAnim();
                loadingTv.setText("正在搜索附近的人...");
                mLocationClient.start();
                break;
            case R.id.fra_find_sweep_ignore:
                //专门用来调试的
                //当定位失败的时候进入到这里面
                Intent intent=new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                mLocationClient.stop();
                break;
        }
    }

    /**
     * GPS定位成功,显示提示信息信息
     */
    @Override
    public void showGPSMsg(BDLocation location) {
        Toast.makeText(context, "GPS定位成功", Toast.LENGTH_SHORT).show();
        /*******在这里存储经纬度,地址,半径范围*******/
        mLocationClient.stop();
        mPresenter.goToMainAc(context, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK, 1000, location);
    }

    /**
     * 网络定位成功,显示提示信息信息
     *
     * @param location
     */
    @Override
    public void showNetMsg(BDLocation location) {
        Toast.makeText(context, "网络定位成功", Toast.LENGTH_SHORT).show();
        /*******在这里存储经纬度,地址,半径范围*******/
        mPresenter.goToMainAc(context, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK, 1000, location);
        mLocationClient.stop();

    }

    /**
     * 离线定位定位成功,显示提示信息信息
     *
     * @param location
     */
    @Override
    public void showNotNetMsg(BDLocation location) {
        Toast.makeText(context, "离线定位成功", Toast.LENGTH_SHORT).show();
        /*******在这里存储经纬度,地址,半径范围*******/
        mPresenter.goToMainAc(context, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK, 1000, location);
        mLocationClient.stop();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            switch (location.getLocType()) {
                case 61:
                    showGPSMsg(location);
                    break;
                case 62:
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
                case 63:
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
                case 64:
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
                case 65:
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
                case 66:
                    showNotNetMsg(location);
                    break;
                case 67:
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
                case 68:
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
                case 161:
                    showNetMsg(location);
                    break;
                case 162:
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
                case 167:
                    Toast.makeText(context, "服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因", Toast.LENGTH_SHORT).show();
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
                case 502:
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
                case 505:
                    goTo(context, MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                case 601:
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
                case 602:
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
                default:
                    ignoreBtn.setVisibility(View.VISIBLE);
                    break;
            }
//            //Receive Location
//            StringBuffer sb = new StringBuffer(256);
//            sb.append("time : ");
//            sb.append(location.getTime());
//            sb.append("\nerror code : ");
//            sb.append(location.getLocType());
//            sb.append("\nlatitude : ");
//            sb.append(location.getLatitude());
//            sb.append("\nlontitude : ");
//            sb.append(location.getLongitude());
//            sb.append("\nradius : ");
//            sb.append(location.getRadius());
//            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                sb.append("\nspeed : ");
//                sb.append(location.getSpeed());// 单位：公里每小时
//                sb.append("\nsatellite : ");
//                sb.append(location.getSatelliteNumber());
//                sb.append("\nheight : ");
//                sb.append(location.getAltitude());// 单位：米
//                sb.append("\ndirection : ");
//                sb.append(location.getDirection());// 单位度
//                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());
//                sb.append("\ndescribe : ");
//                sb.append("gps定位成功");
//            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());
//                //运营商信息
//                sb.append("\noperationers : ");
//                sb.append(location.getOperators());
//                sb.append("\ndescribe : ");
//                sb.append("网络定位成功");
//            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                sb.append("\ndescribe : ");
//                sb.append("离线定位成功，离线定位结果也是有效的");
//            } else if (location.getLocType() == BDLocation.TypeServerError) {
//                sb.append("\ndescribe : ");
//                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//                sb.append("\ndescribe : ");
//                sb.append("网络不同导致定位失败，请检查网络是否通畅");
//            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//                sb.append("\ndescribe : ");
//                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//            }
//            sb.append("\nlocationdescribe : ");
//            sb.append(location.getLocationDescribe());// 位置语义化信息
//            List<Poi> list = location.getPoiList();// POI数据
//            if (list != null) {
//                sb.append("\npoilist size = : ");
//                sb.append(list.size());
//                for (Poi p : list) {
//                    sb.append("\npoi= : ");
//                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
//                }
//            }
//            // Log.i("BaiduLocationApiDem", sb.toString());
//            loadingTv.setText(sb.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
