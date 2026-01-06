/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.pouch.core.WrappedCheckedException;
import io.github.cowwoc.requirements13.java.ValidationFailures;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.EqualityMethod;
import io.github.cowwoc.requirements13.java.internal.MutableStringMappers;
import io.github.cowwoc.requirements13.annotation.CheckReturnValue;

import java.util.function.Function;

/**
 * Determines the behavior of a validator.
 */
public final class MutableConfiguration
{
	private final MutableStringMappers stringMappers;
	// Per https://shipilev.net/blog/2014/safe-public-construction/ section "A final field was written" this
	// object is safe for publication because it contains at least one final field.
	private boolean cleanStackTrace;
	private boolean allowDiff;
	private EqualityMethod equalityMethod;
	private boolean recordStacktrace;
	private boolean throwOnFailure;
	private Function<Throwable, ? extends Throwable> exceptionTransformer;

	/**
	 * Creates a new configuration.
	 *
	 * @param cleanStackTrace      {@code true} if stack traces should be modified, {@code false} otherwise
	 * @param allowDiff            {@code true} if exception messages may include a diff that compares actual
	 *                             and expected values
	 * @param equalityMethod       the equality method that determines whether two values are equivalent
	 * @param stringMappers        the configuration used to map contextual values to a String
	 * @param recordStacktrace     {@code true} if the exception stack trace must be recorded when a validation
	 *                             failure occurs. If {@code false}, the exception type remains the same, but
	 *                             the stack trace points to the invocation of {@code elseGetException()}. Users
	 *                             who only plan to
	 *                             {@link ValidationFailures#getMessages() list of failure messages} instead of
	 *                             retrieving an exception may see a performance improvement if this value is
	 *                             set to {@code false}.
	 * @param throwOnFailure       {@code true} if an exception is thrown on validation failure.
	 * @param exceptionTransformer a function that transforms the validation exception into a suitable runtime
	 *                             exception or error
	 * @throws NullPointerException if any of the arguments are null
	 */
	private MutableConfiguration(boolean cleanStackTrace, boolean allowDiff,
		EqualityMethod equalityMethod, MutableStringMappers stringMappers, boolean recordStacktrace,
		boolean throwOnFailure, Function<Throwable, ? extends Throwable> exceptionTransformer)
	{
		this.cleanStackTrace = cleanStackTrace;
		this.allowDiff = allowDiff;
		this.equalityMethod = equalityMethod;
		this.stringMappers = stringMappers;
		this.recordStacktrace = recordStacktrace;
		this.throwOnFailure = throwOnFailure;
		this.exceptionTransformer = exceptionTransformer;
	}

	/**
	 * @param configuration the immutable configuration
	 * @return a mutable copy of the configuration
	 */
	public static MutableConfiguration from(Configuration configuration)
	{
		return new MutableConfiguration(configuration.cleanStackTrace(), configuration.allowDiff(),
			configuration.equalityMethod(), MutableStringMappers.from(configuration.stringMappers()),
			configuration.recordStacktrace(), configuration.throwOnFailure(), configuration.exceptionTransformer());
	}

	/**
	 * Returns an immutable copy of this configuration.
	 *
	 * @return an immutable copy of this configuration.
	 */
	@CheckReturnValue
	public Configuration toImmutable()
	{
		return new Configuration(cleanStackTrace, allowDiff, equalityMethod, stringMappers.toImmutable(),
			recordStacktrace, throwOnFailure, exceptionTransformer);
	}

	/**
	 * Returns {@code true} if this library should be excluded from exception stack traces, except when that
	 * also removes user code.
	 *
	 * @return {@code true} by default
	 */
	@CheckReturnValue
	public boolean cleanStackTrace()
	{
		return cleanStackTrace;
	}

	/**
	 * Specifies that this library should be excluded from exception stack traces, except when that also removes
	 * user code.
	 *
	 * @param cleanStackTrace {@code true} if stack traces should be modified, {@code false} otherwise
	 * @return this
	 */
	public MutableConfiguration cleanStackTrace(boolean cleanStackTrace)
	{
		this.cleanStackTrace = cleanStackTrace;
		return this;
	}

	/**
	 * Returns {@code true} if exception messages may include a diff that compares actual and expected values.
	 *
	 * @return {@code true} by default
	 */
	@CheckReturnValue
	public boolean allowDiff()
	{
		return allowDiff;
	}

	/**
	 * Specifies whether exception messages may include a diff that compares actual and expected values.
	 *
	 * @param allowDiff {@code true} if exceptions may include a diff, {@code false} otherwise
	 * @return this
	 */
	public MutableConfiguration allowDiff(boolean allowDiff)
	{
		this.allowDiff = allowDiff;
		return this;
	}

	/**
	 * Returns the equality method that determines whether two values are equivalent.
	 *
	 * @return the equality method that determines whether two values are equivalent
	 */
	@CheckReturnValue
	public EqualityMethod equalityMethod()
	{
		return equalityMethod;
	}

