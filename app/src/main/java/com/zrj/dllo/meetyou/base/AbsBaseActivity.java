package com.zrj.dllo.meetyou.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * 这是鞠福娟创建的哟~on 16/11/21.
 * Activity的基类
 */

public abstract class AbsBaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定布局
        setContentView(getLayout());
        //初始化组件
        initView();
        //初始化数据
        initDatas();
    }

    /**
     * 绑定布局
     * @return 将布局返回
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
     */
    protected <T extends View>T bindView(int resId){
        return (T) findViewById(resId);
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
        addAnimator();
    }
    /**
     * Activity跳转(带返回值)
     */
    protected void goTo(Context from,Class<?> to,Bundle bundle){
        Intent intent=new Intent(from,to);
        intent.putExtras(bundle);
        startActivity(intent);
        //跳转动画
        addAnimator();
    }

    /**
     *  显示长时间的Toast提示
     * @param text
     */
    protected void showToastLong(CharSequence text){
        Message message=handler.obtainMessage();
        message.what=Toast.LENGTH_LONG;
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
                    Toast.makeText(AbsBaseActivity.this,(String)msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case Toast.LENGTH_SHORT:
                    Toast.makeText(AbsBaseActivity.this,(String)msg.obj, Toast.LENGTH_SHORT).show();
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
        overridePendingTransition(R.anim.ac_zoom_enter,R.anim.ac_zoom_exit);
    }

    /**
     * 设置通用状态栏
     */
    protected void setStateBar(){

    }


    @Override
    public void finish() {
        super.finish();
        //在这里添加结束动画
        addAnimator();
    }
}
