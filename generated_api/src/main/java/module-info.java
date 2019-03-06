/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * The {@code Requirements}, {@code DefaultRequirements} endpoints. The contents of these classes depend on
 * which plugins are enabled.
 */
module org.bitbucket.cowwoc.requirements
{
	requires org.bitbucket.cowwoc.requirements.java;
	requires org.bitbucket.cowwoc.requirements.guava;
	requires com.google.common;

	exports org.bitbucket.cowwoc.requirements;
}