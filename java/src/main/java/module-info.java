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
@SuppressWarnings("JavaModuleNaming")
module com.github.cowwoc.requirements10.java
{
	requires transitive com.github.cowwoc.requirements10.annotation;
	requires io.github.javadiffutils;
	requires com.github.cowwoc.pouch.core;

	exports com.github.cowwoc.requirements10.java;
	exports com.github.cowwoc.requirements10.java.validator;
	exports com.github.cowwoc.requirements10.java.validator.component;

	exports com.github.cowwoc.requirements10.java.internal.scope to com.github.cowwoc.requirements10.guava,
		com.github.cowwoc.requirements10.jackson,
		com.github.cowwoc.requirements10.test, com.github.cowwoc.requirements10.benchmark.java,
		com.github.cowwoc.requirements10.benchmark.guava, com.github.cowwoc.requirements10.benchmark.jackson;
	exports com.github.cowwoc.requirements10.java.internal.util to com.github.cowwoc.requirements10.guava,
		com.github.cowwoc.requirements10.jackson, com.github.cowwoc.requirements10.test,
		com.github.cowwoc.requirements10.benchmark.java;
	exports com.github.cowwoc.requirements10.java.internal.terminal to
		com.github.cowwoc.requirements10.guava, com.github.cowwoc.requirements10.jackson,
		com.github.cowwoc.requirements10.test, com.github.cowwoc.requirements10.benchmark.java,
		com.github.cowwoc.requirements10.benchmark.guava, com.github.cowwoc.requirements10.benchmark.jackson;
	exports com.github.cowwoc.requirements10.java.internal.validator to com.github.cowwoc.requirements10.guava,
		com.github.cowwoc.requirements10.jackson, com.github.cowwoc.requirements10.test,
		com.github.cowwoc.requirements10.benchmark.java;
	exports com.github.cowwoc.requirements10.java.internal.message to
		com.github.cowwoc.requirements10.guava, com.github.cowwoc.requirements10.jackson,
		com.github.cowwoc.requirements10.test;
	exports com.github.cowwoc.requirements10.java.internal.message.diff to com.github.cowwoc.requirements10.test;
	exports com.github.cowwoc.requirements10.java.internal.message.section to
		com.github.cowwoc.requirements10.guava, com.github.cowwoc.requirements10.jackson,
		com.github.cowwoc.requirements10.test;
	exports com.github.cowwoc.requirements10.java.internal;
}