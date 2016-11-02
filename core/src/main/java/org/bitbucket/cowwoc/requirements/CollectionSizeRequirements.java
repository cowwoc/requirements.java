/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.PrimitiveIntegerRequirementsSpi;

/**
 * Verifies requirements of a {@link Collection#size()}.
 * <p>
 * @author Gili Tzabari
 */
public interface CollectionSizeRequirements
	extends PrimitiveIntegerRequirementsSpi<CollectionSizeRequirements>,
	Isolatable<CollectionSizeRequirements>
{
}
