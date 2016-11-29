package com.zrj.dllo.meetyou.msg.conversation;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hyphenate.chat.EMConversation;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.CommonViewHolder;

import java.util.ArrayList;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/11/28.
 */

public class MsgMsgAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    ArrayList<EMConversation> mEMConversations;

    public void setEMConversations(ArrayList<EMConversation> EMConversations) {
        mEMConversations = EMConversations;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.getViewHolder(parent, R.layout.item_conversation);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        holder.setText(R.id.conversation_name_tv, mEMConversations.get(position).getUserName());
        holder.setText(R.id.conversation_body_tv, mEMConversations.get(position).getLastMessage().getBody().toString());
    }

    @Override
    public int getItemCount() {
        return mEMConversations == null ? 0 : mEMConversations.size();
    }
}
