package com.zrj.dllo.meetyou.internet;

/**
 * Created by ${ZhaoXuancheng} on 16/12/6.
 */
public abstract class NetManager {

    protected abstract <Bean> void get(String url,Class<Bean> clazz, ResponseCallBack<Bean> responseCallBack);
}
