/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import org.bitbucket.cowwoc.requirements.spi.CollectionRequirementsSpi;
import org.bitbucket.cowwoc.requirements.spi.Isolatable;

/**
 * Verifies requirements of a {@link Collection} parameter.
 * <p>
 * @param <E> the type of elements in the collection
 * @author Gili Tzabari
 */
public interface CollectionRequirements<E>
	extends CollectionRequirementsSpi<CollectionRequirements<E>, E>,
	Isolatable<CollectionRequirements<E>>
{
}
