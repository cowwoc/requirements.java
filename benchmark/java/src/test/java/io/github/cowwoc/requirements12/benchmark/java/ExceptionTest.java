/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.benchmark.java;

import io.github.cowwoc.requirements12.java.JavaValidators;
import io.github.cowwoc.requirements12.java.internal.ConfigurationUpdater;
import io.github.cowwoc.requirements12.java.internal.validator.JavaValidatorsImpl;
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

@State(Scope.Benchmark)
@SuppressWarnings({"CanBeFinal", "LongLine", "FieldCanBeLocal", "FieldMayBeFinal", "PMD.ImmutableField",
	"PMD.ExceptionAsFlowControl"})
public class ExceptionTest
{
	private static final boolean FAST_ESTIMATE = Boolean.getBoolean("FAST_ESTIMATE");
	// Fields may not be final:
	// https://github.com/openjdk/jmh/blob/cb3c3a90137dad781a2a37fda72dc11ebf253593/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#L58
	private String name = "map";
	private Map<Integer, Integer> value;
	private JavaValidators validators = JavaValidators.newInstance();
	private JavaValidatorsImpl withoutCleanStackTrace = (JavaValidatorsImpl) JavaValidators.newInstance();
	private JavaValidatorsImpl withoutRecordStacktrace = (JavaValidatorsImpl) JavaValidators.newInstance();

	public ExceptionTest()
	{
		value = HashMap.newHashMap(5);
		for (int i = 0; i < 5; ++i)
			value.put(i, 5 - i);
		try (ConfigurationUpdater config = withoutRecordStacktrace.updateConfiguration())
		{
			config.recordStacktrace(false);
		}
		try (ConfigurationUpdater config = withoutCleanStackTrace.updateConfiguration())
		{
			config.cleanStackTrace(false);
		}
	}

	@Test
	public void runBenchmarks() throws RunnerException
	{
		ChainedOptionsBuilder builder = new OptionsBuilder().
			include(ExceptionTest.class.getSimpleName()).
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
		bh.consume(validators.requireThat(value, name).size().isGreaterThan(3));
	}

	@Benchmark
	public void throwExceptionCatchException(Blackhole bh)
	{
		try
		{
			throw new IllegalArgumentException("Hard-coded exception");
		}
		catch (IllegalArgumentException e)
		{
			// The stack trace is not populated unless we explicitly invoke getStackTrace()
			bh.consume(e.getStackTrace());
		}
	}

	@Benchmark
	public void requireThatWithCleanStackTrace(Blackhole bh)
	{
		try
		{
			validators.requireThat(value, name).size().isLessThan(3);
		}
		catch (IllegalArgumentException e)
		{
			// The stack trace is not populated unless we explicitly invoke getStackTrace()
			bh.consume(e.getStackTrace());
		}
	}

	@Benchmark
	public void requireThatWithoutCleanStackTrace(Blackhole bh)
	{
		try
		{
			withoutCleanStackTrace.requireThat(value, name).size().isLessThan(3);
		}
		catch (IllegalArgumentException e)
		{
			// The stack trace is not populated unless we explicitly invoke getStackTrace()
			bh.consume(e.getStackTrace());
		}
	}

	@Benchmark
	public void checkIfAndGetMessagesWithRecordStacktrace(Blackhole bh)
	{
		bh.consume(validators.checkIf(value, name).isNull().elseGetFailures().getMessages());
	}

	@Benchmark
	public void checkIfAndGetMessagesWithoutRecordStacktrace(Blackhole bh)
	{
		bh.consume(withoutRecordStacktrace.checkIf(value, name).isNull().elseGetFailures().getMessages());
	}
}