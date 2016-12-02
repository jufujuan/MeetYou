package com.zrj.dllo.meetyou.msg.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.OnNmeaMessageListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;

import java.util.List;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/12/1.
 */
public class ChatActivity extends AbsBaseActivity implements View.OnClickListener {

    private LinearLayout msgChatBack;
    private TextView msgChatNameTv;
    private EditText msgChatEt;
    private Button chatSendBtn;
    private RecyclerView msgChatRv;
    private String mUserName;
    private List<EMMessage> mMessages;
    private MsgChatAdapter mAdapter;
    //    private NewMessageBroadcastReceiver msgReceiver;

    @Override
    protected int getLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView() {
        msgChatRv = bindView(R.id.msg_chat_rv);
        msgChatBack = bindView(R.id.msg_chat_back);
        msgChatNameTv = bindView(R.id.msg_chat_name_tv);
        msgChatEt = bindView(R.id.msg_chat_et);
        chatSendBtn = bindView(R.id.chat_send_btn);

        setClickListener(this, msgChatBack, chatSendBtn);
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        mUserName = intent.getStringExtra("userName");
        msgChatNameTv.setText(mUserName);

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(mUserName);
        //获取此会话的所有消息
        mMessages = conversation.getAllMessages();
        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
//        List<EMMessage> messageList = conversation.loadMoreMsgFromDB(startMsgId, pagesize);

        mAdapter = new MsgChatAdapter();
        mAdapter.setEMMessages(mMessages);

        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setStackFromEnd(true);
        msgChatRv.setAdapter(mAdapter);
        msgChatRv.setLayoutManager(manager);
        msgChatRv.smoothScrollToPosition(mMessages.size());


        EMMessageListener msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                for (EMMessage message : messages) {
                    if (mUserName.equals(message.getFrom())) {
                        mMessages.add(message);
                        mAdapter.setEMMessages(mMessages);
                        msgChatRv.smoothScrollToPosition(mMessages.size());
                    }
                }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msg_chat_back:
                finish();
                break;
            case R.id.chat_send_btn:
                String content = msgChatEt.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    msgChatEt.setText("");
                    EMMessage message = EMMessage.createTxtSendMessage(content, mUserName);
                    mMessages.add(message);
                    mAdapter.setEMMessages(mMessages);
                    msgChatRv.smoothScrollToPosition(mMessages.size());
                    // 将新的消息内容和时间加入到下边
//                    mContentText.setText(mContentText.getText() + "\n发送：" + content + " - time: " + message.getMsgTime());
                    // 调用发送消息的方法
                    EMClient.getInstance().chatManager().sendMessage(message);
                    // 为消息设置回调
                    message.setMessageStatusCallback(new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            // 消息发送成功，打印下日志，正常操作应该去刷新ui
                            Log.i("lzan13", "send message on success");
                        }

                        @Override
                        public void onError(int i, String s) {
                            // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
                            Log.i("lzan13", "send message on error " + i + " - " + s);
                        }

                        @Override
                        public void onProgress(int i, String s) {
                            // 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
                        }
                    });
                }
                break;
        }
    }




//    private class NewMessageBroadcastReceiver extends BroadcastReceiver{
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // 注销广播
//            abortBroadcast();
//
//            // 消息id（每条消息都会生成唯一的一个id，目前是SDK生成）
//            String msgId = intent.getStringExtra("msgid");
//            //发送方
//            String username = intent.getStringExtra("from");
//            // 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
//            EMMessage message = EMChatManager.getInstance().getMessage(msgId);
//
//            if (!username.equals(username)) {
//                // 消息不是发给当前会话，return
//                return;
//            }
//
////            conversation.addMessage(message);
////            adapter.notifyDataSetChanged();
////            listView.setAdapter(adapter);
////            listView.setSelection(listView.getCount() - 1);
//        }
//    }


}
