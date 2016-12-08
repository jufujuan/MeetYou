package com.zrj.dllo.meetyou.msg.contact;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/11/26.
 */

public class ContactBean {

    private String avatarUrl;
    private String Name;
    private String PinYin;
    private String FirstPinYin;
    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String pinYin) {
        PinYin = pinYin;
    }

    public String getFirstPinYin() {
        return FirstPinYin;
    }

    public void setFirstPinYin(String firstPinYin) {
        FirstPinYin = firstPinYin;
    }

//    public String toString() {
//        return "姓名:" + getName() + "   拼音:" + getPinYin() + "    首字母:"
//                + getFirstPinYin();
//
//    }
}