	/**
	 * Sets the equality method that determines whether two values are equivalent.
	 *
	 * @param equalityMethod the equality method to use
	 * @return this
	 * @throws NullPointerException if {@code equalityMethod} is null
	 */
	public MutableConfiguration equalityMethod(EqualityMethod equalityMethod)
	{
		if (equalityMethod == null)
			throw new NullPointerException("equalityMethod may not be null");
		this.equalityMethod = equalityMethod;
		return this;
	}

	/**
	 * Returns the configuration used to map contextual values to a String. Supports common types such as
	 * arrays, numbers, collections, maps, paths and exceptions.
	 *
	 * @return a function that takes an object and returns the {@code String} representation of the object
	 */
	@CheckReturnValue
	public MutableStringMappers stringMappers()
	{
		return stringMappers;
	}

	/**
	 * Returns {@code true} if exception stack traces should reference the code that triggers a validation
	 * failure. When set to {@code false}, the exception type remains unchanged, but the stack trace location is
	 * undefined. Users who only plan to {@link ValidationFailures#getMessages() list of failure messages}
	 * instead of exceptions may experience a performance improvement if this value is set to {@code false}.
	 *
	 * @return {@code true} if exceptions must be recorded when a validation failure occurs
	 */
	public boolean recordStacktrace()
	{
		return recordStacktrace;
	}

	/**
	 * Specifies whether exception stack traces should reference the code that triggers a validation failure.
	 * When set to {@code false}, the exception type remains unchanged, but the stack trace location is
	 * undefined. Users who only plan to {@link ValidationFailures#getMessages() list of failure messages}
	 * instead of exceptions may experience a performance improvement if this value is set to {@code false}.
	 *
	 * @param recordStacktrace {@code true} if exceptions must be recorded when a validation failure occurs
	 * @return this
	 */
	public MutableConfiguration recordStacktrace(boolean recordStacktrace)
	{
		this.recordStacktrace = recordStacktrace;
		return this;
	}

	/**
	 * Returns {@code true} if an exception is thrown on validation failure.
	 *
	 * @return {@code true} if an exception is thrown on validation failure
	 */
	@CheckReturnValue
	public boolean throwOnFailure()
	{
		return throwOnFailure;
	}

	/**
	 * Specifies whether an exception is thrown on validation failure.
	 *
	 * @param throwOnFailure {@code true} if an exception is thrown on validation failure
	 * @return this
	 */
	public MutableConfiguration throwOnFailure(boolean throwOnFailure)
	{
		this.throwOnFailure = throwOnFailure;
		return this;
	}

	/**
	 * Returns a function that transforms the validation exception into a suitable runtime exception or error.
	 * The input and output of the function must be subclasses of {@code RuntimeException} or {@code Error}. If
	 * the output is not, it is wrapped in a {@link WrappedCheckedException}. If the function returns
	 * {@code null} the input exception will be thrown.
	 *
	 * @return a function that transforms the validation exception
	 */
	@CheckReturnValue
	public Function<Throwable, ? extends Throwable> exceptionTransformer()
	{
		return exceptionTransformer;
	}

	/**
	 * Transforms validation exceptions before they are thrown or recorded.
	 * <p>
	 * The input and output of the function must be subclasses of {@code RuntimeException} or {@code Error}. If
	 * the output isn’t a subclass of these, it gets wrapped in a {@code WrappedCheckedException}.
	 * <p>
	 * If the function returns {@code null}, it’s treated as if it returned the input exception. Additionally,
	 * if the returned exception wraps a checked exception thrown by the validation method, it’s unwrapped and
	 * thrown or recorded as a checked exception.
	 *
	 * @param exceptionTransformer a function that transforms the validation exception
	 * @return this
	 * @throws NullPointerException if {@code exceptionTransformer} is null
	 */
	public MutableConfiguration exceptionTransformer(
		Function<Throwable, ? extends Throwable> exceptionTransformer)
	{
		if (exceptionTransformer == null)
			throw new NullPointerException("exceptionTransformer may not be null");
		this.exceptionTransformer = exceptionTransformer;
		return this;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 23 * hash + Boolean.hashCode(cleanStackTrace);
		hash = 23 * hash + Boolean.hashCode(allowDiff);
		hash = 23 * hash + equalityMethod.hashCode();
		hash = 23 * hash + stringMappers.hashCode();
		hash = 23 * hash + Boolean.hashCode(recordStacktrace);
		hash = 23 * hash + Boolean.hashCode(throwOnFailure);
		return 23 * hash + exceptionTransformer.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof MutableConfiguration other))
			return false;
		return other.cleanStackTrace == cleanStackTrace && other.allowDiff == allowDiff &&
			other.equalityMethod == equalityMethod && other.stringMappers.equals(stringMappers) &&
			other.recordStacktrace == recordStacktrace() && other.throwOnFailure == throwOnFailure &&
			other.exceptionTransformer == exceptionTransformer;
	}

	@Override
	public String toString()
	{
		return "cleanStackTrace: " + cleanStackTrace + ", allowDiff: " + allowDiff +
			", equalityMethod: " + equalityMethod + ", stringMappers: " + stringMappers +
			", recordStacktrace: " + recordStacktrace + ", throwOnFailure:" + throwOnFailure +
			", exceptionTransformer: " + exceptionTransformer;
	}
}