package com.zrj.dllo.meetyou.cons;

/**
 * If there is no bug, then it is created by ChenFengYao on 2016/11/21,
 * otherwise, I do not know who create it either.
 */
public abstract class NetManager {

    protected abstract <Bean> void get(String url,Class<Bean> clazz, ResponseCallBack<Bean> responseCallBack);
}
