/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.benchmark.guava;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.cowwoc.requirements13.guava.DefaultGuavaValidators;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@SuppressWarnings({"FieldCanBeLocal", "CanBeFinal", "LongLine", "PMD.ImmutableField"})
public class GuavaTest
{
	private static final boolean FAST_ESTIMATE = Boolean.getBoolean("FAST_ESTIMATE");
	// Fields may not be final:
	// https://github.com/openjdk/jmh/blob/cb3c3a90137dad781a2a37fda72dc11ebf253593/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#L58
	private String name = "multimap";
	private Multimap<Integer, Integer> value;

	public GuavaTest()
	{
		value = HashMultimap.create();
		for (int i = 0; i < 5; ++i)
			value.put(i, 5 - i);
	}

	@Test
	public void runBenchmarks() throws RunnerException
	{
		ChainedOptionsBuilder builder = new OptionsBuilder().
			include(GuavaTest.class.getSimpleName()).
			timeUnit(TimeUnit.NANOSECONDS).
			mode(Mode.AverageTime);
		if (FAST_ESTIMATE)
		{
			builder.warmupIterations(5).
				measurementIterations(5).
				forks(1);
		}
		else
		{
			builder.warmupIterations(10).
				measurementIterations(20);
		}
		Options options = builder.build();
		new Runner(options).run();
	}

	@Benchmark
	public void requireThatIsSuccessful(Blackhole bh)
	{
		bh.consume(DefaultGuavaValidators.requireThat(value, name).size().isGreaterThan(3));
	}
}