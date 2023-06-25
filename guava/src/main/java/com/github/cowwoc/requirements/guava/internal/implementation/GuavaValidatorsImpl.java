package com.github.cowwoc.requirements.guava.internal.implementation;

import com.github.cowwoc.requirements.guava.GuavaValidators;
import com.github.cowwoc.requirements.guava.MultimapValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidator;
import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidators;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.google.common.collect.Multimap;

public class GuavaValidatorsImpl extends AbstractValidators<GuavaValidatorsImpl>
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

	@Override
	public <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> requireThat(T value, String name)
	{
		return newInstance(value, name, getRequireThatConfiguration());
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
		return new MultimapValidatorImpl<>(scope, configuration, name, value);
	}
}