/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.jackson.internal.validator;

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
import com.github.cowwoc.requirements10.jackson.JsonNodeValidator;
import com.github.cowwoc.requirements10.jackson.internal.message.JsonNodeMessages;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractObjectValidator;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

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
	 * @param value         the value
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public JsonNodeValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<T> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public JsonNodeValidator<JsonNode> property(String name)
	{
		MaybeUndefined<JsonNode> newValue = value.nullToUndefined().mapDefined(value -> value.get(name));
		if (newValue.isUndefined())
		{
			addIllegalArgumentException(
				JsonNodeMessages.property(scope, this, value, name).toString());
		}
		return new JsonNodeValidatorImpl<>(scope, configuration, this.name + "." + name, newValue, context,
			failures);
	}

	@Override
	public JsonNodeValidator<NumericNode> isNumber()
	{
		isType(JsonNode::isNumber, "a number");
		return assumeExpectedType();
	}

	/**
	 * Validates the type of the value.
	 *
	 * @param predicate returns {@code true} if the value matches the expected type
	 * @param type      a description of the expected type (e.g. "a string")
	 */
	public void isType(Predicate<T> predicate, String type)
	{
		if (value.isNull())
			onNull();
		switch (value.test(predicate))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				JsonNodeMessages.isType(scope, this, value, type).toString());
		}
	}

	@Override
	public JsonNodeValidator<NumericNode> isIntegralNumber()
	{
		isType(JsonNode::isIntegralNumber, "an integral number");
		return assumeExpectedType();
	}

	@Override
	public JsonNodeValidator<NumericNode> isFloatingPointNumber()
	{
		isType(JsonNode::isFloatingPointNumber, "a float or double number");
		return assumeExpectedType();
	}

	@Override
	public JsonNodeValidator<BigIntegerNode> isBigInteger()
	{
		isType(JsonNode::isBigInteger, "a whole number");
		return assumeExpectedType();
	}

	@Override
	public JsonNodeValidator<DecimalNode> isBigDecimal()
	{
		isType(JsonNode::isBigDecimal, "a floating-point number");
		return assumeExpectedType();
	}

	@Override
	public JsonNodeValidator<MissingNode> isMissing()
	{
		isType(JsonNode::isMissingNode, "a missing node");
		return assumeExpectedType();
	}

	@Override
	public JsonNodeValidator<ValueNode> isValue()
	{
		isType(JsonNode::isValueNode, "a binary value, boolean, null, number or string");
		return assumeExpectedType();
	}

	@Override
	public JsonNodeValidator<ContainerNode<?>> isContainer()
	{
		isType(JsonNode::isContainerNode, "an array or object");
		return assumeExpectedType();
	}

	@Override
	public JsonNodeValidator<ArrayNode> isArray()
	{
		isType(JsonNode::isArray, "an array");
		return assumeExpectedType();
	}

	@Override
	public JsonNodeValidator<ObjectNode> isObject()
	{
		isType(JsonNode::isObject, "an object");
		return assumeExpectedType();
	}

	@Override
	public JsonNodeValidator<TextNode> isString()
	{
		isType(JsonNode::isTextual, "a String");
		return assumeExpectedType();
	}
}