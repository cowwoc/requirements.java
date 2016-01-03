/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.base.Strings;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;
import static org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation.DELETE;
import static org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation.EQUAL;
import static org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation.INSERT;

/**
 * Abstract implementation of ObjectPreconditions.
 * <p>
 * @param <S> the type of preconditions that was instantiated
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public abstract class AbstractObjectPreconditions<S extends ObjectPreconditions<S, T>, T>
	implements ObjectPreconditions<S, T>
{
	/**
	 * Calculates the difference between two strings.
	 * <p>
	 * @param expected the expected value
	 * @param actual   the actual value
	 */
	private static void diff(StringBuilder expected, StringBuilder actual)
	{
		DiffMatchPatch diffFactory = new DiffMatchPatch();
		LinkedList<Diff> diff = diffFactory.diffMain(expected.toString(), actual.toString());
		diffFactory.diffCleanupSemantic(diff);
		int expectedOffset = 0;
		int actualOffset = 0;
		for (Diff component: diff)
		{
			switch (component.operation)
			{
				case EQUAL:
				{
					expectedOffset += component.text.length();
					actualOffset += component.text.length();
					break;
				}
				case DELETE:
				{
					expected.insert(expectedOffset, "<");

					// Skip '<' + text
					expectedOffset += 1 + component.text.length();

					expected.insert(expectedOffset, ">");

					// Skip '>'
					++expectedOffset;

					actual.insert(actualOffset, "<" + Strings.repeat(" ", component.text.length()) + ">");

					// Skip '<' + text + '>'
					actualOffset += "<".length() + component.text.length() + ">".length();
					break;
				}
				case INSERT:
				{
					actual.insert(actualOffset, "<");

					// Skip '<' + text
					actualOffset += 1 + component.text.length();

					actual.insert(actualOffset, ">");

					// Skip '>'
					++actualOffset;

					expected.insert(expectedOffset, "<" + Strings.repeat(" ", component.text.length()) + ">");

					// Skip '<' + text + '>'
					expectedOffset += "<".length() + component.text.length() + ">".length();
					break;
				}
				default:
					throw new AssertionError(component.operation.name());
			}
		}
	}
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
	protected AbstractObjectPreconditions(T parameter, String name,
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
		Optional<Class<? extends RuntimeException>> newExceptionOverride = Optional.
			ofNullable(exception);
		if (newExceptionOverride.equals(exceptionOverride))
			return self;
		return valueOf(parameter, name, newExceptionOverride);

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
				getConstructor(String.class
				);
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
		Throwable cause) throws RuntimeException
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

		return throwException(IllegalArgumentException.class, String.format("%s must be null.\n" +
			"Actual: %s", name, parameter));
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
		StringBuilder expected = new StringBuilder(value.toString());
		StringBuilder actual = new StringBuilder(parameter.toString());
		diff(expected, actual);

		return throwException(IllegalArgumentException.class,
			String.format("%s had an unexpected value.\n" +
				"Expected: %s\n" +
				"Actual  : %s", name, expected, actual));
	}

	@Override
	public S isEqualTo(T value, String name) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return self;
		StringBuilder expected = new StringBuilder(value.toString());
		StringBuilder actual = new StringBuilder(parameter.toString());
		diff(expected, actual);

		return throwException(IllegalArgumentException.class,
			String.format("%s must be equal to %s.\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, expected, actual));
	}

	@Override
	public S isNotEqualTo(T value) throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return self;

		return throwException(IllegalArgumentException.class,
			String.format("%s must not be equal to %s", name, value));
	}

	@Override
	public S isNotEqualTo(T value, String name) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return self;

		return throwException(IllegalArgumentException.class,
			String.format("%s must not be equal to %s.\n" +
				"Actual: %s", this.name, name, value));
	}

	@Override
	public S isInstanceOf(Class<?> type) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(type, "type").isNotNull();
		if (type.isInstance(parameter))
			return self;

		return throwException(IllegalArgumentException.class,
			String.format("%s must be an instance of %s.\n" +
				"Actual: %s", name, type, parameter.getClass()));
	}

	@Override
	public S isolate(Consumer<S> consumer)
	{
		consumer.accept(self);
		return self;
	}

	/**
	 * Returns an instance of the current implementation.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 * @return an instance of the current implementation
	 */
	protected abstract S valueOf(T parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride);
}
