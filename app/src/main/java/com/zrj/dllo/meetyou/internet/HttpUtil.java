package com.zrj.dllo.meetyou.internet;

import com.zrj.dllo.meetyou.cons.ConsBean;

/**
 * Created by ${ZhaoXuancheng} on 16/12/6.
 */

public class HttpUtil {

    public static void getTest(String meCons, String heCons, ResponseCallBack<ConsBean> callBack) {

        EncodeUtil.encode(meCons);
        EncodeUtil.encode(heCons);

        String url = "http://apis.baidu.com/txapi/xingzuo/xingzuo" + "?" +
                "me=" + EncodeUtil.encode(meCons) + "&he=" + EncodeUtil.encode(heCons) + "&all=1";

        OkHttpManager.getInstance().get(url, ConsBean.class, callBack);
    }
}
