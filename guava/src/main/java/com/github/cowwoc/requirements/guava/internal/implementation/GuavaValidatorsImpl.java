package com.github.cowwoc.requirements.guava.internal.implementation;

import com.github.cowwoc.requirements.guava.GuavaValidators;
import com.github.cowwoc.requirements.guava.MultimapValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidator;
import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidators;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuavaValidatorsImpl extends AbstractValidators<GuavaValidators>
	implements GuavaValidators
{
	/**
	 * Creates a new instance of this validator with an independent configuration.
	 *
	 * @param scope         the application configuration
	 * @param configuration the configuration to use for new validators
	 * @throws NullPointerException if any of the arguments are null
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
	public <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> requireThat(T value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> assumeThat(T value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> assumeThat(T value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> checkIf(T value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> checkIf(T value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	private <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> newInstance(T value, String name,
		Configuration configuration)
	{
		return new MultimapValidatorImpl<>(scope, configuration, name, value, newValidatorContext(),
			newValidatorFailures());
	}

	private Map<String, Object> newValidatorContext()
	{
		Map<String, Object> context = HashMap.newHashMap(this.context.size() + 2);
		context.putAll(this.context);
		return context;
	}

	private List<ValidationFailure> newValidatorFailures()
	{
		return new ArrayList<>(2);
	}

	@Override
	public GuavaValidatorsImpl copy()
	{
		return new GuavaValidatorsImpl(this);
	}

	@Override
	public GuavaValidatorsImpl withContext(Object value, String name)
	{
		context.put(name, value);
		return this;
	}

	@Override
	public GuavaValidatorsImpl removeContext(String name)
	{
		context.remove(name);
		return this;
	}
}