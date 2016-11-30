package com.zrj.dllo.meetyou.tools;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/30.
 * 帮助计算经纬度距离的工具类
 */

public class DistanceUtils {
    private double loOne = 0;
    private double loTwo = 0;
    private double laOne = 0;
    private double laTwo = 0;
    private static final double EARTH_RADIUS = 6378137.0;

    public void setLoOne(double loOne) {
        this.loOne = loOne;
    }

    public void setLoTwo(double loTwo) {
        this.loTwo = loTwo;
    }

    public void setLaOne(double laOne) {
        this.laOne = laOne;
    }

    public void setLaTwo(double laTwo) {
        this.laTwo = laTwo;
    }

    /**
     * 设置第一个位置的经纬度
     *
     * @param loOne 经度
     * @param laOne 纬度
     */
    public void setOne(double loOne, double laOne) {
        this.loOne = loOne;
        this.laOne = laOne;
    }

    /**
     * 设置第二位置的经纬度
     *
     * @param loTwo 经度
     * @param laTwo 纬度
     */
    public void setTwo(double loTwo, double laTwo) {
        this.loTwo = loTwo;
        this.laTwo = laTwo;
    }

    /**
     * 得到经纬度计算的结果
     *
     * @param longitude1 第一个位置的经度
     * @param latitude1  第一个位置的纬度
     * @param longitude2 第二个位置的经度
     * @param latitude2  第二个位置的纬度
     * @return
     */
    public double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
