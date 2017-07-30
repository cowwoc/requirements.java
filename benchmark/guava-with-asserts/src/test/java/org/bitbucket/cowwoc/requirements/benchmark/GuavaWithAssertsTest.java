/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.concurrent.TimeUnit;
import org.bitbucket.cowwoc.requirements.core.Requirements;
import org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;
import static org.bitbucket.cowwoc.requirements.guava.Requirements.assertThat;
import org.openjdk.jmh.annotations.Benchmark;
import static org.openjdk.jmh.annotations.Level.Iteration;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
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
	private String name = "multimap";
	private Multimap<String, String> value = HashMultimap.create();

	public GuavaWithAssertsTest()
	{
		value.put("key", "value");
		value.put("key", "value");
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
		new Runner(opt).run();
	}

	@Setup(Iteration)
	public void setupFork()
	{
		Requirements.globalConfiguration().withTerminalEncoding(TerminalEncoding.NONE);
	}

	@Benchmark
	public MultimapVerifier<?, ?> staticAssertThat()
	{
		return assertThat(name, value).isNotNull().isNotEmpty();
	}
}
