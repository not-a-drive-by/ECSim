package edu.boun.edgecloudsim.utils;

public final class StaticfinalTags {
    /**penalty项系数**/
    public static final double alpha = 5;

    /**当前时间, 如果为负数，则无效*/
    public static int curTime=0;

    /**设备参数*/
    public static double lambda1 = 0.09; // 0.05job/slot
    public static double lambda2 = 0.09;
    public static double lambda3 = 0.09;

    public static double ratioThres = 0.5;
    public static double disThres = 100;

    /**信道速率更新时间间隔*/
    public static int ratioItv = 15;

    /**移动用户速度*/
    public static double velocity = 0.2;

}
