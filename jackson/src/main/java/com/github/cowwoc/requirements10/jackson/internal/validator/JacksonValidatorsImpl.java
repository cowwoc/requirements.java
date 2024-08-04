package com.github.cowwoc.requirements10.jackson.internal.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.cowwoc.requirements10.jackson.JacksonValidators;
import com.github.cowwoc.requirements10.jackson.JsonNodeValidator;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.cowwoc.requirements10.java.internal.validator.JavaValidatorsImpl.DEFAULT_NAME;

public class JacksonValidatorsImpl extends AbstractValidators<JacksonValidators>
	implements JacksonValidators
{
	/**
	 * Creates a new instance of this validator with an independent configuration.
	 *
	 * @param scope         the application configuration
	 * @param configuration the configuration to use for new validators
	 */
	public JacksonValidatorsImpl(ApplicationScope scope, Configuration configuration)
	{
		super(scope, configuration);
	}

	/**
	 * Creates a copy of an existing validator factory.
	 *
	 * @param other the factory to copy
	 * @throws NullPointerException if {@code other}, {@code other.scope} or {@code other.configuration()} are
	 *                              null
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
		return assumeThat(value, DEFAULT_NAME);
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> checkIf(T value, String name)
	{
		return newInstance(value, name, getCheckIfConfiguration());
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> checkIf(T value)
	{
		return checkIf(value, DEFAULT_NAME);
	}

	private <T extends JsonNode> JsonNodeValidator<T> newInstance(T value, String name,
		Configuration configuration)
	{
		return new JsonNodeValidatorImpl<>(getScope(), configuration, name, MaybeUndefined.defined(value),
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
	public JacksonValidators copy()
	{
		return new JacksonValidatorsImpl(this);
	}

	@Override
	public JacksonValidators withContext(Object value, String name)
	{
		context.put(name, value);
		return this;
	}

	@Override
	public JacksonValidators removeContext(String name)
	{
		context.remove(name);
		return this;
	}
}