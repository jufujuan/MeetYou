package com.zrj.dllo.meetyou.internet;

/**
 * Created by ${ZhaoXuancheng} on 16/12/6.
 */

public interface ResponseCallBack<Bean> {
    void onResponse(Bean bean);
    void onError(Exception exception);
}
