package ru.zaets.spring.staticvsautowired;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringComponent {
    public Integer cacl(int i) {
        return i * i;
    }
}
