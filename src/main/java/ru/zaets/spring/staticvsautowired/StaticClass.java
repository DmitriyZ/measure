package ru.zaets.spring.staticvsautowired;

public class StaticClass {

    public static long cacl(int i) {
        return System.currentTimeMillis() + i;
    }
}
