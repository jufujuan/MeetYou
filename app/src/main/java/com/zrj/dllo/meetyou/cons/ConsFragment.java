package com.zrj.dllo.meetyou.cons;

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

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;

/**
 * Created by ${ZhaoXuancheng} on 16/12/6.
 */

public class ConsFragment extends AbsBaseFragment implements View.OnClickListener {


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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cons_start_btn:

                HttpUtil.getTest(1, new ResponseCallBack<ConsBean>() {
                    @Override
                    public void onResponse(ConsBean testBean) {
                        Log.d("MainActivity", "testBean:" + testBean);
                        String title = testBean.getNewslist().get(0).getTitle();
                        Log.d("99999999", title);
                        Log.d("99999999", testBean.getNewslist().get(0).getGrade());
                        Log.d("99999999", testBean.getNewslist().get(0).getContent());
                        mTextViewGrage.setText(testBean.getNewslist().get(0).getGrade());
                        mTextViewContent.setText(testBean.getNewslist().get(0).getContent());
                        mTextViewCons.setText(testBean.getNewslist().get(0).getTitle());
                    }

                    @Override
                    public void onError(Exception exception) {

                    }
                });

                break;
            case R.id.cons_tv:
                showPopupWindow();
                break;
            case R.id.pop_aquarius:
                mMPopWindow.dismiss();
                break;
            case R.id.pop_pisces:
                mMPopWindow.dismiss();
                break;
            case R.id.pop_aries:
                mMPopWindow.dismiss();
                break;
            case R.id.pop_taurus:
                mMPopWindow.dismiss();
                break;
            case R.id.pop_gemini:
                mMPopWindow.dismiss();
                break;
            case R.id.pop_cancer:
                mMPopWindow.dismiss();
                break;
            case R.id.pop_leo:
                mMPopWindow.dismiss();
                break;
            case R.id.pop_virgo:
                mMPopWindow.dismiss();
                break;
            case R.id.pop_libra:
                mMPopWindow.dismiss();
                break;
            case R.id.pop_scorpio:
                mMPopWindow.dismiss();
                break;
            case R.id.pop_sagittarius:
                mMPopWindow.dismiss();
                break;
            case R.id.pop_capricorn:
                mMPopWindow.dismiss();
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
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
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
        mMPopWindow.showAtLocation(rootview, Gravity.NO_GRAVITY, 250, 250);
    }
}