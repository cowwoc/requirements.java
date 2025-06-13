/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */

package com.github.cowwoc.requirements11.jackson.internal.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.cowwoc.requirements11.jackson.JacksonValidators;
import com.github.cowwoc.requirements11.jackson.validator.JsonNodeValidator;
import com.github.cowwoc.requirements11.java.ValidationFailure;
import com.github.cowwoc.requirements11.java.internal.Configuration;
import com.github.cowwoc.requirements11.java.internal.ConfigurationUpdater;
import com.github.cowwoc.requirements11.java.internal.MutableStringMappers;
import com.github.cowwoc.requirements11.java.internal.StringMapper;
import com.github.cowwoc.requirements11.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements11.java.internal.validator.AbstractValidators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static com.github.cowwoc.requirements11.java.internal.util.ValidationTarget.valid;
import static com.github.cowwoc.requirements11.java.internal.validator.JavaValidatorsImpl.DEFAULT_NAME;

public class JacksonValidatorsImpl extends AbstractValidators<JacksonValidators>
	implements JacksonValidators
{
	private static final StringMapper STRING_MAPPER = (value, seen) ->
	{
		JsonNode node = (JsonNode) value;
		return normalize(node).toPrettyString();
	};

	/**
	 * Creates a new instance of this validator with an independent configuration.
	 *
	 * @param scope         the application configuration
	 * @param configuration the configuration to use for new validators
	 */
	@SuppressWarnings("this-escape")
	public JacksonValidatorsImpl(ApplicationScope scope, Configuration configuration)
	{
		super(scope, configuration);
		// Suppress "this-escape" because this method is only invoked after the class is fully initialized
		try (ConfigurationUpdater config = updateConfiguration())
		{
			MutableStringMappers stringMappers = config.stringMappers();
			stringMappers.putIfAbsent(JsonNode.class, STRING_MAPPER);
		}
	}

	/**
	 * Normalizes the order of object keys and array elements to make them easier to diff.
	 *
	 * @param node a JSON node
	 * @return the updated node
	 * @throws NullPointerException if {@code node} is null
	 */
	public static JsonNode normalize(JsonNode node)
	{
		if (node.isObject())
		{
			ObjectNode objectNode = (ObjectNode) node;

			// Convert the ObjectNode into a TreeMap and sort the keys
			Map<String, JsonNode> sortedMap = new TreeMap<>();
			objectNode.fieldNames().forEachRemaining(field ->
				sortedMap.put(field, normalize(objectNode.get(field))));

			// Convert the TreeMap back into an ObjectNode
			ObjectNode sortedNode = objectNode.objectNode();
			sortedMap.forEach(sortedNode::set);
			return sortedNode;
		}
		return node;
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
	public <T extends JsonNode> JsonNodeValidator<T> assertThat(T value, String name)
	{
		return newInstance(value, name, getAssertThatConfiguration());
	}

	@Override
	public <T extends JsonNode> JsonNodeValidator<T> assertThat(T value)
	{
		return assertThat(value, DEFAULT_NAME);
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
		return new JsonNodeValidatorImpl<>(getScope(), configuration, name, valid(value), newValidatorContext(),
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
	public JacksonValidators copy()
	{
		return new JacksonValidatorsImpl(this);
	}

	@Override
	public JacksonValidators withContext(Object value, String name)
	{
		context.put(name, Optional.ofNullable(value));
		return this;
	}

	@Override
	public JacksonValidators removeContext(String name)
	{
		context.remove(name);
		return this;
	}
}