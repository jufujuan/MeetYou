package com.zrj.dllo.meetyou.msg.conversation;

import android.content.Context;
import android.os.Vibrator;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/11/26.
 */
public class MsgMsgFragment extends AbsBaseFragment {

    private RecyclerView msgMsgRv;
    private List<EMConversation> mConversations;
    private MsgMsgAdapter mAdapter;
    private Vibrator vibrator;

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
        mConversations.addAll(loadConversation());

        mAdapter = new MsgMsgAdapter(context);
        mAdapter.setEMConversations(mConversations);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        msgMsgRv.setAdapter(mAdapter);
        msgMsgRv.setLayoutManager(manager);

        // 震动初始化
        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        // 接收消息的监听
        EMMessageListener msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                mConversations.clear();
                mConversations.addAll(loadConversation());
//                Log.d("MsgMsgFragment", "mConversations:" + mConversations);
                mAdapter.setEMConversations(mConversations);
//                mAdapter.notifyDataSetChanged();
                long [] pattern = {100,400,100,400};
                vibrator.vibrate(pattern,-1);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };

        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }


    /**
     * 加载所有的会话
     * @return
     */
    private Collection<? extends EMConversation> loadConversation () {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();

        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized(conversations){
            for(EMConversation conversation : conversations.values()){
                if(conversation.getAllMessages().size() != 0){
                    sortList.add(new Pair<Long, EMConversation>
                            (conversation.getLastMessage().getMsgTime(), conversation)
                    );
                }
            }
        }

        try{
            sortConversationByLastChatTime(sortList);
        }catch(Exception e){
            e.printStackTrace();
        }

        List<EMConversation> list = new ArrayList<EMConversation>();
        for(Pair<Long, EMConversation> sortItem : sortList){
            list.add(sortItem.second);
        }

        return list;
    }

    /**
     * 根据最后一条消息的时间排序
     * @param sortList
     */
    private void sortConversationByLastChatTime(
            List<Pair<Long, EMConversation>> sortList) {
        Collections.sort(sortList, new Comparator<Pair<Long, EMConversation>>(){

            @Override
            public int compare(Pair<Long, EMConversation> con1,
                               Pair<Long, EMConversation> con2) {
                if(con1.first == con2.first){
                    return 0;
                }else if(con2.first > con1.first){
                    return 1;
                }else{
                    return -1;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        mConversations.addAll(loadConversation());
        mAdapter.setEMConversations(mConversations);
    }

    @Override
    public void onStop() {
        super.onStop();
        vibrator.cancel();
    }
}
