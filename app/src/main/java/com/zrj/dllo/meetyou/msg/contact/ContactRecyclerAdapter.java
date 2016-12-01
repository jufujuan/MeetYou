package com.zrj.dllo.meetyou.msg.contact;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.CommonViewHolder;
import com.zrj.dllo.meetyou.msg.chat.ChatActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by REN - the most cool programmer all over the world
 * on 16/11/26.
 */

public class ContactRecyclerAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private List<ContactBean> mContactBeen = new ArrayList<>();
    private Context mContext;

    public ContactRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setContactBeen(List<ContactBean> contactBeen) {
        mContactBeen = contactBeen;
    }


    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.getViewHolder(parent, R.layout.item_contact);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        final ContactBean contactBean = mContactBeen.get(position);
        // 获取首字母的ascii值
        int selection = contactBean.getFirstPinYin().charAt(0);
        // 判断是否显示首字母
        int positionForSelection = getPositionForSelection(selection);
        if (position == positionForSelection) {// 相等说明需要显示字母
            holder.getView(R.id.item_contact_tag_tv).setVisibility(View.VISIBLE);
            holder.setText(R.id.item_contact_tag_tv, contactBean.getFirstPinYin());
        } else {
            holder.getView(R.id.item_contact_tag_tv).setVisibility(View.GONE);
        }
        holder.setText(R.id.msg_contact_name_tv, contactBean.getName());

        holder.setItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("userName", contactBean.getName());
                mContext.startActivity(intent);
            }
        });

    }

    public int getPositionForSelection(int selection) {
        for (int i = 0; i < mContactBeen.size(); i++) {
            String Fpinyin = mContactBeen.get(i).getFirstPinYin();
            char first = Fpinyin.toUpperCase().charAt(0);
            if (first == selection) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mContactBeen == null ? 0 : mContactBeen.size();
    }
}
