package com.zrj.dllo.meetyou;


import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.zrj.dllo.meetyou.cons.ConsFragment;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zrj.dllo.meetyou.find.listfind.FindPresenter;
import com.zrj.dllo.meetyou.find.listfind.ListFindFragment;
import com.zrj.dllo.meetyou.msg.MsgFragment;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;
import com.zrj.dllo.meetyou.personal.PersonalFragment;
import com.zrj.dllo.meetyou.tools.LiteOrmInstance;

import java.util.List;

/**
 * 绑定布局
 *
 * @return 将布局返回
 */
public class MainActivity extends AbsBaseActivity implements View.OnClickListener {

    private Button mainAtyMeetBtn, mainAtyMsgBtn, mainAtyWeatherBtn, mainAtyMyBtn;
    private TextView mainAtyMeetTv, mainAtyMsgTv, mainAtyWeatherTv, mainAtyMyTv;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private PersonalFragment mFragment;
    private Handler mHandler;

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
        mHandler = new Handler(Looper.getMainLooper());
        mFragmentManager = getSupportFragmentManager();
        SharedPreferences sharedPreferences = getSharedPreferences("night", 0);
        boolean is = sharedPreferences.getBoolean("isFragment",true);

        if (is) {
            btnChange(mainAtyMeetBtn, mainAtyMeetTv);
            Change2Meet();
        }else {
            mFragment = new PersonalFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, mFragment).commit();
            btnChange(mainAtyMyBtn, mainAtyMyTv);
        }

        acceptRequest();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aty_main_meet_btn:
                btnChange(mainAtyMeetBtn, mainAtyMeetTv);
                //切换Fragment
                Change2Meet();
                break;
            case R.id.aty_main_msg_btn:
                btnChange(mainAtyMsgBtn, mainAtyMsgTv);
                // 切换到消息页面
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, new MsgFragment()).commit();
                break;
            case R.id.aty_main_weather_btn:
                btnChange(mainAtyWeatherBtn, mainAtyWeatherTv);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, new ConsFragment()).commit();

                break;
            case R.id.aty_main_my_btn:
                mFragment = new PersonalFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, mFragment).commit();
                btnChange(mainAtyMyBtn, mainAtyMyTv);
                break;
        }
    }

    /**
     * 切换到觅友界面
     */
    private void Change2Meet() {
        ListFindFragment mView=ListFindFragment.newInstance();
        FindPresenter mPresenter=new FindPresenter(mView);
        mView.setPresenter(mPresenter);

        android.support.v4.app.FragmentTransaction transaction =  mFragmentManager.beginTransaction();
        transaction.replace(R.id.main_fl, mView);
        transaction.commit();
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

    // 接收好友请求的监听
    public void acceptRequest() {
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

            @Override
            public void onContactAgreed(String username) {
                //好友请求被同意
            }

            @Override
            public void onContactRefused(String username) {
                //好友请求被拒绝
            }

            @Override
            public void onContactInvited(final String username, String reason) {
                //收到好友邀请
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Person> uName = LiteOrmInstance.getInstance().getQueryByWhere(Person.class, "uName", new String[]{username});
                        if (uName != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        EMClient.getInstance().contactManager().acceptInvitation(username);
                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    }
                }).start();


            }

            @Override
            public void onContactDeleted(String username) {
                //被删除时回调此方法
            }


            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
                Toast.makeText(MainActivity.this, "有新朋友啦", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
