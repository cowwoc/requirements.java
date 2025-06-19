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
module io.github.cowwoc.requirements12.guava
{
	requires transitive io.github.cowwoc.requirements12.annotation;
	requires transitive io.github.cowwoc.requirements12.java;
	requires transitive com.google.common;

	exports io.github.cowwoc.requirements12.guava;
	exports io.github.cowwoc.requirements12.guava.internal.validator to io.github.cowwoc.requirements12.test;
	exports io.github.cowwoc.requirements12.guava.validator;
}