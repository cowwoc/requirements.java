/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Fields may not be final: http://hg.openjdk.java.net/code-tools/jmh/file/ed0a5f40acfb/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#l62
@State(Scope.Benchmark)
public class JavaTest
{
	private String name = "actual";
	private String value = "value";
	private Object nullObject = null;
	private List<Integer> list;

	public JavaTest()
	{
		list = new ArrayList<>(100);
		for (int i = 0; i < 100; ++i)
			list.add(i);
	}

	@Test
	public void runBenchmarks() throws RunnerException
	{
		Options opt = new OptionsBuilder().
			include(JavaTest.class.getSimpleName()).
			timeUnit(TimeUnit.NANOSECONDS).
			mode(Mode.AverageTime).
			warmupIterations(10).
			measurementIterations(20).
			build();
		new Runner(opt).run();
	}

	@Benchmark
	public void emptyMethod()
	{
	}

	@Benchmark
	public CollectionVerifier<List<Integer>, Integer> requirementsWithAssertionsDisabledAssertThat()
	{
		return new Requirements().withAssertionsDisabled().assertThat(name, list).doesNotContainDuplicates();
	}

	@Benchmark
	public CollectionVerifier<?, ?> requirementsRequireThat()
	{
		return new Requirements().requireThat(name, list).doesNotContainDuplicates();
	}

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() can be faster than requireThat() even though it delegates to it
	@Benchmark
	public CollectionVerifier<List<Integer>, Integer> requirementsWithAssertionsEnabledAssertThat()
	{
		return new Requirements().withAssertionsEnabled().assertThat(name, list).doesNotContainDuplicates();
	}

	@Benchmark
	public void throwException(Blackhole bh)
	{
		try
		{
			throw new IllegalArgumentException("Hard-coded exception");
		}
		catch (IllegalArgumentException e)
		{
			bh.consume(e);
		}
	}

	@Benchmark
	public void requirementsThrowException(Blackhole bh)
	{
		try
		{
			new Requirements().requireThat(name, nullObject).isNotNull();
		}
		catch (NullPointerException e)
		{
			bh.consume(e);
		}
	}
}
