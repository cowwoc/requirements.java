/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal;

import com.github.cowwoc.pouch.core.WrappedCheckedException;
import com.github.cowwoc.requirements10.annotation.CheckReturnValue;
import com.github.cowwoc.requirements10.java.ValidationFailures;

import java.util.function.Function;

/**
 * Updates the configuration that will be used by new validators.
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
	 * Returns {@code true} if exception messages may include a diff that compares actual and expected values.
	 *
	 * @return {@code true} by default
	 */
	@CheckReturnValue
	boolean allowDiff();

	/**
	 * Specifies whether exception messages may include a diff that compares actual and expected values.
	 *
	 * @param allowDiff {@code true} if exception messages may include a diff, {@code false} otherwise
	 * @return this
	 */
	ConfigurationUpdater allowDiff(boolean allowDiff);

	/**
	 * Returns the equality method that determines whether two values are equivalent.
	 *
	 * @return the equality method that determines whether two values are equivalent
	 */
	@CheckReturnValue
	EqualityMethod equalityMethod();

	/**
	 * Sets the equality method that determines whether two values are equivalent.
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
	 * Returns {@code true} if exception stack traces should reference the code that triggers a validation
	 * failure. When set to {@code false}, the exception type remains unchanged, but the stack trace location is
	 * undefined. Users who only plan to {@link ValidationFailures#getMessages() list of failure messages}
	 * instead of exceptions may experience a performance improvement if this value is set to {@code false}.
	 *
	 * @return {@code true} if exceptions must be recorded when a validation failure occurs
	 */
	boolean recordStacktrace();

	/**
	 * Specifies whether exception stack traces should reference the code that triggers a validation failure.
	 * When set to {@code false}, the exception type remains unchanged, but the stack trace location is
	 * undefined. Users who only plan to {@link ValidationFailures#getMessages() list of failure messages}
	 * instead of exceptions may experience a performance improvement if this value is set to {@code false}.
	 *
	 * @param recordStacktrace {@code true} if exceptions must be recorded when a validation failure occurs
	 * @return this
	 */
	ConfigurationUpdater recordStacktrace(boolean recordStacktrace);

	/**
	 * Returns a function that transforms the validation exception into a suitable runtime exception or error.
	 * The input and output of the function must be subclasses of {@code RuntimeException} or {@code Error}. If
	 * the output is not, it is wrapped in a {@link WrappedCheckedException}. If the function returns
	 * {@code null} the input exception will be thrown.
	 *
	 * @return a function that transforms the validation exception
	 */
	@CheckReturnValue
	Function<Throwable, ? extends Throwable> exceptionTransformer();

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
	 * @param transformer a function that transforms the validation exception
	 * @return this
	 * @throws NullPointerException if {@code transformer} is null
	 */
	ConfigurationUpdater exceptionTransformer(Function<Throwable, ? extends Throwable> transformer);

	/**
	 * Applies the changes to the configuration.
	 */
	@Override
	void close();
}