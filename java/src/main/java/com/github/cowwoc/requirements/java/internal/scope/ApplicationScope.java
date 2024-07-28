/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.GlobalConfiguration;
import com.github.cowwoc.requirements.java.JavaValidators;

/**
 * The configuration of an application. A process may contain multiple applications.
 */
public interface ApplicationScope extends ProcessScope
{
	/**
	 * @return the global configuration inherited by all validators
	 */
	GlobalConfiguration getGlobalConfiguration();

	/**
	 * @return a validator factory that creates validators to check the arguments of validation methods
	 */
	JavaValidators getInternalValidators();
}