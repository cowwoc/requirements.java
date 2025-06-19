/*
 * Copyright 2019 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Benchmark of the Java module.
 */
@SuppressWarnings("JavaModuleNaming")
module io.github.cowwoc.requirements12.benchmark.java
{
	requires io.github.cowwoc.requirements12.java;
	requires jmh.core;
	requires org.testng;

	exports io.github.cowwoc.requirements12.benchmark.java to org.testng;
	exports io.github.cowwoc.requirements12.benchmark.java.jmh_generated to jmh.core;
}