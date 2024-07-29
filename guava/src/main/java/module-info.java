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
@SuppressWarnings("JavaModuleNaming")
module com.github.cowwoc.requirements10.guava
{
	requires transitive com.google.common;
	requires transitive com.github.cowwoc.requirements10.annotation;
	requires transitive com.github.cowwoc.requirements10.java;

	exports com.github.cowwoc.requirements10.guava;
	exports com.github.cowwoc.requirements10.guava.internal.validator to com.github.cowwoc.requirements10.test;
}