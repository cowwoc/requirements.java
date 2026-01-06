/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.jackson.validator;

import io.github.cowwoc.requirements13.java.validator.PrimitiveUnsignedIntegerValidator;
import io.github.cowwoc.requirements13.java.validator.component.ObjectComponent;
import io.github.cowwoc.requirements13.java.validator.component.ValidatorComponent;
import tools.jackson.core.TreeNode;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.BigIntegerNode;
import tools.jackson.databind.node.BooleanNode;
import tools.jackson.databind.node.ContainerNode;
import tools.jackson.databind.node.DecimalNode;
import tools.jackson.databind.node.MissingNode;
import tools.jackson.databind.node.NumericNode;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.node.StringNode;
import tools.jackson.databind.node.ValueNode;

/**
 * Validates the state of a JsonNode.
 *
 * @param <T> the type of the {@code JsonNode}
 */
public interface JsonNodeValidator<T extends JsonNode> extends
	ValidatorComponent<JsonNodeValidator<T>, T>,
	ObjectComponent<JsonNodeValidator<T>, T>
{
	/**
	 * Returns a validator for the node's property.
	 *
	 * @param name the name of a property
	 * @return a validator for the property
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if the node does not contain the property
	 */
	JsonNodeValidator<JsonNode> property(String name);

	/**
	 * Returns a validator over the node's {@link JsonNode#size() size}.
	 *
	 * @return a validator over the node's {@link JsonNode#size() size}
	 * @throws NullPointerException if the value is null
	 */
	PrimitiveUnsignedIntegerValidator size();

	/**
	 * Ensures that the node contains a {@code Number}.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the node does not contain a number
	 */
	JsonNodeValidator<NumericNode> isNumber();

	/**
	 * Ensures that the node contains a {@code byte}, {@code short}, {@code int} or {@code long}.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the node does not contain an integral number
	 */
	JsonNodeValidator<NumericNode> isIntegralNumber();

	/**
	 * Ensures that the node contains a {@code float} or {@code double}.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the node does not contain a decimal number
	 */
	JsonNodeValidator<NumericNode> isFloatingPointNumber();

	/**
	 * Ensures that the node contains a {@code BigInteger}.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the node does not contain a {@code BigInteger}
	 */
	JsonNodeValidator<BigIntegerNode> isBigInteger();

	/**
	 * Ensures that the node contains a {@code BigDecimal}.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the node does not contain a {@code BigDecimal}
	 */
	JsonNodeValidator<DecimalNode> isBigDecimal();

	/**
	 * Ensures that the value references a missing node.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value does not represent a missing node
	 * @see TreeNode#isMissingNode()
	 */
	JsonNodeValidator<MissingNode> isMissing();

	/**
	 * Ensures that the node contains a binary value, boolean, null, number or string.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a binary value, boolean, null, number or string
	 * @see TreeNode#isValueNode()
	 */
	JsonNodeValidator<ValueNode> isValue();

	/**
	 * Ensures that the node contains an array or object.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is a "missing" or a value node
	 * @see TreeNode#isContainer()
	 */
	JsonNodeValidator<ContainerNode<?>> isContainer();

	/**
	 * Ensures that the node contains an array.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not an array
	 * @see TreeNode#isArray()
	 */
	JsonNodeValidator<ArrayNode> isArray();

	/**
	 * Ensures that the node contains an object.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not an object
	 * @see TreeNode#isObject()
	 */
	JsonNodeValidator<ObjectNode> isObject();

	/**
	 * Ensures that the node contains a String.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a String
	 * @see JsonNode#isString()
	 */
	JsonNodeValidator<StringNode> isString();

	/**
	 * Ensures that the node contains a Boolean.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a Boolean
	 * @see JsonNode#isBoolean()
	 */
	JsonNodeValidator<BooleanNode> isBoolean();
}