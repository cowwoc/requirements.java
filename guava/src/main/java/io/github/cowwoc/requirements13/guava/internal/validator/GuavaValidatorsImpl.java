/*
 * Copyright (c) 2023 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */

package io.github.cowwoc.requirements13.guava.internal.validator;

import com.google.common.collect.Multimap;
import io.github.cowwoc.requirements13.guava.GuavaValidators;
import io.github.cowwoc.requirements13.guava.validator.MultimapValidator;
import io.github.cowwoc.requirements13.java.ValidationFailure;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.validator.AbstractValidators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.github.cowwoc.requirements13.java.internal.util.ValidationTarget.valid;
import static io.github.cowwoc.requirements13.java.internal.validator.JavaValidatorsImpl.DEFAULT_NAME;

public class GuavaValidatorsImpl extends AbstractValidators<GuavaValidators>
	implements GuavaValidators
{
	/**
	 * Creates a new instance of this validator with an independent configuration.
	 *
	 * @param scope         the application configuration
	 * @param configuration the configuration to use for new validators
	 * @throws NullPointerException if {@code scope} or {@code configuration} are null
	 */
	public GuavaValidatorsImpl(ApplicationScope scope, Configuration configuration)
	{
		super(scope, configuration);
	}

	/**
	 * Creates a copy of an existing validator factory.
	 *
	 * @param other the factory to copy
	 * @throws NullPointerException if {@code other} is null
	 */
	public GuavaValidatorsImpl(GuavaValidatorsImpl other)
	{
		this(other.scope, other.configuration());
		this.context.putAll(other.context);
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> requireThat(T value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> assertThat(T value, String name)
	{
		return newInstance(value, name, getAssertThatConfiguration());
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> assertThat(T value)
	{
		return assertThat(value, DEFAULT_NAME);
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> checkIf(T value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> checkIf(T value)
	{
		return checkIf(value, DEFAULT_NAME);
	}

	private <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> newInstance(T value, String name,
		Configuration configuration)
	{
		return new MultimapValidatorImpl<>(scope, configuration, name, valid(value), newValidatorContext(),
			newValidatorFailures());
	}

	private Map<String, Optional<Object>> newValidatorContext()
	{
		Map<String, Optional<Object>> context = HashMap.newHashMap(this.context.size() + 2);
		context.putAll(this.context);
		return context;
	}

	private List<ValidationFailure> newValidatorFailures()
	{
		return new ArrayList<>(2);
	}

	@Override
	public GuavaValidators copy()
	{
		return new GuavaValidatorsImpl(this);
	}

	@Override
	public GuavaValidators withContext(Object value, String name)
	{
		context.put(name, Optional.ofNullable(value));
		return this;
	}

	@Override
	public GuavaValidators removeContext(String name)
	{
		context.remove(name);
		return this;
	}
}