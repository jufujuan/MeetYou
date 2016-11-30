package com.zrj.dllo.meetyou.tools;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/30.
 * 帮助计算经纬度距离的工具类
 */

public class DistanceUtils {
    /**
     * 得到经纬度计算的结果
     *
     * @param lat_a 第一个位置的经度
     * @param lng_a  第一个位置的纬度
     * @param lat_b 第二个位置的经度
     * @param lng_b  第二个位置的纬度
     * @return 距离(单位米)
     */
    public static double getDistance(double lng_a,double lat_a,double lng_b,double lat_b) {
        double pk = 180 / 3.14169;
        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;
        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        return 6366000 * tt;
    }

}
