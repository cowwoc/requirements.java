/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.net.URI;
import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.ObjectRequirementsSpi;

/**
 * Interface needed for Requirements.assertThat().
 * <p>
 * @author Gili Tzabari
 */
public interface UriRequirements
	extends ObjectRequirementsSpi<UriRequirements, URI>, Isolatable<UriRequirements>
{
	/**
	 * Ensures that the parameter is absolute.
	 *
	 * @return this
	 * @throws IllegalArgumentException if parameter is not absolute
	 */
	UriRequirements isAbsolute();
}
