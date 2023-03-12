/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Code generators.
 */
module com.github.cowwoc.requirements.generator
{
	requires org.slf4j;
	requires com.github.javaparser.core;

	exports com.github.cowwoc.requirements.generator;
	exports com.github.cowwoc.requirements.generator.internal.secret to com.github.cowwoc.requirements.maven_plugin;
}