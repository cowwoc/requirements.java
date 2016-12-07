/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.core.spi.PrimitiveIntegerVerifierSpi;

/**
 * Verifies a container's size (e.g. size of a collection, length of a string).
 * <p>
 * @author Gili Tzabari
 */
public interface ContainerSizeVerifier
	extends PrimitiveIntegerVerifierSpi<ContainerSizeVerifier>, 
	Isolatable<ContainerSizeVerifier>
{
}
