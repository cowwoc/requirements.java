/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Classes used to verify
 * <a href="https://en.wikipedia.org/wiki/Precondition">preconditions</a>,
 * <a href="https://en.wikipedia.org/wiki/Postcondition">postconditions</a> and
 * <a href="https://en.wikipedia.org/wiki/Invariant_(mathematics)#Invariants_in_computer_science">
 * invariants</a> of core Java types.
 */
module com.github.cowwoc.requirements.java
{
	requires transitive com.github.cowwoc.requirements.annotation;
	requires transitive com.github.cowwoc.requirements.natives;
	requires io.github.javadiffutils;
	requires org.slf4j;
	requires com.github.cowwoc.pouch.core;

	exports com.github.cowwoc.requirements.java;
	exports com.github.cowwoc.requirements.java.extension;

	exports com.github.cowwoc.requirements.java.internal to com.github.cowwoc.requirements.guava,
		com.github.cowwoc.requirements.test, com.github.cowwoc.requirements.benchmark.java,
		com.github.cowwoc.requirements.benchmark.guava;
	exports com.github.cowwoc.requirements.java.internal.scope to com.github.cowwoc.requirements.guava,
		com.github.cowwoc.requirements.test, com.github.cowwoc.requirements.benchmark.java,
		com.github.cowwoc.requirements.benchmark.guava;
	exports com.github.cowwoc.requirements.java.internal.terminal to com.github.cowwoc.requirements.test,
		com.github.cowwoc.requirements.benchmark.java;
	exports com.github.cowwoc.requirements.java.internal.diff to com.github.cowwoc.requirements.test;
	exports com.github.cowwoc.requirements.java.internal.util to com.github.cowwoc.requirements.guava,
		com.github.cowwoc.requirements.test, com.github.cowwoc.requirements;
	exports com.github.cowwoc.requirements.java.internal.extension to
		com.github.cowwoc.requirements.guava, com.github.cowwoc.requirements.test;
	exports com.github.cowwoc.requirements.java.internal.secrets to com.github.cowwoc.requirements.test;
}