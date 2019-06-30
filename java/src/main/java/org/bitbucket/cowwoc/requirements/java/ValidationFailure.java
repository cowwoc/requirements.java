/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

/**
 * A failed validation.
 */
public interface ValidationFailure
{
	/**
	 * Returns the exception message associated with the failure.
	 *
	 * @return the exception message associated with the failure
	 */
	String getMessage();
}
