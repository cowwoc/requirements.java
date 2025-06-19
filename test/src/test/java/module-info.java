/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
@SuppressWarnings("JavaModuleNaming")
module io.github.cowwoc.requirements12.test
{
	requires io.github.cowwoc.requirements12.guava;
	requires io.github.cowwoc.requirements12.jackson;
	requires io.github.cowwoc.pouch.core;
	requires com.google.common;
	requires org.testng;
	requires org.slf4j;
	requires com.fasterxml.jackson.databind;
	requires static com.google.errorprone.annotations;

	exports io.github.cowwoc.requirements12.test to org.testng;
	exports io.github.cowwoc.requirements12.test.guava to org.testng;
	exports io.github.cowwoc.requirements12.test.jackson to org.testng;
	exports io.github.cowwoc.requirements12.test.java to org.testng;
	exports io.github.cowwoc.requirements12.test.java.internal.diff to org.testng;
	exports io.github.cowwoc.requirements12.test.java.internal.terminal to org.testng;
	exports io.github.cowwoc.requirements12.test.java.internal.util to org.testng;
	exports io.github.cowwoc.requirements12.test.java.terminal to org.testng;
	exports io.github.cowwoc.requirements12.test.sample to org.testng;
}