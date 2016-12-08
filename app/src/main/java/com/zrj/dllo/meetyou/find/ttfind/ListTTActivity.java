package com.zrj.dllo.meetyou.find.ttfind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;
import com.zrj.dllo.meetyou.tools.LogUtils;
import com.zrj.dllo.meetyou.tools.StaticValues;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/26.
 */

public class ListTTActivity extends AbsBaseActivity {

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ImageView mImageView;
    private TextView mTextView, mNameTv;

    @Override
    protected int getLayout() {
        return R.layout.fra_find_more;
    }

    @Override
    protected void initView() {
        mToolbar = bindView(R.id.fra_find_more_toolbar);
        mNavigationView = bindView(R.id.fra_find_more_navigation);
        mImageView = bindView(R.id.fra_find_more_img);
        mTextView = bindView(R.id.fra_list_find_title);
        mNameTv = bindView(R.id.fra_list_find_name);
    }

    @Override
    protected void initDatas() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("遇见那个她");

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        Person person = (Person) bundle.getSerializable("person");
        if (person == null) {
            LogUtils.d("上一个界面的值没有传过来");
        } else {
            Glide.with(this).load(person.getUserImgUrl()).into(mImageView);
            mNameTv.setText(person.getuName());
        }
        if (TextUtils.isEmpty(person.getSignature())) {
            mTextView.setText("这个人太懒了,什么都没有留下");
        } else {
            mTextView.setText(person.getSignature());
        }
    }
}
