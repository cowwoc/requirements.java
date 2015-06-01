/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of ObjectPreconditions.
 * <p>
 * @param <S> the type of preconditions that was instantiated
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
class ObjectPreconditionsImpl<S extends ObjectPreconditions<S, T>, T>
	implements ObjectPreconditions<S, T>
{
	protected final S self;
	@SuppressWarnings("ProtectedField")
	protected final T parameter;
	protected final String name;
	protected final Optional<Class<? extends RuntimeException>> exceptionOverride;

	/**
	 * Creates new ObjectPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	protected ObjectPreconditionsImpl(T parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (name.trim().isEmpty())
			throw new IllegalArgumentException("name may not be empty");
		if (exceptionOverride == null)
			throw new NullPointerException("exceptionOverride may not be null");
		@SuppressWarnings(
			{
				"unchecked", "LocalVariableHidesMemberVariable"
			})
		S self = (S) this;
		this.self = self;
		this.parameter = parameter;
		this.name = name;
		this.exceptionOverride = exceptionOverride;
	}

	@Override
	public S usingException(Class<? extends RuntimeException> exception)
	{
		@SuppressWarnings("unchecked")
		S result = (S) new ObjectPreconditionsImpl<>(parameter, name, Optional.ofNullable(exception));
		return result;
	}

	/**
	 * Throws an exception with the specified message and cause.
	 * <p>
	 * @param <E>       the type of exception to throw
	 * @param exception the type of exception to throw
	 * @param message   the exception message
	 * @return this
	 * @throws RuntimeException the exception
	 */
	protected <E extends RuntimeException> S throwException(Class<E> exception, String message)
		throws RuntimeException
	{
		try
		{
			Constructor<? extends RuntimeException> constructor = exceptionOverride.orElse(exception).
				getConstructor(String.class);
			throw constructor.newInstance(message);
		}
		catch (ReflectiveOperationException e)
		{
			throw new AssertionError(e);
		}
	}

	/**
	 * Throws an exception with the specified message and cause.
	 * <p>
	 * @param <E>       the type of exception to throw
	 * @param exception the type of exception to throw
	 * @param message   the exception message
	 * @param cause     the cause of the exception
	 * @return this
	 * @throws RuntimeException the exception
	 */
	protected <E extends RuntimeException> S throwException(Class<E> exception, String message,
		Throwable cause)
		throws RuntimeException
	{
		try
		{
			Constructor<? extends RuntimeException> constructor = exceptionOverride.orElse(exception).
				getConstructor(String.class, Throwable.class);
			RuntimeException instance = constructor.newInstance(message, cause);
			throw instance;
		}
		catch (ReflectiveOperationException e)
		{
			throw new AssertionError(e);
		}
	}

	@Override
	public S isNull() throws IllegalArgumentException
	{
		if (parameter == null)
			return self;
		return throwException(IllegalArgumentException.class, String.format("%s must be null", name));
	}

	@Override
	public S isNotNull() throws NullPointerException
	{
		if (parameter != null)
			return self;
		return throwException(NullPointerException.class, String.format("%s may not be null", name));
	}

	@Override
	public S isEqualTo(T value) throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be equal to %s. Was: %s", name, value, parameter));
	}

	@Override
	public S isEqualTo(T value, String name) throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be equal to %s (%s). Was: %s", this.name, value, name, parameter));
	}

	@Override
	public S isNotEqualTo(T value) throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not be equal to %s, but it was", name, value));
	}

	@Override
	public S isNotEqualTo(T value, String name) throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not be equal to %s (%s), but it was", this.name, value, name));
	}

	@Override
	public S isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(type, "type").isNotNull();
		if (type.isInstance(parameter))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be an instance of %s. Was: %s", name, type, parameter.getClass()));
	}
}
