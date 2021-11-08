/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
module com.github.cowwoc.requirements.test
{
	requires com.google.common;
	requires org.testng;
	requires com.github.cowwoc.requirements.natives;
	requires com.github.cowwoc.requirements.guava;

	exports com.github.cowwoc.requirements.java.internal.diff.test to org.testng;
	exports com.github.cowwoc.requirements.java.test to org.testng;
}