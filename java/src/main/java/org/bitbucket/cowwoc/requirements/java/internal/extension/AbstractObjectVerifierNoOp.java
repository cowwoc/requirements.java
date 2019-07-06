/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.StringVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.StringVerifierNoOp;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * An {@code ExtensibleObjectVerifier} that does nothing.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractObjectVerifierNoOp<S, T> implements ExtensibleObjectVerifier<S, T>
{
	protected final Configuration config;

	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public AbstractObjectVerifierNoOp(Configuration config)
	{
		assert (config != null) : "config may not be null";
		this.config = config;
	}

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
		return new StringVerifierNoOp(config);
	}

	@Override
	public S asString(Consumer<StringVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return getThis();
	}

	@Override
	public Optional<T> getActual()
	{
		return Optional.empty();
	}
}
