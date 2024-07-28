/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Classes used to validate
 * <a href="https://en.wikipedia.org/docs/Precondition">preconditions</a>,
 * <a href="https://en.wikipedia.org/docs/Postcondition">postconditions</a> and
 * <a href="https://en.wikipedia.org/docs/Invariant_(mathematics)#Invariants_in_computer_science">
 * invariants</a> of <a href="https://github.com/google/guava">Guava</a> types.
 */
module com.github.cowwoc.requirements.guava
{
	requires transitive com.google.common;
	requires transitive com.github.cowwoc.requirements.annotation;
	requires transitive com.github.cowwoc.requirements.java;

	exports com.github.cowwoc.requirements.guava;
	exports com.github.cowwoc.requirements.guava.internal.validator to com.github.cowwoc.requirements.test;
}