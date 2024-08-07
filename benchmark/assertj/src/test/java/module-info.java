/*
 * Copyright 2019 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Benchmark of equivalent assertj functionality.
 */
@SuppressWarnings("JavaModuleNaming")
module com.github.cowwoc.requirements10.benchmark.assertj
{
	requires jmh.core;
	requires org.testng;
	requires org.assertj.core;

	exports com.github.cowwoc.requirements10.benchmark.assertj to org.testng;
	exports com.github.cowwoc.requirements10.benchmark.assertj.jmh_generated to jmh.core;
}