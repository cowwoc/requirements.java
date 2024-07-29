/*
 * Copyright 2019 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Benchmark of the Java module.
 */
@SuppressWarnings("JavaModuleNaming")
module com.github.cowwoc.requirements10.benchmark.java
{
	requires jmh.core;
	requires org.testng;
	requires com.github.cowwoc.requirements10.java;

	exports com.github.cowwoc.requirements10.benchmark.java to org.testng;
	exports com.github.cowwoc.requirements10.benchmark.java.jmh_generated to jmh.core;
}