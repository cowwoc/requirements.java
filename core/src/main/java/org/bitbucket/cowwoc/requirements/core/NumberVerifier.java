/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.capabilities.NumberCapabilities;

/**
 * Verifier for a {@link Number}.
 *
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
@SuppressWarnings("MarkerInterface")
public interface NumberVerifier<T extends Number & Comparable<? super T>>
	extends NumberCapabilities<NumberVerifier<T>, T>
{
}
