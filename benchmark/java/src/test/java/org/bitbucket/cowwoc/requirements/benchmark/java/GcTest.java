/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark.java;

import org.bitbucket.cowwoc.requirements.DefaultRequirements;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@SuppressWarnings({"CanBeFinal", "LongLine"})
public class GcTest
{
	// Fields may not be final:
	// http://hg.openjdk.java.net/code-tools/jmh/file/ed0a5f40acfb/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#l62
	private String name = "actual";
	private String value = "value";

	@Test
	public void runBenchmarks() throws RunnerException
	{
		Options opt = new OptionsBuilder().
			include(GcTest.class.getSimpleName()).
			timeUnit(TimeUnit.NANOSECONDS).
			mode(Mode.AverageTime).
			warmupIterations(10).
			measurementIterations(20).
			addProfiler(GCProfiler.class).
			build();
		new Runner(opt).run();
	}

	@Benchmark
	@SuppressWarnings("EmptyMethod")
	public void emptyMethod()
	{
	}

	@Benchmark
	public SizeVerifier requireThat()
	{
		return DefaultRequirements.requireThat(value, name).isNotNull().length().isGreaterThan(3);
	}

	@Benchmark
	public SizeVerifier assertThatWithAssertionsDisabled()
	{
		return DefaultRequirements.assertThat(value, name).isNotNull().length().isGreaterThan(3);
	}
}