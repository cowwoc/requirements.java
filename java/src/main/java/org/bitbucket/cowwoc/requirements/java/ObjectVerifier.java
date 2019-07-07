/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

/**
 * Verifies the requirements of an {@link Object}.
 *
 * @param <T> the type of the value being verified
 */
public interface ObjectVerifier<T> extends ExtensibleObjectVerifier<ObjectVerifier<T>, T>
{
}
