/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Code generators.
 */
module org.bitbucket.cowwoc.requirements.generator
{
	requires org.slf4j;
	requires com.github.javaparser.core;

	exports org.bitbucket.cowwoc.requirements.generator;
	exports org.bitbucket.cowwoc.requirements.generator.internal.secrets to
		org.bitbucket.cowwoc.requirements.maven_plugin;
	opens delegates to org.bitbucket.cowwoc.requirements.maven_plugin;
}