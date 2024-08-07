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
module com.github.cowwoc.requirements10.jackson
{
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.github.cowwoc.requirements10.annotation;
	requires transitive com.github.cowwoc.requirements10.java;
	requires com.github.cowwoc.pouch.core;

	exports com.github.cowwoc.requirements10.jackson;
	exports com.github.cowwoc.requirements10.jackson.internal.validator to com.github.cowwoc.requirements10.test;
	exports com.github.cowwoc.requirements10.jackson.internal;
	exports com.github.cowwoc.requirements10.jackson.validator;
}