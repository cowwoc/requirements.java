/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.validator.component.ValidatorComponent;

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
	private final boolean allowDiff;
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
		this.allowDiff = true;
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
	 * @param allowDiff            {@code true} if exception messages may include a diff that compares actual
	 *                             and expected values
	 * @param equalityMethod       the equality method that determines whether two values are equivalent
	 * @param stringMappers        the configuration used to map contextual values to a String
	 * @param lazyExceptions       {@code true} if exception creation may be deferred until the user invokes
	 *                             {@link ValidatorComponent#elseGetException()}. The exception matches the
	 *                             original failure, but the stack trace points to {@code elseGetException()} as
	 *                             the cause.
	 * @param throwOnFailure       {@code true} if an exception is thrown on validation failure
	 * @param exceptionTransformer a function that transforms the validation exception before it is thrown or
	 *                             returned
	 */
	public Configuration(boolean cleanStackTrace, boolean allowDiff, EqualityMethod equalityMethod,
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
		this.allowDiff = allowDiff;
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
	 * Returns {@code true} if exception messages may include a diff that compares actual and expected values.
	 *
	 * @return {@code true} by default
	 */
	public boolean allowDiff()
	{
		return allowDiff;
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
	 * {@link ValidatorComponent#elseGetException()}. The exception type remains the same, but the stack trace
	 * points to {@code elseGetException()} as the cause. By deferring the exception creation, you can improve
	 * the performance if you only need a {@link ValidatorComponent#elseGetMessages() list of failure messages}
	 * instead of a full exception.
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
	 * Returns a function that transforms validation exceptions before they are thrown or recorded.
	 * <p>
	 * The input and output of the function must be subclasses of {@code RuntimeException} or {@code Error}. If
	 * the output isn’t a subclass of these, it gets wrapped in a {@code WrappedCheckedException}.
	 * <p>
	 * If the function returns {@code null}, it’s treated as if it returned the input exception. Additionally,
	 * if the returned exception wraps a checked exception thrown by the validation method, it’s unwrapped and
	 * thrown or recorded as a checked exception.
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
		hash = 23 * hash + Boolean.hashCode(allowDiff);
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
		return other.cleanStackTrace == cleanStackTrace && other.allowDiff == allowDiff &&
			other.equalityMethod == equalityMethod && other.stringMappers.equals(stringMappers) &&
			other.lazyExceptions == lazyExceptions() && other.throwOnFailure == throwOnFailure &&
			other.exceptionTransformer == exceptionTransformer;
	}

	@Override
	public String toString()
	{
		return "cleanStackTrace: " + cleanStackTrace + ", allowDiff: " + allowDiff +
			", equalityMethod: " + equalityMethod + ", stringMappers: " + stringMappers +
			", lazyExceptions: " + lazyExceptions + ", throwOnFailure:" + throwOnFailure +
			", exceptionTransformer: " + exceptionTransformer;
	}
}