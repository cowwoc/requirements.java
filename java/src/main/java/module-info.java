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
module io.github.cowwoc.requirements12.java
{
	requires transitive io.github.cowwoc.requirements12.annotation;
	requires io.github.javadiffutils;
	requires io.github.cowwoc.pouch.core;
	requires org.slf4j;

	exports io.github.cowwoc.requirements12.java;
	exports io.github.cowwoc.requirements12.java.validator;
	exports io.github.cowwoc.requirements12.java.validator.component;

	exports io.github.cowwoc.requirements12.java.internal to
		io.github.cowwoc.requirements12.guava, io.github.cowwoc.requirements12.jackson,
		io.github.cowwoc.requirements12.test, io.github.cowwoc.requirements12.benchmark.java;
	exports io.github.cowwoc.requirements12.java.internal.scope to
		io.github.cowwoc.requirements12.guava, io.github.cowwoc.requirements12.jackson,
		io.github.cowwoc.requirements12.test;
	exports io.github.cowwoc.requirements12.java.internal.util to
		io.github.cowwoc.requirements12.guava, io.github.cowwoc.requirements12.jackson,
		io.github.cowwoc.requirements12.test;
	exports io.github.cowwoc.requirements12.java.internal.terminal to
		io.github.cowwoc.requirements12.test;
	exports io.github.cowwoc.requirements12.java.internal.validator to
		io.github.cowwoc.requirements12.guava, io.github.cowwoc.requirements12.jackson,
		io.github.cowwoc.requirements12.test, io.github.cowwoc.requirements12.benchmark.java;
	exports io.github.cowwoc.requirements12.java.internal.message to
		io.github.cowwoc.requirements12.guava, io.github.cowwoc.requirements12.test;
	exports io.github.cowwoc.requirements12.java.internal.message.diff to
		io.github.cowwoc.requirements12.test;
	exports io.github.cowwoc.requirements12.java.internal.message.section to
		io.github.cowwoc.requirements12.guava, io.github.cowwoc.requirements12.jackson,
		io.github.cowwoc.requirements12.test;
}