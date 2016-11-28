package com.zrj.dllo.meetyou.msg.contact;

import java.util.Comparator;

/**
 * Created by REN - the most cool programmer all over the world
 * on 16/11/26.
 */

public class PinyinComparator implements Comparator<ContactBean> {
    @Override
    public int compare(ContactBean lhs, ContactBean rhs) {
        // TODO Auto-generated method stub
        return sort(lhs, rhs);
    }

    private int sort(ContactBean lhs, ContactBean rhs) {
        // 获取asciiֵ值
        int lhs_ascii = lhs.getFirstPinYin().toUpperCase().charAt(0);
        int rhs_ascii = rhs.getFirstPinYin().toUpperCase().charAt(0);
        // 判断若不是字母，则排在字母之后
        if (lhs_ascii < 65 || lhs_ascii > 90)
            return 1;
        else if (rhs_ascii < 65 || rhs_ascii > 90)
            return -1;
        else
            return lhs.getPinYin().compareTo(rhs.getPinYin());
    }
}
