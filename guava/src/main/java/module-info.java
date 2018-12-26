/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
module org.bitbucket.cowwoc.requirements.guava
{
	requires com.google.common;
	requires transitive org.bitbucket.cowwoc.requirements.java;

	exports org.bitbucket.cowwoc.requirements.guava;
}
