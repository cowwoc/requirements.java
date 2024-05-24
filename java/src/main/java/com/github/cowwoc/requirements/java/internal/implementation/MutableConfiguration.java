/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.pouch.core.WrappedCheckedException;
import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.EqualityMethod;
import com.github.cowwoc.requirements.java.MutableStringMappers;
import com.github.cowwoc.requirements.java.type.part.Validator;

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
	private boolean includeDiff;
	private EqualityMethod equalityMethod;
	private boolean lazyExceptions;
	private boolean throwOnFailure;
	private Function<Throwable, ? extends Throwable> exceptionTransformer;

	/**
	 * Creates a new configuration.
	 *
	 * @param cleanStackTrace      {@code true} if stack traces should be modified, {@code false} otherwise
	 * @param includeDiff          {@code true} if exception messages should include a diff that compares actual
	 *                             and expected values if they are too long
	 * @param equalityMethod       the equality method that determines whether two values are equivalent
	 * @param stringMappers        the configuration used to map contextual values to a String
	 * @param lazyExceptions       {@code true} if exception creation may be deferred until the user invokes
	 *                             {@link Validator#elseGetException()}. The exception matches the original
	 *                             failure, but the stack trace points to {@code elseGetException()} as the
	 *                             cause.
	 * @param throwOnFailure       {@code true} if an exception is thrown on validation failure.
	 * @param exceptionTransformer a function that transforms the validation exception into a suitable runtime
	 *                             exception or error
	 * @throws NullPointerException if any of the arguments are null
	 */
	private MutableConfiguration(boolean cleanStackTrace, boolean includeDiff,
		EqualityMethod equalityMethod, MutableStringMappers stringMappers, boolean lazyExceptions,
		boolean throwOnFailure, Function<Throwable, ? extends Throwable> exceptionTransformer)
	{
		this.cleanStackTrace = cleanStackTrace;
		this.includeDiff = includeDiff;
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
		return new MutableConfiguration(configuration.cleanStackTrace(), configuration.includeDiff(),
			configuration.equalityMethod(), MutableStringMappers.from(configuration.stringMappers()),
			configuration.lazyExceptions(), configuration.throwOnFailure(), configuration.exceptionTransformer());
	}

	/**
	 * Returns an immutable copy of this configuration.
	 *
	 * @return an immutable copy of this configuration.
	 */
	@CheckReturnValue
	public Configuration toImmutable()
	{
		return new Configuration(cleanStackTrace, includeDiff, equalityMethod, stringMappers.toImmutable(),
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
	 * Returns {@code true} if exception messages should include a diff that compares actual and expected values
	 * if they are too long. The threshold for "too long" is not specified.
	 *
	 * @return {@code true} by default
	 */
	@CheckReturnValue
	public boolean includeDiff()
	{
		return includeDiff;
	}

	/**
	 * Specifies whether exception messages should include a diff that compares actual and expected values if
	 * they are too long. The threshold for "too long" is not specified.
	 *
	 * @param includeDiff {@code true} if exception messages should include a diff, {@code false} otherwise
	 * @return this
	 */
	public MutableConfiguration includeDiff(boolean includeDiff)
	{
		this.includeDiff = includeDiff;
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
	 * Sets the equality method that determines whether two values are equivalent. The equality method is only
	 * used when both arguments are not null.
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
	 * Specifies whether exception creation may be deferred until the user invokes
	 * {@link Validator#elseGetException()}.
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
	 * Transform the validation exception into a suitable runtime exception or error. The input and output of
	 * the function must be subclasses of {@code RuntimeException} or {@code Error}. If the output is not, it is
	 * wrapped in a {@code WrappedCheckedException}. If the function returns {@code null} the input exception
	 * will be thrown.
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
		hash = 23 * hash + Boolean.hashCode(includeDiff);
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
		return other.cleanStackTrace == cleanStackTrace && other.includeDiff == includeDiff &&
			other.equalityMethod == equalityMethod && other.stringMappers.equals(stringMappers) &&
			other.exceptionTransformer == exceptionTransformer;
	}

	@Override
	public String toString()
	{
		return "Configuration[cleanStackTrace=" + cleanStackTrace + ", diffEnabled=" + includeDiff +
			", equalityMethod=" + equalityMethod + ", stringMappers=" + stringMappers +
			", exceptionTransformer=" + exceptionTransformer + "]";
	}
}