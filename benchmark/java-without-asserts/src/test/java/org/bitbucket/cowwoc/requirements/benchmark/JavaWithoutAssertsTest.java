/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark;

import org.bitbucket.cowwoc.requirements.DefaultRequirements;
import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.StringVerifier;
import org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
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

import static org.bitbucket.cowwoc.requirements.DefaultRequirements.assertThat;
import static org.bitbucket.cowwoc.requirements.DefaultRequirements.requireThat;
import static org.openjdk.jmh.annotations.Level.Iteration;

// Fields may not be final: http://hg.openjdk.java.net/code-tools/jmh/file/ed0a5f40acfb/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#l62
@State(Scope.Benchmark)
public class JavaWithoutAssertsTest
{
	private String name = "actual";
	private String value = "value";
	private Object nullObject = null;
	private List<Integer> list;

	public JavaWithoutAssertsTest()
	{
		list = new ArrayList<>(100);
		for (int i = 0; i < 100; ++i)
			list.add(i);
	}

	@Test
	public void launchBenchmarks() throws RunnerException
	{
		Options opt = new OptionsBuilder().
			include(JavaWithoutAssertsTest.class.getSimpleName()).
			forks(3).
			timeUnit(TimeUnit.NANOSECONDS).
			mode(Mode.AverageTime).
			build();
		new Runner(opt).run();
	}

	@Setup(Iteration)
	public void setupFork()
	{
		DefaultRequirements.globalConfiguration().withTerminalEncoding(TerminalEncoding.NONE);
	}

	@Benchmark
	public void emptyMethod()
	{
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
	public StringVerifier staticAssertThat()
	{
		return assertThat(name, value).isNotNull().isNotEmpty();
	}

	@Benchmark
	public StringVerifier verifiersAssertsDisabled()
	{
		return new Requirements().withAssertionsDisabled().assertThat(name, value).isNotNull().isNotEmpty();
	}

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() can
	// be faster than requireThat() even though it delegates to it
	@Benchmark
	public StringVerifier verifiersAssertsEnabled()
	{
		return new Requirements().withAssertionsEnabled().assertThat(name, value).isNotNull().isNotEmpty();
	}

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() can
	// be faster than requireThat() even though it delegates to it
	@Benchmark
	public StringVerifier verifiersRequireThat()
	{
		return new Requirements().requireThat(name, value).isNotNull().isNotEmpty();
	}

	@Benchmark
	public StringVerifier staticRequireThat()
	{
		return requireThat(name, value).isNotNull().isNotEmpty();
	}

	@Benchmark
	public CollectionVerifier<?, ?> verifiersRequireThatDoesNotContainDuplicates()
	{
		return new Requirements().requireThat(name, list).doesNotContainDuplicates();
	}

	@Benchmark
	public CollectionVerifier<?, ?> verifiersAssertsDisabledDoesNotContainDuplicates()
	{
		return new Requirements().withAssertionsDisabled().assertThat(name, list).
			doesNotContainDuplicates();
	}

	@Benchmark
	public void staticThrowException(Blackhole bh)
	{
		try
		{
			requireThat(name, nullObject).isNotNull();
		}
		catch (NullPointerException e)
		{
			bh.consume(e);
		}
	}

	@Benchmark
	public void verifiersThrowException(Blackhole bh)
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
