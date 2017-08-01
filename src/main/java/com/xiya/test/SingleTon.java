package com.xiya.test;

import java.util.Date;

public class SingleTon {
    private static SingleTon singleTon = new SingleTon();
    private static int count1 = 0;
    private static int count2;
    private static Date date;

    private SingleTon() {
//        count1++;
//        count2++;
        date = new Date();
    }

    public static SingleTon getInstance() {
        return singleTon;
    }

    public static void main(String[] args) {
        System.out.println("count1 = " + SingleTon.count1);
        System.out.println("count2 = " + SingleTon.count2);
        System.out.println("date = " + SingleTon.date);
    }
}
