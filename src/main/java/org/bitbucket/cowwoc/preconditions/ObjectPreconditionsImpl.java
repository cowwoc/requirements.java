/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Optional;

/**
 * Default implementation of ObjectPreconditions.
 * <p>
 * @param <S> the type of preconditions that was instantiated
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
final class ObjectPreconditionsImpl<S extends ObjectPreconditions<S, T>, T>
	extends AbstractObjectPreconditions<S, T>
	implements ObjectPreconditions<S, T>
{
	/**
	 * Creates new ObjectPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	ObjectPreconditionsImpl(T parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	protected S valueOf(T parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
	{
		@SuppressWarnings("unchecked")
		S result = (S) new ObjectPreconditionsImpl<>(parameter, name, exceptionOverride);
		return result;
	}
}
