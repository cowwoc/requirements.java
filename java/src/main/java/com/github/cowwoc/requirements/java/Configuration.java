/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.pouch.core.WrappedCheckedException;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.function.Function;

/**
 * Determines the behavior of a validator.
 */
public final class Configuration
{
	/**
	 * The default configuration.
	 */
	public static final Configuration DEFAULT = new Configuration();
	private final StringMappers stringMappers;
	private final boolean cleanStackTrace;
	private final boolean includeDiff;
	private final EqualityMethod equalityMethod;
	private final boolean lazyExceptions;
	private final boolean throwOnFailure;
	private final Function<Throwable, ? extends Throwable> exceptionTransformer;

	/**
	 * Creates a new configuration that:
	 * <ul>
	 * <li>Has an empty context.</li>
	 * <li>Throws an exception on failure.</li>
	 * <li>Uses {@link Object#equals(Object)} to determine whether two objects are equal.</li>
	 * <li>Excludes this library from exception stack traces.</li>
	 * <li>Constructs exceptions lazily.</li>
	 * <li>May include a diff that compares the actual and expected values.</li>
	 * </ul>
	 */
	public Configuration()
	{
		this.cleanStackTrace = true;
		this.includeDiff = true;
		this.equalityMethod = EqualityMethod.OBJECT;
		this.throwOnFailure = true;
		this.lazyExceptions = true;
		this.exceptionTransformer = t -> t;
		this.stringMappers = StringMappers.DEFAULT;
	}

	/**
	 * Creates a new configuration.
	 *
	 * @param cleanStackTrace      {@code true} if stack traces may be modified, {@code false} otherwise
	 * @param includeDiff          {@code true} if exception messages may include a diff that compares actual
	 *                             and expected values if they are too long
	 * @param equalityMethod       the equality method that determines whether two values are equivalent
	 * @param stringMappers        the configuration used to map contextual values to a String
	 * @param lazyExceptions       {@code true} if exception creation may be deferred until the user invokes
	 *                             {@link Validator#elseGetException()}. The exception matches the original
	 *                             failure, but the stack trace points to {@code elseGetException()} as the
	 *                             cause.
	 * @param throwOnFailure       {@code true} if an exception is thrown on validation failure
	 * @param exceptionTransformer a function that transforms the validation exception before it is thrown or
	 *                             returned
	 * @throws NullPointerException if any of the arguments are null
	 */
	public Configuration(boolean cleanStackTrace, boolean includeDiff, EqualityMethod equalityMethod,
		StringMappers stringMappers, boolean lazyExceptions, boolean throwOnFailure,
		Function<Throwable, ? extends Throwable> exceptionTransformer)
	{
		if (equalityMethod == null)
			throw new NullPointerException("equityMethod may not be null");
		if (stringMappers == null)
			throw new NullPointerException("stringMappers may not be null");
		if (exceptionTransformer == null)
			throw new NullPointerException("exceptionTransformer  may not be null");
		this.cleanStackTrace = cleanStackTrace;
		this.includeDiff = includeDiff;
		this.equalityMethod = equalityMethod;
		this.stringMappers = stringMappers;
		this.lazyExceptions = lazyExceptions;
		this.throwOnFailure = throwOnFailure;
		this.exceptionTransformer = exceptionTransformer;
	}

	/**
	 * Returns {@code true} if this library should be excluded from exception stack traces, except when that
	 * also removes user code.
	 *
	 * @return {@code true} by default
	 */
	public boolean cleanStackTrace()
	{
		return cleanStackTrace;
	}

	/**
	 * Returns {@code true} if exception messages should include a diff that compares actual and expected values
	 * if they are too long. The threshold for "too long" is not specified.
	 *
	 * @return {@code true} by default
	 */
	public boolean includeDiff()
	{
		return includeDiff;
	}

	/**
	 * Returns the equality method that determines whether two values are equivalent.
	 *
	 * @return the equality method that determines whether two values are equivalent
	 */
	public EqualityMethod equalityMethod()
	{
		return equalityMethod;
	}

	/**
	 * Returns the configuration used to map contextual values to a String. Supports common types such as
	 * arrays, numbers, collections, maps, paths and exceptions.
	 *
	 * @return a function that takes an object and returns the {@code String} representation of the object
	 */
	public StringMappers stringMappers()
	{
		return stringMappers;
	}

	/**
	 * Returns {@code true} if exception creation may be deferred until the user invokes
	 * {@link Validator#elseGetException()}. The exception type remains the same, but the stack trace points to
	 * {@code elseGetException()} as the cause. By deferring the exception creation, you can improve the
	 * performance if you only need a {@link Validator#elseGetMessages() list of failure messages} instead of a
	 * full exception.
	 *
	 * @return {@code true} if exceptions may be created on demand instead of when a validation failure occurs
	 */
	public boolean lazyExceptions()
	{
		return lazyExceptions;
	}

	/**
	 * Returns {@code true} if an exception is thrown on validation failure.
	 *
	 * @return {@code true} if an exception is thrown on validation failure
	 */
	public boolean throwOnFailure()
	{
		return throwOnFailure;
	}

	/**
	 * Returns a function that transforms the validation exception into a suitable runtime exception or error.
	 * The input and output of the function must be subclasses of {@code RuntimeException} or {@code Error}. If
	 * the output is not, it is wrapped in a {@link WrappedCheckedException}. If the function returns
	 * {@code null} the input exception will be thrown.
	 *
	 * @return a function that transforms the validation exception
	 */
	public Function<Throwable, ? extends Throwable> exceptionTransformer()
	{
		return exceptionTransformer;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 23 * hash + Boolean.hashCode(cleanStackTrace);
		hash = 23 * hash + Boolean.hashCode(includeDiff);
		hash = 23 * hash + equalityMethod.hashCode();
		hash = 23 * hash + stringMappers.hashCode();
		hash = 23 * hash + Boolean.hashCode(lazyExceptions);
		hash = 23 * hash + Boolean.hashCode(throwOnFailure);
		return 23 * hash + exceptionTransformer.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof Configuration other))
			return false;
		return other.cleanStackTrace == cleanStackTrace && other.includeDiff == includeDiff &&
			other.equalityMethod == equalityMethod && other.stringMappers.equals(stringMappers) &&
			other.lazyExceptions == lazyExceptions() && other.throwOnFailure == throwOnFailure &&
			other.exceptionTransformer == exceptionTransformer;
	}

	@Override
	public String toString()
	{
		return "cleanStackTrace: " + cleanStackTrace + ", diffEnabled: " + includeDiff +
			", equalityMethod: " + equalityMethod + ", stringMappers: " + stringMappers +
			", lazyExceptions: " + lazyExceptions + ", throwOnFailure:" + throwOnFailure +
			", exceptionTransformer: " + exceptionTransformer;
	}
}