/*
 * Copyright 2019 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Benchmark of equivalent assertj functionality.
 */
@SuppressWarnings("JavaModuleNaming")
module io.github.cowwoc.requirements12.benchmark.assertj
{
	requires jmh.core;
	requires org.testng;
	requires org.assertj.core;

	exports io.github.cowwoc.requirements12.benchmark.assertj to org.testng;
	exports io.github.cowwoc.requirements12.benchmark.assertj.jmh_generated to jmh.core;
}