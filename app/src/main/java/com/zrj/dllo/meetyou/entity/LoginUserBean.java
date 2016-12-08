package com.zrj.dllo.meetyou.entity;


import cn.bmob.v3.BmobUser;

/**
 * Created by ${ZhaoXuancheng} on 16/11/26.
 * 数据库的实体类
 */

public class LoginUserBean extends BmobUser{

    private String userImgUrl;//用户头像

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }
}
