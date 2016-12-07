package com.zrj.dllo.meetyou.cons;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

/**
 * Created by ${ZhaoXuancheng} on 16/12/6.
 */

public class ConsFragment extends AbsBaseFragment implements View.OnClickListener {
    private final static int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    private final static String[] constellationArr = new String[]{"摩羯", "水瓶", "双鱼", "白羊", "金牛", "双子", "巨蟹", "狮子", "处女", "天秤", "天蝎", "射手", "摩羯"};
    private PopupWindow mMPopWindow;
    private TextView mTextViewGrage;
    private TextView mTextViewContent;
    private TextView mTextView;
    private ImageView mImageViewMe;
    private ImageView mImageViewHe;
    private TextView mBtnStart;
    private TextView mTextViewCons;

    @Override
    protected int getLayout() {
        return R.layout.fragment_cons;
    }

    @Override
    protected void initView() {
        mTextViewGrage = bindView(R.id.cons_grade_tv);
        mTextViewContent = bindView(R.id.cons_content_tv);
        mTextView = bindView(R.id.cons_tv);
        mImageViewMe = bindView(R.id.cons_me_img);
        mImageViewHe = bindView(R.id.cons_he_img);
        mBtnStart = bindView(R.id.cons_start_btn);
        mBtnStart.setOnClickListener(this);
        mTextView.setOnClickListener(this);
        mTextViewCons = bindView(R.id.cons_start_bottom_btn);
    }

    @Override
    protected void initDatas() {
        getConstellation(10, 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cons_start_btn:

                HttpUtil.getTest(1, new ResponseCallBack<ConsBean>() {
                    @Override
                    public void onResponse(ConsBean testBean) {
                        mTextViewGrage.setText(testBean.getNewslist().get(0).getGrade());
                        mTextViewContent.setText(testBean.getNewslist().get(0).getContent());
                        mTextViewCons.setText(testBean.getNewslist().get(0).getTitle());
                    }

                    @Override
                    public void onError(Exception exception) {
                        Toast.makeText(context, "网络不稳定", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.cons_tv:
                showPopupWindow();
                break;
            case R.id.pop_aquarius:
                mMPopWindow.dismiss();
                Bitmap bitmapAquarius = BitmapFactory.decodeResource(getResources(), R.mipmap.shuoping);
                mImageViewHe.setImageBitmap(bitmapAquarius);
                break;
            case R.id.pop_pisces:
                mMPopWindow.dismiss();
                Bitmap bitmapPisces = BitmapFactory.decodeResource(getResources(), R.mipmap.shuangyu);
                mImageViewHe.setImageBitmap(bitmapPisces);
                break;
            case R.id.pop_aries:
                mMPopWindow.dismiss();
                Bitmap bitmapAries = BitmapFactory.decodeResource(getResources(), R.mipmap.baiyang);
                mImageViewHe.setImageBitmap(bitmapAries);
                break;
            case R.id.pop_taurus:
                mMPopWindow.dismiss();
                Bitmap bitmapTaurus = BitmapFactory.decodeResource(getResources(), R.mipmap.jinniu);
                mImageViewHe.setImageBitmap(bitmapTaurus);
                break;
            case R.id.pop_gemini:
                mMPopWindow.dismiss();
                Bitmap bitmapGemini = BitmapFactory.decodeResource(getResources(), R.mipmap.shuangzi);
                mImageViewHe.setImageBitmap(bitmapGemini);
                break;
            case R.id.pop_cancer:
                mMPopWindow.dismiss();
                Bitmap bitmapCancer = BitmapFactory.decodeResource(getResources(), R.mipmap.juxie);
                mImageViewHe.setImageBitmap(bitmapCancer);
                break;
            case R.id.pop_leo:
                mMPopWindow.dismiss();
                Bitmap bitmapLeo = BitmapFactory.decodeResource(getResources(), R.mipmap.shizi);
                mImageViewHe.setImageBitmap(bitmapLeo);
                break;
            case R.id.pop_virgo:
                mMPopWindow.dismiss();
                Bitmap bitmapVirgo = BitmapFactory.decodeResource(getResources(), R.mipmap.chunv);
                mImageViewHe.setImageBitmap(bitmapVirgo);
                break;
            case R.id.pop_libra:
                mMPopWindow.dismiss();
                Bitmap bitmapLibra = BitmapFactory.decodeResource(getResources(), R.mipmap.tiancheng);
                mImageViewHe.setImageBitmap(bitmapLibra);
                break;
            case R.id.pop_scorpio:
                mMPopWindow.dismiss();
                Bitmap bitmapScorpio = BitmapFactory.decodeResource(getResources(), R.mipmap.tianxie);
                mImageViewHe.setImageBitmap(bitmapScorpio);
                break;
            case R.id.pop_sagittarius:
                mMPopWindow.dismiss();
                Bitmap bitmapSagittarius = BitmapFactory.decodeResource(getResources(), R.mipmap.sheshou);
                mImageViewHe.setImageBitmap(bitmapSagittarius);
                break;
            case R.id.pop_capricorn:
                mMPopWindow.dismiss();
                Bitmap bitmapCapricorn = BitmapFactory.decodeResource(getResources(), R.mipmap.mojie);
                mImageViewHe.setImageBitmap(bitmapCapricorn);
                break;
            case R.id.pop_esc:
                mMPopWindow.dismiss();
                break;
        }
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(context).inflate(R.layout.popuplayout, null);
        mMPopWindow = new PopupWindow(contentView,
                500, 450, true);
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
        mMPopWindow.showAtLocation(rootview, Gravity.CENTER_HORIZONTAL, -30, -80);
    }

    public static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }
}