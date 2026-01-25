/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Classes used to validate
 * <a href="https://en.wikipedia.org/docs/Precondition">preconditions</a>,
 * <a href="https://en.wikipedia.org/docs/Postcondition">postconditions</a> and
 * <a href="https://en.wikipedia.org/docs/Invariant_(mathematics)#Invariants_in_computer_science">
 * invariants</a> of <a href="https://github.com/FasterXML/jackson-databind">Jackson</a> types.
 */
@SuppressWarnings("JavaModuleNaming")
module io.github.cowwoc.requirements13.jackson
{
	requires transitive io.github.cowwoc.requirements13.annotation;
	requires transitive io.github.cowwoc.requirements13.java;
	requires transitive tools.jackson.databind;
	requires io.github.cowwoc.pouch10.core;

	exports io.github.cowwoc.requirements13.jackson;
	exports io.github.cowwoc.requirements13.jackson.internal.validator to io.github.cowwoc.requirements13.test;
	exports io.github.cowwoc.requirements13.jackson.validator;
}