package com.zrj.dllo.meetyou.msg.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/12/1.
 * 聊天页面
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
    private EMConversation mConversation;
    private List<EMMessage> mMessageList;
    private Vibrator vibrator;
    private EMMessageListener mMsgListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());

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
        // 获取传过来的用户名
        Intent intent = getIntent();
        mUserName = intent.getStringExtra("userName");
        msgChatNameTv.setText(mUserName);

        mAdapter = new MsgChatAdapter(this);
        // 根据用户名获取聊天记录
        mConversation = EMClient.getInstance().chatManager().getConversation(mUserName);
        mMessages = new ArrayList<>();

        if (mConversation != null) {
            //获取此会话的所有消息
            mMessages = mConversation.getAllMessages();
            //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
            //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
//            mMessageList = mConversation.loadMoreMsgFromDB(mMessages.get(mMessages.size() - 1).getMsgId(), 10);
//            mMessageList.addAll(mMessages);

            mAdapter.setEMMessages(mMessages);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            msgChatRv.setAdapter(mAdapter);
            msgChatRv.setLayoutManager(manager);
            msgChatRv.smoothScrollToPosition(mMessages.size());
        }


        // 震动初始化
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        // 注册消息监听
        mMsgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                for (final EMMessage message : messages) {
                    if (mUserName.equals(message.getFrom()) || mUserName.equals(message.getTo())) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mMessages.add(message);
                                mAdapter.setEMMessages(mMessages);
                                msgChatRv.smoothScrollToPosition(mMessages.size());
                            }
                        });

                    }
                }
                long[] pattern = {100, 400, 100, 400};
                vibrator.vibrate(pattern, -1);

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

        EMClient.getInstance().chatManager().addMessageListener(mMsgListener);


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
                    // 创建一个消息
                    EMMessage message = EMMessage.createTxtSendMessage(content, mUserName);

                    mMessages.add(message);

                    mAdapter.setEMMessages(mMessages);
                    msgChatRv.smoothScrollToPosition(mMessages.size());
                    // 调用发送消息的方法
                    EMClient.getInstance().chatManager().sendMessage(message);
//                     为消息设置回调
                    message.setMessageStatusCallback(new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            // 消息发送成功
                        }

                        @Override
                        public void onError(int i, String s) {
                            // 消息发送失败
                            Toast.makeText(ChatActivity.this, "发送失败, 请重新发送", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mConversation.markAllMessagesAsRead();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mMsgListener);
        vibrator.cancel();
    }
}
