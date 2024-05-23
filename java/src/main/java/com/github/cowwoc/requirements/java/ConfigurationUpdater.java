/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.function.Function;

/**
 * Updates the configuration used by new validators.
 */
public interface ConfigurationUpdater extends AutoCloseable
{
	/**
	 * Returns {@code true} if this library should be excluded from exception stack traces, except when that
	 * also removes user code.
	 *
	 * @return {@code true} by default
	 */
	@CheckReturnValue
	boolean cleanStackTrace();

	/**
	 * Specifies that this library should be excluded from exception stack traces, except when that also removes
	 * user code.
	 *
	 * @param cleanStackTrace {@code true} if stack traces should be modified, {@code false} otherwise
	 * @return this
	 */
	ConfigurationUpdater cleanStackTrace(boolean cleanStackTrace);

	/**
	 * Returns {@code true} if exception messages should include a diff that compares actual and expected values
	 * if they are too long. The threshold for "too long" is not specified.
	 *
	 * @return {@code true} by default
	 */
	@CheckReturnValue
	boolean includeDiff();

	/**
	 * Specifies whether exception messages should include a diff that compares actual and expected values if
	 * they are too long. The threshold for "too long" is not specified.
	 *
	 * @param includeDiff {@code true} if exception messages should include a diff, {@code false} otherwise
	 * @return this
	 */
	ConfigurationUpdater includeDiff(boolean includeDiff);

	/**
	 * Returns the equality method that determines whether two values are equivalent.
	 *
	 * @return the equality method that determines whether two values are equivalent
	 */
	@CheckReturnValue
	EqualityMethod equalityMethod();

	/**
	 * Sets the equality method that determines whether two values are equivalent. The equality method is only
	 * used when both arguments are not null.
	 *
	 * @param equalityMethod the equality method to use
	 * @return this
	 */
	ConfigurationUpdater equalityMethod(EqualityMethod equalityMethod);

	/**
	 * Returns the configuration used to map contextual values to a String. Supports common types such as
	 * arrays, numbers, collections, maps, paths and exceptions.
	 *
	 * @return a function that takes an object and returns the {@code String} representation of the object
	 */
	@CheckReturnValue
	MutableStringMappers stringMappers();

	/**
	 * Returns {@code true} if exception creation may be deferred until the user invokes
	 * {@link Validator#elseGetException()}. The exception matches the original failure, but the stack trace
	 * points to {@code elseGetException()} as the cause. By deferring the exception creation, you can improve
	 * the performance if only need a {@link Validator#elseGetMessages() list of failure messages} instead of a
	 * full exception.
	 *
	 * @return {@code true} if exceptions may be created on demand instead of when a validation failure occurs
	 */
	boolean lazyExceptions();

	/**
	 * Specifies whether exception creation may be deferred until the user invokes
	 * {@link Validator#elseGetException()}.
	 *
	 * @param lazyExceptions {@code true} if exceptions may be created on demand instead of when a validation
	 *                       failure occurs
	 * @return this
	 */
	ConfigurationUpdater lazyExceptions(boolean lazyExceptions);

	/**
	 * Returns a function that transforms the validation exception into a suitable runtime exception or error.
	 * The input and output of the function must be subclasses of {@code RuntimeException} or {@code Error}. If
	 * the output is not, it is wrapped in a {@code WrappedCheckedException}. If the function returns
	 * {@code null} the input exception will be thrown.
	 *
	 * @return a function that transforms the validation exception
	 */
	@CheckReturnValue
	Function<Throwable, ? extends Throwable> exceptionTransformer();

	/**
	 * Transform the validation exception into a suitable runtime exception or error. The input and output of
	 * the function must be subclasses of {@code RuntimeException} or {@code Error}. If the output is not, it is
	 * wrapped in a {@code WrappedCheckedException}. If the function returns {@code null} the input exception
	 * will be thrown.
	 *
	 * @param transformer a function that transforms the validation exception
	 * @return this
	 * @throws NullPointerException if {@code transformer} is null
	 */
	ConfigurationUpdater exceptionTransformer(Function<Throwable, ? extends Throwable> transformer);

	@Override
	void close();
}