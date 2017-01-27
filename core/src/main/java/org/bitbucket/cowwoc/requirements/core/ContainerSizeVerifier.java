/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.ext.PrimitiveIntegerVerifierExtension;

/**
 * Verifies a container's size (e.g. size of a collection, length of a string).
 *
 * @author Gili Tzabari
 */
@SuppressWarnings("MarkerInterface")
public interface ContainerSizeVerifier
	extends PrimitiveIntegerVerifierExtension<ContainerSizeVerifier>
{
}
