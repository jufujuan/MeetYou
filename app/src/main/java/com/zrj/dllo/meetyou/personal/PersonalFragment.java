package com.zrj.dllo.meetyou.personal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.tools.BitmapBlurUtils;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.editor.EditorActivity;
import com.zrj.dllo.meetyou.entity.EventBusBean;
import com.zrj.dllo.meetyou.login.LoginActivity;
import com.zrj.dllo.meetyou.tools.StaticValues;
import com.zrj.dllo.meetyou.tools.UrlChangeBitmap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.bmob.v3.BmobUser;

/**
 * Created by ${ZhaoXuancheng} on 16/11/21.
 */

public class PersonalFragment extends AbsBaseFragment implements View.OnClickListener {
    private ImageView pullImg;
    private ImageView mNightImage;
    private ImageView mImageViewLogin;
    private Bitmap mBmp;
    private Resources mRes;
    private TextView mTextViewUsername;
    private RelativeLayout mRelativeLayoutEscLogin;
    private TextView mTextViewEdtor;
    private String mImgUrl;
    private String mUserName;
    private SharedPreferences mPreferences;


    /**
     * 绑定布局
     *
     * @return 将布局返回
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_personal;
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initView() {
        pullImg = bindView(R.id.pull_img);
        mNightImage = bindView(R.id.personal_night_iv);
        mNightImage.setOnClickListener(this);
        mImageViewLogin = bindView(R.id.personal_login_image);
        mImageViewLogin.setOnClickListener(this);
        mTextViewUsername = bindView(R.id.personal_username_tv);
        mRelativeLayoutEscLogin = bindView(R.id.personal_esc_login_rl);
        mRelativeLayoutEscLogin.setOnClickListener(this);
        mTextViewEdtor = bindView(R.id.personal_editor_tv);
        mTextViewEdtor.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initDatas() {
        SharedPreferences preferences = context.getSharedPreferences("userMessage", Activity.MODE_PRIVATE);
        mUserName = preferences.getString("userName", "");
        mImgUrl = preferences.getString(StaticValues.SP_USEING_IMG_URL_COLUMN, "4869");
        mTextViewUsername.setText(mUserName);
        Log.d("5566", mImgUrl);

        EventBus.getDefault().register(this);

        mRes = getResources();
        mBmp = BitmapFactory.decodeResource(mRes, R.mipmap.default_head);

        Log.d("hjhjhj", "NIHAO");

        if (!mImgUrl.equals("4869")) {
            ImgAsync imgAsync = new ImgAsync();
            imgAsync.execute();
            Glide.with(this).load(mImgUrl).into(mImageViewLogin);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_night_iv:
                SharedPreferences sharedPreferences = context.getSharedPreferences("night", 0);
                sharedPreferences.edit().putBoolean("isFragment", false).commit();
                Intent intent1 = new Intent("night");
                context.sendBroadcast(intent1);
                break;
            case R.id.personal_login_image:
                break;
            case R.id.personal_esc_login_rl:
                logOut();
                mPreferences = context.getSharedPreferences("userMessage", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.clear();
                Intent intent3 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent3);
                editor.commit();
                BmobUser.logOut();   //清除缓存用户对象
                getActivity().finish();
                Toast.makeText(context, "已退出登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.personal_editor_tv:
                Intent intent2 = new Intent(getActivity(), EditorActivity.class);
                startActivity(intent2);
                break;
        }
    }

    //登录后执行的方法
    @Subscribe
    public void getLoginEvent(EventBusBean eventBusBean) {

        Log.d("nihaoma", "hehe");

        mImgUrl = mPreferences.getString(StaticValues.SP_USEING_IMG_URL_COLUMN, "888888");
        Log.d("nihaoma", mImgUrl);
        if (!mImgUrl.equals("888888")) {
            Glide.with(this).load(mImgUrl).into(mImageViewLogin);
            ImgAsync imgAsync = new ImgAsync();
            imgAsync.execute();
        } else {
            mBmp = BitmapFactory.decodeResource(mRes, R.mipmap.default_head);
            //TODO Handler目前这种写法 可能会导致短期的内存泄露
            //后期需要修改
            BitmapBlurUtils.addTask(mBmp, new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Drawable drawable = (Drawable) msg.obj;
                    pullImg.setImageDrawable(drawable);
                }
            });
            mImageViewLogin.setImageBitmap(mBmp);
        }

//       mPreferences.edit().putString(StaticValues.SP_USEING_NAME_COLUMN,eventBusBean.getUsername()).commit();
        mTextViewUsername.setText(mPreferences.getString(StaticValues.SP_USEING_NAME_COLUMN, "NI"));
    }

    /**
     * 登出
     * 异步
     */
    private void logOut() {
        EMClient.getInstance().logout(false, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.d("LoginActivity", "退出登录成功");
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub

            }
        });
    }

    class ImgAsync extends AsyncTask<String, String, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            mBmp = UrlChangeBitmap.getBitMBitmap(mImgUrl);
            return mBmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //TODO Handler目前这种写法 可能会导致短期的内存泄露
            //后期需要修改
            BitmapBlurUtils.addTask(mBmp,
                    new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Drawable drawable = (Drawable) msg.obj;
                            pullImg.setImageDrawable(drawable);
                        }
                    });
            mImageViewLogin.setImageBitmap(mBmp);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
