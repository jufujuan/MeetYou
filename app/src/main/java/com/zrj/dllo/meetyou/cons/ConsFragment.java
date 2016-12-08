package com.zrj.dllo.meetyou.cons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.editor.EditorActivity;
import com.zrj.dllo.meetyou.eventbus.EventBusBean;
import com.zrj.dllo.meetyou.tools.StaticValues;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by ${ZhaoXuancheng} on 16/12/6.
 */

public class ConsFragment extends AbsBaseFragment implements View.OnClickListener {
    private final static int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    private final static String[] constellationArr = new String[]{"摩羯", "水瓶", "双鱼", "白羊", "金牛", "双子", "巨蟹", "狮子", "处女", "天秤", "天蝎", "射手", "摩羯"};
    private PopupWindow mMPopWindow;
    private TextView mTextViewGrage1;
    private TextView mTextViewGrage2;
    private TextView mTextViewGrage3;
    private TextView mTextViewGrage4;

    private TextView mTextViewContent;
    private TextView mTextView;
    private ImageView mImageViewMe;
    private ImageView mImageViewHe;
    private TextView mBtnStart;
    private TextView mTextViewCons;
    private String mCons;
    private String mHeCons = "巨蟹";
    private int mMonth;
    private int mDay;
    private SharedPreferences mPreferences;


    @Override
    protected int getLayout() {
        return R.layout.fragment_cons;
    }

