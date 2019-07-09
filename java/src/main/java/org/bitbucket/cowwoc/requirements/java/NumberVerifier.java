/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

/**
 * Verifies the requirements of for a {@link Number}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 *
 * @param <T> the type of the value being verified
 */
public interface NumberVerifier<T extends Number & Comparable<? super T>>
	extends ExtensibleNumberVerifier<NumberVerifier<T>, T>
{
}
