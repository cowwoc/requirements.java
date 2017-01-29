/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark;

import java.util.concurrent.TimeUnit;
import static org.bitbucket.cowwoc.requirements.Requirements.assertThat;
import static org.bitbucket.cowwoc.requirements.Requirements.requireThat;
import org.bitbucket.cowwoc.requirements.StringRequirements;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

// Fields may not be final: http://hg.openjdk.java.net/code-tools/jmh/file/ed0a5f40acfb/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#l62
@SuppressWarnings("FieldMayBeFinal")
@State(Scope.Benchmark)
public class Main
{
	public static void main(String[] args) throws RunnerException
	{
		Options opt = new OptionsBuilder()
			.include(Main.class.getSimpleName())
			//			.jvmArgsAppend("-ea")
			.forks(3)
			.timeUnit(TimeUnit.NANOSECONDS)
			.mode(Mode.AverageTime)
			.build();

		new Runner(opt).run();
	}
	private String name = "name";
	private String value = "value";

	@Benchmark
	public void emptyMethod()
	{
	}

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() can
	// be faster than requireThat() even though it delegates to it
	@Benchmark
	public StringRequirements assertMethod()
	{
		return assertThat(value, name).isNotNull().isNotEmpty();
	}

	@Benchmark
	public StringRequirements requireMethod()
	{
		return requireThat(value, name).isNotNull().isNotEmpty();
	}
}
