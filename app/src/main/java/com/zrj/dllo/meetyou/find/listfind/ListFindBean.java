package com.zrj.dllo.meetyou.find.listfind;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/26.
 */

public class ListFindBean{
    private String avatar;//头像
    private String name;
    private String sex;

    public ListFindBean(String avatar, String name, String sex) {
        this.avatar = avatar;
        this.name = name;
        this.sex = sex;
    }

    public ListFindBean() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
