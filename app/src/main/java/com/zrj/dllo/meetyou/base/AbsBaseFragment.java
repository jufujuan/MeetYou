package com.zrj.dllo.meetyou.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 这是鞠福娟创建的哟~on 16/11/21.
 */

public abstract class AbsBaseFragment extends Fragment{

    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(),container,false);

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
     * @param resId 资源id
     * @param <T> 任何继承自view的组件
     * @return 组件
     * getView()用来获得
     */
    protected <T extends View>T bindView(int resId){
        return (T) getView().findViewById(resId);
    }

    /**
     * findviewbyid的简化版(重载)
     * @param resId 资源id
     * @param itemView 将这个view传过来
     * @param <T> 任何继承自view的组件
     * @return
     */
    protected <T extends View> T bindView(int resId,View itemView){
        return (T) itemView.findViewById(resId);
    }
    /**
     * Activity跳转(不带返回值)
     */
    protected void goTo(Context from,Class<?> to){
        Intent intent=new Intent(from,to);
        startActivity(intent);
        //跳转动画
    }
    /**
     * Activity跳转(带返回值)
     */
    protected void goTo(Context from,Class<?> to,Bundle bundle){
        Intent intent=new Intent(from,to);
        intent.putExtras(bundle);
        startActivity(intent);
        //跳转动画
    }

    /**
     *  显示长时间的Toast提示
     * @param text
     */
    protected void showToastLong(CharSequence text){
        Message message=handler.obtainMessage();
        message.what= Toast.LENGTH_LONG;
        message.obj=text;
        handler.sendMessage(message);
    }

    /**
     * 显示短时间的Toast提示
     * @param text
     */
    protected void showToastShort(CharSequence text){
        Message message=handler.obtainMessage();
        message.what=Toast.LENGTH_SHORT;
        message.obj=text;
        handler.sendMessage(message);
    }

    /**
     * 用来在主界面显示toast
     */
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Toast.LENGTH_LONG:
                    Toast.makeText(context,(String)msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case Toast.LENGTH_SHORT:
                    Toast.makeText(context,(String)msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 添加动画效果
     */
    protected void addAnimator(){

    }
}
