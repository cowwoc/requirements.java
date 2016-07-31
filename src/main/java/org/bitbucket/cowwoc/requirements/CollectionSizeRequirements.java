/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;

/**
 * Verifies requirements of a {@link Collection#size()}.
 * <p>
 * @author Gili Tzabari
 */
@SuppressWarnings("MarkerInterface")
public interface CollectionSizeRequirements
	extends PrimitiveIntegerRequirements<CollectionSizeRequirements>
{
}
