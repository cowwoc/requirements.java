/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.jackson.internal.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.github.cowwoc.requirements.jackson.JsonNodeValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.AbstractObjectValidator;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;
import java.util.Map;

/**
 * @param <T> the type of the {@code JsonNode}
 */
public final class JsonNodeValidatorImpl<T extends JsonNode>
	extends AbstractObjectValidator<JsonNodeValidator<T>, T>
	implements JsonNodeValidator<T>
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if any of the mandatory arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public JsonNodeValidatorImpl(ApplicationScope scope, Configuration configuration, String name, T value,
		Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public JsonNodeValidator<T> property(String name)
	{
		validate(() ->
		{
			JsonNode child = value.get(name);
			if (child == null)
			{
				addFailure(new MessageBuilder(getScope(), this,
					getName() + " must contain a property named " + name + ".").
					putContext(value, "Actual").toString(), null, IllegalArgumentException::new);
			}
		});
		return this;
	}

	/**
	 * Checks if a past validation failure has occurred, or if the value is {@code null} before running a
	 * validation.
	 *
	 * @param validation validates the value
	 */
	private void validate(Runnable validation)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.isEmpty(getScope(), this, name, null, null, 0).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(getScope(), this, name).toString());
		}
		else
			validation.run();
	}

	@Override
	public JsonNodeValidator<NumericNode> isNumber()
	{
		validate(() ->
		{
			if (!value.isNumber())
			{
				addFailure(new MessageBuilder(getScope(), this,
						getName() + " must contain a number.").
						putContext(value, getName()).
						putContext(value.getNodeType(), "Type").toString(),
					null, IllegalArgumentException::new);
			}
		});
		@SuppressWarnings("unchecked")
		JsonNodeValidator<NumericNode> newType = (JsonNodeValidator<NumericNode>) this;
		return newType;
	}

	@Override
	public JsonNodeValidator<NumericNode> isIntegralNumber()
	{
		validate(() ->
		{
			if (!value.isIntegralNumber())
			{
				addFailure(new MessageBuilder(getScope(), this,
						getName() + " must contain an integral number.").
						putContext(value, getName()).
						putContext(value.getNodeType(), "Type").toString(),
					null, IllegalArgumentException::new);
			}
		});
		@SuppressWarnings("unchecked")
		JsonNodeValidator<NumericNode> newType = (JsonNodeValidator<NumericNode>) this;
		return newType;
	}

	@Override
	public JsonNodeValidator<NumericNode> isFloatingPointNumber()
	{
		validate(() ->
		{
			if (!value.isFloatingPointNumber())
			{
				addFailure(new MessageBuilder(getScope(), this,
						getName() + " must contain a float or double number.").
						putContext(value, getName()).
						putContext(value.getNodeType(), "Type").toString(),
					null, IllegalArgumentException::new);
			}
		});
		@SuppressWarnings("unchecked")
		JsonNodeValidator<NumericNode> newType = (JsonNodeValidator<NumericNode>) this;
		return newType;
	}

	@Override
	public JsonNodeValidator<BigIntegerNode> isBigInteger()
	{
		validate(() ->
		{
			if (!value.isBigInteger())
			{
				addFailure(new MessageBuilder(getScope(), this,
						getName() + " must contain a BigInteger.").
						putContext(value, getName()).
						putContext(value.getNodeType(), "Type").toString(),
					null, IllegalArgumentException::new);
			}
		});
		@SuppressWarnings("unchecked")
		JsonNodeValidator<BigIntegerNode> newType = (JsonNodeValidator<BigIntegerNode>) this;
		return newType;
	}

	@Override
	public JsonNodeValidator<DecimalNode> isBigDecimal()
	{
		validate(() ->
		{
			if (!value.isBigDecimal())
			{
				addFailure(new MessageBuilder(getScope(), this,
						getName() + " must contain a BigDecimal.").
						putContext(value, getName()).
						putContext(value.getNodeType(), "Type").toString(),
					null, IllegalArgumentException::new);
			}
		});
		@SuppressWarnings("unchecked")
		JsonNodeValidator<DecimalNode> newType = (JsonNodeValidator<DecimalNode>) this;
		return newType;
	}

	@Override
	public JsonNodeValidator<MissingNode> isMissing()
	{
		validate(() ->
		{
			if (!value.isMissingNode())
			{
				addFailure(new MessageBuilder(getScope(), this,
						getName() + " must reference a missing node.").
						putContext(value, getName()).
						putContext(value.getNodeType(), "Type").toString(),
					null, IllegalArgumentException::new);
			}
		});
		@SuppressWarnings("unchecked")
		JsonNodeValidator<MissingNode> newType = (JsonNodeValidator<MissingNode>) this;
		return newType;
	}

	@Override
	public JsonNodeValidator<ValueNode> isValue()
	{
		validate(() ->
		{
			if (!value.isValueNode())
			{
				addFailure(new MessageBuilder(getScope(), this,
						getName() + " must contain a binary value, boolean, null, number or string.").
						putContext(value, getName()).
						putContext(value.getNodeType(), "Type").toString(),
					null, IllegalArgumentException::new);
			}
		});
		@SuppressWarnings("unchecked")
		JsonNodeValidator<ValueNode> newType = (JsonNodeValidator<ValueNode>) this;
		return newType;
	}

	@Override
	public JsonNodeValidator<ContainerNode<?>> isContainer()
	{
		validate(() ->
		{
			if (!value.isContainerNode())
			{
				addFailure(new MessageBuilder(getScope(), this,
						getName() + " must contain an array or object.").
						putContext(value, getName()).
						putContext(value.getNodeType(), "Type").toString(),
					null, IllegalArgumentException::new);
			}
		});
		@SuppressWarnings("unchecked")
		JsonNodeValidator<ContainerNode<?>> newType = (JsonNodeValidator<ContainerNode<?>>) this;
		return newType;
	}

	@Override
	public JsonNodeValidator<ArrayNode> isArray()
	{
		validate(() ->
		{
			if (!value.isArray())
			{
				addFailure(new MessageBuilder(getScope(), this,
						getName() + " must contain an array.").
						putContext(value, getName()).
						putContext(value.getNodeType(), "Type").toString(),
					null, IllegalArgumentException::new);
			}
		});
		@SuppressWarnings("unchecked")
		JsonNodeValidator<ArrayNode> newType = (JsonNodeValidator<ArrayNode>) this;
		return newType;
	}

	@Override
	public JsonNodeValidator<ObjectNode> isObject()
	{
		validate(() ->
		{
			if (!value.isObject())
			{
				addFailure(new MessageBuilder(getScope(), this,
						getName() + " must contain an object.").
						putContext(value, getName()).
						putContext(value.getNodeType(), "Type").toString(),
					null, IllegalArgumentException::new);
			}
		});
		@SuppressWarnings("unchecked")
		JsonNodeValidator<ObjectNode> newType = (JsonNodeValidator<ObjectNode>) this;
		return newType;
	}

	@Override
	public JsonNodeValidator<TextNode> isString()
	{
		validate(() ->
		{
			if (!value.isTextual())
			{
				addFailure(new MessageBuilder(getScope(), this,
						getName() + " must contain a String.").
						putContext(value, getName()).
						putContext(value.getNodeType(), "Type").toString(),
					null, IllegalArgumentException::new);
			}
		});
		@SuppressWarnings("unchecked")
		JsonNodeValidator<TextNode> newType = (JsonNodeValidator<TextNode>) this;
		return newType;
	}
}