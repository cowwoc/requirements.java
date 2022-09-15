/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.benchmark.java;

import com.github.cowwoc.requirements.java.internal.StringVerifierImpl;
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

import java.util.concurrent.TimeUnit;

import static com.github.cowwoc.requirements.DefaultRequirements.requireThat;

@State(Scope.Benchmark)
@SuppressWarnings({"CanBeFinal", "LongLine", "FieldCanBeLocal", "FieldMayBeFinal"})
public class ExceptionTest
{
	// Fields may not be final:
	// http://hg.openjdk.java.net/code-tools/jmh/file/ed0a5f40acfb/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#l62
	private String name = "actual";
	private Object nullObject = null;

	@Test
	public void runBenchmarks() throws RunnerException
	{
		Options opt = new OptionsBuilder().
			include(ExceptionTest.class.getSimpleName()).
			timeUnit(TimeUnit.NANOSECONDS).
			mode(Mode.AverageTime).
			warmupIterations(3).
			measurementIterations(5).
			forks(0).
			build();
		new Runner(opt).run();
	}

	@Benchmark
	public String validationResult(Blackhole bh)
	{
		try
		{
			StringVerifierImpl result = (StringVerifierImpl) requireThat((String) null, "null");
			result.validationResult(IllegalArgumentException.class);
			return result.getActual();
		}
		catch (Throwable t)
		{
			bh.consume(t);
			return null;
		}
	}

	@Benchmark
	public String validateResultType(Blackhole bh)
	{
		try
		{
			StringVerifierImpl result = (StringVerifierImpl) requireThat((String) null, "null");
			result.validationResult(IllegalArgumentException.class);
			return result.getActual();
		}
		catch (Throwable t)
		{
			bh.consume(t);
			return null;
		}
	}
}