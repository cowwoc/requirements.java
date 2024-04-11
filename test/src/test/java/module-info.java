/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
module com.github.cowwoc.requirements.test
{
	requires com.google.common;
	requires org.testng;
	requires org.slf4j;
	requires com.github.cowwoc.requirements.guava;
	requires com.github.cowwoc.requirements.jackson;
	requires com.fasterxml.jackson.databind;

	exports com.github.cowwoc.requirements.test to org.testng;
	exports com.github.cowwoc.requirements.test.guava to org.testng;
	exports com.github.cowwoc.requirements.test.jackson to org.testng;
	exports com.github.cowwoc.requirements.test.java to org.testng;
	exports com.github.cowwoc.requirements.test.java.internal.diff to org.testng;
	exports com.github.cowwoc.requirements.test.java.internal.impl to org.testng;
	exports com.github.cowwoc.requirements.test.java.internal.terminal to org.testng;
	exports com.github.cowwoc.requirements.test.java.internal.util to org.testng;
	exports com.github.cowwoc.requirements.test.java.terminal to org.testng;
	exports com.github.cowwoc.requirements.test.sample to org.testng;
}