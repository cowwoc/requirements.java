/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.GlobalConfiguration;
import com.github.cowwoc.requirements.java.JavaValidators;

/**
 * The configuration of an application. A JVM may contain multiple applications.
 */
public interface ApplicationScope extends JvmScope
{
	/**
	 * @return the global configuration inherited by all validators
	 */
	GlobalConfiguration getGlobalConfiguration();

	/**
	 * @return creates validators used to check the arguments of validation methods
	 */
	JavaValidators getInternalValidators();

	@Override
	void close();
}