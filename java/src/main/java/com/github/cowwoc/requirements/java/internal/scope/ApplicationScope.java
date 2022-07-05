/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.internal.util.Exceptions;

import java.util.function.Supplier;

/**
 * The configuration of an application. A JVM may contain multiple applications.
 */
public interface ApplicationScope extends JvmScope
{
	/**
	 * @return the default configuration (value may change with every invocation)
	 */
	Supplier<Configuration> getDefaultConfiguration();

	/**
	 * @return an instance of {@code Exceptions}
	 */
	Exceptions getExceptions();

	/**
	 * @return a verifier that can be used to check a verifier's own parameters
	 */
	JavaRequirements getInternalVerifier();

	@Override
	void close();
}
