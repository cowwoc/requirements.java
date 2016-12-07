/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.Collection;
import org.bitbucket.cowwoc.requirements.core.spi.CollectionVerifierSpi;
import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;

/**
 * Verifies a {@link Collection} parameter.
 * <p>
 * @param <E> the type of elements in the collection
 * @author Gili Tzabari
 */
public interface CollectionVerifier<E>
	extends CollectionVerifierSpi<CollectionVerifier<E>, E>, Isolatable<CollectionVerifier<E>>
{
}
