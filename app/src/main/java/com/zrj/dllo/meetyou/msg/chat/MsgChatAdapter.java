package com.zrj.dllo.meetyou.msg.chat;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.CommonViewHolder;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/12/1.
 */

public class MsgChatAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private static final int CHAT_TYPE_RECEIVE = 0;
    private static final int CHAT_TYPE_SEND = 1;
    private static final int CHAT_TYPE_TIME = 2;


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
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case CHAT_TYPE_RECEIVE:
                break;
            case CHAT_TYPE_SEND:
                break;
            case CHAT_TYPE_TIME:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
