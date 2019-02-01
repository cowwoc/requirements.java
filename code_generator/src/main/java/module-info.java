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

	exports org.bitbucket.cowwoc.requirements.generator;
	exports org.bitbucket.cowwoc.requirements.generator.internal.secrets to org.bitbucket.cowwoc.requirements.maven_plugin;
}