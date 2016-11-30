package com.zrj.dllo.meetyou.msg.conversation;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/11/28.
 */

public class MsgMsgAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    List<EMConversation> mEMConversations;

    public void setEMConversations(List<EMConversation> EMConversations) {
        mEMConversations = EMConversations;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.getViewHolder(parent, R.layout.item_conversation);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        holder.setText(R.id.conversation_name_tv, mEMConversations.get(position).getUserName());
        holder.setText(R.id.conversation_body_tv, getMessage(mEMConversations.get(position).getLastMessage()));
    }

    @Override
    public int getItemCount() {
        return mEMConversations == null ? 0 : mEMConversations.size();
    }

    private String getMessage(EMMessage message){

        String str = "";

        switch(message.getType()){

            //图片消息
            case IMAGE:{
                EMImageMessageBody imageBody = (EMImageMessageBody) message.getBody();
                str = "[picture]" + imageBody.getFileName();
                break;
            }

            case TXT:{
                EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
                str = txtBody.getMessage();

                break;
            }

        }

        return str;
    }
}
