/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.StringVerifier;
import com.github.cowwoc.requirements.java.internal.StringVerifierNoOp;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * An {@code ExtensibleObjectVerifier} that does nothing. A validator that ignores all subsequent
 * failures because they are guaranteed to fail and would add any value to the end-user. For example, an
 * attempt was made to dereference null or cast the value to an incompatible type.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value being verified
 */
public abstract class AbstractObjectVerifierNoOp<S, T> implements ExtensibleObjectVerifier<S, T>
{
	/**
	 * @return this
	 */
	protected abstract S getThis();

	@Override
	public S isEqualTo(Object value)
	{
		return getThis();
	}

	@Override
	public S isEqualTo(Object value, String name)
	{
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object value)
	{
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object value, String name)
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
	public StringVerifier asString()
	{
		return StringVerifierNoOp.getInstance();
	}

	@Override
	public S asString(Consumer<StringVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return getThis();
	}

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
}
