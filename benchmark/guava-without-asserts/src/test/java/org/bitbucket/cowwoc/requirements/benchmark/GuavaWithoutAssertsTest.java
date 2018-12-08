/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.DefaultRequirements;
import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
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
public class GuavaWithoutAssertsTest
{
	private String name = "multimap";
	private Multimap<String, String> value = HashMultimap.create();
	private Multimap<?, ?> nullMultimap = null;
	private List<Integer> list;

	public GuavaWithoutAssertsTest()
	{
		value.put("key", "value");
		value.put("key", "value");
		list = new ArrayList<>(100);
		for (int i = 0; i < 100; ++i)
			list.add(i);
	}

	@Test
	public void launchBenchmarks() throws RunnerException
	{
		Options opt = new OptionsBuilder().
			include(GuavaWithoutAssertsTest.class.getSimpleName()).
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
	public MultimapVerifier<?, ?> staticAssertThat()
	{
		return assertThat(name, value).isNotNull().isNotEmpty();
	}

	@Benchmark
	public MultimapVerifier<?, ?> verifiersAssertsDisabled()
	{
		return new Requirements().withAssertionsDisabled().assertThat(name, value).isNotNull().isNotEmpty();
	}

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() can
	// be faster than requireThat() even though it delegates to it
	@Benchmark
	public MultimapVerifier<?, ?> verifiersAssertsEnabled()
	{
		return new Requirements().withAssertionsEnabled().assertThat(name, value).isNotNull().isNotEmpty();
	}

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() can
	// be faster than requireThat() even though it delegates to it
	@Benchmark
	public MultimapVerifier<?, ?> verifiersRequireThat()
	{
		return new Requirements().requireThat(name, value).isNotNull().isNotEmpty();
	}

	@Benchmark
	public MultimapVerifier<?, ?> staticRequireThat()
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
			requireThat(name, nullMultimap).isNotNull();
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
			new Requirements().requireThat(name, nullMultimap).isNotNull();
		}
		catch (NullPointerException e)
		{
			bh.consume(e);
		}
	}
}
