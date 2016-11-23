package com.zrj.dllo.meetyou;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;
import com.zrj.dllo.meetyou.personal.PersonalFragment;

public class MainActivity extends AbsBaseActivity{



    /**
     * 绑定布局
     *
     * @return 将布局返回
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initView() {
        Log.d("MainActivity", "initView");
        PersonalFragment fragment = new PersonalFragment();
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content,fragment).commit();

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initDatas() {

    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "destory");
//        getSupportFragmentManager().beginTransaction()
        super.onDestroy();
    }
}
