/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.StringValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.StringValidatorNoOp;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * An {@code ExtensibleObjectValidator} that does nothing. A validator that ignores all subsequent
 * failures because they are guaranteed to fail and would add any value to the end-user. For example, an
 * attempt was made to dereference null or cast the value to an incompatible type.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public abstract class AbstractObjectValidatorNoOp<S, T> implements ExtensibleObjectValidator<S, T>
{
	protected final List<ValidationFailure> failures;

	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public AbstractObjectValidatorNoOp(List<ValidationFailure> failures)
	{
		assert (failures != null) : "failures may not be null";
		this.failures = failures;
	}

	/**
	 * @return this
	 */
	protected abstract S getThis();

	@Override
	public boolean isActualAvailable()
	{
		return false;
	}

	@Override
	public T getActual()
	{
		return null;
	}

	@Override
	public List<ValidationFailure> getFailures()
	{
		return failures;
	}

	@Override
	public S isEqualTo(Object expected)
	{
		return getThis();
	}

	@Override
	public S isEqualTo(Object expected, String name)
	{
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object other)
	{
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object other, String name)
	{
		return getThis();
	}

	@Override
	public S isSameObjectAs(Object expected, String name)
	{
		return getThis();
	}

	@Override
	public S isNotSameObjectAs(Object other, String name)
	{
		return getThis();
	}

	@Override
	public S isOneOf(Collection<? super T> collection)
	{
		return getThis();
	}

	@Override
	public S isNotOneOf(Collection<? super T> collection)
	{
		return getThis();
	}

	@Override
	public S isInstanceOf(Class<?> type)
	{
		return getThis();
	}

	@Override
	public S isNotInstanceOf(Class<?> type)
	{
		return getThis();
	}

	@Override
	public S isNull()
	{
		return getThis();
	}

	@Override
	public S isNotNull()
	{
		return getThis();
	}

	@Override
	public StringValidator asString()
	{
		return new StringValidatorNoOp(failures);
	}

	@Override
	public S asString(Consumer<StringValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return getThis();
	}
}
