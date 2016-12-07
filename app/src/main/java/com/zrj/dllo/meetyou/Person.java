package com.zrj.dllo.meetyou;

import android.os.Parcelable;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.zrj.dllo.meetyou.tools.StaticValues;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/29.
 */
@Table(StaticValues.LO_TABLE_LIKE_NAME)
public class Person extends BmobObject implements Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)

    private String searchR;//搜索半径
    private int day;//出生天
    private int moon;//出生月
    private int year;//出生年
    private String signature;//签名
    private String realName;//真实姓名
    private String sex;//性别
    private String uName;//用户名
    private String uPassword;//密码
    private String userImgUrl;//用户头像
    private String likeCount;//喜欢的人数
    private String locationDate;//最近更新位置日期
    private String latitude;//纬度
    private String lontitude;//经度
    private String adress;//地址
    private String radius;//半径

    public Person() {
    }

    public Person(String uName, String uPassword, String userImgUrl, String likeCount, String locationDate, String latitude, String lontitude, String adress, String radius) {
        this.uName = uName;
        this.uPassword = uPassword;
        this.userImgUrl = userImgUrl;
        this.likeCount = likeCount;
        this.locationDate = locationDate;
        this.latitude = latitude;
        this.lontitude = lontitude;
        this.adress = adress;
        this.radius = radius;
        this.searchR = searchR;
        this.day = day;
        this.moon = moon;
        this.year = year;
        this.signature = signature;
        this.realName = realName;
        this.sex = sex;

    }

    public Person(String tableName, String uName, String uPassword, String userImgUrl, String likeCount, String locationDate, String latitude, String lontitude, String adress, String radius) {
        super(tableName);
        this.uName = uName;
        this.uPassword = uPassword;
        this.userImgUrl = userImgUrl;
        this.likeCount = likeCount;
        this.locationDate = locationDate;
        this.latitude = latitude;
        this.lontitude = lontitude;
        this.adress = adress;
        this.radius = radius;
        this.searchR = searchR;
        this.day = day;
        this.moon = moon;
        this.year = year;
        this.signature = signature;
        this.realName = realName;
        this.sex = sex;
    }

    public String getSearchR() {
        return searchR;
    }

    public void setSearchR(String searchR) {
        this.searchR = searchR;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMoon() {
        return moon;
    }

    public void setMoon(int moon) {
        this.moon = moon;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
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
