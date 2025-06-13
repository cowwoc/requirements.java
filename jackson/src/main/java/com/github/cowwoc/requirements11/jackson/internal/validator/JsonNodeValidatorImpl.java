/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements11.jackson.internal.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.github.cowwoc.requirements11.jackson.internal.message.JsonNodeMessages;
import com.github.cowwoc.requirements11.jackson.validator.JsonNodeValidator;
import com.github.cowwoc.requirements11.java.ValidationFailure;
import com.github.cowwoc.requirements11.java.internal.Configuration;
import com.github.cowwoc.requirements11.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements11.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements11.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements11.java.internal.validator.AbstractObjectValidator;
import com.github.cowwoc.requirements11.java.internal.validator.ObjectSizeValidatorImpl;
import com.github.cowwoc.requirements11.java.validator.PrimitiveUnsignedIntegerValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
	 * @param value         the value being validated
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public JsonNodeValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<T> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public JsonNodeValidator<JsonNode> property(String name)
	{
		ValidationTarget<JsonNode> newValue = value.nullToInvalid().map(v -> v.get(name)).nullToInvalid();
		if (!newValue.isValid())
		{
			failOnNull();
			addIllegalArgumentException(
				JsonNodeMessages.property(this, name).toString());
		}
		return new JsonNodeValidatorImpl<>(scope, configuration, this.name + "." + name, newValue, context,
			failures);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator size()
	{
		failOnNull();
		return new ObjectSizeValidatorImpl(scope, configuration, this, name + ".size()",
			value.nullToInvalid().map(JsonNode::size), Pluralizer.ELEMENT, context, failures);
	}

	@Override
	public JsonNodeValidator<NumericNode> isNumber()
	{
		isType(JsonNode::isNumber, "a number");
		return self();
	}

	/**
	 * Validates the type of the value.
	 *
	 * @param predicate returns {@code true} if the value matches the expected type
	 * @param type      a description of the expected type (e.g. "a string")
	 */
	public void isType(Predicate<T> predicate, String type)
	{
		if (value.validationFailed(predicate))
		{
			failOnNull();
			addIllegalArgumentException(
				JsonNodeMessages.isType(this, type).toString());
		}
	}

	@Override
	public JsonNodeValidator<NumericNode> isIntegralNumber()
	{
		isType(JsonNode::isIntegralNumber, "an integral number");
		return self();
	}

	@Override
	public JsonNodeValidator<NumericNode> isFloatingPointNumber()
	{
		isType(JsonNode::isFloatingPointNumber, "a float or double number");
		return self();
	}

	@Override
	public JsonNodeValidator<BigIntegerNode> isBigInteger()
	{
		isType(JsonNode::isBigInteger, "a whole number");
		return self();
	}

	@Override
	public JsonNodeValidator<DecimalNode> isBigDecimal()
	{
		isType(JsonNode::isBigDecimal, "a floating-point number");
		return self();
	}

	@Override
	public JsonNodeValidator<MissingNode> isMissing()
	{
		isType(JsonNode::isMissingNode, "a missing node");
		return self();
	}

	@Override
	public JsonNodeValidator<ValueNode> isValue()
	{
		isType(JsonNode::isValueNode, "a binary value, boolean, null, number or string");
		return self();
	}

	@Override
	public JsonNodeValidator<ContainerNode<?>> isContainer()
	{
		isType(JsonNode::isContainerNode, "an array or object");
		return self();
	}

	@Override
	public JsonNodeValidator<ArrayNode> isArray()
	{
		isType(JsonNode::isArray, "an array");
		return self();
	}

	@Override
	public JsonNodeValidator<ObjectNode> isObject()
	{
		isType(JsonNode::isObject, "an object");
		return self();
	}

	@Override
	public JsonNodeValidator<TextNode> isString()
	{
		isType(JsonNode::isTextual, "a String");
		return self();
	}

	@Override
	public JsonNodeValidator<BooleanNode> isBoolean()
	{
		isType(JsonNode::isBoolean, "a Boolean");
		return self();
	}
}