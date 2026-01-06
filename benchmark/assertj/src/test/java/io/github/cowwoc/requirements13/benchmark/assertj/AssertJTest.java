/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.benchmark.assertj;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@State(Scope.Benchmark)
@SuppressWarnings({"CanBeFinal", "LongLine", "FieldCanBeLocal", "FieldMayBeFinal", "PMD.ImmutableField"})
public class AssertJTest
{
	private static final boolean FAST_ESTIMATE = Boolean.getBoolean("FAST_ESTIMATE");
	// Fields may not be final:
	// https://github.com/openjdk/jmh/blob/cb3c3a90137dad781a2a37fda72dc11ebf253593/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#L58
	private String name = "map";
	private Map<Integer, Integer> value;

	public AssertJTest()
	{
		value = HashMap.newHashMap(5);
		for (int i = 0; i < 5; ++i)
			value.put(i, 5 - i);
	}

	@Test
	public void runBenchmarks() throws RunnerException
	{
		ChainedOptionsBuilder builder = new OptionsBuilder().
			include(AssertJTest.class.getSimpleName()).
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
	public void assertThatIsSuccessful(Blackhole bh)
	{
		bh.consume(assertThat(value).as(name + ": %s").size().
			as("""
				%s must be greater than %s.
				actual: %s
				actual.size: %s""", name, 3, value, value.size()).isGreaterThan(3));
	}

	@Benchmark
	public void assertThatWithCleanStackTrace(Blackhole bh)
	{
		Assertions.setRemoveAssertJRelatedElementsFromStackTrace(true);
		try
		{
			assertThat(value).as(name).size().isLessThan(3);
		}
		catch (AssertionError e)
		{
			// The stack trace is not populated unless we explicitly invoke getStackTrace()
			bh.consume(e.getStackTrace());
		}
	}

	@Benchmark
	public void assertThatWithoutCleanStackTrace(Blackhole bh)
	{
		Assertions.setRemoveAssertJRelatedElementsFromStackTrace(false);
		try
		{
			assertThat(value).as(name).size().isLessThan(3);
		}
		catch (AssertionError e)
		{
			// The stack trace is not populated unless we explicitly invoke getStackTrace()
			bh.consume(e.getStackTrace());
		}
	}

	@Benchmark
	public void softAssertionsErrorsCollected(Blackhole bh)
	{
		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(value).as(name).size().isLessThan(3);
		bh.consume(softly.errorsCollected());
	}
}