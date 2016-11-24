package com.zrj.dllo.meetyou;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.zrj.dllo.meetyou.Utils.LogUtils;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;

import com.zrj.dllo.meetyou.find.FindFragment;

import com.zrj.dllo.meetyou.personal.PersonalFragment;


/**
 * 绑定布局
 *
 * @return 将布局返回
 */
public class MainActivity extends AbsBaseActivity implements View.OnClickListener {

    private Button mainAtyMeetBtn, mainAtyMsgBtn, mainAtyWeatherBtn, mainAtyMyBtn;
    private TextView mainAtyMeetTv, mainAtyMsgTv, mainAtyWeatherTv, mainAtyMyTv;
    private FragmentManager mFragmentManager;
    private PersonalFragment mFragment;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "destory");
        super.onDestroy();

    }

    @Override
    protected void initView() {

        Log.d("MainActivity", "initView");

        mainAtyMeetBtn = bindView(R.id.aty_main_meet_btn);
        mainAtyMsgBtn = bindView(R.id.aty_main_msg_btn);
        mainAtyWeatherBtn = bindView(R.id.aty_main_weather_btn);
        mainAtyMyBtn = bindView(R.id.aty_main_my_btn);
        mainAtyMeetTv = bindView(R.id.aty_main_meet_tv);
        mainAtyMsgTv = bindView(R.id.aty_main_msg_tv);
        mainAtyWeatherTv = bindView(R.id.aty_main_weather_tv);
        mainAtyMyTv = bindView(R.id.aty_main_my_tv);

        setClickListener(this, mainAtyMeetBtn, mainAtyMsgBtn, mainAtyWeatherBtn, mainAtyMyBtn);
    }

    @Override
    protected void initDatas() {
        mFragmentManager = getSupportFragmentManager();
        SharedPreferences sharedPreferences = getSharedPreferences("night", 0);
        boolean is = sharedPreferences.getBoolean("isFragment",true);

        if (is) {
            btnChange(mainAtyMeetBtn, mainAtyMeetTv);
            FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
            mTransaction.replace(R.id.main_fl, FindFragment.newInstance());
            mTransaction.commit();
        }else {
            mFragment = new PersonalFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, mFragment).commit();
            btnChange(mainAtyMyBtn, mainAtyMyTv);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aty_main_meet_btn:
                btnChange(mainAtyMeetBtn, mainAtyMeetTv);
                //切换Fragment
                FragmentTransaction transaction =  mFragmentManager.beginTransaction();
                transaction.replace(R.id.main_fl, FindFragment.newInstance());
                transaction.commit();
                break;
            case R.id.aty_main_msg_btn:
                btnChange(mainAtyMsgBtn, mainAtyMsgTv);
                break;
            case R.id.aty_main_weather_btn:
                btnChange(mainAtyWeatherBtn, mainAtyWeatherTv);
                break;
            case R.id.aty_main_my_btn:
                mFragment = new PersonalFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, mFragment).commit();
                btnChange(mainAtyMyBtn, mainAtyMyTv);
                break;
        }
    }

    /**
     * 改变btn的状态
     *
     * @param btn      点击的btn
     * @param textView 点击的textView
     */

    public void btnChange(Button btn, TextView textView) {
        mainAtyMeetTv.setVisibility(View.GONE);
        mainAtyMsgTv.setVisibility(View.GONE);
        mainAtyWeatherTv.setVisibility(View.GONE);
        mainAtyMyTv.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);

        mainAtyMeetBtn.setBackgroundResource(R.drawable.btn_meet);
        mainAtyMsgBtn.setBackgroundResource(R.drawable.btn_msg);
        mainAtyWeatherBtn.setBackgroundResource(R.drawable.btn_weather);
        mainAtyMyBtn.setBackgroundResource(R.drawable.btn_my);
        switch (btn.getId()) {
            case R.id.aty_main_meet_btn:
                mainAtyMeetBtn.setBackgroundResource(R.drawable.btn_meet_select);
                break;
            case R.id.aty_main_msg_btn:
                mainAtyMsgBtn.setBackgroundResource(R.drawable.btn_msg_select);
                break;
            case R.id.aty_main_weather_btn:
                mainAtyWeatherBtn.setBackgroundResource(R.drawable.btn_weather_select);
                break;
            case R.id.aty_main_my_btn:
                mainAtyMyBtn.setBackgroundResource(R.drawable.btn_my_select);
                break;
        }
    }
}
