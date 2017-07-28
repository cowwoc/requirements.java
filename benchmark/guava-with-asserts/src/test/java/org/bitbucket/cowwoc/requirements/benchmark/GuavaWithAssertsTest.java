/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Verifiers;
import org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;
import static org.bitbucket.cowwoc.requirements.guava.Requirements.assertThat;
import static org.bitbucket.cowwoc.requirements.guava.Requirements.requireThat;
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

// Fields may not be final: http://hg.openjdk.java.net/code-tools/jmh/file/ed0a5f40acfb/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#l62
@SuppressWarnings("FieldMayBeFinal")
@State(Scope.Benchmark)
public class GuavaWithAssertsTest
{
	private String name = "name";
	private Multimap<String, String> value = HashMultimap.create();
	private Multimap<?, ?> nullMultimap = null;
	private List<Integer> list;

	public GuavaWithAssertsTest()
	{
		value.put("key", "value");
		list = new ArrayList<>(100);
		for (int i = 0; i < 100; ++i)
			list.add(i);
	}

	@Test
	public void launchBenchmarks() throws RunnerException
	{
		Options opt = new OptionsBuilder().
			include(GuavaWithAssertsTest.class.getSimpleName()).
			jvmArgsAppend("-ea").
			forks(3).
			timeUnit(TimeUnit.NANOSECONDS).
			mode(Mode.AverageTime).
			build();
		org.bitbucket.cowwoc.requirements.core.Requirements.globalConfiguration().
			withTerminalEncoding(TerminalEncoding.NONE);

		new Runner(opt).run();
	}

	@Benchmark
	public MultimapVerifier<?, ?> staticAssertThat()
	{
		return assertThat(name, value).isNotNull().isNotEmpty();
	}

	@Benchmark
	public MultimapVerifier<?, ?> verifiersAssertsDisabled()
	{
		return new Verifiers().withAssertionsDisabled().assertThat(name, value).isNotNull().isNotEmpty();
	}

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() can
	// be faster than requireThat() even though it delegates to it
	@Benchmark
	public MultimapVerifier<?, ?> verifiersAssertsEnabled()
	{
		return new Verifiers().withAssertionsEnabled().assertThat(name, value).isNotNull().isNotEmpty();
	}

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() can
	// be faster than requireThat() even though it delegates to it
	@Benchmark
	public MultimapVerifier<?, ?> verifiersRequireThat()
	{
		return new Verifiers().requireThat(name, value).isNotNull().isNotEmpty();
	}

	@Benchmark
	public MultimapVerifier<?, ?> staticRequireThat()
	{
		return requireThat(name, value).isNotNull().isNotEmpty();
	}

	@Benchmark
	public CollectionVerifier<?, ?> verifiersRequireThatDoesNotContainDuplicates()
	{
		return new Verifiers().requireThat(name, list).doesNotContainDuplicates();
	}

	@Benchmark
	public CollectionVerifier<?, ?> verifiersAssertsDisabledDoesNotContainDuplicates()
	{
		return new Verifiers().withAssertionsDisabled().assertThat(name, list).
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
			new Verifiers().requireThat(name, nullMultimap).isNotNull();
		}
		catch (NullPointerException e)
		{
			bh.consume(e);
		}
	}
}
