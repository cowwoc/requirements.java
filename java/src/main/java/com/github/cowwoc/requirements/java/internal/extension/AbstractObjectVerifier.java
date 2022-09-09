/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.StringVerifier;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;
import com.github.cowwoc.requirements.java.internal.StringVerifierImpl;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Default implementation of {@code ExtensibleObjectVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <V> the type of validator used by the verifier
 * @param <T> the type of the value being verified
 */
public abstract class AbstractObjectVerifier<S, V extends ExtensibleObjectValidator<V, T>, T>
	implements ExtensibleObjectVerifier<S, T>
{
	protected final V validator;

	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	protected AbstractObjectVerifier(V validator)
	{
		assert (validator != null) : "validator may not be null";
		this.validator = validator;
	}

	/**
	 * @return this
	 */
	protected abstract S getThis();

	@Override
	public S isEqualTo(Object expected)
	{
		validator.isEqualTo(expected);
		return validationResult();
	}

	/**
	 * Equivalent to {@link #validationResult(Supplier) validationResult(this::getThis)} but with better
	 * runtime performance.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the validation failed
	 */
	protected S validationResult()
	{
		return validationResult(IllegalArgumentException.class);
	}

	/**
	 * Throws an exception if the validation failed. If the exception associated with the failure is a
	 * runtime exception or an exception of type {@code <E>} it is thrown as-is; otherwise, the exception is
	 * wrapped in an {@code AssertionError}.
	 *
	 * @param <E>       the type of exception that may be thrown
	 * @param exception the type of exception that may be thrown
	 * @return {@link #getThis()}
	 * @throws E if the validation failed
	 */
	protected <E extends Exception> S validationResult(Class<E> exception) throws E
	{
		assert (exception != null) : "exception may not be null";
		List<ValidationFailure> failures = validator.getFailures();
		if (failures.isEmpty())
			return getThis();
		return validationFailed(failures.get(0), exception);
	}

	/**
	 * Throws an exception if the validation failed.
	 *
	 * @param <R>    the type of value to return on success
	 * @param result the value to return on success
	 * @return the value returned by {@code result}
	 * @throws IllegalArgumentException if the validation failed
	 * @see #validationResult(Supplier, Class)
	 */
	protected <R> R validationResult(Supplier<R> result)
	{
		return validationResult(result, IllegalArgumentException.class);
	}

	/**
	 * Throws an exception if the validation failed. If the exception associated with the failure is a
	 * runtime exception or an exception of type {@code <E>} it is thrown as-is; otherwise, the exception is
	 * wrapped in an {@code AssertionError}.
	 *
	 * @param <R>       the type of value to return on success
	 * @param <E>       the type of exception that may be thrown
	 * @param result    the value to return on success
	 * @param exception the type of exception that may be thrown
	 * @return the value returned by {@code result}
	 * @throws E if the validation failed
	 */
	protected <R, E extends Exception> R validationResult(Supplier<R> result, Class<E> exception) throws E
	{
		assert (result != null) : "result may not be null";
		assert (exception != null) : "exception may not be null";
		List<ValidationFailure> failures = validator.getFailures();
		if (failures.isEmpty())
			return result.get();
		return validationFailed(failures.get(0), exception);
	}

	/**
	 * Throws an exception because the validation has failed. If the exception associated with the failure is a
	 * runtime exception or an exception of type {@code <E>} it is thrown as-is; otherwise, the exception is
	 * wrapped in an {@code AssertionError}.
	 *
	 * @param <R>       the type of value to return on success
	 * @param <E>       the type of exception that may be thrown
	 * @param exception the type of exception that may be thrown
	 * @return {@link #getThis()}
	 * @throws E if the validation failed
	 */
	private <R, E extends Exception> R validationFailed(ValidationFailure failure, Class<E> exception) throws E
	{
		Class<? extends Exception> actualExceptionType = failure.getExceptionType();
		// if "actualExceptionType" instanceof "exception"
		if (exception.isAssignableFrom(actualExceptionType))
			throw failure.createException(exception);
		// if "actualExceptionType" instanceof RuntimeException
		if (RuntimeException.class.isAssignableFrom(actualExceptionType))
		{
			@SuppressWarnings("unchecked")
			Class<? extends RuntimeException> actualRuntimeException = (Class<? extends RuntimeException>)
				failure.getExceptionType();
			throw failure.createException(actualRuntimeException);
		}
		throw new AssertionError("Unexpected exception type", failure.createException(actualExceptionType));
	}

	@Override
	public S isEqualTo(Object expected, String name)
	{
		validator.isEqualTo(expected, name);
		return validationResult();
	}

	@Override
	public S isNotEqualTo(Object other)
	{
		validator.isNotEqualTo(other);
		return validationResult();
	}

	@Override
	public S isNotEqualTo(Object other, String name)
	{
		validator.isNotEqualTo(other, name);
		return validationResult();
	}

	@Override
	public S isSameObjectAs(Object expected, String name)
	{
		validator.isSameObjectAs(expected, name);
		return validationResult();
	}

	@Override
	public S isNotSameObjectAs(Object other, String name)
	{
		validator.isNotSameObjectAs(other, name);
		return validationResult();
	}

	@Override
	public S isOneOf(Collection<? super T> collection)
	{
		validator.isOneOf(collection);
		return validationResult();
	}

	@Override
	public S isNotOneOf(Collection<? super T> collection)
	{
		validator.isNotOneOf(collection);
		return validationResult();
	}

	@Override
	public S isInstanceOf(Class<?> type)
	{
		validator.isInstanceOf(type);
		return validationResult();
	}

	@Override
	public S isNotInstanceOf(Class<?> type)
	{
		validator.isNotInstanceOf(type);
		return validationResult();
	}

	@Override
	public S isNull()
	{
		validator.isNull();
		return validationResult();
	}

	@Override
	public S isNotNull()
	{
		validator.isNotNull();
		return validationResult(NullPointerException.class);
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(validator.asString());
	}

	@Override
	public S asString(Consumer<StringVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asString());
		return getThis();
	}

	@Override
	public T getActual()
	{
		return validator.getActual();
	}
}
