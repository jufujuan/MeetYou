package com.zrj.dllo.meetyou.cons;

/**
 * If there is no bug, then it is created by ChenFengYao on 2016/11/21,
 * otherwise, I do not know who create it either.
 * 网络请求的接口,有请求成功和请求失败
 */

public interface ResponseCallBack<Bean> {
    //请求成功 直接返回 数据类
    void onResponse(Bean bean);

    //请求失败,返回异常信息
    void onError(Exception exception);
}
