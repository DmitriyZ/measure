package ru.zaets.spring.staticvsautowired;

public class SpringImpl implements ISpring {
    @Override
    public long cacl(int i) {
        return System.currentTimeMillis() + i;
    }
}
