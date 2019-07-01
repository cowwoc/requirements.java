/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark.guava;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;
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

// Fields may not be final:
// http://hg.openjdk.java.net/code-tools/jmh/file/ed0a5f40acfb/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#l62
@State(Scope.Benchmark)
@SuppressWarnings({"FieldCanBeLocal", "CanBeFinal", "LongLine"})
public class GuavaTest
{
	private String name = "multimap";
	private Multimap<String, String> value = HashMultimap.create();

	@Test
	public void runBenchmarks() throws RunnerException
	{
		Options opt = new OptionsBuilder().
			include(GuavaTest.class.getSimpleName()).
			timeUnit(TimeUnit.NANOSECONDS).
			mode(Mode.AverageTime).
			warmupIterations(10).
			measurementIterations(20).
			addProfiler(GCProfiler.class).
			build();
		new Runner(opt).run();
	}

	@Benchmark
	public MultimapVerifier<String, String> requireThat()
	{
		return new Requirements().requireThat(value, name).isNotNull();
	}
}
