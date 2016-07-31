/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

/**
 * An implementation of CollectionSizeRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpCollectionSizeRequirements
	extends AbstractNoOpNumberRequirements<CollectionSizeRequirements, Integer>
	implements CollectionSizeRequirements
{
	public static final NoOpCollectionSizeRequirements INSTANCE
		= new NoOpCollectionSizeRequirements();

	@Override
	public CollectionSizeRequirements usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
