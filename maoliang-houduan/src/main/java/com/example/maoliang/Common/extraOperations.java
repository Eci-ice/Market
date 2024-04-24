package com.example.maoliang.Common;

import java.util.Random;

public class extraOperations {
    public static String randomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static double calculateDER(int catState, double catWeight) {
        double RER = 70 * Math.pow(catWeight, 0.75); // 根据猫的体重计算RER
      //  System.out.println("RER:"+RER+"weight"+catWeight);
        switch (catState) {
            case 1://"肥胖（打算减肥）":
                return RER * 0.9;// 0.8到1.0的平均值
            case 2://"不活跃":
                return RER * 1.0;
            case 3://"绝育成猫":
                return RER * 1.2;
            case 4://"未绝育成猫":
                return RER * 1.4;
            case 5://"妊娠":
                return RER * 2.5; // 2到3的平均值
            case 6://"哺乳期":
                return RER * 4.0; // 2到6的平均值
            case 7://"未成年":
                return RER * 2.5; // 2到3的平均值
            default:
                throw new IllegalArgumentException("无效的猫状态: " + catState);
        }
    }

}