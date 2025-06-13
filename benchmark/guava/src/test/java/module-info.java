/*
 * Copyright 2019 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Benchmark of the guava module.
 */
@SuppressWarnings("JavaModuleNaming")
module com.github.cowwoc.requirements11.benchmark.guava
{
	requires jmh.core;
	requires com.google.common;
	requires org.testng;
	requires com.github.cowwoc.requirements11.guava;
	requires static com.google.errorprone.annotations;

	exports com.github.cowwoc.requirements11.benchmark.guava to org.testng;
	exports com.github.cowwoc.requirements11.benchmark.guava.jmh_generated to jmh.core;
}