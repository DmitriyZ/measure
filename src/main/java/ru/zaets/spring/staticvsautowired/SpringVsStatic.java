package ru.zaets.spring.staticvsautowired;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SpringVsStatic {

    private SpringComponent bean;

    @Setup
    public void setup() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringComponent.class);
        bean = context.getBean(SpringComponent.class);
    }

    @Benchmark
    @Measurement(batchSize = 10000)
    public void staticMethod(Blackhole blackhole) {
        blackhole.consume(StaticUtil.cacl(100));
    }

    @Benchmark
    @Measurement(batchSize = 10000)
    public void beanMethod(Blackhole blackhole) {
        blackhole.consume(bean.cacl(100));
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SpringVsStatic.class.getSimpleName())
                .warmupIterations(3)
                .measurementIterations(3)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
