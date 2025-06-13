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
module com.github.cowwoc.requirements11.jackson
{
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.github.cowwoc.requirements11.annotation;
	requires transitive com.github.cowwoc.requirements11.java;
	requires com.github.cowwoc.pouch.core;
	requires java.xml;

	exports com.github.cowwoc.requirements11.jackson;
	exports com.github.cowwoc.requirements11.jackson.internal.validator to com.github.cowwoc.requirements11.test;
	exports com.github.cowwoc.requirements11.jackson.validator;
}