/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.capabilities.ArrayCapabilities;

/**
 * Verifies a byte array.
 */
@SuppressWarnings("MarkerInterface")
public interface PrimitiveByteArrayVerifier extends
	ArrayCapabilities<PrimitiveByteArrayVerifier, Byte, byte[]>
{
}
