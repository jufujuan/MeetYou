package com.zrj.dllo.meetyou.login;


import cn.bmob.v3.BmobUser;

/**
 * Created by ${ZhaoXuancheng} on 16/11/26.
 * 数据库的实体类
 */

public class LoginUserBean extends BmobUser{

    private String userImgUrl;//用户头像
    private String likeCount;//喜欢的人数
    private String locationDate;//最近更新位置日期
    private String latitude;//纬度
    private String lontitude;//经度
    private String adress;//地址
    private String radius;//半径

    public LoginUserBean() {
        this.setTableName("_User");
    }

    public LoginUserBean(String userImgUrl, String likeCount, String locationDate, String latitude, String lontitude, String adress, String radius) {
        this.userImgUrl = userImgUrl;
        this.likeCount = likeCount;
        this.locationDate = locationDate;
        this.latitude = latitude;
        this.lontitude = lontitude;
        this.adress = adress;
        this.radius = radius;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getLocationDate() {
        return locationDate;
    }

    public void setLocationDate(String locationDate) {
        this.locationDate = locationDate;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLontitude() {
        return lontitude;
    }

    public void setLontitude(String lontitude) {
        this.lontitude = lontitude;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

}
