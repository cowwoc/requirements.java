/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

/**
 * An implementation of StringLengthRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpStringLengthRequirements extends AbstractNoOpNumberRequirements<StringLengthRequirements, Integer>
	implements StringLengthRequirements
{
	public static final NoOpStringLengthRequirements INSTANCE
		= new NoOpStringLengthRequirements();

	@Override
	public StringLengthRequirements usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
