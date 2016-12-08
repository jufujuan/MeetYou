package com.zrj.dllo.meetyou.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 这是鞠福娟创建的哟~on 16/11/21.
 */

public abstract class AbsBaseFragment extends Fragment {

    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化组件
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化数据
        initDatas();
    }

    /**
     * 绑定布局
     *
     * @return 布局的资源id
     */
    protected abstract int getLayout();

    /**
     * 初始化组件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initDatas();

    /**
     * findviewbyid的简化版
     *
     * @param resId 资源id
     * @param <T>   任何继承自view的组件
     * @return 组件
     * getView()用来获得
     */
    protected <T extends View> T bindView(int resId) {
        return (T) getView().findViewById(resId);
    }

    /**
     * findviewbyid的简化版(重载)
     *
     * @param resId    资源id
     * @param itemView 将这个view传过来
     * @param <T>      任何继承自view的组件
     * @return
     */
    protected <T extends View> T bindView(int resId, View itemView) {
        return (T) itemView.findViewById(resId);
    }

    /**
     * Activity跳转(带着flags的)
     */
    protected void goTo(Context from, Class<?> to,int flags) {
        Intent intent = new Intent(from, to);
        intent.setFlags(flags);
        startActivity(intent);
        //跳转动画
    }


    /**
     * view添加点击事件
     * @param clickListener 点击事件的监听
     * @param views 要添加的view
     */
    protected void setClickListener(View.OnClickListener clickListener, View... views) {
        for (View view :
                views) {
            view.setOnClickListener(clickListener);
        }
    }
}
