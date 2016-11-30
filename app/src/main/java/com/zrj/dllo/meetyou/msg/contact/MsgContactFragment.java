package com.zrj.dllo.meetyou.msg.contact;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/11/26.
 */
public class MsgContactFragment extends AbsBaseFragment {

    private RecyclerView msgContactRv;
    private TextView contactCenterTv;
    private SideBar contactSidebar;
    private ContactRecyclerAdapter mContactRecyclerAdapter;
    private Handler mHandler;
    private List<String> mUserNames;
    private List<ContactBean> mData;


    @Override
    protected int getLayout() {
        return R.layout.fragment_msg_contact;
    }

    @Override
    protected void initView() {
        msgContactRv = bindView(R.id.msg_contact_rv);
        contactCenterTv = bindView(R.id.contact_center_tv);
        contactSidebar = bindView(R.id.contact_sidebar);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void initDatas() {
        contactSidebar.setTextView(contactCenterTv);
        mContactRecyclerAdapter = new ContactRecyclerAdapter();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mUserNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mData = getData(mUserNames.toArray(new String[mUserNames.size()]));
                            Collections.sort(mData, new PinyinComparator());
                            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                            Log.d("MsgContactFragment", "data:" + mData);
                            mContactRecyclerAdapter.setContactBeen(mData);
                            msgContactRv.setAdapter(mContactRecyclerAdapter);
                            msgContactRv.setLayoutManager(manager);
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 设置字母索引触摸监听
        contactSidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                Log.d("MsgContactFragment", "mContactRecyclerAdapter:" + mContactRecyclerAdapter);
                int position = mContactRecyclerAdapter.getPositionForSelection(s.charAt(0));
                if (position != -1) {
                    // 将选中字母对应的item滑到最上,不知道是不是这个方法
                    msgContactRv.scrollToPosition(position);
                }
            }
        });
    }


    private List<ContactBean> getData(String[] data) {
        List<ContactBean> arrayList = new ArrayList<ContactBean>();
        for (int i = 0; i < data.length; i++) {
            String pinyin = PinYinUtils.getPingYin(data[i]);
            String Fpinyin = pinyin.substring(0, 1).toUpperCase();

            ContactBean contactBean = new ContactBean();
            contactBean.setName(data[i]);
            contactBean.setPinYin(pinyin);
            // 正则表达式，判断首字母是否是英文字母
            if (Fpinyin.matches("[A-Z]")) {
                contactBean.setFirstPinYin(Fpinyin);
            } else {
                contactBean.setFirstPinYin("#");
            }

            arrayList.add(contactBean);
        }
        return arrayList;

    }
}
