package com.zrj.dllo.meetyou.tools;

import android.graphics.Bitmap;

/**
 * Created by ${ZhaoXuancheng} on 16/11/29.
 * 静态常量类
 */

public class StaticValues {
    /*************sp***************/

    /**
     * 存储正在登录的用户的SP表名
     */
    public final static String SP_USEING_TABLE_NAME = "userMessage";
    /**
     * 正在登录的用户账号
     */
    public final static String SP_USEING_NAME_COLUMN = "userName";
    /**
     *  正在登录的用户头像
     */
    public final static String SP_USEING_IMG_URL_COLUMN= "userImgUrl";

    /**
     *  正在登录的用户纬度
     */
    public final static String SP_USEING_LATITUDE_COLUMN= "latitude";
    /**
     *  正在登录的用户经度
     */
    public final static String SP_USEING_LONTITUDE_COLUMN= "lontitude";
    /**
     *  正在登录的用户地址
     */
    public final static String SP_USEING_ADRESS_COLUMN= "adress";
    /**
     *  正在登录的用户地址
     */
    public final static String SP_USEING_ID= "id";





    /**************liteorm*****************/
    /**
     * LiteOrm数据库的名字
     */
    public static  final String LO_DB_NAME="meetyoulo";
    /**
     * LiteOrm喜欢的人的列表的名字
     */
    public static final String LO_TABLE_LIKE_NAME="like";

}