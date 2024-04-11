package com.github.cowwoc.requirements.jackson.internal.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.cowwoc.requirements.jackson.JacksonValidators;
import com.github.cowwoc.requirements.jackson.JsonNodeValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidator;
import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidators;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JacksonValidatorsImpl extends AbstractValidators<JacksonValidators>
	implements JacksonValidators
{
	/**
	 * Creates a new instance of this validator with an independent configuration.
	 *
	 * @param scope         the application configuration
	 * @param configuration the configuration to use for new validators
	 * @throws NullPointerException if any of the arguments are null
	 */
	public JacksonValidatorsImpl(ApplicationScope scope, Configuration configuration)
	{
		super(scope, configuration);
	}

	/**
	 * Creates a copy of an existing validator factory.
	 *
	 * @param other the factory to copy
	 * @throws NullPointerException if {@code other} is null
	 */
	public JacksonValidatorsImpl(JacksonValidatorsImpl other)
	{
		this(other.scope, other.configuration());
		this.context.putAll(other.context);
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> requireThat(T value, String name)
	{
		return newInstance(value, name, configuration());
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> assumeThat(T value, String name)
	{
		return newInstance(value, name, getAssumeThatConfiguration());
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> assumeThat(T value)
	{
		return assumeThat(value, AbstractValidator.DEFAULT_NAME);
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> checkIf(T value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> checkIf(T value)
	{
		return checkIf(value, AbstractValidator.DEFAULT_NAME);
	}

	private <T extends JsonNode> JsonNodeValidator<T> newInstance(T value, String name,
		Configuration configuration)
	{
		return new JsonNodeValidatorImpl<>(getScope(), configuration, name, value, newValidatorContext(),
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
	public JacksonValidatorsImpl copy()
	{
		return new JacksonValidatorsImpl(this);
	}

	@Override
	public JacksonValidatorsImpl putContext(Object value, String name)
	{
		context.put(name, value);
		return this;
	}

	@Override
	public JacksonValidatorsImpl removeContext(String name)
	{
		context.remove(name);
		return this;
	}
}