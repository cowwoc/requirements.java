/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

/**
 * An implementation of MapSizeRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpMapSizeRequirements
	extends AbstractNoOpNumberRequirements<MapSizeRequirements, Integer>
	implements MapSizeRequirements
{
	public static final NoOpMapSizeRequirements INSTANCE = new NoOpMapSizeRequirements();

	@Override
	public MapSizeRequirements usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
