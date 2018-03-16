package ru.zaets.research;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Test show what variable definition in cycle or out of cycle have same result.
 * But in "in cycle variant" have better variable scope
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LoopInOutVariable {

    @Benchmark
    public void outVariable(Blackhole bh) {
        double intermediateResult = 0;
        List<Double> results = new ArrayList<>(1);
        for (int i = 0; i < 1000; i++) {
            intermediateResult = i * i;
            results.add(0, intermediateResult);
        }
        bh.consume(results);
        bh.consume(intermediateResult);
    }


    @Benchmark
    public void inVariable(Blackhole bh) {
        List<Double> results = new ArrayList<>(1);
        for (int i = 0; i < 1000; i++) {
            double intermediateResult = i * i;
            results.add(0, intermediateResult);
        }
        bh.consume(results);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LoopInOutVariable.class.getSimpleName())
                .warmupIterations(3)
                .measurementIterations(3)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
