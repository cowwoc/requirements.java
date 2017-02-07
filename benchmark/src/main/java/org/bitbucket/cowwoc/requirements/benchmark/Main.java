/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Requirements;
import static org.bitbucket.cowwoc.requirements.core.Requirements.assertThat;
import static org.bitbucket.cowwoc.requirements.core.Requirements.requireThat;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.Verifiers;
import org.bitbucket.cowwoc.requirements.core.impl.CoreVerifiersImpl;
import org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding;
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
		Requirements.globalConfiguration().withTerminalEncoding(TerminalEncoding.NONE);

		new Runner(opt).run();
	}
	private String name = "name";
	private String value = "value";
	private List<Integer> list;

	public Main()
	{
		list = new ArrayList<>(100);
		for (int i = 0; i < 100; ++i)
			list.add(i);
	}

//	@Benchmark
//	public void emptyMethod()
//	{
//	}
//
	@Benchmark
	public StringVerifier staticAssertThat()
	{
		return assertThat(value, name).isNotNull().isNotEmpty();
	}

	@Benchmark
	public StringVerifier verifiersAssertsDisabled()
	{
		return new Verifiers().withAssertionsDisabled().assertThat(value, name).isNotNull().isNotEmpty();
	}

	@Benchmark
	public StringVerifier coreAssertsDisabled()
	{
		return new CoreVerifiersImpl().withAssertionsDisabled().assertThat(value, name).isNotNull().
			isNotEmpty();
	}

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() can
	// be faster than requireThat() even though it delegates to it
	@Benchmark
	public StringVerifier verifiersAssertsEnabled()
	{
		return new Verifiers().withAssertionsEnabled().assertThat(value, name).isNotNull().isNotEmpty();
	}

	@Benchmark
	public StringVerifier coreAssertsEnabled()
	{
		return new CoreVerifiersImpl().withAssertionsEnabled().assertThat(value, name).isNotNull().
			isNotEmpty();
	}

	// See http://stackoverflow.com/a/38862964/14731 for why assertThat() can
	// be faster than requireThat() even though it delegates to it
	@Benchmark
	public StringVerifier verifiersRequireThat()
	{
		return new Verifiers().requireThat(value, name).isNotNull().isNotEmpty();
	}

	@Benchmark
	public StringVerifier staticRequireThat()
	{
		return requireThat(value, name).isNotNull().isNotEmpty();
	}

	@Benchmark
	public CollectionVerifier<?> verifiersRequireThatContainsDuplicates()
	{
		return new Verifiers().requireThat(list, name).doesNotContainDuplicates();
	}

	@Benchmark
	public CollectionVerifier<?> verifiersAssertsDisabledContainsDuplicates()
	{
		return new Verifiers().withAssertionsDisabled().assertThat(list, name).
			doesNotContainDuplicates();
	}
}
