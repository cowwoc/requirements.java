/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Classes used to verify
 * <a href="https://en.wikipedia.org/docs/Precondition">preconditions</a>,
 * <a href="https://en.wikipedia.org/docs/Postcondition">postconditions</a> and
 * <a href="https://en.wikipedia.org/docs/Invariant_(mathematics)#Invariants_in_computer_science">
 * invariants</a> of core Java types.
 */
module com.github.cowwoc.requirements.java
{
	requires transitive com.github.cowwoc.requirements.annotation;
	requires io.github.javadiffutils;
	requires com.github.cowwoc.pouch.core;

	exports com.github.cowwoc.requirements.java;
	exports com.github.cowwoc.requirements.java.terminal;
	exports com.github.cowwoc.requirements.java.type;
	exports com.github.cowwoc.requirements.java.type.part;

	exports com.github.cowwoc.requirements.java.internal.implementation.message to
		com.github.cowwoc.requirements.guava;
	exports com.github.cowwoc.requirements.java.internal.scope to com.github.cowwoc.requirements.guava,
		com.github.cowwoc.requirements.test, com.github.cowwoc.requirements.benchmark.java,
		com.github.cowwoc.requirements.benchmark.guava;
	exports com.github.cowwoc.requirements.java.internal.diff to com.github.cowwoc.requirements.test;
	exports com.github.cowwoc.requirements.java.internal.util to com.github.cowwoc.requirements.guava,
		com.github.cowwoc.requirements.test, com.github.cowwoc.requirements.benchmark.java;
	exports com.github.cowwoc.requirements.java.internal.terminal to
		com.github.cowwoc.requirements.benchmark.guava, com.github.cowwoc.requirements.benchmark.java,
		com.github.cowwoc.requirements.guava, com.github.cowwoc.requirements.test;
	exports com.github.cowwoc.requirements.java.internal.implementation to com.github.cowwoc.requirements.guava,
		com.github.cowwoc.requirements.test;
}