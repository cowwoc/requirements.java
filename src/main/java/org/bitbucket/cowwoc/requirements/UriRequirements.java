/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.net.URI;

/**
 * Interface needed for Requirements.assertThat().
 * <p>
 * @author Gili Tzabari
 */
public interface UriRequirements extends ObjectRequirements<UriRequirements, URI>
{
	/**
	 * Ensures that the parameter is absolute.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is not absolute
	 */
	UriRequirements isAbsolute() throws IllegalArgumentException;
}
