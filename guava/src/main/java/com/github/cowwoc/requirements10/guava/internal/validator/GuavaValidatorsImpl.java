package com.github.cowwoc.requirements10.guava.internal.validator;

import com.github.cowwoc.requirements10.guava.GuavaValidators;
import com.github.cowwoc.requirements10.guava.validator.MultimapValidator;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidators;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.cowwoc.requirements10.java.internal.validator.JavaValidatorsImpl.DEFAULT_NAME;

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
		return new MultimapValidatorImpl<>(scope, configuration, name, MaybeUndefined.defined(value),
			newValidatorContext(), newValidatorFailures());
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
	public GuavaValidators copy()
	{
		return new GuavaValidatorsImpl(this);
	}

	@Override
	public GuavaValidators withContext(Object value, String name)
	{
		context.put(name, value);
		return this;
	}

	@Override
	public GuavaValidators removeContext(String name)
	{
		context.remove(name);
		return this;
	}
}