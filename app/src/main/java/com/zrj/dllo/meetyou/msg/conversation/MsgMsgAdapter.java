package com.zrj.dllo.meetyou.msg.conversation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.CommonViewHolder;
import com.zrj.dllo.meetyou.msg.chat.ChatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/11/28.
 */

public class MsgMsgAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private Context mContext;
    List<EMConversation> mEMConversations;

    public MsgMsgAdapter(Context context) {
        mContext = context;
    }

    public void setEMConversations(List<EMConversation> EMConversations) {
        mEMConversations = EMConversations;
        notifyDataSetChanged();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.getViewHolder(parent, R.layout.item_conversation);
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {
        final String userName = mEMConversations.get(position).getUserName();
        final TextView countTv = holder.getView(R.id.conversation_count_tv);
        // 设置用户名
        // 先在bomb上查询是否设置昵称
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.addWhereEqualTo("uName", userName);
        bmobQuery.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                if (e == null) {
                    String realName = list.get(0).getRealName();
                    if (realName == null) {
                        realName = userName;
//                        holder.setText(R.id.conversation_name_tv, realName);
                    }
                        holder.setText(R.id.conversation_name_tv, realName);

                    // 设置头像
                    Glide.with(mContext).load(list.get(0).getUserImgUrl()).into((ImageView) holder.getView(R.id.conversation_avatar));
                }
            }
        });


        // 设置会话信息
        if (getMessage(mEMConversations.get(position).getLastMessage()).length() > 15) {
            holder.setText(R.id.conversation_body_tv, new StringBuffer(getMessage(mEMConversations.get(position).getLastMessage())).substring(0, 15) + "...");
        } else {
            holder.setText(R.id.conversation_body_tv, getMessage(mEMConversations.get(position).getLastMessage()));
        }

        // 时间转换
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(mEMConversations.get(position).getLastMessage().getMsgTime());
        // 设置时间
        holder.setText(R.id.conversation_time_tv, time);
        // 设置未读消息的小红点
        if (mEMConversations.get(position).getUnreadMsgCount() > 0) {
            countTv.setVisibility(View.VISIBLE);
            if (mEMConversations.get(position).getUnreadMsgCount() > 9) {
                holder.setText(R.id.conversation_count_tv, "9+");
            } else {
                holder.setText(R.id.conversation_count_tv, String.valueOf(mEMConversations.get(position).getUnreadMsgCount()));
            }
        }
        // bmob查询头像
//        bmobQuery.addWhereEqualTo("uName", userName);
//        bmobQuery.findObjects(new FindListener<Person>() {
//            @Override
//            public void done(List<Person> list, BmobException e) {
//                if (e == null) {
//                    Glide.with(mContext).load(list.get(0).getUserImgUrl()).into((ImageView) holder.getView(R.id.conversation_avatar));
//                }
//            }
//        });

        // 点击item进入聊天页面
        holder.setItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("userName", userName);
                mContext.startActivity(intent);
                mEMConversations.get(position).markAllMessagesAsRead();
                countTv.setVisibility(View.GONE);
            }
        });
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
