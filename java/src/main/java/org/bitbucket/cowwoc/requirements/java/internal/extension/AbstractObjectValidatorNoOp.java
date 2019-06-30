/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.StringValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.StringValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * An ExtensibleObjectValidator that does nothing.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractObjectValidatorNoOp<S, T> implements ExtensibleObjectValidator<S, T>
{
	protected final ApplicationScope scope;
	protected final Configuration config;
	protected final List<ValidationFailure> failures;

	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public AbstractObjectValidatorNoOp(ApplicationScope scope, Configuration config,
	                                   List<ValidationFailure> failures)
	{
		assert (scope != null) : "scope may not be null";
		assert (config != null) : "config may not be null";
		assert (failures != null) : "failures may not be null";
		this.scope = scope;
		this.config = config;
		this.failures = failures;
	}

	/**
	 * @return this
	 */
	protected S getThis()
	{
		@SuppressWarnings("unchecked")
		S result = (S) this;
		return result;
	}

	@Override
	public Optional<T> getActual()
	{
		return Optional.empty();
	}

	@Override
	public List<ValidationFailure> getFailures()
	{
		return Collections.unmodifiableList(failures);
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
		return new StringValidatorNoOp(scope, config, failures);
	}

	@Override
	public S asString(Consumer<StringValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return getThis();
	}
}
