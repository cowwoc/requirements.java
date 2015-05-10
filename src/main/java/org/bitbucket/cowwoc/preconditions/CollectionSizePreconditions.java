/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Collection;

/**
 * Verifies preconditions of a {@link Collection#size()}.
 * <p>
 * @author Gili Tzabari
 */
@SuppressWarnings("MarkerInterface")
public interface CollectionSizePreconditions
	extends PrimitiveIntegerPreconditions<CollectionSizePreconditions>
{
}