    @Override
    protected void initView() {
        mTextViewGrage1 = bindView(R.id.cons_grade1_tv);
        mTextViewGrage2 = bindView(R.id.cons_grade2_tv);
        mTextViewGrage3 = bindView(R.id.cons_grade3_tv);
        mTextViewGrage4 = bindView(R.id.cons_grade4_tv);
        mTextViewContent = bindView(R.id.cons_content_tv);
        mTextView = bindView(R.id.cons_tv);
        mImageViewMe = bindView(R.id.cons_me_img);
        mImageViewHe = bindView(R.id.cons_he_img);
        mBtnStart = bindView(R.id.cons_start_btn);
        mBtnStart.setOnClickListener(this);
        mTextView.setOnClickListener(this);
        mTextViewCons = bindView(R.id.cons_start_bottom_btn);
        TextView textViewIntentEditor = bindView(R.id.cons_me_cons_tv);
        textViewIntentEditor.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        EventBus.getDefault().register(this);
        mPreferences = context.getSharedPreferences(StaticValues.SP_USEING_TABLE_NAME, Context.MODE_PRIVATE);
        mMonth = mPreferences.getInt(StaticValues.SP_USEING_MOUTH_COLUMN, 12);
        mDay = mPreferences.getInt(StaticValues.SP_USEING_DAY_COLUMN, 15);
        Log.d("tttttt", "month:" + mMonth);
        Log.d("tttttt", "day:" + mDay);

        mCons = getConstellation(mMonth, mDay);
//       mCons = "射手";
        mTextViewCons.setText(mCons + ":" + mHeCons);
        EventBus.getDefault().post(new EventBusBean());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cons_start_btn:

                HttpUtil.getTest(mCons, mHeCons, new ResponseCallBack<ConsBean>() {
                    @Override
                    public void onResponse(ConsBean testBean) {
//                        mTextViewGrage.setText(testBean.getNewslist().get(0).getGrade());
                        mTextViewContent.setText(testBean.getNewslist().get(0).getContent());

                        String content = testBean.getNewslist().get(0).getGrade();
                        Log.d("ConsFragment", content);
                        StringBuffer buffer = new StringBuffer(content);
                        int pos = buffer.indexOf("：");
                        int pos1 = buffer.indexOf("：", pos + 1);
                        String content1 = content.substring(0, pos1 - 2);
                        int pos2 = buffer.indexOf("：", pos1 + 1);
                        Log.d("ConsFragment", content1);
                        String content2 = content.substring(pos1 - 2, pos2 - 2);
                        int pos3 = buffer.indexOf("：", pos2 + 1);
                        Log.d("ConsFragment", content2);
                        String content3 = content.substring(pos2 - 2, pos3 - 2);
                        int pos4 = buffer.indexOf("：", pos3 + 1);
                        Log.d("ConsFragment", content3);
                        String content4 = content.substring(pos3 - 2, content.length());
                        mTextViewGrage1.setText(content1);
                        mTextViewGrage2.setText(content2);
                        mTextViewGrage3.setText(content3);
                        mTextViewGrage4.setText(content4);
                    }

                    @Override
                    public void onError(Exception exception) {
                        Toast.makeText(context, "网络不稳定", Toast.LENGTH_SHORT).show();
                        Log.d("ConsFragment", "exception:" + exception);
                    }
                });

                break;
            case R.id.cons_tv:
                showPopupWindow();
                break;
            case R.id.pop_aquarius:
                mMPopWindow.dismiss();
                mHeCons = "水瓶";
                Bitmap bitmapAquarius = BitmapFactory.decodeResource(getResources(), R.mipmap.shuiping1);
                mImageViewHe.setImageBitmap(bitmapAquarius);
                mTextViewCons.setText(mCons + ":" + mHeCons);
                break;
            case R.id.pop_pisces:

                mMPopWindow.dismiss();
                mHeCons = "双鱼";
                Bitmap bitmapPisces = BitmapFactory.decodeResource(getResources(), R.mipmap.shuangyu1);
                mImageViewHe.setImageBitmap(bitmapPisces);
                mTextViewCons.setText(mCons + ":" + mHeCons);
                break;
            case R.id.pop_aries:
                mMPopWindow.dismiss();
                mHeCons = "白羊";
                Bitmap bitmapAries = BitmapFactory.decodeResource(getResources(), R.mipmap.baiyang1);
                mImageViewHe.setImageBitmap(bitmapAries);
                mTextViewCons.setText(mCons + ":" + mHeCons);
                break;
            case R.id.pop_taurus:
                mHeCons = "金牛";
                mMPopWindow.dismiss();
                Bitmap bitmapTaurus = BitmapFactory.decodeResource(getResources(), R.mipmap.jinniu1);
                mImageViewHe.setImageBitmap(bitmapTaurus);
                mTextViewCons.setText(mCons + ":" + mHeCons);

                break;
            case R.id.pop_gemini:
                mHeCons = "双子";
                mMPopWindow.dismiss();
                Bitmap bitmapGemini = BitmapFactory.decodeResource(getResources(), R.mipmap.shuangzi1);
                mImageViewHe.setImageBitmap(bitmapGemini);
                mTextViewCons.setText(mCons + ":" + mHeCons);
                break;
            case R.id.pop_cancer:
                mHeCons = "巨蟹";
                mMPopWindow.dismiss();
                Bitmap bitmapCancer = BitmapFactory.decodeResource(getResources(), R.mipmap.juxie1);
                mImageViewHe.setImageBitmap(bitmapCancer);
                mTextViewCons.setText(mCons + ":" + mHeCons);
                break;
            case R.id.pop_leo:
                mHeCons = "狮子";
                mMPopWindow.dismiss();
                Bitmap bitmapLeo = BitmapFactory.decodeResource(getResources(), R.mipmap.shizi1);
                mImageViewHe.setImageBitmap(bitmapLeo);
                mTextViewCons.setText(mCons + ":" + mHeCons);
                break;
            case R.id.pop_virgo:
                mHeCons = "处女";
                mMPopWindow.dismiss();
                Bitmap bitmapVirgo = BitmapFactory.decodeResource(getResources(), R.mipmap.chunv1);
                mImageViewHe.setImageBitmap(bitmapVirgo);
                mTextViewCons.setText(mCons + ":" + mHeCons);
                break;
            case R.id.pop_libra:
                mHeCons = "天秤";
                mMPopWindow.dismiss();
                Bitmap bitmapLibra = BitmapFactory.decodeResource(getResources(), R.mipmap.tiancheng1);
                mImageViewHe.setImageBitmap(bitmapLibra);
                mTextViewCons.setText(mCons + ":" + mHeCons);
                break;
            case R.id.pop_scorpio:
                mHeCons = "天蝎";
                mMPopWindow.dismiss();
                Bitmap bitmapScorpio = BitmapFactory.decodeResource(getResources(), R.mipmap.tianxie1);
                mImageViewHe.setImageBitmap(bitmapScorpio);
                mTextViewCons.setText(mCons + ":" + mHeCons);
                break;
            case R.id.pop_sagittarius:
                mHeCons = "射手";
                mMPopWindow.dismiss();
                Bitmap bitmapSagittarius = BitmapFactory.decodeResource(getResources(), R.mipmap.sheshou1);
                mImageViewHe.setImageBitmap(bitmapSagittarius);
                mTextViewCons.setText(mCons + ":" + mHeCons);
                break;
            case R.id.pop_capricorn:
                mHeCons = "摩羯";
                mMPopWindow.dismiss();
                Bitmap bitmapCapricorn = BitmapFactory.decodeResource(getResources(), R.mipmap.mojie1);
                mImageViewHe.setImageBitmap(bitmapCapricorn);
                mTextViewCons.setText(mCons + ":" + mHeCons);
                break;
            case R.id.pop_esc:
                mMPopWindow.dismiss();
                break;
            case R.id.cons_me_cons_tv:
                Intent intent = new Intent(context, EditorActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(context).inflate(R.layout.popuplayout, null);
        mMPopWindow = new PopupWindow(contentView,
                1300, 900, true);
        mMPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        TextView aquariusTv = (TextView) contentView.findViewById(R.id.pop_aquarius);
        TextView piscesTv = (TextView) contentView.findViewById(R.id.pop_pisces);
        TextView ariesTv = (TextView) contentView.findViewById(R.id.pop_aries);
        TextView taurusTv = (TextView) contentView.findViewById(R.id.pop_taurus);
        TextView geminiTv = (TextView) contentView.findViewById(R.id.pop_gemini);
        TextView cancerTv = (TextView) contentView.findViewById(R.id.pop_cancer);
        TextView leoTv = (TextView) contentView.findViewById(R.id.pop_leo);
        TextView virgoTv = (TextView) contentView.findViewById(R.id.pop_virgo);
        TextView libraTv = (TextView) contentView.findViewById(R.id.pop_libra);
        TextView scorpioTv = (TextView) contentView.findViewById(R.id.pop_scorpio);
        TextView sagittariusTv = (TextView) contentView.findViewById(R.id.pop_sagittarius);
        TextView capricornTv = (TextView) contentView.findViewById(R.id.pop_capricorn);
        TextView escTv = (TextView) contentView.findViewById(R.id.pop_esc);

        aquariusTv.setOnClickListener(this);
        piscesTv.setOnClickListener(this);
        ariesTv.setOnClickListener(this);
        taurusTv.setOnClickListener(this);
        geminiTv.setOnClickListener(this);
        cancerTv.setOnClickListener(this);
        leoTv.setOnClickListener(this);
        virgoTv.setOnClickListener(this);
        libraTv.setOnClickListener(this);
        scorpioTv.setOnClickListener(this);
        sagittariusTv.setOnClickListener(this);
        capricornTv.setOnClickListener(this);
        escTv.setOnClickListener(this);

        //显示PopupWindow
        View rootview = LayoutInflater.from(context).inflate(R.layout.fragment_cons, null);
        mMPopWindow.showAtLocation(rootview, Gravity.START, 150, -230);
    }

    public static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }

