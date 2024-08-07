/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.pouch.core.WrappedCheckedException;
import com.github.cowwoc.requirements10.annotation.CheckReturnValue;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.EqualityMethod;
import com.github.cowwoc.requirements10.java.internal.MutableStringMappers;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

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
	private boolean lazyExceptions;
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
	 * @param lazyExceptions       {@code true} if exception creation may be deferred until the user invokes
	 *                             {@link ValidatorComponent#elseGetException()}. The exception matches the
	 *                             original failure, but the stack trace points to {@code elseGetException()} as
	 *                             the cause.
	 * @param throwOnFailure       {@code true} if an exception is thrown on validation failure.
	 * @param exceptionTransformer a function that transforms the validation exception into a suitable runtime
	 *                             exception or error
	 * @throws NullPointerException if any of the arguments are null
	 */
	private MutableConfiguration(boolean cleanStackTrace, boolean allowDiff,
		EqualityMethod equalityMethod, MutableStringMappers stringMappers, boolean lazyExceptions,
		boolean throwOnFailure, Function<Throwable, ? extends Throwable> exceptionTransformer)
	{
		this.cleanStackTrace = cleanStackTrace;
		this.allowDiff = allowDiff;
		this.equalityMethod = equalityMethod;
		this.stringMappers = stringMappers;
		this.lazyExceptions = lazyExceptions;
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
			lazyExceptions, throwOnFailure, exceptionTransformer);
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
	 * Specifies whether exception creation may be deferred until the user invokes
	 * {@link ValidatorComponent#elseGetException()}.
	 *
	 * @param lazyExceptions {@code true} if exceptions may be created on demand instead of when a validation
	 *                       failure occurs
	 * @return this
	 */
	public MutableConfiguration lazyExceptions(boolean lazyExceptions)
	{
		this.lazyExceptions = lazyExceptions;
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
			other.exceptionTransformer == exceptionTransformer;
	}

	@Override
	public String toString()
	{
		return "cleanStackTrace: " + cleanStackTrace + ", allowDiff: " + allowDiff +
			", equalityMethod: " + equalityMethod + ", stringMappers: " + stringMappers +
			", exceptionTransformer: " + exceptionTransformer;
	}
}