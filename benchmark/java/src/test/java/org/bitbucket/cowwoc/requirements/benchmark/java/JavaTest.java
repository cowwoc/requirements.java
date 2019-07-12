/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark.java;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Fields may not be final:
// http://hg.openjdk.java.net/code-tools/jmh/file/ed0a5f40acfb/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#l62
@State(Scope.Benchmark)
@SuppressWarnings({"CanBeFinal", "LongLine"})
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
	@SuppressWarnings("EmptyMethod")
	public void emptyMethod()
	{
	}

	@Benchmark
	public SizeVerifier requireThat()
	{
		return new Requirements().requireThat(value, name).isNotNull().length().isGreaterThan(3);
	}

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() may be faster than requireThat() even
	// though it delegates to it
	@Benchmark
	public SizeVerifier assertThatWithAssertionsEnabled()
	{
		return new Requirements().withAssertionsEnabled().assertThat(value, name).isNotNull().length().
			isGreaterThan(3);
	}

	@Benchmark
	public CollectionVerifier<List<Integer>, Integer> requireThatDoesNotContainDuplicates()
	{
		return new Requirements().requireThat(list, name).doesNotContainDuplicates();
	}

	@Benchmark
	public CollectionVerifier<List<Integer>, Integer> assertThatDoesNotContainDuplicates()
	{
		return new Requirements().withAssertionsDisabled().assertThat(list, name).doesNotContainDuplicates();
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
	public void requireThatThrowException(Blackhole bh)
	{
		try
		{
			new Requirements().requireThat(nullObject, name).isNotNull();
		}
		catch (NullPointerException e)
		{
			bh.consume(e);
		}
	}

	@Benchmark
	public void throwAndConsumeStackTrace(Blackhole bh)
	{
		try
		{
			throw new IllegalArgumentException("Hard-coded exception");
		}
		catch (IllegalArgumentException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			bh.consume(sw.toString());
		}
	}

	@Benchmark
	public void requireThatThrowAndConsumeStackTrace(Blackhole bh)
	{
		try
		{
			new Requirements().requireThat(nullObject, name).isNotNull();
		}
		catch (NullPointerException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			bh.consume(sw.toString());
		}
	}
}