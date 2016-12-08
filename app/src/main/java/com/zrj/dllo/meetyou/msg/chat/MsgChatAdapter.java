package com.zrj.dllo.meetyou.msg.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.CommonViewHolder;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/12/1.
 * 聊天页面的适配器
 */

public class MsgChatAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private List<EMMessage> mEMMessages;
    private Context mContext;

    private static final int CHAT_TYPE_RECEIVE = 0; // 接收消息的布局
    private static final int CHAT_TYPE_SEND = 1; // 发送消息的布局

    public MsgChatAdapter(Context context) {
        mContext = context;
    }

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
                return 2;
        }
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, int position) {
        EMTextMessageBody body = (EMTextMessageBody) mEMMessages.get(position).getBody();
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        switch (getItemViewType(position)) {
            case CHAT_TYPE_RECEIVE:
                holder.setText(R.id.msg_chat_receive_tv, body.getMessage());
                // bmob查询头像
                bmobQuery.addWhereEqualTo("uName", mEMMessages.get(position).getFrom());
                bmobQuery.findObjects(new FindListener<Person>() {
                    @Override
                    public void done(List<Person> list, BmobException e) {
                        if (e == null) {
                            Glide.with(mContext).load(list.get(0).getUserImgUrl()).into((ImageView) holder.getView(R.id.msg_chat_receive_avatar));
                        }
                    }
                });
                break;
            case CHAT_TYPE_SEND:
                holder.setText(R.id.msg_chat_send_tv, body.getMessage());
                // bmob查询头像
                bmobQuery.addWhereEqualTo("uName", mEMMessages.get(position).getFrom());
                bmobQuery.findObjects(new FindListener<Person>() {
                    @Override
                    public void done(List<Person> list, BmobException e) {
                        if (e == null) {
                            Glide.with(mContext).load(list.get(0).getUserImgUrl()).into((ImageView) holder.getView(R.id.msg_chat_send_avatar));
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mEMMessages == null ? 0 : mEMMessages.size();
    }
}
