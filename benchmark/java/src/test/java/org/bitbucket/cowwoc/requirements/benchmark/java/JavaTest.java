/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark.java;

import org.bitbucket.cowwoc.requirements.DefaultRequirements;
import org.bitbucket.cowwoc.requirements.Requirements;
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
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@SuppressWarnings({"CanBeFinal", "LongLine", "FieldCanBeLocal"})
public class JavaTest
{
	// Fields may not be final:
	// http://hg.openjdk.java.net/code-tools/jmh/file/ed0a5f40acfb/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#l62
	private String name = "actual";
	private String value = "value";
	private Object nullObject = null;
	private Requirements requirementsWithAssertions = new Requirements().withAssertionsEnabled();

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

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() may be faster than requireThat() even
	// though it delegates to it
	@Benchmark
	public SizeVerifier assertThatWithAssertionsEnabled()
	{
		return requirementsWithAssertions.assertThat(value, name).isNotNull().length().
			isGreaterThan(3);
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
			DefaultRequirements.requireThat(nullObject, name).isNotNull();
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
			DefaultRequirements.requireThat(nullObject, name).isNotNull();
		}
		catch (NullPointerException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			bh.consume(sw.toString());
		}
	}
}