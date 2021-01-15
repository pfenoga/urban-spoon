package org.kupa.xmlbench;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class BenchmarkRunner {
	public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(XmlBenchmark.class.getSimpleName())
                .forks(2)
                .measurementTime(TimeValue.seconds(5))
                .warmupIterations(2)
                .build();

        new Runner(opt).run();
    }
}