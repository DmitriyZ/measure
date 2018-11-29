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

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
//
//@BenchmarkMode(Mode.Throughput)
//@OutputTimeUnit(TimeUnit.SECONDS)

//@CompilerControl(CompilerControl.Mode.DONT_INLINE)
public class SpringVsSpringInterfaceVsStatic {

    private SpringCompnent bean;
    private ISpring iSpring;

    @Setup
    public void setup() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfiguration.class);
        bean = context.getBean(SpringCompnent.class);
        iSpring = context.getBean(ISpring.class);
    }

    @Benchmark
    public void staticMethod(Blackhole blackhole) {
        blackhole.consume(StaticClass.cacl(1));
    }

    @Benchmark
    public void beanMethod(Blackhole blackhole) {
        blackhole.consume(bean.cacl(1));
    }

    @Benchmark
    public void beanViaInterface(Blackhole blackhole) {
        blackhole.consume(iSpring.cacl(1));
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SpringVsSpringInterfaceVsStatic.class.getSimpleName())
                .warmupIterations(3)
                .warmupBatchSize(1000)
                .measurementIterations(5)
                .measurementBatchSize(1000000)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
