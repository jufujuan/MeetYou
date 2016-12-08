package com.zrj.dllo.meetyou.internet;

import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
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

    private <Bean> void sendHttpRequest(Request request, final Class<Bean> aClass, final ResponseCallBack<Bean> responseCallBack) {
        mClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        mHandler.post(new ErrorRunnable<Bean>(responseCallBack, e));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        try {
                            Bean bean = mGson.fromJson(json, aClass);

                            mHandler.post(new ResponseRunnable<Bean>(responseCallBack, bean));
                        } catch (Exception e) {
                            e.printStackTrace();
                            mHandler.post(new ErrorRunnable<Bean>(responseCallBack, e));
                        }
                    }
                });
    }

    public <Bean> void get(String url, Class<Bean> clazz, ResponseCallBack<Bean> responseCallBack) {
        Request request = new Request.Builder().
                addHeader("apikey", "bbdf7a8139affb63107b852410af613e").url(url).build();
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

        public ErrorRunnable(ResponseCallBack<Bean> responseCallBack, Exception e) {
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
