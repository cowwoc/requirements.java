/*
 * Copyright 2019 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Benchmark of the guava module.
 */
@SuppressWarnings("JavaModuleNaming")
module io.github.cowwoc.requirements13.benchmark.guava
{
	requires io.github.cowwoc.requirements13.guava;
	requires jmh.core;
	requires com.google.common;
	requires org.testng;
	requires static com.google.errorprone.annotations;

	exports io.github.cowwoc.requirements13.benchmark.guava to org.testng;
	exports io.github.cowwoc.requirements13.benchmark.guava.jmh_generated to jmh.core;
}