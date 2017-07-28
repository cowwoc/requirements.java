/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark;

import java.util.concurrent.TimeUnit;
import org.bitbucket.cowwoc.requirements.core.Requirements;
import static org.bitbucket.cowwoc.requirements.core.Requirements.assertThat;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.testng.annotations.Test;

// Fields may not be final: http://hg.openjdk.java.net/code-tools/jmh/file/ed0a5f40acfb/jmh-samples/src/main/java/org/openjdk/jmh/samples/JMHSample_10_ConstantFold.java#l62
@SuppressWarnings("FieldMayBeFinal")
@State(Scope.Benchmark)
public class CoreWithAssertsTest
{
	private String name = "name";
	private String value = "value";

	@Test
	public void launchBenchmarks() throws RunnerException
	{
		Options opt = new OptionsBuilder().
			include(CoreWithAssertsTest.class.getSimpleName()).
			jvmArgsAppend("-ea").
			forks(3).
			timeUnit(TimeUnit.NANOSECONDS).
			mode(Mode.AverageTime).
			build();
		Requirements.globalConfiguration().withTerminalEncoding(TerminalEncoding.NONE);

		new Runner(opt).run();
	}

	@Benchmark
	public StringVerifier staticAssertThat()
	{
		return assertThat(name, value).isNotNull().isNotEmpty();
	}
}
