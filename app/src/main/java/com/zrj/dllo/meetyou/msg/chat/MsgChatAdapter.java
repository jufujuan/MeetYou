package com.zrj.dllo.meetyou.msg.chat;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.CommonViewHolder;

import java.util.List;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/12/1.
 */

public class MsgChatAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private List<EMMessage> mEMMessages;

    private static final int CHAT_TYPE_RECEIVE = 0;
    private static final int CHAT_TYPE_SEND = 1;
    private static final int CHAT_TYPE_TIME = 2;

    public void setEMMessages(List<EMMessage> EMMessages) {
        mEMMessages = EMMessages;
        notifyDataSetChanged();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CHAT_TYPE_RECEIVE:
                return CommonViewHolder.getViewHolder(parent, R.layout.item_chat_receive);
            case CHAT_TYPE_SEND:
                return CommonViewHolder.getViewHolder(parent, R.layout.item_chat_send);
            case CHAT_TYPE_TIME:
                return CommonViewHolder.getViewHolder(parent, R.layout.item_chat_time);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mEMMessages.get(position).direct()) {
            case RECEIVE:
                return CHAT_TYPE_RECEIVE;
            case SEND:
                return CHAT_TYPE_SEND;
            default:
                return CHAT_TYPE_TIME;
        }
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        EMTextMessageBody body = (EMTextMessageBody) mEMMessages.get(position).getBody();
        switch (getItemViewType(position)) {
            case CHAT_TYPE_RECEIVE:
                holder.setText(R.id.msg_chat_receive_tv,body.getMessage());
                break;
            case CHAT_TYPE_SEND:
                holder.setText(R.id.msg_chat_send_tv, body.getMessage());
                break;
            case CHAT_TYPE_TIME:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mEMMessages== null ? 0 : mEMMessages.size();
    }
}
