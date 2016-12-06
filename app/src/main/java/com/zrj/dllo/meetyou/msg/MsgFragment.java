package com.zrj.dllo.meetyou.msg;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.personal.PersonalFragment;
import com.zrj.dllo.meetyou.tools.CircularImageViewUtils;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.msg.contact.MsgContactFragment;
import com.zrj.dllo.meetyou.msg.conversation.MsgMsgFragment;
import com.zrj.dllo.meetyou.tools.StaticValues;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/11/26.
 */

public class MsgFragment extends AbsBaseFragment implements View.OnClickListener {

    private ImageView fraMsgTitleAvatar;
    private RadioButton msgMsgBtn;
    private RadioButton msgContactBtn;
    private FragmentManager mFragmentManager;


    @Override
    protected int getLayout() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void initView() {
        fraMsgTitleAvatar = bindView(R.id.fra_msg_title_avatar);
        msgMsgBtn = bindView(R.id.msg_msg_btn);
        msgContactBtn = bindView(R.id.msg_contact_btn);

        setClickListener(this, msgMsgBtn, msgContactBtn);
    }

    @Override
    protected void initDatas() {
        mFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.msg_fl, new MsgMsgFragment());
        transaction.commit();


        SharedPreferences preferences = context.getSharedPreferences("userMessage", Activity.MODE_PRIVATE);
        String mImgUrl = preferences.getString(StaticValues.SP_USEING_IMG_URL_COLUMN, "4869");
        if (!mImgUrl.equals("4869")) {
            Glide.with(this).load(mImgUrl).into(fraMsgTitleAvatar);
        }
    }

    @Override
    public void onClick(View v) {
//        mFragmentManager = getFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.msg_msg_btn:
                transaction.replace(R.id.msg_fl, new MsgMsgFragment());
                break;
            case R.id.msg_contact_btn:
                transaction.replace(R.id.msg_fl, new MsgContactFragment());
                break;
        }
        transaction.commit();
    }


}
