/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
@SuppressWarnings("JavaModuleNaming")
module io.github.cowwoc.requirements13.test
{
	requires io.github.cowwoc.requirements13.guava;
	requires io.github.cowwoc.requirements13.jackson;
	requires io.github.cowwoc.pouch10.core;
	requires com.google.common;
	requires org.testng;
	requires org.slf4j;
	requires tools.jackson.databind;
	requires static com.google.errorprone.annotations;

	exports io.github.cowwoc.requirements13.test to org.testng;
	exports io.github.cowwoc.requirements13.test.guava to org.testng;
	exports io.github.cowwoc.requirements13.test.jackson to org.testng;
	exports io.github.cowwoc.requirements13.test.java to org.testng;
	exports io.github.cowwoc.requirements13.test.java.internal.diff to org.testng;
	exports io.github.cowwoc.requirements13.test.java.internal.terminal to org.testng;
	exports io.github.cowwoc.requirements13.test.java.internal.util to org.testng;
	exports io.github.cowwoc.requirements13.test.java.terminal to org.testng;
	exports io.github.cowwoc.requirements13.test.sample to org.testng;
}