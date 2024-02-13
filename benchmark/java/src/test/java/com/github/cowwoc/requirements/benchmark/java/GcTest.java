/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.benchmark.java;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.github.cowwoc.requirements.java.DefaultJavaValidators.assumeThat;

@State(Scope.Benchmark)
@SuppressWarnings({"CanBeFinal", "LongLine", "FieldMayBeFinal"})
public class GcTest
{
	private static final boolean FAST_ESTIMATE = false;
	// Fields may not be final:
	// https://github.com/openjdk/jmh/blob/cb3c3a90137dad781a2a37fda72dc11ebf253593/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#L58
	private String name = "Actual";
	private Map<Integer, Integer> value;

	public GcTest()
	{
		value = new HashMap<>(5, 1f);
		for (int i = 0; i < 5; ++i)
			value.put(i, 5 - i);
	}

	@Test
	public void runBenchmarks() throws RunnerException
	{
		ChainedOptionsBuilder builder = new OptionsBuilder().
			include(GcTest.class.getSimpleName()).
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
		Options options = builder.
			addProfiler(GCProfiler.class).
			build();
		new Runner(options).run();
	}

	@Benchmark
	@Fork(jvmArgsAppend = "-da")
	@SuppressWarnings("EmptyMethod")
	public void emptyMethod()
	{
	}

	@Benchmark
	public void manualPreconditionCheck()
	{
		if (value.size() <= 3)
			throw new IllegalArgumentException("value.size() must be greater than 3");
	}

	@Benchmark
	@Fork(jvmArgsAppend = "-da")
	public void assumeThatWithAssertionsDisabled(Blackhole bh)
	{
		assert blackholeAssert(bh, assumeThat(value, name).size().isGreaterThan(3).elseThrow());
	}

	// See http://stackoverflow.com/a/38862964/14731 for why asserted code may execute faster than
	// non-asserted code, even though it theoretically does more work.
	//@CompilerControl(CompilerControl.Mode.DONT_INLINE)
	@Benchmark
	@Fork(jvmArgsAppend = "-ea")
	public void assumeThatWithAssertionsEnabled(Blackhole bh)
	{
		assert blackholeAssert(bh, assumeThat(value, name).size().isGreaterThan(3).elseThrow());
	}

	private boolean blackholeAssert(Blackhole bh, boolean resultOfAssertion)
	{
		bh.consume(resultOfAssertion);
		return true;
	}
}