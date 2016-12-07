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
     * @param callBack
     */
    public static void getTest(String meCons, String heCons, ResponseCallBack<ConsBean> callBack) {
        //获得一个真正的网络请求url

        EncodeUtil.encode(meCons);
        EncodeUtil.encode(heCons);

        String url = "http://apis.baidu.com/txapi/xingzuo/xingzuo" + "?" +
                "me=" + EncodeUtil.encode(meCons) + "&he=" + EncodeUtil.encode(heCons) + "&all=1";

        //使用Manager来发起网络请求
        OkHttpManager.getInstance().get(url, ConsBean.class, callBack);
    }
}
