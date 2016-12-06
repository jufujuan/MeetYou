package com.zrj.dllo.meetyou.cons;

/**
 * If there is no bug, then it is created by ChenFengYao on 2016/11/21,
 * otherwise, I do not know who create it either.
 * <p>
 * 网址的常量类
 */

public class NetApi {
    public static final String BASE_URL = "http://food.boohee.com/fb/v1";
    public static final String FEEDS = "/feeds";
    public static final String CATEGORY_FEED = "/category_feed";

    public static String getTestUrl(int page, String category,
                                    int per) {
        return BASE_URL + FEEDS + CATEGORY_FEED
                + "?" + "page=" + page
                + "&category=" + category
                + "&per=" + per;

    }

    public static final String TEST_URL_BASE = "http://food.boohee.com/fb/v1/feeds/category_feed?category=2&per=10";
}
