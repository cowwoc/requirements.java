/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.ArrayCapabilities;

/**
 * Verifies a boolean array.
 */
@SuppressWarnings("MarkerInterface")
public interface PrimitiveBooleanArrayVerifier extends
	ArrayCapabilities<PrimitiveBooleanArrayVerifier, Boolean, boolean[]>
{
}
