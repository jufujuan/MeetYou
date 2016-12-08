package com.zrj.dllo.meetyou.internet;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${ZhaoXuancheng} on 16/12/6.
 */

public class OkHttpManager extends NetManager {
    private static OkHttpManager ourInstance;
    private OkHttpClient mClient;
    private Handler mHandler;
    private Gson mGson;

    public static OkHttpManager getInstance() {
        if (ourInstance == null) {
            synchronized (OkHttpManager.class) {
                if (ourInstance == null) {
                    ourInstance = new OkHttpManager();
                }
            }
        }

        return ourInstance;
    }

    private OkHttpManager() {
        mClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    public <Bean> void post(String url
            , Class<Bean> clazz
            , ResponseCallBack<Bean> responseCallBack
            , HashMap<String, String> body) {
        FormBody.Builder formBuilder
                = new FormBody.Builder();

        for (String s : body.keySet()) {
            formBuilder.add(s, body.get(s));
        }

        FormBody formBody = formBuilder.build();

        Request postRequest
                = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        sendHttpRequest(postRequest, clazz, responseCallBack);
    }

    private <Bean> void sendHttpRequest(Request request
            , final Class<Bean> clazz
            , final ResponseCallBack<Bean> responseCallBack) {
        //发起网络请求
        mClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        //网络请求失败
                        mHandler.post(new ErrorRunnable<Bean>(responseCallBack, e));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //网络请求成功
                        String data = response.body().string();
                        Log.d("3366", data);
                        //尝试解析
                        try {//防止因为奇葩的数据 导致解析失败
                            Bean bean = mGson.fromJson(data, clazz);

                            mHandler.post(new ResponseRunnable<Bean>(responseCallBack
                                    , bean));
                        } catch (Exception e) {
                            e.printStackTrace();//把错误信息 直接输出
                            mHandler.post(new ErrorRunnable<Bean>(responseCallBack, e));
                        }
                    }
                });
    }

    public <Bean> void get(String url,
                           Class<Bean> clazz,
                           ResponseCallBack<Bean> responseCallBack) {
        Request request =
                new Request.Builder().addHeader("apikey", "bbdf7a8139affb63107b852410af613e")
                        .url(url)
                        .build();

        sendHttpRequest(request, clazz, responseCallBack);
    }


    abstract class HTTPRunnable<Bean> implements Runnable {
        protected ResponseCallBack<Bean> mResponseCallBack;

        public HTTPRunnable(ResponseCallBack<Bean> responseCallBack) {
            mResponseCallBack = responseCallBack;
        }
    }


    class ErrorRunnable<Bean> extends HTTPRunnable<Bean> {
        private Exception mException;

        public ErrorRunnable(ResponseCallBack<Bean> responseCallBack
                , Exception e) {
            super(responseCallBack);
            mException = e;
        }

        @Override
        public void run() {
            mResponseCallBack.onError(mException);
        }
    }

    class ResponseRunnable<Bean> extends HTTPRunnable<Bean> {
        private Bean mBean;

        public ResponseRunnable(ResponseCallBack<Bean> responseCallBack
                , Bean bean) {
            super(responseCallBack);
            this.mBean = bean;
        }

        @Override
        public void run() {
            mResponseCallBack.onResponse(mBean);
        }
    }
}
