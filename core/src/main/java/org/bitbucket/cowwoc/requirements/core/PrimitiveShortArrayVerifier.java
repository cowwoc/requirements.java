/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.capabilities.ArrayCapabilities;

/**
 * Verifies a short array.
 *
 * @author Gili Tzabari
 */
@SuppressWarnings("MarkerInterface")
public interface PrimitiveShortArrayVerifier extends
	ArrayCapabilities<PrimitiveShortArrayVerifier, Short, short[]>
{
}
