package com.zrj.dllo.meetyou.msg.conversation;

import android.support.v7.widget.RecyclerView;

import com.hyphenate.chat.EMConversation;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/11/26.
 */
public class MsgMsgFragment extends AbsBaseFragment {

    private RecyclerView msgMsgRv;
    private List<EMConversation> mConversations;

    @Override
    protected int getLayout() {
        return R.layout.fragment_msg_msg;
    }

    @Override
    protected void initView() {
        msgMsgRv = bindView(R.id.msg_msg_Rv);
        mConversations = new ArrayList<>();
    }

    @Override
    protected void initDatas() {

    }
}