    @Subscribe
    public void getLoginEvent(EventBusBean eventBusBean) {

        mMonth = mPreferences.getInt(StaticValues.SP_USEING_MOUTH_COLUMN, 12);
        mDay = mPreferences.getInt(StaticValues.SP_USEING_DAY_COLUMN, 15);
        mCons = getConstellation(mMonth, mDay);
        mTextViewCons.setText(mCons + ":" + mHeCons);
        Log.d("ConsFragment", "GAIBIAN");
        Toast.makeText(context, "更新我的星座成功", Toast.LENGTH_SHORT).show();

        switch (mCons) {
            case "水瓶":
                Bitmap bitmapAquarius = BitmapFactory.decodeResource(getResources(), R.mipmap.shuiping1);
                mImageViewMe.setImageBitmap(bitmapAquarius);
                break;
            case "双鱼":
                Bitmap bitmapPisces = BitmapFactory.decodeResource(getResources(), R.mipmap.shuangyu1);
                mImageViewMe.setImageBitmap(bitmapPisces);
                break;
            case "白羊":
                Bitmap bitmapAries = BitmapFactory.decodeResource(getResources(), R.mipmap.baiyang1);
                mImageViewMe.setImageBitmap(bitmapAries);
                break;
            case "金牛":
                Bitmap bitmapTaurus = BitmapFactory.decodeResource(getResources(), R.mipmap.jinniu1);
                mImageViewMe.setImageBitmap(bitmapTaurus);
                break;
            case "双子":
                Bitmap bitmapGemini = BitmapFactory.decodeResource(getResources(), R.mipmap.shuangzi1);
                mImageViewMe.setImageBitmap(bitmapGemini);
                break;
            case "巨蟹":
                Bitmap bitmapCancer = BitmapFactory.decodeResource(getResources(), R.mipmap.juxie1);
                mImageViewMe.setImageBitmap(bitmapCancer);
                break;
            case "狮子":
                Bitmap bitmapLeo = BitmapFactory.decodeResource(getResources(), R.mipmap.shizi1);
                mImageViewMe.setImageBitmap(bitmapLeo);
                break;
            case "处女":
                Bitmap bitmapVirgo = BitmapFactory.decodeResource(getResources(), R.mipmap.chunv1);
                mImageViewMe.setImageBitmap(bitmapVirgo);
                break;
            case "天秤":
                Bitmap bitmapLibra = BitmapFactory.decodeResource(getResources(), R.mipmap.tiancheng1);
                mImageViewMe.setImageBitmap(bitmapLibra);
                break;
            case "天蝎":
                Bitmap bitmapScorpio = BitmapFactory.decodeResource(getResources(), R.mipmap.tianxie1);
                mImageViewMe.setImageBitmap(bitmapScorpio);
                break;
            case "射手":
                Bitmap bitmapSagittarius = BitmapFactory.decodeResource(getResources(), R.mipmap.sheshou1);
                mImageViewMe.setImageBitmap(bitmapSagittarius);
                break;
            case "摩羯":
                Bitmap bitmapCapricorn = BitmapFactory.decodeResource(getResources(), R.mipmap.mojie1);
                mImageViewMe.setImageBitmap(bitmapCapricorn);
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}