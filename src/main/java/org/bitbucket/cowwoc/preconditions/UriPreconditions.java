/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.net.URI;

/**
 * Interface needed for Preconditions.assertThat().
 * <p>
 * @author Gili Tzabari
 */
public interface UriPreconditions extends ObjectPreconditions<UriPreconditions, URI>
{
	/**
	 * Ensures that the parameter is absolute.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is not absolute
	 */
	UriPreconditions isAbsolute() throws IllegalArgumentException;
}
