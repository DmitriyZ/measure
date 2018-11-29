package ru.zaets.spring.staticvsautowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {
    @Bean
    public SpringCompnent springCompnent() {
        return new SpringCompnent();
    }

    @Bean
    ISpring iSpring() {
        return new SpringImpl();
    }
}
