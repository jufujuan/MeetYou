package com.zrj.dllo.meetyou.cons;

/**
 * If there is no bug, then it is created by ChenFengYao on 2016/11/21,
 * otherwise, I do not know who create it either.
 * 发起网络请求,直接的操作类
 */

public class HttpUtil {
    /**
     * 请求测评页面的数据
     *
     * @param page     页数
     * @param callBack
     */
    public static void getTest(int page, ResponseCallBack<ConsBean> callBack) {
        //获得一个真正的网络请求url
//        String url = NetApi.TEST_URL_BASE + "&page=" + page;
        String url = "http://apis.baidu.com/txapi/xingzuo/xingzuo" + "?" + "me=%E9%87%91%E7%89%9B&he=%E5%B7%A8%E8%9F%B9&all=1";

        //使用Manager来发起网络请求
        OkHttpManager.getInstance()
                .get(url, ConsBean.class, callBack);
    }


}
