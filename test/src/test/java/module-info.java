/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
@SuppressWarnings("JavaModuleNaming")
module com.github.cowwoc.requirements11.test
{
	requires com.google.common;
	requires org.testng;
	requires org.slf4j;
	requires com.github.cowwoc.pouch.core;
	requires com.github.cowwoc.requirements11.guava;
	requires com.github.cowwoc.requirements11.jackson;
	requires com.fasterxml.jackson.databind;
	requires static com.google.errorprone.annotations;

	exports com.github.cowwoc.requirements11.test to org.testng;
	exports com.github.cowwoc.requirements11.test.guava to org.testng;
	exports com.github.cowwoc.requirements11.test.jackson to org.testng;
	exports com.github.cowwoc.requirements11.test.java to org.testng;
	exports com.github.cowwoc.requirements11.test.java.internal.diff to org.testng;
	exports com.github.cowwoc.requirements11.test.java.internal.terminal to org.testng;
	exports com.github.cowwoc.requirements11.test.java.internal.util to org.testng;
	exports com.github.cowwoc.requirements11.test.java.terminal to org.testng;
	exports com.github.cowwoc.requirements11.test.sample to org.testng;
}