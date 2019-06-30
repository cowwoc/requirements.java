/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.internal.diff.DiffGenerator;
import org.bitbucket.cowwoc.requirements.java.internal.util.Exceptions;

import java.util.function.Supplier;

/**
 * The configuration of an application. A JVM may contain multiple applications.
 * <p>
 * Implementations must be thread-safe.
 */
public interface ApplicationScope extends JvmScope
{
	/**
	 * @return the default configuration (value may change with every invocation)
	 */
	Supplier<Configuration> getDefaultConfiguration();

	DiffGenerator getDiffGenerator();

	Exceptions getExceptions();

	/**
	 * @return a verifier that can be used to check a verifier's own parameters
	 */
	JavaRequirements getInternalVerifier();

	@Override
	void close();
}
