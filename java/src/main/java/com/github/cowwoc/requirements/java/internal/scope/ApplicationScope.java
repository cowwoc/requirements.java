/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.ThreadConfiguration;

import java.util.function.Supplier;

/**
 * The configuration of an application. A JVM may contain multiple applications.
 */
public interface ApplicationScope extends JvmScope
{
	/**
	 * @return the global configuration inherited by all verifiers
	 */
	GlobalConfiguration getGlobalConfiguration();

	/**
	 * @return the default configuration (value may change with every invocation)
	 */
	Supplier<Configuration> getDefaultConfiguration();

	/**
	 * @return the configuration shared by all verifiers invoked by the current thread
	 */
	Supplier<ThreadConfiguration> getThreadConfiguration();

	/**
	 * @return a verifier that can be used to check a verifier's own parameters
	 */
	JavaRequirements getInternalVerifier();

	@Override
	void close();
}
