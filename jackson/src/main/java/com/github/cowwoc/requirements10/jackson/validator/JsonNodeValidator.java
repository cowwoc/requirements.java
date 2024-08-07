/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.jackson.validator;

import com.fasterxml.jackson.core.TreeNode;
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
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

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
	 * @see TreeNode#isContainerNode()
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
	 * @see JsonNode#isTextual()
	 */
	JsonNodeValidator<TextNode> isString();
}